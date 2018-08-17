package io.adc.adccallreceiver;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.TelephonyManager;


import org.json.JSONException;
import org.json.JSONArray;

import io.adc.adccallreceiver.Test;

public class AdcCallReceiver extends CordovaPlugin {


	
    public static  CallbackContext cbc;
  
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        
        AdcCallReceiver.cbc=callbackContext;
        return true;
    }
       

    public static class CallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, Intent intent) {
           
            String msg="";
			if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
                msg="RINGING"; 
               // sendResult(msg);
			   
				
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent trIntent = new Intent("android.intent.action.MAIN");
                    trIntent.setClass(context, Test.class);
                    trIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    trIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    Intent in = new Intent(context, Test.class);
                    context.startActivity(trIntent);


                }
            }, 2000);
            }
            else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                msg="OFFHOOK";
               // sendResult(msg);
			   Intent local = new Intent();
				local.setAction("com.hello.action");
				context.sendBroadcast(local);
                 
            }
            else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
                msg="IDLE";
				//sendResult(msg);
				Intent local = new Intent();
				local.setAction("com.hello.action");
				context.sendBroadcast(local);
            }
            
            
        }

		public void sendResult(String msg){
			PluginResult result = new PluginResult(PluginResult.Status.OK, msg);
			result.setKeepCallback(true);

            if (AdcCallReceiver.cbc!=null){
			AdcCallReceiver.cbc.sendPluginResult(result);
            }
            
        } 
            
            
	}

}   
   




