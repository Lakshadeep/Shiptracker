package com.shiptracker;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class TrackJourney extends FragmentActivity  {
    LatLng coordinate;
    Marker startPerc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackjourney);
        Bundle gotBasket = getIntent().getExtras();
        //if(gotBasket!=null){
     	   String lati,longi,name;
     	   double latd=18,longd=72;
     	   lati = gotBasket.getString("latitude");
     	   longi = gotBasket.getString("longitude");
     	   name = gotBasket.getString("name");
     	   
     	    latd = Double.parseDouble(lati);
     	    longd = Double.parseDouble(longi);
     	   LatLng coordinate = new LatLng(latd, longd);

        GoogleMap map;
        
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map1)).getMap();
        startPerc = map.addMarker(new MarkerOptions()
        .position(coordinate)
          .title("name")
          .snippet("Ship start Position")
          .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 8));
        
       
       

        

        

    //}

    
    	
       
       
    	    
    	   
    	    }
}
       
       
    


   


