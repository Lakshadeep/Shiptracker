package com.shiptracker;

import java.text.DecimalFormat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class NearestPorts extends FragmentActivity implements LocationListener {
    private GoogleMap map;
    private LocationManager locationManager;
    private String provider;
    LatLng coordinate;
    Marker startPerc,closest1,closest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       

        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabledGPS = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean enabledWiFi = service
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // Check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to 
        // go to the settings
        if (!enabledGPS) {
            Toast.makeText(this, "GPS signal not found", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            Toast.makeText(this, "Selected Provider " + provider,
                    Toast.LENGTH_SHORT).show();
            onLocationChanged(location);
        } else {

            //do something
        }
       

    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 300, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
    	
    	
    
        double lat =  location.getLatitude();
        double lng = location.getLongitude();
        Toast.makeText(this, "Location " + lat+","+lng,
                Toast.LENGTH_LONG).show();
        LatLng coordinate = new LatLng(lat, lng);
        Toast.makeText(this, "Location " + coordinate.latitude+","+coordinate.longitude,
                Toast.LENGTH_LONG).show();
        if(map!=null){
        	map.clear();
        }
        //map.clear();
        GoogleMap map;
        if(startPerc!=null){
        	startPerc.remove();
        }
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        startPerc = map.addMarker(new MarkerOptions()
        .position(coordinate)
          .title("Current ship position")
          .snippet(lat+","+lng)
          .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 13));
        String names[]={"Port of Mumbai","Jawaharlal Nehru Port,Mumbai","Port of Chennai","Port of Cochin","Port of Haldia","Port of Tuticorin","Port of kandla","Port of Vishakahapatnam","Port of Paradip",
    			"Port of Mangalore","Port of Marmagoa","Goa Shipyard Limited","Cochin Shipyard Limited","Garden Reach Shipbuilders and Engineers,Kolkatta","Hindustan Shipyard Limited,Vishakhapatnam","Mazagon Dock Limited,Mumbai","Naval Dockyard,Mumbai","Bharti Shipyard Limited"};
    	String latitude[]={"18.5630","18.5700","13.0600","09.5800","22.0213","08.4530","23.0049","17.4136","20.1614","12.5537","15.41103","15.404865","9.9547826","22.572646","17.686815","19.07598","18.92627","9.95518"};
    	String longitude[]={"72.459","72.5700","80.1800","76.1400","88.0554","78.1121","70.1332","83.1722","86.4016","74.48422","73.803742","73.824856","76.29189","88.363895","83.218415","72.87765","72.83733","76.28198"};
        double distances[]={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        for(int i=0;i<18;i++){
        	Location locationA = new Location("Point A");

        	locationA.setLatitude(lat);
        	locationA.setLongitude(lng);
        	
        	double latn = 7.798079;
        	double longn = 77.29248;
        	Location neutral = new Location("Point N");
        	neutral.setLatitude(latn);
        	neutral.setLongitude(longn);
        	
     	    double latd = Double.parseDouble(latitude[i]);
     	    double longd = Double.parseDouble(longitude[i]);
     	    
     	   Location locationb = new Location("Point B");
     	   locationb.setLatitude(latd);
     	   locationb.setLatitude(longd);
     	    
        	if((lng>78 & longd <78) || (lng<78 & longd>78)){
        		
        		//double distance1 = locationA.distanceTo(neutral);
        		//double distance2 = locationb.distanceTo(neutral);
        		double distance1 = CalculationByDistance(lat, lng, latn, longn);
        		double distance2 = CalculationByDistance(latd, longd, latn, longn);
        		distances[i]=distance1+distance2;
        	}
        	else{
        		
        		distances[i]=CalculationByDistance(lat, lng, latd, longd);
        	}
        }
        	for(int k=0;k<12;k++){
                for(int j=0;j<k;j++){
                   if(distances[k]<distances[j])
                    {
                        double temp=distances[k]; //swap
                        distances[k]=distances[j];
                        distances[j]=temp;
                        
                        String tempn = names[k];
                        names[k]=names[j];
                        names[j]=tempn;
                        
                        String templat = latitude[k];
                        latitude[k]=latitude[j];
                        latitude[j]=templat;
                        
                        String templong = longitude[k];
                        longitude[k]=longitude[j];
                        longitude[j]=templong;
                    }


                }
        	}
                
                LatLng crd1 = new LatLng(Double.parseDouble(latitude[0]),Double.parseDouble(longitude[0]));
                LatLng crd2 = new LatLng(Double.parseDouble(latitude[1]),Double.parseDouble(longitude[1]));
                
                closest1 = map.addMarker(new MarkerOptions()
                .position(crd1)
                  .title("Closest port/shipyard-"+names[0])
                  .snippet(""+distances[0]+" kms away")
                  .icon(BitmapDescriptorFactory.fromResource(R.drawable.close1)));
                 closest2 = map.addMarker(new MarkerOptions()
                .position(crd2)
                  .title("Closest port/shipyard-"+names[1])
                  .snippet(""+distances[1]+" kms away")
                  .icon(BitmapDescriptorFactory.fromResource(R.drawable.close2)));
                
                
                


            
        	
        	
       
        
        
       
        
    }


    @ Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }
    void Delay(int Seconds){
        long Time = 0;
        Time = System.currentTimeMillis();
        while(System.currentTimeMillis() < Time+(Seconds*1000));
    }
    public double CalculationByDistance(double lat1,double lon1,double lat2,double lon2) {
        int Radius=6371;//radius of earth in Km         
        
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult= Radius*c;
        double km=valueResult/1;
        DecimalFormat newFormat = new DecimalFormat("####");
        Integer kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult%1000;
        Integer meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value",""+valueResult+"   KM  "+kmInDec+" Meter   "+meterInDec);

        return Radius * c;
     }
}

