package com.dips.androbotix;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class game extends Activity{
	
	static final int REQUEST_ENABLE_BT = 1;
	BluetoothAdapter bt=null;
	TextView dir,spd,lf;
	BluetoothSocket bs=null;
	OutputStream ops=null;
	private static final String t="arduino";
	private static final UUID id=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static String address= "20:14:04:10:12:89";
	Button f1,f2,f3,b1,b2,b3,cr,ar;
	Handler h= new Handler();
	int delay=1,c=0;
	MediaPlayer m2,m3;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		
		bt = BluetoothAdapter.getDefaultAdapter();
		
		dir=(TextView)findViewById(R.id.textView3);
		spd=(TextView)findViewById(R.id.textView2);
		lf=(TextView)findViewById(R.id.textView4);
		
		
		
		SensorManager sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        
        final float[] mValuesMagnet      = new float[3];
        final float[] mValuesAccel       = new float[3];
        final float[] mValuesOrientation = new float[3];
        final float[] mRotationMatrix    = new float[9];
        
        final SensorEventListener mEventListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            public void onSensorChanged(SensorEvent event) {
                // Handle the events for which we registered
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_ACCELEROMETER:
                        System.arraycopy(event.values, 0, mValuesAccel, 0, 3);
                        break;

                    case Sensor.TYPE_MAGNETIC_FIELD:
                        System.arraycopy(event.values, 0, mValuesMagnet, 0, 3);
                        break;
                }
            };
        };
        setListners(sensorManager, mEventListener);
        
        h.postDelayed(new Runnable(){
        	public void run(){
        		SensorManager.getRotationMatrix(mRotationMatrix, null, mValuesAccel, mValuesMagnet);
                SensorManager.getOrientation(mRotationMatrix, mValuesOrientation);
                final float y=mValuesOrientation[1];
                if(y<(-.3) && c!=1){
                	send("R");
                	lf.setText("RIGHT");
                	c=1;
                }
                else if(y>(.3) && c!=2){
                	send("L");
                	lf.setText("LEFT");
                	c=2;
                }
                else if(y>(-.3) && y<(.3) && c!=0){
                	send("F");
                	lf.setText("DIRECTION");
                	c=0;
                }
                h.postDelayed(this, delay);
        	}}, delay);
        
        cr=(Button)findViewById(R.id.button9);
    	cr.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action= event.getAction();
				if(action==MotionEvent.ACTION_DOWN){
					send("U");
					dir.setText("GEAR: CLOCKWISE");
				}
				if(action==MotionEvent.ACTION_UP){
					send("z");
					dir.setText("GEAR");
				}
				return false;
			}
		});

        ar=(Button)findViewById(R.id.button10);
    	ar.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action= event.getAction();
				if(action==MotionEvent.ACTION_DOWN){
					send("V");
					dir.setText("GEAR: ANTICLOCKWISE");
				}
				if(action==MotionEvent.ACTION_UP){
					send("z");
					dir.setText("GEAR");
				}
				return false;
			}
		});
		
        
		b1=(Button)findViewById(R.id.button5);
    	b1.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action= event.getAction();
				if(action==MotionEvent.ACTION_DOWN){
					send("a");
					dir.setText("GEAR: BACKWARD");
					spd.setText("SPEED: 1");
				}
				if(action==MotionEvent.ACTION_UP){
					send("z");
					spd.setText("SPEED: 0");
					dir.setText("GEAR");
				}
				return false;
			}
		});
    	
    	b2=(Button)findViewById(R.id.button4);
    	b2.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action= event.getAction();
				if(action==MotionEvent.ACTION_DOWN){
					send("b");
					dir.setText("GEAR: BACKWARD");
					spd.setText("SPEED: 2");
				}
				if(action==MotionEvent.ACTION_UP){
					send("z");
					spd.setText("SPEED: 0");
					dir.setText("GEAR");
				}
				return false;
			}
		});
		
    	b3=(Button)findViewById(R.id.button3);
    	b3.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action= event.getAction();
				if(action==MotionEvent.ACTION_DOWN){
					send("c");
					dir.setText("GEAR: BACKWARD");
					spd.setText("SPEED: 3");
				}
				if(action==MotionEvent.ACTION_UP){
					send("z");
					spd.setText("SPEED: 0");
					dir.setText("GEAR");
				}
				return false;
			}
		});
		
    	
		
		
    	f1=(Button)findViewById(R.id.button6);
    	f1.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action= event.getAction();
				if(action==MotionEvent.ACTION_DOWN){
					send("A");
					dir.setText("GEAR: FORWARD");
					spd.setText("SPEED: 1");
				}
				if(action==MotionEvent.ACTION_UP){
					send("z");
					spd.setText("SPEED: 0");
					dir.setText("GEAR");
				}
				return false;
			}
		});
    	
    	f2=(Button)findViewById(R.id.button7);
    	f2.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action= event.getAction();
				if(action==MotionEvent.ACTION_DOWN){
					send("B");
					dir.setText("GEAR: FORWARD");
					spd.setText("SPEED: 2");
				}
				if(action==MotionEvent.ACTION_UP){
					send("z");
					spd.setText("SPEED: 0");
					dir.setText("GEAR");
				}
				return false;
			}
		});
    	
    	f3=(Button)findViewById(R.id.button8);
    	f3.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				int action= event.getAction();
				if(action==MotionEvent.ACTION_DOWN){
					send("C");
					dir.setText("GEAR: FORWARD");
					spd.setText("SPEED: 3");
				}
				if(action==MotionEvent.ACTION_UP){
					send("z");
					spd.setText("SPEED: 0");
					dir.setText("GEAR");
				}
				return false;
			}
		});
    	
    	
    	
		
	}
	
	@SuppressLint("NewApi") public void onResume(){
		super.onResume();
		m2= MediaPlayer.create(getApplicationContext(), R.raw.engine);
		m3=MediaPlayer.create(getApplicationContext(), R.raw.game);
		m3.setLooping(true);
		m2.setNextMediaPlayer(m3);
		m2.start();
		Log.d(t, "attempting client connect");
		BluetoothDevice target= bt.getRemoteDevice(address);
		try { bs=target.createInsecureRfcommSocketToServiceRecord(id);}
		catch(IOException e){
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		bt.cancelDiscovery();
		Log.d(t, "connecting to remote");
		try { bs.connect();}
		catch(IOException e1){
			try {bs.close();}
		catch(IOException e2){
			Toast.makeText(getApplicationContext(), e2.getMessage(), Toast.LENGTH_SHORT).show();
		}}
		Log.d(t, "creating socket");
		try { ops= bs.getOutputStream();}
		catch(IOException e){
			Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
		}
	}
    
    public void onPause(){
		super.onPause();
		m2.stop();
		m3.stop();
		Log.d(t, "In pause");
		if(ops!=null){
			try {ops.flush();}
			catch(IOException e){
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
		try { bs.close();}
		catch(IOException e2){
			Toast.makeText(getApplicationContext(), e2.getMessage(), Toast.LENGTH_SHORT).show();

		}
	}

	public void send(String m){
		byte[] mb= m.getBytes();
		Log.d(t, "sending data" + m);
		try { ops.write(mb);}
		catch (IOException e){}
	}
	
	public void setListners(SensorManager sensorManager, SensorEventListener mEventListener)
    {
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
                SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), 
                SensorManager.SENSOR_DELAY_NORMAL);


    }

	
}
