package com.nju.xiadan;

import com.example.liveangel.test.R;
import com.nju.xiadan.data.orderdata;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class GoodsList extends Activity {
	private EditText edittext;
	private TextView textview;
	private Spinner mainspinner;
	private Spinner lastspinner;
	private ListView mycokelist;
	ArrayAdapter<String> mainadapter;
	ArrayAdapter<String> lastadapter;
	static int pot;
	public String item1;
	public String item2;

	private String[] main = new String[] { "全部", "饮料", "食品", "文教", "日用", "电子",
			"其他", };
	private String[][] last = new String[][] { { "" },
			{ "可口可乐", "百事可乐", "美年达", "芬达", "七喜", "矿泉水", "其他" },
			{ "小吃", "水果", "正餐", "蔬菜", "特色食品", "休闲食品", "点心", "其他" },
			{ "文具类", "纸本类", "教科书", "健身类", "代复印", "装扮类", "纪念品类", "其他" },
			{ "洗漱类", "洗衣类", "安全类", "洗澡用品", "防蚊防虫", "日用药品类", "厨房用品类", "其他" },
			{ "计算器", "收音机", "电灯类", "数据线充电器", "电池类", "日用电子", "其他" },
			{ "杂货", "代理" } };
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_list);
		setSpinner();
		edittext = (EditText) findViewById(R.id.editText1);
		textview = (TextView) findViewById(R.id.textView5);
		mycokelist=(ListView) findViewById(R.id.myCokeList);
		

		// textview.setText("您选择的是："+item1+edittext.getText().toString());
	}

	private void setSpinner() {
		// TODO Auto-generated method stub
		mainspinner = (Spinner) findViewById(R.id.spinner1);
		lastspinner = (Spinner) findViewById(R.id.spinner2);

		mainadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, main);
		mainspinner.setAdapter(mainadapter);
		mainspinner.setSelection(0, true);

		lastadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, last[0]);
		lastspinner.setAdapter(lastadapter);
		lastspinner.setSelection(0, true);

		mainspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						lastadapter = new ArrayAdapter<String>(GoodsList.this,
								android.R.layout.simple_spinner_item,
								last[arg2]);
						lastspinner.setAdapter(lastadapter);
						item1 = mainadapter.getItem(arg2);
						pot = arg2;
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		
		lastspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				item2=lastadapter.getItem(arg2);
				textview.setText("   您选择的是："+item1+"类"+"  "+item2);
				setList();
                String requestIP = "http://10.0.3.2:8080/LazyGift/MyAddrSet";
                HttpClient client = new DefaultHttpClient();
                HttpPost httpRequest = new HttpPost(requestIP);
                ArrayList<NameValuePair> params;
                params = new ArrayList<>();
                params.add(new BasicNameValuePair("item1",item1));
                params.add(new BasicNameValuePair("goodsName",item2));
                try {
                    httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

                    HttpResponse response = client.execute(httpRequest);

                    if(response.getStatusLine().getStatusCode() == 200)
                    {
            /*取出响应字符串*/

                        String strResult = EntityUtils.toString(response.getEntity());
                        //show strResult?
                        System.out.println(strResult);
                    }
                    else
                    {
                        //处理错误。。。。
                        System.out.println("Error Response: " + response.getStatusLine().toString());
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	
	private void setList() {
		// TODO Auto-generated method stub
		if(item2=="百事可乐"){
			mycokelist.setAdapter(new ArrayAdapter<String>(GoodsList.this,android.R.layout.simple_expandable_list_item_1, orderdata.getPesidata));
		}else if(item2=="可口可乐"){
			mycokelist.setAdapter(new ArrayAdapter<String>(GoodsList.this, android.R.layout.simple_expandable_list_item_1, orderdata.getCokedata));
		}else if(item2=="芬达"){
			mycokelist.setAdapter(new ArrayAdapter<String>(GoodsList.this, android.R.layout.simple_expandable_list_item_1, orderdata.getFenda));
		}else if(item2=="矿泉水"){
			mycokelist.setAdapter(new ArrayAdapter<String>(GoodsList.this, android.R.layout.simple_expandable_list_item_1, orderdata.getWarter));
		}else if(item2=="七喜"){
			mycokelist.setAdapter(new ArrayAdapter<String>(GoodsList.this, android.R.layout.simple_expandable_list_item_1, orderdata.get7updata));
		}else{
			mycokelist.setAdapter(new ArrayAdapter<String>(GoodsList.this, android.R.layout.simple_expandable_list_item_1, orderdata.getEmpty));
		}
//		switch (item2) {
//		case "百事可乐":
//			mycokelist.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,getPesidata));
//			break;
//		case "可口可乐":
//			mycokelist.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getCokedata));
//			break;
//		case "七喜":
//			mycokelist.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, get7updata));
//		default:
//			mycokelist.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getEmpty));
//			break;
//		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.goods_list, menu);
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
