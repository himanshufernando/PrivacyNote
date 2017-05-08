package tkhub.project.PrivacyNote.presenter.home;

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

public interface HomePresenter {
    void setAllNote(ArrayList<NoteItem> noteItems,String keyword);
    void setAllNavagationItem(ArrayList<NavigationDrawerItem> navigationDrawerItems);
    void getFingerprintAutherAccess();
    void saveNote(String title, String userName, String password, String other);
    void setSearchAutoComplteText(List<String> titleList);
    void deleteNote(long id);

    void writeBackup(Context context,Activity activity,File backupfile, String filename);
    void readBackup(Context context,Activity activity);

    void resetBackup(String oldFilePath, String outFileName,Activity activity);
    void setChoice(int userChoice);
    void getChoice();

   /* void readBackup();
    void writeBackup(String oldFilePath, String outFileName,Activity activity);
    void resetBackup(File backupfile,String filename);*/
}
