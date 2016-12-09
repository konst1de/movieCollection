package de.schule.media_collection.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputScanner {
	BufferedReader br = null;

	public InputScanner(){
        br = new BufferedReader(new InputStreamReader(System.in));
	}
	
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
	public void closeStream(){
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
