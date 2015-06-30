package com.InputAndMatch;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.liveangel.test.R;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import java.util.logging.LogRecord;

/**
 * Created by GoLD on 2015/6/1.
 */
public class inputAndMatch extends Activity{

    ArrayList<String> arr = new ArrayList<String>();
    String[] goods = new String[64];

   // String[] goods = new String[]{"百事可乐","可口可乐"};


    Handler handler;
  //  String fileName = "data.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        new getDataTask().execute();
      //  initt();


        handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg){

            }
        };

        // 创建一个ArrayAdapter封装数组
        com.InputAndMatch.ArrayAdapter<String> av = new com.InputAndMatch.ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, goods);
        AutoCompleteTextView auto = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        auto.setAdapter(av);

    }

    public void initt(){
        goods[6] = "ABC";
        goods[7]="BBF";
    }



   /* public void getId(){
        String name = null;
        //String name = auto.getText().toString();
        int id = 0;
        int parentId = 0;
        for(int i=0;i<list.size();i++){
            if(list.get(i).name==name){
                id = list.get(i).id;
                parentId = list.get(i).parentId;
                return;
            }
        }
    }*/

    private class getDataTask extends AsyncTask<String,Void,Object>{

        protected Object doInBackground(String... params) {
            getData();
            return null;
        }

        public void getData(){
            String requestIP = "http://10.0.3.2:8080/LazyGift/getGoods";
            HttpClient client = new DefaultHttpClient();
            HttpPost httpRequest = new HttpPost(requestIP);

            for(int i=0;i<16;i++){

                final int tem = i;
                ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new NameValuePair() {
                    @Override
                    public String getName() {
                        return "Goods_ID";
                    }

                    @Override
                    public String getValue() {
                        return Integer.toString(tem);
                    }
                });

                try {
                    httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                    HttpResponse response = client.execute(httpRequest);
                    if(response.getStatusLine().getStatusCode() == 200)
                    {
            /*取出响应字符串*/
                        String strResult = EntityUtils.toString(response.getEntity());
                        System.out.println(strResult);

                        if(strResult != null){
                            arr.add(strResult);
                        }




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

            for(int i=0;i<arr.size();i++){
                goods[i]=arr.get(i);
            }
            handler.sendMessage(new Message());
        }
    }


    /*public void writeFileData(String data){
        try{
            FileOutputStream fout = openFileOutput(fileName,MODE_PRIVATE);
            byte[] bytes = data.getBytes();
            fout.write(bytes);
            fout.close();

        }catch (Exception e){

        }
    }

    public String readFileData(){
        String result="";
        try{
            FileInputStream fin = openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
        }catch (Exception e){

        }
        return result;
    }*/

}
