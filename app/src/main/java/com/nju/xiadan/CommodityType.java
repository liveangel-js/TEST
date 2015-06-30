package com.nju.xiadan;

import com.example.liveangel.test.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class CommodityType extends Activity {
	private TextView textview3;
	private Spinner spinner;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_commodity_type);
		spinner=(Spinner)findViewById(R.id.spinner1);
		textview3=(TextView)findViewById(R.id.textView2);
		
		
		final ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(CommodityType.this, R.array.spinner1,android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter2); 
		spinner.setPrompt("信息："); 
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
			//获取所选择的地址
			textview3.setText("您想购买的是"+adapter2.getItem(arg2).toString()+"商品");
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				textview3.setText("您还没有选中。");
				}
				});
		
		
		
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(CommodityType.this, MainList.class));
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.commodity_type, menu);
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
