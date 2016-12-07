package de.schule.media_collection;

import java.sql.SQLException;

import de.schule.media_collection.view.Tui;
import de.schule.media_collection.view.View;

public class Main{
	
	public static void main(String[] args){
		try {
			Tui tui = new Tui();
			tui.menu();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		View.launch(View.class, args);
		
	}
}
