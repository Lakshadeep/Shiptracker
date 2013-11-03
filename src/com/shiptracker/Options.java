package com.shiptracker;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Options extends ListActivity{
	
	String classes[]={"PORTS","MAINACTIVITY","JOURNEY","NEARESTPORTS","SPEED","INSTRUCTIONS","CREDITS"};
	String display[]={"Set/Update starting Point","Find current location of Ship","Track your Journey","Find nearby Ports/Shipyards","Speed of Ship","Instructions","Credits"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 
		 setListAdapter(new ArrayAdapter<String>(Options.this, android.R.layout.simple_list_item_1, display));
	}
		
		 
		 protected void onListItemClick(ListView l, View v, int position, long id) {
				// TODO Auto-generated method stub
				super.onListItemClick(l, v, position, id);

				Intent i = new Intent("com.shiptracker."+classes[position]);
				startActivity(i);
					    
					
					
				
			
			}
		 
		
		 
		 
		 
	}
	


