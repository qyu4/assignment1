package com.example.finalcounterassignment;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * @author qyu4
 *
 */
public class OverViewManue extends Activity {
	
	Button createNewCounter;
	Button viewOldCounter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_over_view_manue);
		createNewCounter = (Button) findViewById(R.id.Bnew);

		viewOldCounter = (Button) findViewById(R.id.Bold);

        createNewCounter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent openMainActivity = new Intent("android.intent.action.CREATENEWCOUNTER");
				startActivity(openMainActivity);
				}
			});
		
		viewOldCounter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent openOldActivity = new Intent("android.intent.action.LISTOFALLCOUNTER");
				startActivity(openOldActivity);
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.over_view_manue, menu);
		return true;
	}

}
