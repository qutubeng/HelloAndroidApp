package com.example.helloandroid;

public class SensorData {

	private float accDataX;
	private float accDataY;
	private float accDataZ;
	
	private float gyroDataX;
	private float gyroDataY;
	private float gyroDataZ;
	
	private double time;
	
	public SensorData(float accDataX, float accDataY, float accDataZ,
			float gyroDataX, float gyroDataY, float gyroDataZ, double time) {
		super();
		this.accDataX = accDataX;
		this.accDataY = accDataY;
		this.accDataZ = accDataZ;
		this.gyroDataX = gyroDataX;
		this.gyroDataY = gyroDataY;
		this.gyroDataZ = gyroDataZ;
		this.time = time / 5;			// We use time /5 to get the value to seconds (The sensor delay Normal mode records with 200 000 Microseconds / 0.2 seconds delay)
										// If you want to change the delay on sensor recorder in the .java file you have to change the /5 here in order to get the mesure to seconds.
	}
	
	public SensorData() {
		super();
		this.accDataX = 0;
		this.accDataY = 0;
		this.accDataZ = 0;
		this.gyroDataX = 0;
		this.gyroDataY = 0;
		this.gyroDataZ = 0;
		this.time = 0;
	}

	@Override
	public String toString() {	
				
		return "" + time + "\t X= " + accDataX + " Y= " + accDataY
				+ " Z= " + accDataZ + "\t X= " + gyroDataX
				+ " Y= " + gyroDataY + " Z= " + gyroDataZ;
	}
	public String toStringSeconds() {	
		
		return "" + time;
	}
	
}
