package tkhub.project.PrivacyNote.model.home;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import tkhub.project.PrivacyNote.data.model.NavigationDrawerItem;
import tkhub.project.PrivacyNote.data.model.NoteItem;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomeInteractor {

    void setAllNote(ArrayList<NoteItem> noteItems, String keyword, OnFinishedListener onFinishedListener);
    void setAllNavigationItem(ArrayList<NavigationDrawerItem> navigationDrawerItems,OnFinishedListener onFinishedListener);
    void getFingerprintAutherAccess(OnFinishedListener onFinishedListener);
    void saveNote(String title,String userName, String password, String other, OnFinishedListener onFinishedListener);
    void setSearchAutoComplteText(List<String> titleList,OnFinishedListener onFinishedListener);

    interface OnFinishedListener {
        void onsetAllNote();
        void onsetAllNavigationItems();
        void ongetFingerprintAutherAccess(boolean isAccess);

        void onTitleEmpty();
        void onSaveSuccess();
        void onSaveFail();

        void onSetSearchAutoComplteText();
    }



}
