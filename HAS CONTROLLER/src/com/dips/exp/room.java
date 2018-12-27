package com.dips.exp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;

public class room extends Activity{
	
	Button b[]= new Button[4];
	Handler mh= new Handler();
	Drawable img[]= new Drawable[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.room);
		b[0]=(Button)findViewById(R.id.buttonF1);
		b[1]=(Button)findViewById(R.id.ButtonF2);
		b[2]=(Button)findViewById(R.id.ButtonF3);
		b[3]=(Button)findViewById(R.id.ButtonF4);
		img[0]=getBaseContext().getResources().getDrawable(R.drawable.bulbauto);
		img[1]=getBaseContext().getResources().getDrawable(R.drawable.bulboff);
		img[2]=getBaseContext().getResources().getDrawable(R.drawable.bulbon);
		
		
		b[0].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(Connections.status[0]!=0){
						Connections.send("AJ");
					}
				else{
						Connections.send("AI");
				}
			}
		});
		
		
		b[0].setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				Connections.appliance= 'A';
				Intent i1=new Intent(getBaseContext(),controls.class);
				startActivity(i1);
				return true;
			}
			
		});
		
		b[1].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(Connections.status[1]!=0){
						Connections.send("BJ");
					}
				else{
						Connections.send("BI");
				}}
		});
		
		
		b[1].setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				Connections.appliance= 'B';
				Intent i1=new Intent(getBaseContext(),controls.class);
				startActivity(i1);
				return true;
			}
			
		});
		
		b[2].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(Connections.status[2]!=0){
						Connections.send("CJ");
					}
				else{
						Connections.send("CI");
				}}
		});
		
		
		b[2].setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				Connections.appliance= 'C';
				Intent i1=new Intent(getBaseContext(),controls.class);
				startActivity(i1);
				return true;
			}
			
		});
		
		b[3].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(Connections.status[3]!=0){
						Connections.send("DJ");
					}
				else{
						Connections.send("DI");
				}}
		});
		
		
		b[3].setOnLongClickListener(new OnLongClickListener(){

			@Override
			public boolean onLongClick(View v) {
				Connections.appliance= 'D';
				Intent i1=new Intent(getBaseContext(),controls.class);
				startActivity(i1);
				return true;
			}
			
		});
		
		
		
		mh.postDelayed(new Runnable(){
	    	 public void run(){
	    		 
	    		 for(int i=0; i<4; i++){
	    			 b[i].setCompoundDrawablesWithIntrinsicBounds(img[Connections.status[i]+1], null, null,null);
	    		 }
	    		 mh.postDelayed(this, 500);
	    	 }
	     }, 500);
		
		
	}
	
	

}
