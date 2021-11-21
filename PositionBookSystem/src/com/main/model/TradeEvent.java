package com.main.model;

public class TradeEvent {

	private Integer id;
	private Event event;
	private String account;
	private String security;
	private Integer quantity;
	
	public TradeEvent(Integer id, Event event, String acc, String sec, Integer quantity) {
		this.id = id;
		this.event = event;
		this.account = acc;
		this.security = sec;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "[ id:" + id
				+ ", " + event
				+ ", " + account
				+ ", " + security
				+ ", " + quantity
				+ " ]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
