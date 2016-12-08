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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnString;
	
	}
	public int expectInteger(){
		int returnInt = 0;
		try {
			returnInt = Integer.parseInt(br.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnInt;
	}
	public int expectDay(){
		int returnInt = 0;
		try {
			returnInt = Integer.parseInt(br.readLine());
			if(!(returnInt>0 && returnInt <= 31)){
				System.out.println("Bitte Tag im Bereich von 1 und 31 angeben.");
				return this.expectDay();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnInt;
	}
	public int expectMonth(){
		int returnInt = 0;
		try {
			
			returnInt = Integer.parseInt(br.readLine());
			if(!(returnInt>0 && returnInt <= 12)){
				System.out.println("Bitte Monat im Bereich von 1 bis 12 angeben.");
				return this.expectDay();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnInt;
	}
	public int expectYear(){
		int returnInt = 0;
		try {
			String year = br.readLine();
			if(year.length() < 4){
				System.out.println("Bitte Jahr in 4 Stellen ausschreiben.");
				return this.expectDay();
			}
			returnInt = Integer.parseInt(year);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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