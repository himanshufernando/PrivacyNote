package tkhub.project.PrivacyNote.model.home;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import tkhub.project.PrivacyNote.Adapter.NavigationDrawerItem;
import tkhub.project.PrivacyNote.data.model.NoteItem;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomeInteractor {

    void setAllNote(Realm realm, ArrayList<NoteItem> noteItems, String keyword, OnFinishedListener onFinishedListener);
    void setAllNavigationItem(Realm realm, ArrayList<NavigationDrawerItem> navigationDrawerItems,OnFinishedListener onFinishedListener);


    interface OnFinishedListener {
        void onsetAllNote();
        void onsetAllNavigationItems();
    }


}
