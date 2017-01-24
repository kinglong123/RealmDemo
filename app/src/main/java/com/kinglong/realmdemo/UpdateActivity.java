package com.kinglong.realmdemo;

import com.kinglong.realmdemo.util.RealmHelper;
import com.kinglong.realmdemo.util.StringUtil;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lanjl on 2017/1/24.
 */

public class UpdateActivity extends FragmentActivity implements View.OnClickListener {


    @BindView(R.id.tv)
    TextView mTv;

    @BindView(R.id.et)
    EditText mEt;

    @BindView(R.id.bt)
    Button mBt;

    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;

    String personName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);

        personName = (String) getIntent().getStringExtra("personName");
        if (personName == null) {
            return;
        }
        mTv.setText(personName);
        mBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt:
                update();
                break;
        }

    }

    public void update() {
        if (StringUtil.isNotBlank((mEt.getText().toString()))) {
            RealmHelper.getRealmHelper().updatePerson(personName,mEt.getText().toString());
            finish();
        }


    }
}
