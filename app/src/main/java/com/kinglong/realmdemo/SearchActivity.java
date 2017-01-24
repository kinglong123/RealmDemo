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
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

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

    @BindView(R.id.bt2)
    Button mBt2;

    RealmResults<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        initLocalData();
        initView();

    }

    public void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mSimpleAdapter = new SimpleAdapter(this, this);
        mSimpleAdapter.setData(personList);
        mList.setAdapter(mSimpleAdapter);
        mBt.setOnClickListener(this);
        mBt2.setOnClickListener(this);

    }

    public void initLocalData() {
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
            case R.id.bt2:
                addRxSearch();
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

    Subscription mSubscription;

    private void addRxSearch() {
        String s = mEt.getText().toString();
        mSubscription = RealmHelper.getRealmHelper().getRealm().where(Person.class)
                .beginsWith("name", s)
                .findAllSortedAsync("name")
                .asObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<RealmResults<Person>>() {
                            @Override
                            public void call(RealmResults<Person> persons) {
                                personList = persons;
                                mSimpleAdapter.setData(personList);
                                mSimpleAdapter.notifyDataSetChanged();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {

                            }
                        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }
}
