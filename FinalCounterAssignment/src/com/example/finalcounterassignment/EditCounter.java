package com.example.finalcounterassignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * @author qyu4
 *
 */
public class EditCounter extends Activity {
    private static final String FILENAME = "file.sav";
	Button add;
	Button reset;
	Button back;
	Button rename;
	Button details;
	int count;
	TextView display;
	TextView counterName;
	String newName=null;
	String getNewCname = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_counter);
		add = (Button) findViewById(R.id.Badd);
		reset= (Button) findViewById(R.id.Breset);
		back = (Button) findViewById(R.id.Bback);
		details = (Button) findViewById(R.id.Bstat);
		rename = (Button) findViewById(R.id.Brename);
		display = (TextView) findViewById(R.id.textView1);
		counterName = (TextView) findViewById(R.id.Cname);

		Intent getName= getIntent();
		newName=(String)getName.getSerializableExtra("text");
		String[] getNewCnameList =newName.split(" ");
		getNewCname = getNewCnameList[0];
		int getNewCnumber = Integer.parseInt(getNewCnameList[1]);
		count = getNewCnumber;
		display.setText("total is " + count);
		counterName.setText(getNewCname.toString());
		
		add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count++;
				display.setText("total is " + count);
				String[] loadToSave = loadFromFile();
				for (int i = 0; i < loadToSave.length; i++){
					String _findCounterName = loadToSave[i];
					String[] findCounterName = _findCounterName.split("\\|");
					String newCountString = "";
					if (findCounterName[0].equals(getNewCname)){
						newCountString = findCounterName[0]+ "|" +count;
						for (int j = 2; j < findCounterName.length; j++){
							 newCountString +="|" + findCounterName[j];
						}
						List<String> list = new ArrayList<String>(Arrays.asList(loadToSave));
						list.remove(loadToSave[i]);
						loadToSave = list.toArray(new String[0]);
						saveInFileClearRecord(newCountString,new Date(System.currentTimeMillis()));
						for(int z = 0; z < loadToSave.length; z++){
							String toSave =loadToSave[z];
							saveInFile(toSave);
						}
						i = loadToSave.length;
						}
					
				}
				
			}
		});
		reset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count = 0;
				display.setText("total is " + count);
				String[] loadToSave = loadFromFile();
				for (int i = 0; i < loadToSave.length; i++){
					String _findCounterName = loadToSave[i];
					String[] findCounterName = _findCounterName.split("\\|");
					String newCountString = "";
					if (findCounterName[0].equals(getNewCname)){
						newCountString = findCounterName[0]+ "|" +count;
						List<String> list = new ArrayList<String>(Arrays.asList(loadToSave));
						list.remove(loadToSave[i]);
						loadToSave = list.toArray(new String[0]);
						saveInFileClearRecord(newCountString,new Date(System.currentTimeMillis()));
						for(int z = 0; z < loadToSave.length; z++){
							String toSave =loadToSave[z];
							saveInFile(toSave);
						}
						i = loadToSave.length;
						}
					
				}
				}
		});
		rename.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent passCname = new Intent(EditCounter.this, CounterRename.class);
				passCname.putExtra("text", getNewCname + " " + count);
				startActivity(passCname);
				
			}
		});
		details.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent passCname = new Intent(EditCounter.this, CounterDetail.class);
				passCname.putExtra("text", getNewCname);
				startActivity(passCname);				
			}
		});
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("android.intent.action.OVERVIEWMANUE");
				startActivity(intent);
				
			}
		});
	}
	
	
    private String[] loadFromFile() {
        ArrayList<String> tweets = new ArrayList<String>();
        try {
                FileInputStream fis = openFileInput(FILENAME);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                String line = in.readLine();
                while (line != null) {
                  		tweets.add(line);
                        line = in.readLine();
                }

        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
        return tweets.toArray(new String[tweets.size()]);
}

private void saveInFile(String text) {
        try {
                FileOutputStream fos = openFileOutput(FILENAME,
                                Context.MODE_APPEND);
                fos.write(new String(text + "\n")
                                .getBytes());
                fos.close();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        }
}
private void saveInFileClearRecord(String text, Date date) {
    try {
            FileOutputStream fos = openFileOutput(FILENAME,
                            Context.MODE_PRIVATE);
            fos.write(new String(text + "|" + date.toString() + "\n")
                            .getBytes());
            fos.close();
    } catch (FileNotFoundException e) {
            e.printStackTrace();
    } catch (IOException e) {
            e.printStackTrace();
    }
}


}
