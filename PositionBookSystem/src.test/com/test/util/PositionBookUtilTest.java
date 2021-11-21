package com.test.util;

import org.junit.Assert;
import org.junit.Test;

import com.main.model.Event;
import com.main.model.TradeEvent;
import com.main.util.PositionBookUtil;

public class PositionBookUtilTest {

	@Test
	public void test_checkEndInput() {
		Assert.assertFalse(PositionBookUtil.checkEndInput("S"));
		Assert.assertTrue(PositionBookUtil.checkEndInput("N"));
	}
	
	@Test
	public void test_isSellEvent() {
		TradeEvent trade = getTradeEvent();
		Assert.assertFalse(PositionBookUtil.isSellEvent(trade));
		
		trade.setEvent(Event.SELL);
		Assert.assertTrue(PositionBookUtil.isSellEvent(trade));
	}
	
	@Test
	public void test_isBuyEvent() {
		TradeEvent trade = getTradeEvent();
		trade.setEvent(Event.SELL);
		Assert.assertFalse(PositionBookUtil.isBuyEvent(trade));
		
		trade.setEvent(Event.BUY);
		Assert.assertTrue(PositionBookUtil.isBuyEvent(trade));
	}
	
	@Test
	public void test_validateTradeAndAddToBook() {
		
		//Valid buy
		Assert.assertTrue(PositionBookUtil.validateTradeAndAddToBook("1 BUY ACC1 SEC1 100"));
		Assert.assertEquals(1, PositionBookUtil.positionBook.size());
		
		//Invalid acc
		Assert.assertFalse(PositionBookUtil.validateTradeAndAddToBook("1 BUY AC1 SEC1 100"));
		
		//Invalid length of input
		Assert.assertFalse(PositionBookUtil.validateTradeAndAddToBook("1 BUY AC1 SEC1"));
		
		//Invalid quantity
		Assert.assertFalse(PositionBookUtil.validateTradeAndAddToBook("1 BUY AC1 SEC1 ff"));
		
		//Invalid security
		Assert.assertFalse(PositionBookUtil.validateTradeAndAddToBook("1 BUY ACC1 SE1 100"));
		
		//Duplicate id for buy
		Assert.assertFalse(PositionBookUtil.validateTradeAndAddToBook("1 BUY ACC1 SEC1 100"));
		
		//Duplicate id for sell
		Assert.assertFalse(PositionBookUtil.validateTradeAndAddToBook("1 SELL ACC1 SEC1 100"));
		
		//Invalid sell as not buy exist
		Assert.assertFalse(PositionBookUtil.validateTradeAndAddToBook("1 SELL ACC2 SEC1 100"));
		
		//Invalid CANCEL as id dont exist
		Assert.assertFalse(PositionBookUtil.validateTradeAndAddToBook("2 CANCEL ACC1 SEC1 0"));
		
		Assert.assertEquals(1, PositionBookUtil.positionBook.size());
		
		//Valid Buy
		Assert.assertTrue(PositionBookUtil.validateTradeAndAddToBook("2 BUY ACC1 SEC1 100"));
		Assert.assertEquals(1, PositionBookUtil.positionBook.size());
		Assert.assertEquals(2, PositionBookUtil.positionBook.get("ACC1 SEC1").size());
		
		Assert.assertTrue(PositionBookUtil.validateTradeAndAddToBook("3 SELL ACC1 SEC1 10"));
		Assert.assertEquals(1, PositionBookUtil.positionBook.size());
		Assert.assertEquals(3, PositionBookUtil.positionBook.get("ACC1 SEC1").size());
		
		Assert.assertTrue(PositionBookUtil.validateTradeAndAddToBook("2 CANCEL ACC1 SEC1 0"));
		Assert.assertEquals(1, PositionBookUtil.positionBook.size());
		Assert.assertEquals(4, PositionBookUtil.positionBook.get("ACC1 SEC1").size());
	}
	
	@Test
	public void test_calculateOverallQuantity() {
		
		Assert.assertEquals("90", PositionBookUtil.calculateOverallQuantity("ACC1 SEC1").toString());
	}
	
	private TradeEvent getTradeEvent() {
		return new TradeEvent(1, Event.BUY, "ACC", "SEC", 1);
	}
}
