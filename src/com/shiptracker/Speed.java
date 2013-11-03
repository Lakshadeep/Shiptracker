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

public class Speed extends FragmentActivity implements LocationListener {
    private GoogleMap map;
    private LocationManager locationManager;
    private String provider;
    LatLng coordinate;
    Marker startPerc;
    double prevloc[]={0,0};
    double speed;

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
        // Define the criteria how to select the location provider -> use
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
        locationManager.requestLocationUpdates(provider, 10000, 1, this);
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
          .title("Your current location")
          .snippet("Speed "+ speed +" km/hr")
          .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher)));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 8));
        if(prevloc[0]==0){
        	prevloc[0]= lat;
        	prevloc[1]=lng;
        	
        }
        else{
        	double distance = CalculationByDistance(prevloc[0], prevloc[1], lat, lng);
        	speed = (distance*1000*18)/(10*5);
        	prevloc[0]=lat;
        	prevloc[1]=lng;
        	
        	
        	
        }
        
       
       
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
