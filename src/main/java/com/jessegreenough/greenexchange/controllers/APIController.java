package com.jessegreenough.greenexchange.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jessegreenough.greenexchange.models.Currency;

@RestController
public class APIController {

		
		@RequestMapping("/api")
		public ResponseEntity<List<Currency>> apiGetCurrencies(){
			String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
		    List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
		    paratmers.add(new BasicNameValuePair("symbol","BTC,BNB,ETH,QNT,XRP,HBAR,DAG,XLM,ALGO,XDC"));
		    paratmers.add(new BasicNameValuePair("convert","USD"));
		    
		    List<Currency> result = new ArrayList<Currency>();

		    try {
		      result = AppController.makeAPICall(uri, paratmers);
		    } catch (IOException e) {
		      System.out.println("Error: cannont access content - " + e.toString());
		    } catch (URISyntaxException e) {
		      System.out.println("Error: Invalid URL " + e.toString());
		    }
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		
}
