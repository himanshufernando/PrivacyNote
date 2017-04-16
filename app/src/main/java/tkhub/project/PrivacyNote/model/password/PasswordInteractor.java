package tkhub.project.PrivacyNote.model.password;

import android.app.Activity;
import android.content.Context;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface PasswordInteractor {
  void setReadBackup(Context context, Activity activity);
  void setWriteBackup(String oldFilePath, String outFileName,Activity activity,OnFinishedListener onFinishedListener);

  interface OnFinishedListener {
    void onWriteBackupFinished();
    void onWriteBackupError(String error);
  }
}
