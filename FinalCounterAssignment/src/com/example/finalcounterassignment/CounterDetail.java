package com.example.finalcounterassignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

import org.joda.time.DateTime;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * @author qyu4
 *
 */
public class CounterDetail extends Activity {
    private static final String FILENAME = "file.sav";
    String nameNoFromEdit = "";
    ListView counterStatus;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_detail);
		String[] overAllSatats =  loadFromFile();		
		Intent getName= getIntent();
		SimpleDateFormat formatter;
		nameNoFromEdit=(String)getName.getSerializableExtra("text");
		String retrieveName = "";
		counterStatus = (ListView) findViewById(R.id.CounterStatus);
		
		

		String finalResult = "";
		ArrayList<String> monthResult = new ArrayList<String>();
		ArrayList<String> dayResult = new ArrayList<String>();
		ArrayList<String> hourResult = new ArrayList<String>();
		ArrayList<String> weekResult = new ArrayList<String>();
	
		/*
		 * This part is getting month status
		 */
		
		for (int i = 0; i < overAllSatats.length; i++){
			
			String retrieve = overAllSatats[i];
			String[] findName =	retrieve.split("\\|");
			retrieveName = findName[0];

			if (nameNoFromEdit.equals(retrieveName)){
				ArrayList<Date> dateArray = new ArrayList<Date>();
				ArrayList<Date> comparingDateArray = new ArrayList<Date>();
			    formatter = new SimpleDateFormat("EEEE MMM dd HH:mm:ss z yyyy");
				for (int j = 3; j < findName.length; j++){
					try {
						Date date = formatter.parse(findName[j]);
						
						dateArray.add(date);
						comparingDateArray.add(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				for(int k=0; k< dateArray.size(); k++){
					DateTime testDate = new DateTime(dateArray.get(k));
					ArrayList<String> monthName = new ArrayList<String>();
					ArrayList<String> monthNoDuplicate = new ArrayList<String>();
					int yearDefault = testDate.year().get();
					for(int l=0; l <comparingDateArray.size();l++){
						DateTime compDate = new DateTime(dateArray.get(l));
						int year = compDate.year().get();
						
						if(year == yearDefault){
							monthName.add("Month Status: "+year+" " + compDate.monthOfYear().getAsText());
						}
					}
					HashSet<String> hs = new HashSet<String>();
					hs.addAll(monthName);
					monthNoDuplicate.addAll(hs);
					
					for(int y = 0; y < monthNoDuplicate.size(); y++){
						monthResult.add(monthNoDuplicate.get(y).toString()+ ": " +  Collections.frequency(monthName, monthNoDuplicate.get(y).toString())+"\n"+"|");
					}
				}
			}
		}
		HashSet<String> hsMonth = new HashSet<String>();
		hsMonth.addAll(monthResult);
		monthResult.clear();
		monthResult.addAll(hsMonth);

		for(int i = 0; i < monthResult.size(); i++){
			finalResult+= monthResult.get(i);
		}
					/*																	*/
		 			/*                this part is retrieving day of month status		*/
		 			/*																	*/												
		
		for (int i = 0; i < overAllSatats.length; i++){
			
			String retrieve = overAllSatats[i];
			String[] findName =	retrieve.split("\\|");
			retrieveName = findName[0];

			if (nameNoFromEdit.equals(retrieveName)){
				ArrayList<Date> dateArray = new ArrayList<Date>();
				ArrayList<Date> comparingDateArray = new ArrayList<Date>();
			    formatter = new SimpleDateFormat("EEEE MMM dd HH:mm:ss z yyyy");
				for (int j = 3; j < findName.length; j++){
					try {
						Date date = formatter.parse(findName[j]);
						
						dateArray.add(date);
						comparingDateArray.add(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				for(int k=0; k< dateArray.size(); k++){
					DateTime testDate = new DateTime(dateArray.get(k));

					ArrayList<String> dayInMonth = new ArrayList<String>();
					ArrayList<String> DayNoDuplicate = new ArrayList<String>();
					int yearDefault = testDate.year().get();
					int monthDefault = testDate.getMonthOfYear();
					for(int l=0; l <comparingDateArray.size();l++){
						DateTime compDate = new DateTime(dateArray.get(l));
						int year = compDate.year().get();
						
						if(year == yearDefault){
							int month = compDate.getMonthOfYear();
							if(month == monthDefault){
									dayInMonth.add("Day Status: "+year +" " +compDate.monthOfYear().getAsText() +" " + compDate.dayOfMonth().get());
								
							}
						}
					}
					HashSet<String> hs = new HashSet<String>();
					hs.addAll(dayInMonth);
					DayNoDuplicate.addAll(hs);
					
					for(int y = 0; y < DayNoDuplicate.size(); y++){
						dayResult.add(DayNoDuplicate.get(y).toString()+ ": " +  Collections.frequency(dayInMonth, DayNoDuplicate.get(y).toString())+"\n"+"|");
					}
					
				}
			}
		}
		HashSet<String> hsDay = new HashSet<String>();
		hsDay.addAll(dayResult);
		dayResult.clear();
		dayResult.addAll(hsDay);
					/*											*/
					/* this part is retrieving hour status		*/
					/*											*/
		
		for(int i = 0; i < dayResult.size(); i++){
			finalResult+= dayResult.get(i);
		}
		
		for (int i = 0; i < overAllSatats.length; i++){
			
			String retrieve = overAllSatats[i];
			String[] findName =	retrieve.split("\\|");
			retrieveName = findName[0];

			if (nameNoFromEdit.equals(retrieveName)){
				ArrayList<Date> dateArray = new ArrayList<Date>();
				ArrayList<Date> comparingDateArray = new ArrayList<Date>();
			    formatter = new SimpleDateFormat("EEEE MMM dd HH:mm:ss z yyyy");
				for (int j = 3; j < findName.length; j++){
					try {
						Date date = formatter.parse(findName[j]);
						
						dateArray.add(date);
						comparingDateArray.add(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				for(int k=0; k< dateArray.size(); k++){
					DateTime testDate = new DateTime(dateArray.get(k));

					ArrayList<String> hourInDay = new ArrayList<String>();
					ArrayList<String> hourNoDuplicate = new ArrayList<String>();
					int yearDefault = testDate.year().get();
					int monthDefault = testDate.getMonthOfYear();
					int dayDefault = testDate.getDayOfMonth();
					for(int l=0; l <comparingDateArray.size();l++){
						DateTime compDate = new DateTime(dateArray.get(l));
						int year = compDate.year().get();
						
						if(year == yearDefault){
							int month = compDate.getMonthOfYear();
							if(month == monthDefault){
								int day = compDate.getDayOfMonth();
								if(day == dayDefault){
									hourInDay.add("Hour Status: "+year +" " +compDate.monthOfYear().getAsText() +" " + compDate.dayOfMonth().get() +" at " + compDate.hourOfDay().get()+ " o'clock");
								}
							}
						}
					}
					HashSet<String> hs = new HashSet<String>();
					hs.addAll(hourInDay);
					hourNoDuplicate.addAll(hs);
					
					for(int y = 0; y < hourNoDuplicate.size(); y++){
						hourResult.add(hourNoDuplicate.get(y).toString()+ ": " +  Collections.frequency(hourInDay, hourNoDuplicate.get(y).toString())+"\n"+"|");
					}
					
				}
			}
		}
		HashSet<String> hsHour = new HashSet<String>();
		hsHour.addAll(hourResult);
		hourResult.clear();
		hourResult.addAll(hsHour);
		for(int i = 0; i < hourResult.size(); i++){
			finalResult+= hourResult.get(i);
		}
					/*											*/
					/* this part is retrieving week status		*/
					/*											*/
		for (int i = 0; i < overAllSatats.length; i++){
			
			String retrieve = overAllSatats[i];
			String[] findName =	retrieve.split("\\|");
			retrieveName = findName[0];

			if (nameNoFromEdit.equals(retrieveName)){
				ArrayList<Date> dateArray = new ArrayList<Date>();
				ArrayList<Date> comparingDateArray = new ArrayList<Date>();
			    formatter = new SimpleDateFormat("EEEE MMM dd HH:mm:ss z yyyy");
				for (int j = 3; j < findName.length; j++){
					try {
						Date date = formatter.parse(findName[j]);
						
						dateArray.add(date);
						comparingDateArray.add(date);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				for(int k=0; k< dateArray.size(); k++){
					DateTime testDate = new DateTime(dateArray.get(k));
					ArrayList<String> weekName = new ArrayList<String>();
					ArrayList<String> weekNoDuplicate = new ArrayList<String>();
					int yearDefault = testDate.year().get();
					int weekDefault = testDate.getWeekOfWeekyear();
					for(int l=0; l <comparingDateArray.size();l++){
						DateTime compDate = new DateTime(dateArray.get(l));
						int year = compDate.year().get();
						
						if(year == yearDefault){
							int week  = compDate.getWeekOfWeekyear();
							if(week == weekDefault){
							weekName.add("Week Status: "+ year+" " + compDate.monthOfYear().getAsText()+" "+compDate.dayOfMonth().get() );
						}
						}
					}
					
					HashSet<String> hs = new HashSet<String>();
					hs.addAll(weekName);
					weekNoDuplicate.addAll(hs);
					
					for(int y = 0; y < weekNoDuplicate.size(); y++){
						weekResult.add(weekNoDuplicate.get(y).toString()+ ": " +  Collections.frequency(weekName, weekNoDuplicate.get(y).toString())+"\n"+"|");
					}
					
				}
			}
			
		}
		HashSet<String> hsWeek = new HashSet<String>();
		hsWeek.addAll(weekResult);
		weekResult.clear();
		weekResult.addAll(hsWeek);
		
		for(int i = 0; i < weekResult.size(); i++){
			finalResult+= weekResult.get(i);
		}
							/*												*/
							/* this part is combining all the details 		*/
							/* and post it to the layout.					*/
							/*												*/
		String[] resultArray = finalResult.split("\\|");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, resultArray);
		counterStatus.setAdapter(adapter);
	}
		


    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.counter_detail, menu);

		return true;
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

}

