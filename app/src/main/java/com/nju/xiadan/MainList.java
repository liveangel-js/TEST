package com.nju.xiadan;

import com.example.liveangel.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainList extends Activity {
	private TextView textview1;
	private Spinner spinner1;
	private Animation animation1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainlist);
		
		textview1=(TextView)findViewById(R.id.textview1);
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			//IP10.0.3.2
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in= new Intent(MainList.this,ArriveSpot.class);
				startActivity(in);
				
			}
		});
		
		findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in=new Intent(MainList.this, AvailableSpot.class);
				startActivity(in);
				
			}
		});
		
		findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainList.this, GoodsList.class));
				
			}
		});
		
		findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainList.this, Deadline.class));
				
			}
		});
		
//		findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				startActivity(new Intent(MainActivity.this, CustomerCredit.class));
//				
//			}
//		});
		
//		final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner1,android.R.layout.simple_spinner_item);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner1.setAdapter(adapter); 
//		spinner1.setPrompt("信息："); 
//		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
//		public void onItemSelected(AdapterView<?> arg0, View arg1,
//		int arg2, long arg3) {
//		//获取所选择的地址
//		textview1.setText("筛选方式："+adapter.getItem(arg2).toString());
//		}
//		public void onNothingSelected(AdapterView<?> arg0) {
//			textview1.setText("您还没有选中。");
//			}
//			}); 
//			 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
