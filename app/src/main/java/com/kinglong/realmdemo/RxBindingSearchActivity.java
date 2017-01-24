package com.kinglong.realmdemo;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;
import com.kinglong.realmdemo.util.RealmHelper;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.kinglong.realmdemo.util.RealmHelper.getRealmHelper;

/**
 * Created by lanjl on 2017/1/24.
 */

public class RxBindingSearchActivity extends FragmentActivity implements View.OnClickListener {


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

    Subscription subscription;

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

    @Override
    protected void onResume() {
        super.onResume();
        subscription = RxTextView.textChangeEvents(mEt)
                .debounce(200, TimeUnit.MILLISECONDS) // 在一定的时间内没有操作就会发送事件 default Scheduler is Schedulers.computation()
                .observeOn(AndroidSchedulers.mainThread()) // Needed to access Realm data
                .flatMap(new Func1<TextViewTextChangeEvent, Observable<RealmResults<Person>>>() {
                    @Override
                    public Observable<RealmResults<Person>> call(TextViewTextChangeEvent event) {
                        // Use Async API to move Realm queries off the main thread.
                        // Realm currently doesn't support the standard Schedulers.
                        return  RealmHelper.getRealmHelper().getRealm().where(Person.class)
                                .beginsWith("name", event.text().toString())
                                .findAllSortedAsync("name").asObservable();


                    }
                })
                .filter(new Func1<RealmResults<Person>, Boolean>() {
                    @Override
                    public Boolean call(RealmResults<Person> persons) {
                        // Only continue once data is actually loaded
                        // RealmObservables will emit the unloaded (empty) list as its first item
                        return persons.isLoaded();
                    }
                })
                .subscribe(new Action1<RealmResults<Person>>() {
                    @Override
                    public void call(RealmResults<Person> persons) {
                        personList = persons;
                        mSimpleAdapter.setData(personList);
                        mSimpleAdapter.notifyDataSetChanged();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        subscription.unsubscribe();
    }

}
