package com.jessegreenough.greenexchange.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.jessegreenough.greenexchange.models.Currency;
import com.jessegreenough.greenexchange.models.User;
import com.jessegreenough.greenexchange.services.UserService;

@Controller
public class AppController {
	private static String apiKey = "2c0e56cb-0f17-44ed-822f-f91163512e62";
	
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String guestDashboard(
				Model model) {
		
		String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
	    List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
	    paratmers.add(new BasicNameValuePair("symbol","BTC,BNB,ETH,QNT,XRP,HBAR,DAG,XLM,ALGO,XDC"));
	    paratmers.add(new BasicNameValuePair("convert","USD"));

	    try {
	      ArrayList<Currency> result = makeAPICall(uri, paratmers);
	      model.addAttribute("result", result);
	    } catch (IOException e) {
	      System.out.println("Error: cannont access content - " + e.toString());
	    } catch (URISyntaxException e) {
	      System.out.println("Error: Invalid URL " + e.toString());
	    }
	    return "guestDisplay.jsp";
	  }
	
	@GetMapping("/dashboard")
	public String userDashboard(
				Model model,
				HttpSession session) {
		if (session.getAttribute("userId") == null) {
			return "redirect:/login";
		}
		session.removeAttribute("notEnough");
		
		String uri = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest";
	    List<NameValuePair> paratmers = new ArrayList<NameValuePair>();
	    paratmers.add(new BasicNameValuePair("symbol","BTC,BNB,ETH,QNT,XRP,HBAR,DAG,XLM,ALGO,XDC"));
	    paratmers.add(new BasicNameValuePair("convert","USD"));

	    try {
	      ArrayList<Currency> result = makeAPICall(uri, paratmers);
	      model.addAttribute("result", result);
	    } catch (IOException e) {
	      System.out.println("Error: cannont access content - " + e.toString());
	    } catch (URISyntaxException e) {
	      System.out.println("Error: Invalid URL " + e.toString());
	    }
	    User thisUser = userService.getUserInfo((Long)session.getAttribute("userId"));
	    model.addAttribute("thisUser", thisUser);
	    return "userDisplay.jsp";
	  }
	
	public static ArrayList<Currency> makeAPICall(String uri, List<NameValuePair> parameters)
		      throws URISyntaxException, IOException {
		    String response_content = "";

		    URIBuilder query = new URIBuilder(uri);
		    query.addParameters(parameters);

		    CloseableHttpClient client = HttpClients.createDefault();
		    HttpGet request = new HttpGet(query.build());

		    request.setHeader(HttpHeaders.ACCEPT, "application/json");
		    request.addHeader("X-CMC_PRO_API_KEY", apiKey);

		    CloseableHttpResponse response = client.execute(request);

		    try {
		      System.out.println(response.getStatusLine());
		      HttpEntity entity = response.getEntity();

		      if (entity != null) {
		      response_content = EntityUtils.toString(entity);
		      JSONObject result = new JSONObject(response_content); //Convert String to JSON Object
		      
	            ArrayList<Currency> tokenList = new ArrayList<Currency>();
	            JSONObject data = (JSONObject) result.get("data");
	            Set<String> keys = data.keySet();
	            
	             for(int i=0 ; i < keys.size() ; i++) {
	            	 Object[] finalKeys = keys.toArray();
	            	 JSONObject jObj = (JSONObject) data.get((String) finalKeys[i]);

	            	 
	            	 Double pChange = (Double) ((JSONObject) ((JSONObject) jObj.get("quote")).get("USD")).get("percent_change_24h");
	            	 BigDecimal bd = BigDecimal.valueOf(pChange);
	            	 bd = bd.setScale(2, RoundingMode.HALF_UP);
	            	 pChange = bd.doubleValue();
	            	 
	            	 DecimalFormat decimalFormat = new DecimalFormat("#.00");
	                 decimalFormat.setGroupingUsed(true);
	                 decimalFormat.setGroupingSize(3);
	                 
	            	 Double price = (Double) ((JSONObject) ((JSONObject) jObj.get("quote")).get("USD")).get("price");
	            	 BigDecimal bd1 = BigDecimal.valueOf(price);
	            	 bd1 = bd1.setScale(2, RoundingMode.HALF_UP);
	            	 price = bd1.doubleValue();
	            	 String price1 = "";
	            	 if (price < 1) {
	            		 price1 = decimalFormat.format(price);
	            		 price1 = "0"+price1;
	            	 } else {
	            		 price1 = decimalFormat.format(price);
	            	 }
	            	 
	            	 Double market = (Double) ((JSONObject) ((JSONObject) jObj.get("quote")).get("USD")).get("market_cap");
	            	 String market1 = decimalFormat.format(market);
	            	 
	            	 Currency c = new Currency((String) jObj.getString("symbol"), pChange, price1, market1); 
	            	 
	            	 tokenList.add(c);
	             }
	             
	             return tokenList;
		    }}
		    
		    finally {
		      response.close();
		    }

		    return null;
		  }
}
