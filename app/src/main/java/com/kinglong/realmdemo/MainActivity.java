package com.kinglong.realmdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);

        findViewById(R.id.btn_rx_query).setOnClickListener(this);
        findViewById(R.id.btn_async).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                startActivity(new Intent(MainActivity.this,AddDelOrUpdateActivity.class));

                break;
            case R.id.btn_query:
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
                break;
            case R.id.btn_rx_query:
                startActivity(new Intent(MainActivity.this,RxBindingSearchActivity.class));
                break;

            case R.id.btn_async:
                startActivity(new Intent(MainActivity.this,AddDelOrUpdateActivity.class));

                break;
            default:
                break;
        }
    }
}
