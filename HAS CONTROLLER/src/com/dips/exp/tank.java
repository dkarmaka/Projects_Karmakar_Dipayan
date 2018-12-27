package com.dips.exp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class tank extends Activity{
	
	Button b1,b2,b3,b4;
	EditText t1;
	SharedPreferences sharedpf;
	Editor ed;
	ImageView i1,i2,i3;
	TextView s1;
	Handler h= new Handler();
	Drawable img[]= new Drawable[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tankc);
		
		Connections.send("HS");
		
		b1=(Button)findViewById(R.id.buttonT1);
		b2=(Button)findViewById(R.id.ButtonT2);
		b3=(Button)findViewById(R.id.ButtonT3);
		b4=(Button)findViewById(R.id.ButtonT4);
		t1=(EditText)findViewById(R.id.editTextT1);
		sharedpf=getSharedPreferences("data", MODE_PRIVATE);
        ed=sharedpf.edit();
        i1=(ImageView)findViewById(R.id.imageView1);
        i2=(ImageView)findViewById(R.id.imageView2);
        i3=(ImageView)findViewById(R.id.imageView3);
        s1=(TextView)findViewById(R.id.textViewT1);
		img[0]=getBaseContext().getResources().getDrawable(R.drawable.bulbauto);
		img[1]=getBaseContext().getResources().getDrawable(R.drawable.bulboff);
		img[2]=getBaseContext().getResources().getDrawable(R.drawable.bulbon);
        

        i1.requestLayout();
        i1.getLayoutParams().height=515;
		i2.requestLayout();
		i2.getLayoutParams().height=500;
		

	     h.postDelayed(new Runnable(){
	    	 public void run(){
	    		 
	    		 if(Connections.msgchk==1){
	    			 if(Connections.buffer[0]=='t' && Connections.buffer[1]>=0 && Connections.buffer[1]<=100){
	    					i2.requestLayout();
	    					i2.getLayoutParams().height=500-(5*Connections.buffer[1]);
	    					s1.setText(String.valueOf(Connections.buffer[1])+"%");
	    			 }
	    		 }
	    		 
	    		 i3.setImageDrawable(img[Connections.status[7]+1]);
	    		 
	    		 h.postDelayed(this, 100);
	    	 }
	     }, 100);
		
		
		
		
		b1.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
					Connections.send("HI");
			}
		});
		
		b2.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
					Connections.send("HJ");
			}
		});
		
		b3.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
					Connections.send("HL");
			}
		});
		
		t1.setText(String.valueOf(Connections.pow[8]));
		b4.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				
				Connections.pow[8]=Double.parseDouble(t1.getText().toString());
				t1.setText(String.valueOf(Connections.pow[8]));
				ed.putString("pow8", String.valueOf(Connections.pow[8])).apply();
			}
		});
		
		t1.setOnEditorActionListener(new OnEditorActionListener(){
			

			@Override
			public boolean onEditorAction(TextView v, int actionid, KeyEvent event) {
				// TODO Auto-generated method stub
				if(actionid==EditorInfo.IME_ACTION_DONE){
					b4.performClick();
					return true;
				}
				return false;
			}});
		
		t1.setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View v, int keycode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keycode==KeyEvent.KEYCODE_ENTER){
					b4.performClick();
					return true;
				}
				return false;
			}
			
		});
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		Connections.send("HD");
	}
	
	

}
