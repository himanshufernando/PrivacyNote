package tkhub.project.PrivacyNote.model.home;

import android.app.Activity;
import android.content.Context;

import java.io.File;
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
    void deleteNote(long id,OnFinishedListener onFinishedListener);

    void setWriteBackup(Context context,Activity activity,File file,String filename,OnFinishedListener onFinishedListener);
    void ReadBackup(Context context, Activity activity);
    void setResetBackup(String oldFilePath, String outFileName,Activity activity,OnFinishedListener onFinishedListener);


    interface OnFinishedListener {
        void onsetAllNote();
        void onsetAllNavigationItems();
        void ongetFingerprintAutherAccess(boolean isAccess);

        void onTitleEmpty();
        void onSaveSuccess();
        void onSaveFail();

        void onSetSearchAutoComplteText();

        void onNoteDeleteSuccess();
        void onNoteDeleteFail();

        void onWriteBackupFinished(String saveFileName);

        void onResetBackupFinished();
        void onResetBackupError(String error);


    }



}
