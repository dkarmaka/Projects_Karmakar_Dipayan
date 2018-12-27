package com.dips.exp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSreader extends BroadcastReceiver{
	public void onReceive(Context context, Intent intent){
		Bundle myb= intent.getExtras();
		SmsMessage messages= null;
		String [] s= null;
		SharedPreferences sharedpf=context.getSharedPreferences("data", android.content.Context.MODE_PRIVATE);
		
		if(myb!=null && sharedpf.getBoolean("sms", false)){
			Object [] pdus= (Object[])myb.get("pdus");
			messages= SmsMessage.createFromPdu((byte[])pdus[0]);
			if(messages.getOriginatingAddress().equals(sharedpf.getString("phone", "")) || messages.getOriginatingAddress().equals("+91"+sharedpf.getString("phone", ""))){
			s=messages.getMessageBody().split(" ");
			if(s[0].equals("HAS") && s[1].equals("ALLOFF")){
	        	context.startService(new Intent(context.getApplicationContext(),Connections.class));
	        	Connections.a=1;
	        	while(!Connections.connection[0]){
	        		try {
	        			Thread.sleep(300);
	        		} catch (InterruptedException e) {
	        			// TODO Auto-generated catch block
	        			e.printStackTrace();
	        		}
	        	}
	        	Connections.send("X");
			}
			}
		}
	}
}
