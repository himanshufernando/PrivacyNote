package tkhub.project.PrivacyNote.presenter.home;

import android.app.Activity;

import java.util.ArrayList;

import io.realm.Realm;
import tkhub.project.PrivacyNote.data.model.NavigationDrawerItem;
import tkhub.project.PrivacyNote.data.model.NoteItem;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomePresenter {
    void setAllNote(Realm realm,ArrayList<NoteItem> noteItems,String keyword);
    void setAllNavagationItem(Realm realm, ArrayList<NavigationDrawerItem> navigationDrawerItems);
}
