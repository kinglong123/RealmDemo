package com.kinglong.realmdemo.util;

import com.kinglong.realmdemo.Person;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by lanjl on 2017/1/24.
 */

public class RealmHelper {
    public static final String DB_NAME = "myRealm.realm";
    private Realm mRealm;
//    RealmConfiguration defaultConfig;

    public static   RealmHelper sRealmHelper;

    private RealmHelper(){
        RealmConfiguration defaultConfig = new RealmConfiguration.Builder().build();
        mRealm = Realm.getInstance(defaultConfig);
    }

    public static RealmHelper getRealmHelper(){
        if(sRealmHelper == null){
            synchronized (RealmHelper.class){
                if(sRealmHelper == null){
                    sRealmHelper = new RealmHelper();
                }
            }

        }
        return  sRealmHelper;
    }


    public Realm getRealm() {
        return mRealm;
    }

    /**
     * add （增）
     */
    public void addPerson(final Person person) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(person);
        mRealm.commitTransaction();

    }

    public void addPerson2(final Person person) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(person);
            }
        });

    }


    /**
     * delete （删）
     */
    public void deletePersion(String name) {
        final RealmResults<Person> personList = mRealm.where(Person.class).equalTo("name", name).findAll();
//        mRealm.beginTransaction();
//        personList.deleteAllFromRealm();
//        mRealm.commitTransaction();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                personList.deleteAllFromRealm();
            }
        });

    }


    /**
     * update （改）
     */
    public void updatePerson(String oldName, String newName) {
        RealmResults<Person> persons = mRealm.where(Person.class).equalTo("name", oldName).findAll();
        if(persons!=null && persons.size() >0) {
            mRealm.beginTransaction();
            for(Person person :persons){
                person.setName(newName);
            }
            mRealm.commitTransaction();
        }
    }
}
