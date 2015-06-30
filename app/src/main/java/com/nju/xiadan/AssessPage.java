package com.nju.xiadan;

import java.util.ArrayList;
import com.example.liveangel.test.R;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AssessPage extends Activity {
	private EditText edittext1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assess_page);
		edittext1=(EditText)findViewById(R.id.editText1);
		
		findViewById(R.id.button0).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(AssessPage.this, CustomerCredit.class);
				i.putExtra("data", edittext1.getText().toString());
				setResult(1,i);
				finish();
			}
		});
		
		findViewById(R.id .button2).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent in=new Intent(AssessPage.this, MainList.class);
				startActivity(in);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assess_page, menu);
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
	
	private class SubmitAddressTask extends AsyncTask<String,Void,Object>{
        @Override
        protected Object doInBackground(String... params) {
            String id = "mg1546256548";
            String assess=edittext1.getText().toString();

            submitUserAddr(id, assess);
            return null;
        }

        private void submitUserAddr(String id, String assess){
            //text.setText(id + ": " + addr);
            String requestIP = "http://10.0.3.2:8080/LazyGift/MyAddrSet";
            HttpClient client = new DefaultHttpClient();
            HttpPost httpRequest = new HttpPost(requestIP);
            //String url = requestIP + "?ID="+ id + "&ADDRESS="+addr;
            //HttpGet getRequest = new HttpGet(url);
            ArrayList<NameValuePair> params;
            params = new ArrayList<>();
            params.add(new BasicNameValuePair("ID",id));
            params.add(new BasicNameValuePair("ASSESS",assess));
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
    }
}
