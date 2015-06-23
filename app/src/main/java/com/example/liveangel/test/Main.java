package com.example.liveangel.test;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.Button;

import com.order.manager.OrderManagerActivity;


public class Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        //initialize button
        Button orderManagerButton = (Button) findViewById(R.id.orderManager);
        orderManagerButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent();
                intent.setClass(Main.this, OrderManagerActivity.class);
                startActivity(intent);

            }
        });
        Button orderManagerButton = (Button) findViewById(R.id.btnGHJ);
        orderManagerButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view){
                Intent in = new Intent();
                intent.setClass(Main.this, MainList.class);
                startActivity(in);

            }
        });
        
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // TESTjjj
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
