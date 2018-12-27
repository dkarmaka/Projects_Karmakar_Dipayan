package com.dips.exp;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class Connections extends Service{
	
    public BluetoothAdapter bt=null;
    static BluetoothSocket bs=null;
    static OutputStream ops=null;
    static InputStream ips=null;
    private static final String t="arduino";
	private static final UUID id=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private static String address= "20:14:04:10:12:89";
	static DataInputStream di=null;
	Handler h= new Handler();
	int d=100;
	static int msgchk=0;
	static int k=0,n=0,a;
	static byte[] buffer= new byte[256];
	Service m_service;
	static Character appliance='#';
	static double[] pow= new double[10];
	static double energy;
	static String ph=null;
	static int[] status= new int[8];
	SharedPreferences sharedpf;
	Editor ed;
	static boolean connection[]=new boolean[2];
	
	
	public class MyBinder extends Binder{
		public Connections getService(){
			return Connections.this;
		}}
	
	public Connections(){
		
	}

	
	
	@Override
	public IBinder onBind(Intent intent1) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	public void onCreate() {

		Toast.makeText(getApplicationContext(), "created", Toast.LENGTH_SHORT).show();
		a=1;
		connection[0]=false;
		connection[1]=false;
	}

	
public int onStartCommand(Intent intent2, int flags, int startId){
		
	

    sharedpf=this.getSharedPreferences("data", MODE_PRIVATE);
    ed=sharedpf.edit();
    for(int i=0;i<10;i++){
    pow[i]=Double.parseDouble(sharedpf.getString("pow"+String.valueOf(i), "0.0"));
    }
    energy=Double.parseDouble(sharedpf.getString("energy", "0.0"));
    ph=sharedpf.getString("phone", null);
    
		Intent intent= new Intent(this,Connections.class);
		bindService(intent, msc,BIND_AUTO_CREATE);
		
		
		Intent i1=new Intent(this,MainActivity.class);
		i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pi=PendingIntent.getActivity(this, 0, i1, 0);
		NotificationCompat.Builder builder= new NotificationCompat.Builder(this);
		builder.setContentTitle("title").setContentText("content").setContentInfo("info").setWhen(System.currentTimeMillis()).setAutoCancel(false).setContentIntent(pi);
		Notification note= builder.build();
		startForeground(2337,note);

        bt=BluetoothAdapter.getDefaultAdapter();
        	
		
		Toast.makeText(getApplicationContext(), "started", Toast.LENGTH_SHORT).show();


	     h.postDelayed(new Runnable(){
	    	 public void run(){
	    		 
	    		 if(a==-1){
	    				disconnect();
	    				connection[0]=false;
	    				connection[1]=false;
	    				Toast.makeText(getApplicationContext(), "disconnected", Toast.LENGTH_SHORT).show();
	    				a=0;
	    		 }
	    		 
	    		 if(a==1){
	 	        	bt.enable();
			        while(!bt.isEnabled()){
			        	
			        }
	    		        connect();
	    		 }
	    		 
	    		 if(a>1){
	    			 a--;
	    		 }
	    		 
	    		 if(ips!=null){
		    		 try {
						n=di.available();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		 if(n>0){
		    			 read();
		    		 }
		    		 
		    		 }
	    		 

	    		 if(msgchk==1){
	    			 if(buffer[0]==112 && buffer[1]>96){
	    				 int p=0,i=2;
	    				 while(i<k){
	    					 p=p*10+(buffer[i]-48);
	    					 i++;
	    				 }
	    				 energy-=((p*pow[buffer[1]-96])/(3600000*1000));
	    				 ed.putString("energy", String.valueOf(energy)).apply();
	    				 msgchk=0;
	    			 }
	    			 
	    			 if(buffer[0]=='s' || buffer[0]=='l' || buffer[0]=='m'){
	    				 for(int i=0;i<k/2;i++){
	    					 if(buffer[2*i]=='s' && buffer[2*i+1]<8){
			    				 status[buffer[2*i+1]]=1;
	    					 }
	    					 if(buffer[2*i]=='l' && buffer[2*i+1]<8){
			    				 status[buffer[2*i+1]]=-1;
	    					 }
	    					 if(buffer[2*i]=='m' && buffer[2*i+1]<8){
			    				 status[buffer[2*i+1]]=0;
	    					 }
	    				 }
	    				 msgchk=0;
	    			 }
	    			 if(buffer[0]=='x'){
	    				 for(int i=0;i<8;i++){
	    					 status[i]=0;
	    				 }
	    				 msgchk=0;
	    			 }
	    			 if(buffer[0]=='y'){
	    				 for(int i=0;i<8;i++){
	    					 status[i]=-1;
	    				 }
	    				 msgchk=0;
	    			 }
	    		 }
	    		 
	    		 
	    		 h.postDelayed(this, d);
	    	 }
	     }, d);
	     
	     return START_STICKY;
	     
	    	 }


public void onDestroy(){

	Toast.makeText(getApplicationContext(), "destroyed", Toast.LENGTH_SHORT).show();
	disconnect();
	stopForeground(true);
	connection[0]=false;
	connection[1]=false;
}


private ServiceConnection msc= new ServiceConnection(){
	@Override
	public void onServiceConnected(ComponentName name, IBinder service) {
		// TODO Auto-generated method stub
		m_service= ((Connections.MyBinder)service).getService();
		
	}
	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stub
		m_service=null;
		
	}
};



public static void send(String m){
	byte[] mb= m.getBytes();
	Log.d(t, "sending data" + m);
	try { ops.write(mb);}
	catch (IOException e){}
	
	}


public static void read(){
		try{
			k=di.read(buffer);
			msgchk=1;
		}
		catch (IOException e){}
	}

/*
public static void statuschk(){
	

	try {
		Thread.sleep(300);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	if(ips!=null){
		 try {
			 n=di.available();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 if(n>0){
			 read();
		 }
		 
		 }
		 if(msgchk==1){
			 if(buffer[0]=='s' || buffer[0]=='l' || buffer[0]=='m'){
				 for(int j=0;j<k/2;j++){
					 if(buffer[2*j]=='s' && buffer[2*j+1]<8){
						 
						 status[buffer[2*j+1]]=1;
					 }
					 if(buffer[2*j]=='l' && buffer[2*j+1]<8){
						 
						 status[buffer[2*j+1]]=-1;
					 }
					 if(buffer[2*j]=='m' && buffer[2*j+1]<8){
						 
						 status[buffer[2*j+1]]=0;
					 }
				 }
				 msgchk=0;
			 }
		 }
}*/


public void connect() {
    a=0;
	BluetoothDevice target= bt.getRemoteDevice(address);
	try { bs=target.createRfcommSocketToServiceRecord(id);}
	catch(IOException e){
		Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
	}
	Log.d(t, "connecting to remote");
	try { bs.connect();}
	catch(IOException e1){
		try {bs.close();
		a=100;
		Toast.makeText(getApplicationContext(), "could not connect", Toast.LENGTH_SHORT).show();
		}
	catch(IOException e2){
		Toast.makeText(getApplicationContext(), e2.getMessage(), Toast.LENGTH_SHORT).show();
	}}
	try { 	ips=bs.getInputStream();
			di=new DataInputStream(ips);
	}
	catch(IOException e){
		Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
	}
	try{
		ops=bs.getOutputStream();
	}
	catch(IOException e){
		Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
	}
	if(a==0){
		connection[0]=true;
	}
	connection[1]=true;
	
}



private void disconnect() {
	try { bs.close();}
	catch(IOException e2){
		Toast.makeText(getApplicationContext(), e2.getMessage(), Toast.LENGTH_SHORT).show();

	}
	try {
		ops.flush();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		ips.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}

