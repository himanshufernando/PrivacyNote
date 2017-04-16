package tkhub.project.PrivacyNote.model.home;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import tkhub.project.PrivacyNote.data.model.NoteItem;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomeInteractor {

  void setAllNote(Realm realm, ArrayList<NoteItem> noteItems, OnFinishedListener onFinishedListener);


  interface OnFinishedListener {
    void onsetAllNote();
  }


}
