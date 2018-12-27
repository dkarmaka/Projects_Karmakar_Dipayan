package com.dips.exp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class controls extends Activity{
	
	Button b[]= new Button[4];
	EditText t[]= new EditText[3];
	SharedPreferences sharedpf;
	Editor ed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bulb1);

		t[0]=(EditText)findViewById(R.id.editTextG1);
		t[1]=(EditText)findViewById(R.id.EditTextG2);
		t[2]=(EditText)findViewById(R.id.editTextG3);
		b[0]=(Button)findViewById(R.id.ButtonG1);
		b[1]=(Button)findViewById(R.id.ButtonG2);
		b[2]=(Button)findViewById(R.id.ButtonG3);
		b[3]=(Button)findViewById(R.id.buttonG4);
		sharedpf=getSharedPreferences("data", MODE_PRIVATE);
        ed=sharedpf.edit();
        

		
		
		b[0].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(!t[0].getText().toString().matches("")){
					Connections.send(Connections.appliance.toString()+"O"+t[0].getText()+"K");
				}
				else{
					Toast.makeText(getApplicationContext(), "Time Not Entered", Toast.LENGTH_SHORT).show();
				}
			}
		});

		b[1].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				
				if(!t[1].getText().toString().matches("")){
					Connections.send(Connections.appliance.toString()+"F"+t[1].getText()+"K");
				}
				else{
					Toast.makeText(getApplicationContext(), "Time Not Entered", Toast.LENGTH_SHORT).show();
				}
			}
		});

		t[2].setText(String.valueOf(Connections.pow[Connections.appliance-64]));
		b[2].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				if(!t[2].getText().toString().matches("")){
					Connections.pow[Connections.appliance-64]=Double.parseDouble(t[2].getText().toString());
				}
				else{
					Connections.pow[Connections.appliance-64]=0.0;
				}
				t[2].setText(String.valueOf(Connections.pow[Connections.appliance-64]));
				ed.putString("pow"+String.valueOf(Connections.appliance-64), String.valueOf(Connections.pow[Connections.appliance-64])).apply();
			
				
			}
		});
		
		b[3].setOnClickListener(new OnClickListener(){
			public void onClick(View v){
					Connections.send(Connections.appliance.toString()+"L");
			}
		});


		t[0].setOnEditorActionListener(new OnEditorActionListener(){
			
			@Override
			public boolean onEditorAction(TextView v, int actionid, KeyEvent event) {
				// TODO Auto-generated method stub
				if(actionid==EditorInfo.IME_ACTION_DONE){
					b[0].performClick();
					return true;
				}
				return false;
			}});
		
		t[1].setOnEditorActionListener(new OnEditorActionListener(){
			
			@Override
			public boolean onEditorAction(TextView v, int actionid, KeyEvent event) {
				// TODO Auto-generated method stub
				if(actionid==EditorInfo.IME_ACTION_DONE){
					b[1].performClick();
					return true;
				}
				return false;
			}});

		t[2].setOnEditorActionListener(new OnEditorActionListener(){
	
			@Override
			public boolean onEditorAction(TextView v, int actionid, KeyEvent event) {
				// TODO Auto-generated method stub
				if(actionid==EditorInfo.IME_ACTION_DONE){
					b[2].performClick();
					return true;
				}
				return false;
			}});
		
		
		
		
		t[0].setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View v, int keycode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keycode==KeyEvent.KEYCODE_ENTER){
					b[0].performClick();
					return true;
				}
				return false;
			}
			
		});
		
		
		t[1].setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View v, int keycode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keycode==KeyEvent.KEYCODE_ENTER){
					b[1].performClick();
					return true;
				}
				return false;
			}
			
		});
		
		
		t[2].setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View v, int keycode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keycode==KeyEvent.KEYCODE_ENTER){
					b[2].performClick();
					return true;
				}
				return false;
			}
			
		});
		
	}
	
	

}
