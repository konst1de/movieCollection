package de.schule.media_collection.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Class to handle input and more important handle wrong input.
 * Possible Improvements: 
 * 	- special input to cancel / quit
 * @author konstantinvogel
 *
 */
public class InputScanner {
	BufferedReader br = null;

	public InputScanner(){
        br = new BufferedReader(new InputStreamReader(System.in));
	}
	/**
	 * Method which expects a string and handles exception with recursion
	 * @return String 
	 */
	public String expectString(){
		String returnString = null;
		try {
			returnString = br.readLine();
		} catch (IOException e) {
			System.out.println("The input was not valid. Please type in a String.");
			return this.expectString();
		}
		return returnString;
	}
	/**
	 * Method which just listens for an enter. 
	 * Used for showing lists and with enter the user can continue.
	 */
	public void expectEnter(){
		try {
			System.out.println("======== Press enter to continue. ========");
			br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Method which expects an integer and handles exceptions with recursion.
	 * @return Integer
	 */
	public int expectInteger(){
		int returnInt = 0;
		try {
			returnInt = Integer.parseInt(br.readLine());
		} catch (IOException e) {
			System.out.println("The input was not valid. Please type in an Integer.");
			return this.expectInteger();
		}
		return returnInt;
	}
	/**
	 * Method which expects a integer for an date. Also checking range from 1 to 31. 
	 * Handles exceptions or integers outside of this given range with recursion.
	 * @return Integer containing a day
	 */
	public int expectDay(){
		int returnInt = 0;
		try {
			returnInt = Integer.parseInt(br.readLine());
			if(!(returnInt>0 && returnInt <= 31)){
				System.out.println("Only a Integer between 1 and 31 is valid.");
				return this.expectDay();
			}
		} catch (IOException e) {
			System.out.println("The input was not valid. Please type in an Integer between 1 and 31.");
			return expectDay();
		}
		return returnInt;
	}
	/**
	 * Method which expects a integer for a month. Checking range from 1 to 12. 
	 * Handles exceptions or integers outside of this given range with recursion.
	 * @return Integer containing a month
	 */
	public int expectMonth(){
		int returnInt = 0;
		try {
			
			returnInt = Integer.parseInt(br.readLine());
			if(!(returnInt>0 && returnInt <= 12)){
				System.out.println("Only a Integer between 1 and 12 is valid.");
				return this.expectDay();
			}
		} catch (IOException e) {
			System.out.println("The input was not valid. Please type in an Integer between 1 and 12.");
			return this.expectDay();
		}
		return returnInt;
	}
	/**
	 * Method which expects a integer for a year which has to be exactly 4 characters long. 
	 * Handles exceptions or integers with less than 4 characters with recursion.
	 * @return Integer containing a year
	 */
	public int expectYear(){
		int returnInt = 0;
		try {
			String year = br.readLine();
			if(year.length() < 4){
				System.out.println("Please write the year in 4 numbers.");
				return this.expectDay();
			}
			returnInt = Integer.parseInt(year);
		} catch (IOException e) {
			System.out.println("Please write the year in 4 numbers.");
			return this.expectDay();
		}
		return returnInt;
	}
	/**
	 * Method to close the buffered reader.
	 */
	public void closeStream(){
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
