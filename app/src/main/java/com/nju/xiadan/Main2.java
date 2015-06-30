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
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class Main2 extends Activity {
	private TextView textview1;
	private Spinner spinner2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		textview1=(TextView)findViewById(R.id.textview3);
		spinner2=(Spinner)findViewById(R.id.spinner2);
		
		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in2=new Intent(Main2.this, MainList.class);
				startActivity(in2);
				
			}
		});
		
		findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in2=new Intent(Main2.this, Main3.class);
				startActivity(in2);
				
			}
		});
		
		
		final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.spinner2,android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2); 
		spinner2.setPrompt("信息："); 
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
			//获取所选择的地址
			textview1.setText("筛选方式："+adapter2.getItem(arg2).toString());
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				textview1.setText("您还没有选中。");
				}
				});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main2, menu);
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
