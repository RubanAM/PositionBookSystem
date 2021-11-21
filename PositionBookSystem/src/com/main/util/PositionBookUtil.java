package com.main.util;

import static com.main.constants.Contants.ACCOUNT_PREFIX;
import static com.main.constants.Contants.REQUEST_NEXT_INPUT;
import static com.main.constants.Contants.SECURITY_PREFIX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.main.exception.TradeException;
import com.main.model.Event;
import com.main.model.TradeEvent;

public class PositionBookUtil {
	
	//Map with key as ACC SEC
	//Value as list of all trades for those ACC SEC
	public static Map<String, List<TradeEvent>> positionBook = new HashMap<>();
	
	private PositionBookUtil() {}

	public static boolean checkEndInput(String entry) {
		return "N".equalsIgnoreCase(entry);
	}

	public static boolean isSellEvent(TradeEvent trade) {
		return Event.SELL.compareTo(trade.getEvent()) == 0;
	}

	public static boolean isBuyEvent(TradeEvent trade) {
		return Event.BUY.compareTo(trade.getEvent()) == 0;
	}
	
	public static void provideInputSysout() {
		System.out.println(REQUEST_NEXT_INPUT);
	}
	
	/**
	 * Method to get all trade events for given accAndSec and calculate its overall quantity
	 * @param accAndSec
	 * @return
	 */
	public static Integer calculateOverallQuantity(String accAndSec) {
		
		Integer overAllQuantity = 0;
		try {
			
			List<TradeEvent> listOfTrade = positionBook.get(accAndSec);
			
			for(TradeEvent trade : listOfTrade) {
				
				if(isBuyEvent(trade)) {
					overAllQuantity = overAllQuantity + trade.getQuantity();
				} else if (isSellEvent(trade)) {
					overAllQuantity = overAllQuantity - trade.getQuantity();
				} else {
					TradeEvent cancelledTrade = listOfTrade.stream()
							.filter(eachTrade -> Arrays.asList(Event.BUY, Event.SELL).contains(eachTrade.getEvent()))
							.filter(eachTrade -> eachTrade.getId().equals(trade.getId()))
							.findFirst()
							.orElseThrow(() -> new TradeException("ERROR: Matching BUY/SELL event not found for CANCEL id: " + trade.getId()));
					if(isBuyEvent(cancelledTrade)) {
						overAllQuantity = overAllQuantity - cancelledTrade.getQuantity();
					} else {
						overAllQuantity = overAllQuantity + cancelledTrade.getQuantity();
					}
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return overAllQuantity;
	}

	/**
	 * Validates input trade entry
	 * if valid then adds to positionBook map
	 * @param entry
	 * @return
	 */
	public static Boolean validateTradeAndAddToBook(String entry) {
		
		try {
			String[] inputLine = entry.split(" ");
			
			if(inputLine.length != 5) {
				return false;
			} else {
				Integer id = Integer.valueOf(inputLine[0]);
				Event trans = Event.valueOf(inputLine[1]);
				String acc = inputLine[2].toUpperCase();
				
				if(acc.startsWith(ACCOUNT_PREFIX)) {
					
					String sec = inputLine[3].toUpperCase();
					if(sec.startsWith(SECURITY_PREFIX)) {
						Integer quantity = Integer.valueOf(inputLine[4]);
						addToPositionBook(id, trans, acc, sec, quantity);
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
			
		} catch(TradeException e) {
			System.out.println("TradeException: " + e.getMessage());
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to add given trade details to map
	 * If given ACC SEC exist in map then add to it, else create new
	 * @param id
	 * @param trans
	 * @param acc
	 * @param sec
	 * @param quantity
	 */
	public static void addToPositionBook(Integer id, Event trans, String acc, String sec, Integer quantity) {
		
		String key = acc + " " + sec;
		
		List<TradeEvent> listOfCurrentTrade = new ArrayList<>();
		positionBook.values().forEach(list -> {
			listOfCurrentTrade.addAll(list);
		});
		
		boolean idExist = listOfCurrentTrade.stream().anyMatch(trade -> trade.getId().equals(id));
		if(Arrays.asList(Event.BUY, Event.SELL).contains(trans)) {
			if(idExist) {
				throw new TradeException("ID: " + id + " already exist");
			}
			if(Event.SELL.compareTo(trans) == 0) {
				if(!positionBook.containsKey(key)
						|| !positionBook.get(key).stream().anyMatch(trade -> isBuyEvent(trade))) {
					throw new TradeException("No BUY event exist for " + acc + " " + sec + ". So cant do SELL.");
				}
			}
		} else {
			//If CANCEL event, the id should exist already
			if(!idExist) {
				throw new TradeException("ID: " + id + " doesnt exist to CANCEL");
			}
		}
		
		if(positionBook.containsKey(key)) {
			positionBook.get(key).add(new TradeEvent(id, trans, acc, sec, quantity));
		} else {
			List<TradeEvent> tradeList = new LinkedList<>();
			tradeList.add(new TradeEvent(id, trans, acc, sec, quantity));
			positionBook.put(key, tradeList);
		}
	}
	
	public static void printPositionBook() {
		positionBook.forEach((accAndSec, tradeList) -> {
			System.out.println(accAndSec + " " + calculateOverallQuantity(accAndSec));
			tradeList.forEach((trade) -> System.out.println("			" + trade.toString()));
		});
	}
}
