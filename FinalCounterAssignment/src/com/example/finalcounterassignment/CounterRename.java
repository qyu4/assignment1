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
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author qyu4
 *
 */
public class CounterRename extends Activity {
	private EditText newName;
	private String getNewName = null;
	String cnameToChange = null;
	private String nameNoFromEdit= null;
	private String CnoToSave = null;
	private Button confirm;
	private Button cancel;
    private static final String FILENAME = "file.sav";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * 
		 * Declaring all the buttons, TextView, and Edit text in the layout file
		 * 
		 */
		setContentView(R.layout.activity_counter_rename);
		newName = (EditText) findViewById(R.id.newName);
		cancel = (Button) findViewById(R.id.BtoEdit);
		confirm=(Button) findViewById(R.id.Bconfirm);
		Intent getName= getIntent();
		nameNoFromEdit=(String)getName.getSerializableExtra("text");
		String[] nameNoFromEditList =nameNoFromEdit.split(" ");
		cnameToChange = nameNoFromEditList[0];
		CnoToSave = nameNoFromEditList[1];
		
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getNewName = newName.getText().toString();
				String[] loadToSave = loadFromFile();
				


				setResult(RESULT_OK);
				String[] checkDuplicate = loadFromFileForString();
				int checkDigit = 2;											//initialize checkDigit as 2 if it changes in the following loop then 
																			//the new name is duplicated
				for (int i = 0; i < checkDuplicate.length; i++){  
						
                    	if (checkDuplicate[i].equals(getNewName)){
                    		checkDigit = 1;
                    	}

                    }
				if (checkDigit == 1){
					if (cnameToChange.equals(getNewName)){
						Toast.makeText(CounterRename.this, "No change at all, please enter a new one", Toast.LENGTH_SHORT).show();
					}else if(cnameToChange != getNewName){
						Toast.makeText(CounterRename.this, "Duplicate counter name, please enter a new one", Toast.LENGTH_SHORT).show();
					}
				}else{
					/*
					 * if the new name is not duplicate
					 */
					for (int i = 0; i < loadToSave.length; i++){				
						String _findCounterName = loadToSave[i];
						String[] findCounterName = _findCounterName.split("\\|");
						String newCountString = "";
							
						if (findCounterName[0].equals(cnameToChange)){
							newCountString = getNewName+ "|" + CnoToSave;
							for (int j = 2; j < findCounterName.length; j++){
								newCountString +="|" + findCounterName[j];
									
							}
							List<String> list = new ArrayList<String>(Arrays.asList(loadToSave));
							list.remove(loadToSave[i]);
							loadToSave = list.toArray(new String[0]);
							saveInFileClearRecord(newCountString);
							for(int z = 0; z < loadToSave.length; z++){
								String toSave =loadToSave[z];
								saveInFile(toSave);
							}
							i = loadToSave.length;
						}
					
					}
					Intent passCname = new Intent(CounterRename.this, EditCounter.class);
					passCname.putExtra("text", getNewName + " " + CnoToSave);
					startActivity(passCname);
				}
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		
		
		
	}
	/*
	 * normal loadfile method
	 */
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
	/*
	 * normal savefile method
	 */
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
	/*
	 * savefile method with PRIVATE mode.
	 */
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
/*
 * loadfile method only for a string output for passing names between activity.
 */
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.counter_rename, menu);
		return true;
	}

}
