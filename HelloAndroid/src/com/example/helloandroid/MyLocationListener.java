package com.example.helloandroid;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/*---------- Listener class to get coordinates ------------- */
public class MyLocationListener extends HelloAndroid implements LocationListener {

	public void onLocationChanged(Location loc) {
		testText.setText("latitute: " + loc.getLatitude());
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

}