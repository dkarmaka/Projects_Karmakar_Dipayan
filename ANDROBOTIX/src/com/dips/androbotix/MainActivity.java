package com.dips.androbotix;

import java.io.OutputStream;
import com.dips.androbotix.R;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	static final int REQUEST_ENABLE_BT = 1;
	BluetoothAdapter bt=null;
	TextView txt;
	BluetoothSocket bs=null;
	OutputStream ops=null;
	Button b1,b2;
	MediaPlayer mp;
	int l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mp= MediaPlayer.create(getApplicationContext(), R.raw.music);
        mp.setLooping(true);
        mp.start();
        
        bt = BluetoothAdapter.getDefaultAdapter();
        if(bt == null)
        {txt= (TextView)findViewById(R.id.textView1);
        txt.setText("Status:not supported");
        b1.setEnabled(false);
        }
        else{
        	b1=(Button)findViewById(R.id.button1);
        	b1.setOnClickListener(new OnClickListener()
        	{public void onClick(View v)
        	{on(v);}

			private void on(View v) {
				// TODO Auto-generated method stub
				if(!bt.isEnabled())
		        {
		        	Intent enablebt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					startActivityForResult(enablebt, REQUEST_ENABLE_BT);
					}
		        else
		        {
		        	txt= (TextView)findViewById(R.id.textView1);
		            txt.setText("Status: ENABLE");
		        	Toast.makeText(getApplicationContext(), "Bluetooth is Already ON", Toast.LENGTH_SHORT).show();
		        	b2.setEnabled(true);
		        }
		        
			}});
        	
        	b2=(Button)findViewById(R.id.button2);
        	b2.setOnClickListener(new OnClickListener()
        	{public void onClick(View v){
        		Intent i1= new Intent(getBaseContext(),game.class);
        		startActivity(i1);
        	}});
        	
        	
    }}
    
    public void onPause(){
    	super.onPause();
    	mp.pause();
    	l=mp.getCurrentPosition();
    }
    
    public void onResume(){
    	super.onResume();
    	mp.seekTo(l);
    	mp.start();
    }
    
        
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if(requestCode == REQUEST_ENABLE_BT)
		{if(bt.isEnabled())
		{Toast.makeText(getApplicationContext(), "Bluetooth Turned ON", Toast.LENGTH_SHORT).show();
		txt= (TextView)findViewById(R.id.textView1);
        txt.setText("Status: ENABLE");
        b2.setEnabled(true);
		}
		else { txt= (TextView)findViewById(R.id.textView1);
        txt.setText("Status: DISABLE");}}}
	


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
