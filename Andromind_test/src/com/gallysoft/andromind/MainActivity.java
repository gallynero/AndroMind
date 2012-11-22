package com.gallysoft.andromind;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.gallysoft.*;

public class MainActivity extends Activity implements AndroMindListener  {
	
	
	BluetoothSocket cone;
	InputStream in;
int REQUEST_ENABLE_BT = 2;
BluetoothAdapter mBluetoothAdapter;
Set<BluetoothDevice> pairedDevices;
AndroMindLib dvblue;


ProgressBar bsignal;
ProgressBar bmeditation;
ProgressBar battention;
ProgressBar bdelta;
ProgressBar btheta;
ProgressBar blalpha;
ProgressBar bhalpha;
ProgressBar blbeta;
ProgressBar bhbeta;
ProgressBar blgamma;
ProgressBar bhgamma;

AndroMindListener esta;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
         mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            
        	Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;

        	
            
            
            
            
        	
        }
        
      
      bsignal = (ProgressBar) findViewById(R.id.psignal);
      bmeditation = (ProgressBar) findViewById(R.id.pmeditation);
      battention = (ProgressBar) findViewById(R.id.pattention);
      bdelta = (ProgressBar) findViewById(R.id.pdelta);
      btheta = (ProgressBar) findViewById(R.id.ptheta);
      blalpha = (ProgressBar) findViewById(R.id.plalpha);
      bhalpha = (ProgressBar) findViewById(R.id.phalpha);
      blbeta = (ProgressBar) findViewById(R.id.plbeta);
      bhbeta = (ProgressBar) findViewById(R.id.phbeta);
      blgamma = (ProgressBar) findViewById(R.id.plgamma);
      bhgamma = (ProgressBar) findViewById(R.id.phgamma);
        
     
        
      
      bsignal.setMax(200);
      
      
      bmeditation.setMax(100);
      
      
      battention.setMax(100);
      
     
      bdelta.setMax(1800);
      
      
      btheta.setMax(1800);
      
      
      blalpha.setMax(1800);
      
      
      bhalpha.setMax(1800);
      
   
      blbeta.setMax(1800);
      
     
      bhbeta.setMax(1800);
      
      
      blgamma.setMax(1800);
      
    
      bhgamma.setMax(1800);  
        
        
        esta = this;
    }
    
    
    protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
            	
            	this.conectar();
            	
            }else{
            	
            	Toast.makeText(this, "Bluetooth is disable", Toast.LENGTH_LONG).show();
            }
        }
    }
    
    
    private void conectar(){
    	Toast.makeText(this, "Bluetooth is enable", Toast.LENGTH_LONG).show();
    	
    	CharSequence[] items;
    	pairedDevices = mBluetoothAdapter.getBondedDevices();
    	// If there are paired devices
    	if (pairedDevices.size() > 0) {
    		
    		items = new CharSequence[pairedDevices.size()];
    		int i=0;
    	    for (BluetoothDevice device : pairedDevices) {
    	        // Add the name and address to an array adapter to show in a ListView
    	        items[i]=(device.getName() + "\n" + device.getAddress());
    	        i++;
    	    }
    	    
    	    
    	}else{
    		items = new CharSequence[1];
    		items[0]=("No found");
    	}
    	
    	
    	
    	

    	AlertDialog.Builder pbuilder = new AlertDialog.Builder(this);
    	pbuilder.setTitle("Select a device:");
    	pbuilder.setItems(items, new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int item) {
    	        
    	    	if(item<pairedDevices.size()){
    	    		if(mBluetoothAdapter.isDiscovering())
    	    		mBluetoothAdapter.cancelDiscovery();
    	    		BluetoothDevice tmp = (BluetoothDevice) pairedDevices.toArray()[item];
    	    		 dvblue = new AndroMindLib(tmp);
    	    		 dvblue.setNewCaptureListener(esta);
    	    		dvblue.start();
    	    	}else{
    	    		
    	    		
    	    	}

    	    	
    	    	
    	    }
    	});
    	
    	AlertDialog palert = pbuilder.create();
    	palert.show();
    	
    	
    	
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    protected void onDestroy(){
    	if(dvblue != null){
    	dvblue.cancel();
    	dvblue = null;
    	}
    	super.onDestroy();
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       
    	int id = item.getItemId();
    	if (id == R.id.menu_conectar) {
    		 if (!mBluetoothAdapter.isEnabled()) {
          	    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
          	   
  				
  				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
  				
          	}else{
          		
          		this.conectar();
          		
          	}
             
             return true;
    	} else if (id == R.id.menu_desconectar) {
    		
    		dvblue.cancel();
        	
        	
        	
        	
            return true;
    	} else {
    		return super.onOptionsItemSelected(item);
    	}
    	
    	
    	
        
    }


	@Override
	public void newCaptured(AndroMindLib andromind) {
		
		bsignal.setProgress((int) andromind.getSignal());
		bmeditation.setProgress((int) andromind.getMeditation());
	    battention.setProgress((int) andromind.getAttention());
	    bdelta.setProgress((int) andromind.getProdelta());
	    btheta.setProgress((int) andromind.getProtheta());
	    blalpha.setProgress((int) andromind.getProlalpha());
	    bhalpha.setProgress((int) andromind.getProhalpha());
	    blbeta.setProgress((int) andromind.getProlbeta());
	    bhbeta.setProgress((int) andromind.getProhbeta());
	    blgamma.setProgress((int) andromind.getProlgamma());
	    bhgamma.setProgress((int) andromind.getProhgamma()); 
	}
    
}
