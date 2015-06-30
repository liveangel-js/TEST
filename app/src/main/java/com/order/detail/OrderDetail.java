package com.order.detail;

import java.io.ByteArrayInputStream;
        import java.io.DataInputStream;
        import java.io.IOException;
        import java.io.UnsupportedEncodingException;
        import java.util.ArrayList;
        import java.util.List;
        import org.apache.http.HttpEntity;
        import org.apache.http.HttpResponse;
        import org.apache.http.NameValuePair;
        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.entity.UrlEncodedFormEntity;
        import org.apache.http.client.methods.HttpPost;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.apache.http.message.BasicNameValuePair;
        import org.apache.http.protocol.HTTP;
        import org.apache.http.util.EntityUtils;
        import org.json.JSONException;
        import org.json.JSONObject;

        import android.annotation.SuppressLint;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.TextView;

        import com.example.liveangel.test.R;

public class OrderDetail extends Activity {
    //private static final int REQUEST_CODE = 2;
    Button btn_orderDetail;
    TextView tv_o_staff_id;
    TextView tv_o_state;
    TextView tv_o_goods_id;
    TextView tv_o_create_time;
    TextView tv_o_delivery_time;
    TextView tv_o_total_price;
    TextView tv_o_user_id;
    TextView tv_o_destination;
    TextView tv_o_remark;
    ProgressDialog progressDialog;
    String order_id;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        //初始化登陆界面
        initView();
        loadData();
        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Loading...");
        final UriAPI uriapi = new UriAPI();
        btn_orderDetail.setOnClickListener(new OnClickListener() {

            @SuppressLint("NewApi")
            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {
                //通过AsyncTask类提交数据 异步显示
                new AT(uriapi).execute();
            }

        });

    }

    public void initView(){
        btn_orderDetail = (Button) findViewById(R.id.btn_orderDetail);
        tv_o_staff_id = (TextView) findViewById(R.id.tv_o_staff_id);
        tv_o_state = (TextView) findViewById(R.id.tv_o_state);
        tv_o_goods_id = (TextView) findViewById(R.id.tv_o_goods_id);
        tv_o_create_time = (TextView) findViewById(R.id.tv_o_create_time);
        tv_o_delivery_time = (TextView) findViewById(R.id.tv_o_delivery_time);
        tv_o_total_price = (TextView) findViewById(R.id.tv_o_total_price);
        tv_o_user_id = (TextView) findViewById(R.id.tv_o_user_id);
        tv_o_destination = (TextView) findViewById(R.id.tv_o_destination);
        tv_o_remark = (TextView) findViewById(R.id.tv_o_remark);
    }

    public void loadData() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");
    }

    public class UriAPI {
        /** 定义一个Uri **/
//        public static final String HTTPCustomer ="http://10.0.3.2:8080/LazyGift/orderDetail";
        public static final String HTTPCustomer ="http://218.94.159.104:5000/LazyGift/orderDetail";
    }
    @SuppressLint("NewApi")
    @SuppressWarnings("rawtypes")
    class AT extends AsyncTask{

        String result="";
        UriAPI uriapi;
        public AT(UriAPI uriapi) {
            this.uriapi = uriapi;
        }
        @Override
        protected void onPreExecute() {
            //加载progressDialog
            Log.i("服务器返回信息:", "pre");
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object... params_obj) {
            //请求数据
            HttpPost httpRequest  = new HttpPost(UriAPI.HTTPCustomer);
            //创建参数
            List<NameValuePair> params=new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("order_id", order_id));

            //params.add(new BasicNameValuePair("flag","0"));
            try {
                //对提交数据进行编码

                httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
                HttpResponse httpResponse=new DefaultHttpClient().execute(httpRequest);
                //获取响应服务器的数据
                if (httpResponse.getStatusLine().getStatusCode()==200) {
                    //利用字节数组流和包装的绑定数据
                    byte[] data =new byte[2048];
                    //先把从服务端来的数据转化成字节数组
                    data =EntityUtils.toByteArray((HttpEntity)httpResponse.getEntity());
                    //再创建字节数组输入流对象
                    ByteArrayInputStream bais = new ByteArrayInputStream(data);
                    //绑定字节流和数据包装流
                    DataInputStream dis = new DataInputStream(bais);
                    //将字节数组中的数据还原成原来的各种数据类型，代码如下：
                    result=new String(dis.readUTF());
                    Log.i("服务器返回信息:", result);

                }
            } catch(ClientProtocolException e){
                e.printStackTrace();
            }catch(UnsupportedEncodingException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return result;
        }

        @Override
        protected void onPostExecute(Object result) {
            Log.i("服务器返回信息:", "post");
            //获得服务器返回信息成功后
            try {
                JSONObject jsonObj = new JSONObject(result.toString());
                String staff_id = jsonObj.optString("id");
                String state = jsonObj.optString("state");
                String goods_id = jsonObj.optString("goodsId");
                String create_time = jsonObj.optString("createTime");
                String delivery_time = jsonObj.optString("deliveryTime");
                String total_price = jsonObj.optString("totalPrice");
                String user_id = jsonObj.optString("userId");
                String destination = jsonObj.optString("destination");
                String remark = jsonObj.optString("remark");
                tv_o_staff_id.setText("接单人id: " + staff_id);
                tv_o_state.setText("订单状态: " + state);
                tv_o_goods_id.setText("物品id: " + goods_id);
                tv_o_create_time.setText("开始时间:" + create_time);
                tv_o_delivery_time.setText("结束时间: " + delivery_time);
                tv_o_total_price.setText("总金额: " + total_price);
                tv_o_user_id.setText("下单人id: " + user_id);
                tv_o_destination.setText("目的地: " + destination);
                tv_o_remark.setText("评论: " + remark);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.cancel();
        }
    }
}