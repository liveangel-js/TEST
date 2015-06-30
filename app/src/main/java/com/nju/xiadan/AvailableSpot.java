package com.nju.xiadan;

import com.example.liveangel.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AvailableSpot extends Activity {
	private Spinner priceSpinner;
	private ArrayAdapter<String> priceAdapter;
	
	private String[] price= new String[]{"0~5元","5~10元","10~20元","20~40元","40~100元","100元以上"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_available_spot);
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(AvailableSpot.this, MainList.class));
				
			}
		});
		
		setSpinner2();
	}

	private void setSpinner2() {
		// TODO Auto-generated method stub
		priceSpinner=(Spinner) findViewById(R.id.PriceSpanner);
		priceAdapter=new ArrayAdapter<String>(AvailableSpot.this, android.R.layout.simple_spinner_dropdown_item, price);
		priceSpinner.setAdapter(priceAdapter);
		priceSpinner.setSelection(0,true);
		priceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.available_spot, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}	
}
