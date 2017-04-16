package tkhub.project.PrivacyNote.model.about;

import android.content.Context;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface AboutInteractor {


     interface OnLoginFinishedListener {
          void onFinishCurrentVersion(String result);
     }


     void getCurrentVersion(OnLoginFinishedListener listener, Context con);


}
