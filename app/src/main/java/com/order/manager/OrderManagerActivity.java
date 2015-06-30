package com.order.manager;

import android.app.ListActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.liveangel.test.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.List;

public class OrderManagerActivity extends ListActivity {

    private ArrayList<HashMap<String, String>> list;
    private SimpleAdapter listAdapter;
    private String context = "http://218.94.159.104:5000/LazyGift/";

    private static Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manager);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                setListAdapter(listAdapter);
                if(msg.what == 1){
                    //显示购买成功提示
                    Toast.makeText(getApplicationContext(),
                            "购买记录成功",
                            Toast.LENGTH_SHORT).show();
                }
            }
        };

        list = new ArrayList<HashMap<String, String>>();

        //新建容器adapter
        listAdapter = new SimpleAdapter(this, list, R.layout.order,
                new String[]{"orderContext", "orderPrice","orderState","orderReceiveData"}, new
                int[]{R.id.orderContext, R.id.orderPrice, R.id.orderState,R.id.orderReceiveData});
        setListAdapter(listAdapter);


        // 使用Post方法向本地服务器发送数据
        OrdersThread ordersThread = new OrdersThread();
        ordersThread.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_manager, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        //保证innerClass使用
        final int fPosition = position;
        new AlertDialog.Builder(this).setTitle("我的提示").setMessage("确定已购买到该商品吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HashMap<String,String> map1 = list.get(fPosition);
                        PurchaseThread purchaseThread = new PurchaseThread( map1.get("id"), fPosition);
                        purchaseThread.start();
                    }
                }).show();

    }


    //子线程：获取订单列表
    class OrdersThread extends Thread {

        @Override
        public void run() {
            HttpClient httpClient = new DefaultHttpClient();
            String url = context+"getorderlist";
            HttpPost httpPost = new HttpPost(url);
            try {
                HttpResponse response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    //处理返回值
                    HttpEntity entity = response.getEntity();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(entity.getContent()));
                    String result = reader.readLine();

                    JSONArray orders = new JSONArray(result);
                    for(int i = 0 ; i<orders.length() ; i++) {
                        JSONObject order = orders.getJSONObject(i);

                        HashMap<String, String> map1 = new HashMap<String, String>();
                        //初始化表数据

                        map1.put("id", order.getLong("id")+"");
                        map1.put("orderContext", order.getString("context"));
                        map1.put("orderPrice", "￥" + order.getDouble("price"));
                        map1.put("orderState", order.getString("state"));
                        map1.put("orderReceiveData", order.getString("createTime"));

                        list.add(map1);
                    }
                    Message message = new Message();
                    message.what = 0;
                    mHandler.sendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //子线程：获取订单列表
    class PurchaseThread extends Thread {

        String orderId;
        int position;

        public PurchaseThread(String orderId, int position) {
            this.orderId = orderId;
            this.position = position;
        }

        @Override
        public void run() {
            HttpClient httpClient = new DefaultHttpClient();
            String url = context+"buyorder";
            HttpPost httpPost = new HttpPost(url);

            List<NameValuePair> paramList = new ArrayList<NameValuePair>();
            NameValuePair param = new BasicNameValuePair("id",orderId);
            paramList.add(param);

            try {
                httpPost.setEntity(new UrlEncodedFormEntity(paramList,HTTP.UTF_8));
                HttpResponse response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    //处理返回值
                    HttpEntity entity = response.getEntity();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(entity.getContent()));
                    String result = reader.readLine();

                    if(result.equals( "true")){
                        //初始化表数据
                        list.remove(position);
                        Message message = new Message();
                        message.what = 1;
                        mHandler.sendMessage(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
