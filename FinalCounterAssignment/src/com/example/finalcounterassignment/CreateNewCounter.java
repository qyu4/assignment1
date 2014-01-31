package com.example.finalcounterassignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author qyu4
 *
 */
public class CreateNewCounter extends Activity {

    private static final String FILENAME = "file.sav";
    private EditText bodyText;
    private ListView oldCounter;
    private Button saveButton;
	private Button back;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_new_counter);

            bodyText   = (EditText) findViewById(R.id.body);
            saveButton = (Button) findViewById(R.id.save);
            back = (Button) findViewById(R.id.BackToMain);
            oldCounter = (ListView) findViewById(R.id.OldCounters);
            saveButton.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                    	/*
                    	 * checking new counter name if it's empty or duplicate
                    	 */
                    	String text = bodyText.getText().toString();
                    	if (text.equals("")){
                    		Toast.makeText(CreateNewCounter.this, "counter name cannot be empty",Toast.LENGTH_SHORT).show();
                    	}else{
                            setResult(RESULT_OK);
                            String[] checkDuplicate = loadFromFile();
                            int checkDigit = 2;
                            
                            for (int i = 0; i < checkDuplicate.length; i++){                           
                            	if (checkDuplicate[i].equals(text)){
                            		checkDigit = 1;
                            	}

                            }
                            if (checkDigit == 1){
                            	Toast.makeText(CreateNewCounter.this, "Duplicate counter, please try again",Toast.LENGTH_SHORT).show();
                            }else{
                            	/*
                            	 * if everything is good, then pass the new name to the EditCounter Activity
                            	 */
                            	Intent passCname = new Intent(CreateNewCounter.this, EditCounter.class);
                            	passCname.putExtra("text", text + " "+ "0");
                            	startActivity(passCname);
                            	saveInFile(text, new Date(System.currentTimeMillis()));	
								
                            }


                    	}

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

    @Override
    protected void onStart() {
            // TODO Auto-generated method stub
            super.onStart();
            String[] counts = loadFromFile();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                            R.layout.list_item, counts);
            oldCounter.setAdapter(adapter);
    }

    private String[] loadFromFile() {
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
    
    private void saveInFile(String text, Date date) {
            try {
                    FileOutputStream fos = openFileOutput(FILENAME,
                                    Context.MODE_APPEND);
                    fos.write(new String(text + "|" + "0"+ "|" + date.toString() + "\n")
                                    .getBytes());
                    fos.close();
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
            } catch (IOException e) {
                    e.printStackTrace();
            }
    }


}
