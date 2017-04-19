package tkhub.project.PrivacyNote.presenter.home;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import tkhub.project.PrivacyNote.data.model.NavigationDrawerItem;
import tkhub.project.PrivacyNote.data.model.NoteItem;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomePresenter {
    void setAllNote(ArrayList<NoteItem> noteItems,String keyword);
    void setAllNavagationItem(ArrayList<NavigationDrawerItem> navigationDrawerItems);
    void getFingerprintAutherAccess();
    void saveNote(String title, String userName, String password, String other);
    void setSearchAutoComplteText(List<String> titleList);
    void deleteNote(int id);
}
