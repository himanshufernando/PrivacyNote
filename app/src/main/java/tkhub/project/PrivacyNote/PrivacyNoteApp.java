package tkhub.project.PrivacyNote;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import java.io.FileNotFoundException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import tkhub.project.PrivacyNote.data.database.Migration;

/**
 * Created by Himanshu on 3/29/2017.
 */

public class PrivacyNoteApp extends Application {


    @Override public void onCreate() {
        super.onCreate();


        Realm.init(this);
        RealmConfiguration config0 = new RealmConfiguration.Builder()
                .name("default0.realm")
                .schemaVersion(2)
                .build();
        try {
            Realm.migrateRealm(config0, new Migration());
        } catch (FileNotFoundException ignored) {
        }
        Realm.getInstance(config0);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

    }
}
