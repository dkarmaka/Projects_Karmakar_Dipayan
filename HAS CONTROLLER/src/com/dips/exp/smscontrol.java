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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class smscontrol extends Activity{
	
	EditText t1;
	Button b1;
	Switch s1;
	SharedPreferences sharedpf;
	Editor ed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smscontrol);
		
		t1=(EditText)findViewById(R.id.editTextS1);
		b1=(Button)findViewById(R.id.buttonS1);
		s1=(Switch)findViewById(R.id.switchS1);
		sharedpf=getSharedPreferences("data", MODE_PRIVATE);
        ed=sharedpf.edit();
        
		
		t1.setText(Connections.ph);
		s1.setChecked(sharedpf.getBoolean("sms", false));
		
		b1.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Connections.ph=t1.getText().toString();
				t1.setText(Connections.ph);
				ed.putString("phone", Connections.ph).apply();
				
			}
		});
		
		s1.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				ed.putBoolean("sms", isChecked).apply();
			}
		});
		
		t1.setOnEditorActionListener(new OnEditorActionListener(){
			

			@Override
			public boolean onEditorAction(TextView v, int actionid, KeyEvent event) {
				// TODO Auto-generated method stub
				if(actionid==EditorInfo.IME_ACTION_DONE){
					b1.performClick();
					return true;
				}
				return false;
			}});
		
		t1.setOnKeyListener(new OnKeyListener(){

			@Override
			public boolean onKey(View v, int keycode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keycode==KeyEvent.KEYCODE_ENTER){
					b1.performClick();
					return true;
				}
				return false;
			}
			
		});
		
	}
	

}
