package tkhub.project.PrivacyNote.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class AboutInteractorImpil implements AboutInteractor {


    @Override
    public void getCurrentVersion(OnLoginFinishedListener listener, Context con) {
        String version = null;
        try {
            PackageManager manager = con.getPackageManager();
            PackageInfo info = manager.getPackageInfo(con.getPackageName(), 0);
            version="Version "+info.versionName;
        }catch (Exception ex){
            version= "Version 1.0.0 ";
        }
        listener.onFinishCurrentVersion(version);
    }
}
