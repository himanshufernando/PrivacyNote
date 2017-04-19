package tkhub.project.PrivacyNote.model.home;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.data.database.SecurityDB;
import tkhub.project.PrivacyNote.data.model.NavigationDrawerItem;
import tkhub.project.PrivacyNote.data.database.NoteDB;
import tkhub.project.PrivacyNote.data.model.NoteItem;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class HomeInteractorImpil implements HomeInteractor {

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
            final Realm realmtest = Realm.getDefaultInstance();
            final Long tableSize = realmtest.where(NoteDB.class).count();
            realmtest.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    Object primaryKeyValue = tableSize + 1;
                    NoteDB note = bgRealm.createObject(NoteDB.class, primaryKeyValue);
                    note.setTitle(title);
                    note.setUserName(userName);
                    note.setPassword(password);
                    note.setOther(other);
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
}
