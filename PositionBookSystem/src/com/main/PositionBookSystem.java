package com.main;

import java.util.Scanner;

import static com.main.constants.Contants.*;
import static com.main.util.PositionBookUtil.*;

/**
 * @author Ruban
 * Main class for PositionBook system
 */
public class PositionBookSystem {
	
	public static void main(String[] args) {
		
		//Get input
		Scanner scanner = new Scanner(System.in);
		provideInputSysout();
		
		//Until input is N get next trade line
		Boolean inputEnd = true;
		while (inputEnd) {
			
			String entry = scanner.nextLine();
			
			if(entry == null || entry.isBlank()) {
				System.out.println(ERROR_INPUT);
				provideInputSysout();
			} else if(checkEndInput(entry)) {
				inputEnd = false;
			} else {
				Boolean validTrade = validateTradeAndAddToBook(entry);
				if(validTrade) {
					provideInputSysout();
				} else {
					System.out.println(ERROR_INPUT);
					provideInputSysout();
				}
			}
		}
		
		//Print positionBook as output
		printPositionBook();
		
		scanner.close();
	}
}
