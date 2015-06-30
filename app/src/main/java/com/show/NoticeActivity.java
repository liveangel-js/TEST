package com.show;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.liveangel.test.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liveangel on 15-6-29.
 */
public class NoticeActivity extends Activity {

    private String[] mListTitle={"姓名1","姓名2","姓名3","姓名4","姓名5"};
    private String[] mListStr = {"描述1","描述2","描述3","描述4","描述5"};
    private ListView rank_today = null;
    private ListView rank_week = null;
    private ListView rank_total = null;
    private Handler handler;
    private int count=0;
    private String context = "http://localhost/LazyGift/";


    protected void onCreate(Bundle savedInstanceState){



        setContentView(R.layout.activity_notice);
        //handle初始化处理异步网络请求
        handler= new Handler() {
            @Override
            //当有消息发送出来的时候就执行Handler的这个方法
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();

                //处理返回请求
                String tab = data.getString("tab");
                String val = data.getString("value");
                Log.i("mylog","请求结果-->" + val);



                Map<String,Object> item = new HashMap<String,Object>();
                item.put("title", "测试POST");
                item.put("text",val);
                ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();
                mData.add(item);
                updateTabData(tab, mData);
            }
        };



        //设置使用TabHost布局
        TabHost tabHost = (TabHost) findViewById(R.id.notice_tab);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab01").setIndicator("今日热门").setContent(R.id.notice_tab01));
        tabHost.addTab(tabHost.newTabSpec("tab02").setIndicator("一周热门").setContent(R.id.notice_tab02));
        tabHost.addTab(tabHost.newTabSpec("tab03").setIndicator("总热门").setContent(R.id.notice_tab03));
        tabChange(tabHost);
        //今日排行
        rank_today =  (ListView) findViewById(R.id.location_listview_today);
        rank_week = (ListView) findViewById(R.id.location_listview_week);
        rank_total = (ListView) findViewById(R.id.location_listview_total);
        updateTabData("tab01");
        super.onCreate(savedInstanceState);

    }

    private void tabChange(TabHost tabhost){
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener(){
            public void onTabChanged(String tabId){
                updateTabData(tabId);

            }
        });

    }
    private boolean updateTabData(String tabId, ArrayList<Map<String,Object>> mData ){
        if(tabId.equals("tab01")){
            SimpleAdapter adapter = new SimpleAdapter(this,mData,android.R.layout.simple_list_item_2,
                    new String[]{"title","text"},new int[]{android.R.id.text1,android.R.id.text2});
            rank_today.setAdapter(adapter);
            rank_today.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                        long id) {
                    Toast.makeText(getApplicationContext(), "您选择了标题：" + mListTitle[position] + "内容：" + mListStr[position], Toast.LENGTH_LONG).show();
                }
            });

        }else if(tabId.equals("tab02")){
            SimpleAdapter adapter = new SimpleAdapter(this,mData,android.R.layout.simple_list_item_2,
                    new String[]{"title","text"},new int[]{android.R.id.text1,android.R.id.text2});
            rank_week.setAdapter(adapter);
            rank_week.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                        long id) {
                    Toast.makeText(getApplicationContext(), "您选择了标题：" + mListTitle[position] + "内容：" + mListStr[position], Toast.LENGTH_LONG).show();
                }
            });

        }else if(tabId.equals("tab03")){
            SimpleAdapter adapter = new SimpleAdapter(this,mData,android.R.layout.simple_list_item_2,
                    new String[]{"title","text"},new int[]{android.R.id.text1,android.R.id.text2});
            rank_total.setAdapter(adapter);
            rank_total.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                        long id) {
                    Toast.makeText(getApplicationContext(), "您选择了标题：" + mListTitle[position] + "内容：" + mListStr[position], Toast.LENGTH_LONG).show();
                }
            });

        }
        return true;
    }

    private boolean updateTabData(String tabId){
        //计算起止时间
        Date dateNow = new Date();
        Date dateBofore = (Date)dateNow.clone();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginDate = df.format(dateNow);
        String endDate = null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateBofore);



        if(tabId.equals("tab01")){
            Log.d("操作","刷新tab01");

            calendar.add(Calendar.DAY_OF_MONTH, 0);
            dateBofore = calendar.getTime();
            endDate = df.format(dateBofore);
            getLocationData("tab01",1,15, beginDate, endDate);



        }else if(tabId.equals("tab02")){
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            dateBofore = calendar.getTime();
            endDate = df.format(dateBofore);
            getLocationData("tab02",1,15, beginDate, endDate);

        }else if(tabId.equals("tab03")){
            //总历史 endDate="";
            endDate = "";
            getLocationData("tab03",1,15, beginDate, "");


        }else{
        }

        return true;


    }
    private void getLocationData(final String tab,final int rankTop, final int rankEnd, final String beginDate, final String endDate ){
        ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();

        int lengh = mListTitle.length;
        for(int i =0; i < lengh; i++) {
            count++;
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("title", count+mListTitle[i]);
            item.put("text", mListStr[i]);
            mData.add(item);
        }


        //发送 POST 请求
        final String url= "http://218.94.159.104:5000/LazyGift/notice";
        final String para = "rankTop=" + rankTop + "&rankEnd=" + rankEnd + "&beginDate=" + beginDate + "&endDate=" + endDate;

        Log.i("通讯","Thread");
        Log.i("通讯","参数"+para);
        new Thread(){

            public void run(){

                String sr= sendPost(url,para);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value",sr);
                data.putString("tab",tab);
                msg.setData(data);
                handler.sendMessage(msg);

            }

        }.start();


    }



    private static String sendPost(String url, String param){
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            Log.d("通讯", "发送 POST 请求出现异常！");
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }




}
