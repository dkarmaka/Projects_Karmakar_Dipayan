package com.dips.exp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

	

		Button b1,b2,b3,b4;
	    static public BluetoothAdapter bt=null;
		Handler h= new Handler();
		
		
		@SuppressLint("NewApi") @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        

		     h.postDelayed(new Runnable(){
		    	 public void run(){
		    		 b2.setEnabled(Connections.connection[0]);
		    		 b3.setEnabled(Connections.connection[1]);
		    		 b4.setEnabled(Connections.connection[1]);
		    		 h.postDelayed(this, 100);
		    	 }
		     }, 100);
	        
	        

	        
	        bt=BluetoothAdapter.getDefaultAdapter();
	        if(bt==null){
	        	Toast.makeText(getApplicationContext(), "no Bluetooth", Toast.LENGTH_SHORT).show();
	        }
	        
	        b1=(Button)findViewById(R.id.buttonA1);
	        b1.setOnClickListener(new OnClickListener()
	        {public void onClick(View v)
	        { 
	        	startService(new Intent(getApplicationContext(),Connections.class));
	        	Connections.a=1;
	        	Drawable img=getBaseContext().getResources().getDrawable(R.drawable.ic_launcher1);
	        	b1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
	        }

	        });
	        
	        
	        

	        b4=(Button)findViewById(R.id.buttonA4);
			b4.setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					Intent i1=new Intent(getBaseContext(),smscontrol.class);
					startActivity(i1);
				}
			});
	        
	        
	        
	        b2=(Button)findViewById(R.id.buttonA2);
	        b2.setOnClickListener(new OnClickListener(){
	        	public void onClick(View v){
					Intent i1=new Intent(getBaseContext(),mainpage.class);
					startActivity(i1);
	        	}
	        });
	        
	        
	        b3=(Button)findViewById(R.id.ButtonA3);
	        b3.setOnClickListener(new OnClickListener()
	        {public void onClick(View v)
	        {
	        	Connections.a=-1;

	        	Drawable img=getBaseContext().getResources().getDrawable(R.drawable.ic_launcher11);
	        	b1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
	        	
	        }

			
	        });
	        
	        
	        
	        
	    }
		
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.action_settings) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);
	    }
	}
