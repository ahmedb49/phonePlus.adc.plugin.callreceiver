package io.adc.adccallreceiver;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Test extends Activity {
     Context ctx ;
     LinearLayout ly;
    WindowManager wm;
    TextView textView;
    Button yes;
    Button no;

    int lastAction;

    RelativeLayout rel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
      
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
                       // | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.setFinishOnTouchOutside(false);
        super.onCreate(savedInstanceState);

		Application app = getApplication();
		String package_name = app.getPackageName();
        Resources resources = app.getResources();
        setContentView(resources.getIdentifier("activity_test", "layout", package_name));
		
        //setContentView(R.layout.activity_test);

        yes=findViewById(resources.getIdentifier("yes", "id", package_name));
        no=findViewById(resources.getIdentifier("no", "id", package_name));

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackageManager pm = getPackageManager();
                Intent launchIntent = pm.getLaunchIntentForPackage("io.ionic.starter");
                finish();
                startActivity(launchIntent);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
       
      

        ViewGroup view = (ViewGroup)getWindow().getDecorView();
        view.setBackgroundColor(0xFF00FF00);
     

        IntentFilter filter = new IntentFilter();

        filter.addAction("com.hello.action");
        registerReceiver(receiver, filter);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
           
            finishAndRemoveTask();
        }


    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //wm.removeViewImmediate(ly);

        unregisterReceiver(receiver);
    }
}
