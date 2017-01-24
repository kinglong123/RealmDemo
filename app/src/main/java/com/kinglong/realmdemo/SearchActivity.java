package com.kinglong.realmdemo;

import com.kinglong.realmdemo.util.RealmHelper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

import static com.kinglong.realmdemo.util.RealmHelper.getRealmHelper;

/**
 * Created by lanjl on 2017/1/24.
 */

public class SearchActivity extends FragmentActivity implements View.OnClickListener {


    @BindView(R.id.et)
    EditText mEt;

    @BindView(R.id.list)
    RecyclerView mList;



    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;

    SimpleAdapter mSimpleAdapter;

    @BindView(R.id.bt)
    Button mBt;

    RealmResults<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initLocalData();
        initView();

    }

    public void initView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mSimpleAdapter = new SimpleAdapter(this, this);
        mSimpleAdapter.setData(personList);
        mList.setAdapter(mSimpleAdapter);
        mBt.setOnClickListener(this);

    }

    public void initLocalData(){
        personList = getRealmHelper().getRealm().where(Person.class).findAll();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

//            case R.id.ll_person:
//                Person person = (Person) v.getTag();
//                update(person);
//                break;

            case R.id.bt:
                addSearch();
                break;
        }
    }


    private void update(Person person) {



    }

    private void addSearch() {
        String s = mEt.getText().toString();
        personList = RealmHelper.getRealmHelper().getRealm().where(Person.class)
                .beginsWith("name", s).findAll();
        mSimpleAdapter.setData(personList);
        mSimpleAdapter.notifyDataSetChanged();
    }
}
