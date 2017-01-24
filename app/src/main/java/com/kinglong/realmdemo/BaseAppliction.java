package com.kinglong.realmdemo;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import android.app.Application;

import java.util.regex.Pattern;

import io.realm.Realm;

/**
 * Created by lanjl on 2017/1/24.
 */

public class BaseAppliction extends Application {

    public final void onCreate() {
        super.onCreate();
        Realm.init(this);




        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this)
                                .databaseNamePattern(Pattern.compile(".+\\.realm"))
                                .build())
                        .build());
    }
}
