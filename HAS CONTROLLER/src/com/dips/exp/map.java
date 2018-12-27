package com.dips.exp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class map extends Activity{
	
	Button b1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
		
		b1=(Button)findViewById(R.id.buttonE1);
		b1.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				Intent i1=new Intent(getBaseContext(),room.class);
				startActivity(i1);
			}
		});
	}
	
	

}
