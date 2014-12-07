package com.example.helloandroid;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class HelloAndroid extends Activity implements SensorEventListener, LocationListener {
	private SensorManager sensorManager;
	private SensorManager sensorManager2;
	
	private LocationManager locationManager;
	private LocationListener locationListener;
	private String provider;

	TextView xCoor; // declare X axis object
	TextView yCoor; // declare Y axis object
	TextView zCoor; // declare Z axis object
	
	TextView xCoor2; // declare X axis object
	TextView yCoor2; // declare Y axis object
	TextView zCoor2; // declare Z axis object
	
	TextView testText;
	
	TextView timeSeconds; // declare a object to display seconds counter wile recording data
	EditText editText;
	
	private int nrOfSensorSamples;		// declare a counter that will hold the value of how many measure values we save.
	
	private float aX = 0, aY = 0, aZ = 0;	// declare accelerometer data (xyz)
	private float gX = 0, gY = 0, gZ = 0;	// declare gyrometer data (xyz)
	
	private ArrayList<SensorData> sensorData;	// declare ArrayList type SensorData (the array that will hold the sensor data)
	
	private boolean recordData;		//declare a true/false for recording data
	private String filename = "";	//declare a String to hold the different wanted names of the files.
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		this.sensorData = new ArrayList<SensorData>();		// Create ArrayList type SensorData
		
		this.nrOfSensorSamples = 0;
		this.recordData = false;		//set false because we don't want it to start recording it when application starts


		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_android );

		xCoor=(TextView)findViewById(R.id.xcoor); // create X axis object
		yCoor=(TextView)findViewById(R.id.ycoor); // create Y axis object
		zCoor=(TextView)findViewById(R.id.zcoor); // create Z axis object
		
		xCoor2=(TextView)findViewById(R.id.xcoor2); // create X axis object
		yCoor2=(TextView)findViewById(R.id.ycoor2); // create Y axis object
		zCoor2=(TextView)findViewById(R.id.zcoor2); // create Z axis object
		
		testText = (TextView)findViewById(R.id.testText);
		
		
		timeSeconds=(TextView)findViewById(R.id.timeSeconds);

		sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_NORMAL);
		
		sensorManager2=(SensorManager)getSystemService(SENSOR_SERVICE);
		// add listener. The listener will be HelloAndroid (this) class
		sensorManager2.registerListener(this,
				sensorManager2.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
				SensorManager.SENSOR_DELAY_NORMAL);
		
		// Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider -> use
	    // default
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);

	    // Initialize the location fields
	    if (location != null) {
	        System.out.println("Provider " + provider + " has been selected.");
	        int lat = (int) (location.getLatitude());
	        int lng = (int) (location.getLongitude());
	        testText.setText(String.valueOf(lat));
	        //longitudeField.setText(String.valueOf(lng));
	    } else {
	    	testText.setText("Provider not available");
	        //longitudeField.setText("Provider not available");
	    }
		
		/*locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		      //makeUseOfNewLocation(location);
		    	
		    	Location newLocation = location;
		    	testText.setText("lat: " + newLocation.getLatitude());
		    	newLocation.getLongitude();
		    	
		    }

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
		};
		
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);*/

		/*	More sensor speeds
		    SENSOR_DELAY_FASTEST	 0 microsecond delay 			 / get sensor data as fast as possible
		    SENSOR_DELAY_GAME		20,000 microsecond delay		 / rate suitable for games
		 	SENSOR_DELAY_NORMAL		200,000 microseconds delay		 / rate (default) suitable for screen orientation changes
		*/
	}

	public void onAccuracyChanged(Sensor sensor,int accuracy){

	}

	public void onSensorChanged(SensorEvent event) {
			
			// check sensor type
			if(event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION) {
	
				// assign directions
				aX=event.values[0];
				aY=event.values[1];
				aZ=event.values[2];
	
				xCoor.setText("X: "+aX);
				yCoor.setText("Y: "+aY);
				zCoor.setText("Z: "+aZ);
				
			}
			
			if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
	
				// assign directions
				gX=event.values[0];
				gY=event.values[1];
				gZ=event.values[2];

				xCoor2.setText("X: "+gX);
				yCoor2.setText("Y: "+gY);
				zCoor2.setText("Z: "+gZ);			
			}
			
			
			if(recordData)	//if recordData is true start recording data.
			{
				//Saving data
				sensorData.add(new SensorData(aX,aY,aZ,gX,gY,gZ,nrOfSensorSamples));		//Add the registered sensor data to the ArrayList<SensorData>
				timeSeconds.setText("Seconds recorded: " + sensorData.get(nrOfSensorSamples).toStringSeconds());				//Example print on Phone (to check if ok)
				nrOfSensorSamples++;	
			}
	}
	
	public void startRecording(View view) {
		this.recordData = true;
		//this.testText.setText("jgfgjg");
	}
	
	public void stoppRecording(View view) {
		this.recordData = false;
	}
	
	public void resetRecording(View view) {
		//You should not be able to reset if it's recording (only when it's stopped)
		if(this.recordData == false) {
			timeSeconds.setText("Seconds recorded: 0");
			this.sensorData.clear();
			this.nrOfSensorSamples = 0;
		}
	}
	
	public void saveRecording(View view) {	//You should not be able to save if it's recording (only when it's stopped)
		//Save the data stored in ArrayList
		
		
		if(this.recordData == false && isExternalStorageWritable())	{
			// **** Start saving ****
			editText = (EditText)findViewById(R.id.edit);
		    this.filename = editText.getText().toString() + ".txt";
			
			byte[] data = new String(storeDataToString()).getBytes();
			
			File sdCard = Environment.getExternalStorageDirectory();
			File dir = new File (sdCard.getAbsolutePath() + "/others/sensordata");	//all the collected data will be saved to SD-card/others/sensordata
			dir.mkdirs();					//if you want to change the directory that data should be saved to, simply change in the "/others/sensordata" to what ever you want.
			File file = new File(dir, this.filename);

			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
			} catch (FileNotFoundException e1) {
				// handle exception
				e1.printStackTrace();
			}

			try {
			    fos = new FileOutputStream(file);
			    fos.write(data);
			    fos.flush();
			    fos.close();
		
			} catch (IOException e) {
			    // handle exception
			}
			
			editText.setText("");
			editText.setHint("File Name");
		}
	}
	
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	public String storeDataToString(){
		
		String allData = "Seconds \t Accelerometer \t\t\t\t\t Gyrometer";
		
		for(int i = 0; i < this.nrOfSensorSamples ; i++) {
			allData = allData + " \r\n " + this.sensorData.get(i).toString();			
		}
		
		return allData;
	}

	public void onLocationChanged(Location location) {
		int lat = (int) (location.getLatitude());
	    int lng = (int) (location.getLongitude());
	    testText.setText(String.valueOf(lat));
		//testText.setText("latitute: " + loc.getLatitude());
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