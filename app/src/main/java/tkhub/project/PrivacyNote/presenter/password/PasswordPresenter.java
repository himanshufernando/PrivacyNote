package tkhub.project.PrivacyNote.presenter.password;

import android.app.Activity;
import android.content.Context;

import io.realm.Realm;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface PasswordPresenter {
    void readBackup();
    void writeBackup(String oldFilePath, String outFileName,Activity activity);

}
