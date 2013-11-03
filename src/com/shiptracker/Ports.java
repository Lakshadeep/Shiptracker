package com.shiptracker;


import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Ports extends ListActivity{
	
	
	 String names[]={"Port of Mumbai","Jawaharlal Nehru Port,Mumbai","Port of Chennai","Port of Cochin","Port of Haldia","Port of Tuticorin","Port of kandla","Port of Vishakahapatnam","Port of Paradip",
 			"Port of Mangalore","Port of Marmagoa","Goa Shipyard Limited","Cochin Shipyard Limited","Garden Reach Shipbuilders and Engineers,Kolkatta","Hindustan Shipyard Limited,Vishakhapatnam","Mazagon Dock Limited,Mumbai","Naval Dockyard,Mumbai","Bharti Shipyard Limited"};
 	String latitude[]={"18.5630","18.5700","13.0600","09.5800","22.0213","08.4530","23.0049","17.4136","20.1614","12.5537","15.41103","15.404865","9.9547826","22.572646","17.686815","19.07598","18.92627","9.95518"};
 	String longitude[]={"72.459","72.5700","80.1800","76.1400","88.0554","78.1121","70.1332","83.1722","86.4016","74.48422","73.803742","73.824856","76.29189","88.363895","83.218415","72.87765","72.83733","76.28198"};
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 
		 setListAdapter(new ArrayAdapter<String>(Ports.this, android.R.layout.simple_list_item_1, names));
	}
		
		 
		 protected void onListItemClick(ListView l, View v, int position, long id) {
				// TODO Auto-generated method stub
				super.onListItemClick(l, v, position, id);

				Bundle basket = new Bundle();
				basket.putString("name", names[position]);
				basket.putString("latitude", latitude[position]);
				basket.putString("longitude", longitude[position]);
				
				Boolean diditwork = true;
	        	try {
	        		
	            	
	            	
	            	Db entry = new Db(Ports.this);
	            	entry.open();
	            	entry.createEntry(names[position],latitude[position],longitude[position]);
	            	entry.close();
					
				} catch (Exception e) {
					diditwork = false;
					// TODO: handle exception
				}finally{
					if (diditwork) {
						Dialog d = new Dialog(this);
						d.setTitle("Your Start Location was");
						TextView tv = new TextView(this);
						tv.setText("successfully saved");
						d.setContentView(tv);
						d.show();
					}
				}
	        	
	        	
	        	
				Intent i = new Intent("com.shiptracker.TRACKJOURNEY");
				i.putExtras(basket);
				startActivity(i);
					    
					
					
				
			
			}
		 
		
		 
		 
		 
	}
	


