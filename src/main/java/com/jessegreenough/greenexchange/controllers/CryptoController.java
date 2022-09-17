package com.jessegreenough.greenexchange.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jessegreenough.greenexchange.models.Crypto;
import com.jessegreenough.greenexchange.models.Currency;
import com.jessegreenough.greenexchange.models.User;
import com.jessegreenough.greenexchange.services.CryptoService;
import com.jessegreenough.greenexchange.services.UserService;

@Controller
public class CryptoController {

		@Autowired
		private UserService userService;
		
		@Autowired
		private CryptoService cryptoService;
		
		@GetMapping("/exchange")
		public String exchange(
					Model model,
					HttpSession session,
					RedirectAttributes flash) {
			if (session.getAttribute("userId") == null) {
				return "redirect:/login";
			}
			session.removeAttribute("overdraw");
			
			String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
		    List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
		    paratmers.add(new BasicNameValuePair("symbol","BTC,BNB,ETH,QNT,XRP,HBAR,DAG,XLM,ALGO,XDC"));
		    paratmers.add(new BasicNameValuePair("convert","USD"));

		    try {
		      ArrayList<Currency> result = AppController.makeAPICall(uri, paratmers);
		      model.addAttribute("result", result);
		    } catch (IOException e) {
		      System.out.println("Error: cannont access content - " + e.toString());
		    } catch (URISyntaxException e) {
		      System.out.println("Error: Invalid URL " + e.toString());
		    }
			
			User thisUser = userService.getUserInfo((Long)session.getAttribute("userId"));
		    model.addAttribute("thisUser", thisUser);
			return "dex.jsp";
		}
		
		@PostMapping("/exchange/trade")
		private String makeTrade(
				@RequestParam("symbol") String symbol,
				@RequestParam("purchaseAmount") Integer purchase,
				@RequestParam("price") String price,
				@RequestParam("estimate") Integer estimate,
				HttpSession session) {
			User thisUser = userService.getUserInfo((Long) session.getAttribute("userId"));
			
			Integer available = thisUser.getAvailableAccountUSD();
			if (available < purchase) {
				session.setAttribute("notEnough", "Dont have enough funds for the trade!");
				return "redirect:/exchange";
			}
			available -= purchase;
			thisUser.setAvailableAccountUSD(available);
			
			List<Crypto> thisWallet = thisUser.getWallet();
			Crypto thisOne = null;
			for (int i = 0; i < thisWallet.size(); i++) {
				Crypto these = thisWallet.get(i);
				if (these.getSymbol().equals(symbol)) {
					thisOne = thisWallet.get(i);
				}
			}
			
			if (thisOne == null) {
				Crypto newCrypto = new Crypto();
				Integer holding = newCrypto.getHoldings();
				holding += estimate;
				newCrypto.setHoldings(holding);
				newCrypto.setSymbol(symbol);
				newCrypto.setPurchaseAt(price);
				newCrypto.setUser(thisUser);
				cryptoService.createCrypto(newCrypto);
			} 
			else {
				Integer holding = thisOne.getHoldings();
				holding += estimate;
				thisOne.setHoldings(holding);
				thisOne.setSymbol(symbol);
				thisOne.setPurchaseAt(price);
				cryptoService.updateCrypto(thisOne);
			}
			userService.updateUser(thisUser);
			session.removeAttribute("notEnough");
			return "redirect:/portfolio";
		}
		
		@GetMapping("/portfolio")
		public String displayUserPortfolio(
					Model model,
					HttpSession session,
					RedirectAttributes flash) {
			if (session.getAttribute("userId") == null) {
				return "redirect:/login";
			}
			session.removeAttribute("overdraw");
			session.removeAttribute("notEnough");
			
			User thisUser = userService.getUserInfo((Long)session.getAttribute("userId"));
		    model.addAttribute("thisUser", thisUser);
		    
		    List<Crypto> thisWallet = thisUser.getWallet();
		    model.addAttribute("cryptos", thisWallet);
			return "portfolio.jsp";
		}
		
		
}
