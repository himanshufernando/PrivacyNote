package tkhub.project.PrivacyNote.model.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import tkhub.project.PrivacyNote.data.database.NoteDB;
import tkhub.project.PrivacyNote.data.model.NoteItem;
import tkhub.project.PrivacyNote.model.password.PasswordInteractor;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class HomeInteractorImpil implements HomeInteractor {
    @Override
    public void setAllNote(Realm realm,ArrayList<NoteItem> noteItems, OnFinishedListener onFinishedListener) {
        noteItems.clear();
        for (NoteDB no : realm.where(NoteDB.class).equalTo("allowe", 0).findAll()) {
            noteItems.add(new NoteItem(no.getId(), no.getTitle(), no.getUserName(), no.getPassword(), no.getOther()));
            System.out.println("sssssss :"+no.getTitle());

        }
        onFinishedListener.onsetAllNote();
    }


}
