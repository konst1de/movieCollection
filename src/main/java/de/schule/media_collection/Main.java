package de.schule.media_collection;

import java.sql.SQLException;

import de.schule.media_collection.view.Tui;
import de.schule.media_collection.view.View;

public class Main{
	
	public static void main(String[] args){
		boolean useSQL = args.length > 0 && "--use-sql".equals(args[0]);
		boolean useGUI = args.length > 1 && "--use-gui".equals(args[1]);
		
		if(useGUI){
			View.launch(View.class, args);
		}else{
			try {
				Tui tui = new Tui(useSQL);
				tui.menu();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
