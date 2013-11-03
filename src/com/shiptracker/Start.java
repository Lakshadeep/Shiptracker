package com.shiptracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Start extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        Thread timer = new Thread(){
        	public void run(){
        		try{
                	sleep(3000);
                	
                }
        		catch (InterruptedException e) {
					// TODO: handle exception
        			e.printStackTrace();
				}finally{
					Intent i = new Intent("com.shiptracker.OPTIONS");
					startActivity(i);
				}
        	}
        	
        	};
        timer.start();
        
        
        
        
    }

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}