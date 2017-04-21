package tkhub.project.PrivacyNote.model.password;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import tkhub.project.PrivacyNote.PrivacyNoteApp;
import tkhub.project.PrivacyNote.ui.actitvity.PasswordActivity;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class PasswordInteractorImpil implements PasswordInteractor {

    static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_READ = 124;
    @Override
    public void setReadBackup(Context context,Activity activity) {

        if (!checkPermissionRead(context)) {
            requestPermissionRead(activity);
        } else {
            new MaterialFilePicker()
                    .withActivity(activity)
                    .withRequestCode(1)
                    .withFilterDirectories(true) // Set directories filterable (false by default)
                    .withHiddenFiles(true) // Show hidden files and folders
                    .start();
        }

    }

    @Override
    public void setWriteBackup(String oldFilePath, String outFileName,Activity activity,OnFinishedListener onFinishedListener) {
        try {
            File file = new File(activity.getFilesDir(), outFileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            FileInputStream inputStream = new FileInputStream(new File(oldFilePath));
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            onFinishedListener.onWriteBackupFinished();
        } catch (IOException e) {
            e.printStackTrace();
            onFinishedListener.onWriteBackupError(e.toString());
        }
    }


    private boolean checkPermissionRead(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissionRead(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},  MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_READ);
    }

}
