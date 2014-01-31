package com.example.finalcounterassignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author qyu4
 *
 */
public class ListOfAllCounters extends Activity {
	private ListView oldCounter;
	private static final String FILENAME = "file.sav";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_all_counters);
		oldCounter = (ListView) findViewById(R.id.oldCounter);
		oldCounter.setOnItemClickListener(new OnItemClickListener() {
	        @Override
	        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	                long arg3) {
                String[] listOfAllCounters = loadFromFileForString();
                String[] listOfAllCountersNumber = loadFromFileNumber();
                
                int indexOfLoadedCounter = arg2;
                String loadedString = listOfAllCounters[indexOfLoadedCounter];
                String loadedNumber = listOfAllCountersNumber[indexOfLoadedCounter];
                
            	Intent passCname = new Intent(ListOfAllCounters.this, EditCounter.class);
            	passCname.putExtra("text", loadedString + " " + loadedNumber);
            	startActivity(passCname);
	        }
		
		});
		oldCounter.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                    int index, long arg3) {
            	String[] allCounterToDelete = loadFromFile();
            	allCounterToDelete[index] = null;
            	if(allCounterToDelete.length == 1){
            		allCounterToDelete[0] = null;
            		saveInFileNoRecord();
            		Intent intent = new Intent("android.intent.action.OVERVIEWMANUE");
            		startActivity(intent);
            		

            	}else{
            	for(int i = index; i < allCounterToDelete.length-1; i++){
            		allCounterToDelete[i] = allCounterToDelete[i+1];
            	}
            	saveInFileClearRecord(allCounterToDelete[0]);
            	for(int i = 1; i < allCounterToDelete.length-1; i++){
            		saveInFile(allCounterToDelete[i]);
            	}
            		Intent intent = getIntent();
            		finish();
            		startActivity(intent);
            	}
            		return true;
            }
}); 

	}
    @Override
    protected void onStart() {
            super.onStart();
            String[] tweets = loadFromFile();
            if (tweets.length == 0){
            		Toast.makeText(ListOfAllCounters.this, "No counter record, please create a new one", Toast.LENGTH_SHORT).show();
            		Intent intent = new Intent("android.intent.action.OVERVIEWMANUE");
            		startActivity(intent);
            	}else{
            
            		for(int i = 0; i < tweets.length; i++){
            			for (int j = i; j< tweets.length; j++){
            				if (tweets[i].length() > tweets[j].length()){					//sort the list;
            					String temperyString = tweets[j];
            					tweets[j] = tweets[i];
            					tweets[i] = temperyString;
            				}
            			}
            		}
            			saveInFileClearRecord(tweets[0]);
            			for(int i= 1; i< tweets.length; i++){
            				saveInFile(tweets[i]);
            			}
            			for(int i = 0; i < tweets.length; i++){
            				
            				String[] breakDownMsg = tweets[i].split("\\|");
            				tweets[i] = breakDownMsg[0].toString() + "                      " + breakDownMsg[1].toString();
            			}

            			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                            R.layout.list_item, tweets);
            			oldCounter.setAdapter(adapter);
            		}
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
private String[] loadFromFileForString() {
    ArrayList<String> tweets = new ArrayList<String>();
    try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            while (line != null) {
            		String[] breakDownMsg = line.split("\\|");
              		tweets.add(breakDownMsg[0].toString());
                    line = in.readLine();
            }

    } catch (FileNotFoundException e) {
            e.printStackTrace();
    } catch (IOException e) {
            e.printStackTrace();
    }
    return tweets.toArray(new String[tweets.size()]);
}
private String[] loadFromFileNumber() {
    ArrayList<String> tweets = new ArrayList<String>();
    try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            while (line != null) {
            		String[] breakDownMsg = line.split("\\|");
              		tweets.add(breakDownMsg[1].toString());
              		//tweets.add(line);
                    line = in.readLine();
            }
    		
    } catch (FileNotFoundException e) {
            e.printStackTrace();
    } catch (IOException e) {
            e.printStackTrace();
    }
    return tweets.toArray(new String[tweets.size()]);
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.list_of_all_counters, menu);
		return true;
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
	private void saveInFileClearRecord(String text) {
	    try {
	            FileOutputStream fos = openFileOutput(FILENAME,
	                            Context.MODE_PRIVATE);
	            fos.write(new String(text + "\n")
	                            .getBytes());
	            fos.close();
	    } catch (FileNotFoundException e) {
	            e.printStackTrace();
	    } catch (IOException e) {
	            e.printStackTrace();
	    }
	}
	private void saveInFileNoRecord() {
	    try {
	            FileOutputStream fos = openFileOutput(FILENAME,
	                            Context.MODE_PRIVATE);
	            
	            fos.write(new String("")
	                            .getBytes());
	            fos.close();
	    } catch (FileNotFoundException e) {
	            e.printStackTrace();
	    } catch (IOException e) {
	            e.printStackTrace();
	    }
	}

}
