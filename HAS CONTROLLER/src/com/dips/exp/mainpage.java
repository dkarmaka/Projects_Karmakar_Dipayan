package com.dips.exp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class mainpage extends Activity{

	Button b1,b2,b3,b4,b5,b6;
	TextView t1;
	Handler h= new Handler();
	Editor ed;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		
		ed=this.getSharedPreferences("data", MODE_PRIVATE).edit();
		b1=(Button)findViewById(R.id.buttonB1);
		b2=(Button)findViewById(R.id.ButtonB2);
		b3=(Button)findViewById(R.id.buttonB3);
		b4=(Button)findViewById(R.id.ButtonB4);
		b5=(Button)findViewById(R.id.buttonB5);
		b6=(Button)findViewById(R.id.buttonB6);
		t1=(TextView)findViewById(R.id.textViewB1);
		
		Connections.send("S");
		
		b1.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent i1=new Intent(getBaseContext(),tank.class);
				startActivity(i1);
			}
		});
		
		b2.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Connections.send("X");
			}
		});
		
		b3.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Connections.send("Y");
			}
		});
		
		b4.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent i1=new Intent(getBaseContext(),map.class);
				startActivity(i1);
			}
		});
		
		b5.setOnClickListener(new OnClickListener(){
			public void onClick(View v){

				Intent i1=new Intent(getBaseContext(),smscontrol.class);
				startActivity(i1);
			}
		});
		
		b6.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				ed.putString("energy", "0").apply();
				Connections.energy=0;
			}
		});
		
		

		h.postDelayed(new Runnable(){
	    	 public void run(){
		 t1.setText("ENERGY CONSUMED: " + String.valueOf(Connections.energy)+" Units");
		 h.postDelayed(this, 100);
	    	 }
	     }, 100);

	}
	
	

}
