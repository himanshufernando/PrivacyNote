package tkhub.project.PrivacyNote.model.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmResults;
import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.data.database.AppuserDB;
import tkhub.project.PrivacyNote.data.database.ChoiceDB;
import tkhub.project.PrivacyNote.data.database.SecurityDB;
import tkhub.project.PrivacyNote.data.model.NavigationDrawerItem;
import tkhub.project.PrivacyNote.data.database.NoteDB;
import tkhub.project.PrivacyNote.data.model.NoteItem;
import tkhub.project.PrivacyNote.ui.actitvity.HomeActivity;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class HomeInteractorImpil implements HomeInteractor {

    static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_WRITE = 123;
    static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_READ = 124;

    @Override
    public void setAllNote(ArrayList<NoteItem> noteItems, String keyword, OnFinishedListener onFinishedListener) {
        final Realm realm = Realm.getDefaultInstance();
        noteItems.clear();
        if (keyword.equals("")) {
            for (NoteDB no : realm.where(NoteDB.class).equalTo("allowe", 0).findAllAsync()) {
                noteItems.add(new NoteItem(no.getId(), no.getTitle(), no.getUserName(), no.getPassword(), no.getOther()));
            }
        } else {
            for (NoteDB no : realm.where(NoteDB.class).beginsWith("title", keyword, Case.INSENSITIVE).equalTo("allowe", 0).findAllAsync()) {
                noteItems.add(new NoteItem(no.getId(), no.getTitle(), no.getUserName(), no.getPassword(), no.getOther()));
            }
        }
        onFinishedListener.onsetAllNote();
    }

    @Override
    public void setAllNavigationItem(ArrayList<NavigationDrawerItem> navigationDrawerItems, OnFinishedListener onFinishedListener) {

        final Realm realm = Realm.getDefaultInstance();
        if (navigationDrawerItems.isEmpty()) {
            navigationDrawerItems.add(new NavigationDrawerItem("Home", R.string.icon_navigation_home));
            navigationDrawerItems.add(new NavigationDrawerItem("Backup", R.string.icon_navigation_backup));
            if (realm.where(SecurityDB.class).count() > 0) {
                navigationDrawerItems.add(new NavigationDrawerItem("Password Reset", R.string.icon_navigation_reset));
            } else {
                navigationDrawerItems.add(new NavigationDrawerItem("Restore ", R.string.icon_navigation_restore));
            }
            navigationDrawerItems.add(new NavigationDrawerItem("About", R.string.icon_navigation_about));
        } else {
        }
        onFinishedListener.onsetAllNavigationItems();
    }

    @Override
    public void getFingerprintAutherAccess(OnFinishedListener onFinishedListener) {
        final Realm realm = Realm.getDefaultInstance();
        boolean isAccess;
        if (realm.where(SecurityDB.class).count() > 0) {
            isAccess = false;
        } else {
            isAccess = true;
        }
        onFinishedListener.ongetFingerprintAutherAccess(isAccess);
    }

    @Override
    public void saveNote(final String title, final String userName, final String password, final String other, final OnFinishedListener onFinishedListener) {
        if (title.equals("")) {
            onFinishedListener.onTitleEmpty();
        } else {
            final Realm realm = Realm.getDefaultInstance();
            final Date date = new Date(System.currentTimeMillis());
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    Object primaryKeyValue = System.currentTimeMillis();
                    NoteDB note = bgRealm.createObject(NoteDB.class, primaryKeyValue);
                    note.setTitle(title);
                    note.setUserName(userName);
                    note.setPassword(password);
                    note.setOther(other);
                    note.setAdDate(date);
                    note.setAllowe(0);
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    onFinishedListener.onSaveSuccess();
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    System.out.println("sssvf :"+error);
                    onFinishedListener.onSaveFail();
                }
            });
        }
    }

    @Override
    public void setSearchAutoComplteText(List<String> titleList, OnFinishedListener onFinishedListener) {
        titleList.clear();
        final Realm realm = Realm.getDefaultInstance();
        for (NoteDB no : realm.where(NoteDB.class).findAll()) {
            titleList.add(no.getTitle());
        }
        onFinishedListener.onSetSearchAutoComplteText();
    }

    @Override
    public void deleteNote(final long id, final OnFinishedListener onFinishedListener) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                final RealmResults<NoteDB> results = realm.where(NoteDB.class).equalTo("id",id).findAll();
                System.out.println("delete id :"+id);
                System.out.println("delete results :"+results);
                results.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                onFinishedListener.onNoteDeleteSuccess();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                onFinishedListener.onNoteDeleteFail();
            }
        });
    }

    @Override
    public void setWriteBackup(Context context, Activity activity, File file, String filename, OnFinishedListener onFinishedListener) {
         if (!checkPermission(context)) {
            requestPermission(activity);
        } else {
             final Realm realm = Realm.getDefaultInstance();
             File exportRealmFile;
             file.mkdirs();
             exportRealmFile = new File(file, filename);
             exportRealmFile.delete();
             realm.writeCopyTo(exportRealmFile);

             String msg = "File exported to Path: " + file + "/" + filename;
             realm.close();
             onFinishedListener.onWriteBackupFinished(msg);
        }
    }

    @Override
    public void ReadBackup(Context context, Activity activity) {
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
    public void setResetBackup(String oldFilePath, String outFileName, Activity activity, OnFinishedListener onFinishedListener) {
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
            onFinishedListener.onResetBackupFinished();
        } catch (IOException e) {
            onFinishedListener.onResetBackupError(e.toString());
            e.printStackTrace();
        }catch (Exception ex){
            onFinishedListener.onResetBackupError(ex.toString());
        }
    }

    @Override
    public void setUserChoices(final int userChoice) {
        final Realm realm = Realm.getDefaultInstance();
        final Long tableSize = realm.where(ChoiceDB.class).count();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                if(tableSize==0){
                    ChoiceDB choice = bgRealm.createObject(ChoiceDB.class);
                    choice.setId(userChoice);

                }else {
                    ChoiceDB choice = bgRealm.where(ChoiceDB.class).findFirst();
                    choice.setId(userChoice);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }

    @Override
    public void getUserChoices(OnFinishedListener onFinishedListener) {
        final Realm realm = Realm.getDefaultInstance();
        int result;
        ChoiceDB choiceDB = realm.where(ChoiceDB.class).findFirst();
        if (realm.where(SecurityDB.class).count() == 0){
            onFinishedListener.ongetUserChoices(-1);
        }else if (choiceDB == null) {
            onFinishedListener.ongetUserChoices(0);
        }else {
            result=choiceDB.getId();
            onFinishedListener.ongetUserChoices(result);
        }
    }


    private boolean checkPermissionRead(Context context) {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private boolean checkPermission(Context context) {
        int result = ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissionRead(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},  MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_READ);
    }
    private void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_WRITE);
    }

}
