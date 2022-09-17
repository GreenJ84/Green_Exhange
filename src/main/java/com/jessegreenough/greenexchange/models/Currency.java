package com.jessegreenough.greenexchange.models;

public class Currency {
	private Integer amountHeld;
	private String symbol;
	private Double percentChange;
	private String price;
	private String marketCap;
	
	
	public Currency() {
	}


	public Currency(String symbol, Double percentChange, String price, String market1) {
		this.symbol = symbol;
		this.percentChange = percentChange;
		this.price = price;
		this.marketCap = market1;
	}

	public Integer getAmountHeld() {
		return amountHeld;
	}


	public void setAmountHeld(Integer amountHeld) {
		this.amountHeld = amountHeld;
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}


	public Double getPercentChange() {
		return percentChange;
	}


	public void setPercentChange(Double percentChange) {
		this.percentChange = percentChange;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getMarketCap() {
		return marketCap;
	}


	public void setMarketCap(String marketCap) {
		this.marketCap = marketCap;
	}
	
	
	
}
