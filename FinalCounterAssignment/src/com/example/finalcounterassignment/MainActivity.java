package com.example.finalcounterassignment;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;

/**
 * @author qyu4
 *
 */
public class MainActivity extends Activity {
	ProgressBar thebar ;
	int i = 0;
	@Override
	/*
	 * this activity is just for loading.
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		thebar = (ProgressBar) findViewById(R.id.progressBar2); 
		thebar.setMax(500);
		
		Thread timer = new Thread(){
			public void run(){
				try {
					//sleep(5000);
					while(i!=500)
					{   thebar.setProgress(i);
						sleep(10);
						i++;
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					Intent openOverView = new Intent("android.intent.action.OVERVIEWMANUE");
					startActivity(openOverView);
				}
			}
		};
		timer.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
