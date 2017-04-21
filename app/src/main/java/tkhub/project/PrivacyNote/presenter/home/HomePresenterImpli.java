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
import tkhub.project.PrivacyNote.model.home.HomeInteractor;
import tkhub.project.PrivacyNote.model.home.HomeInteractorImpil;
import tkhub.project.PrivacyNote.model.password.PasswordInteractor;
import tkhub.project.PrivacyNote.model.password.PasswordInteractorImpil;
import tkhub.project.PrivacyNote.presenter.password.PasswordPresenter;
import tkhub.project.PrivacyNote.ui.view.HomeView;
import tkhub.project.PrivacyNote.ui.view.PasswordView;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class HomePresenterImpli implements HomePresenter,HomeInteractor.OnFinishedListener{


    HomeView homeView;
    HomeInteractor homeInteractor;

    Context context;
    Activity activity;
    public HomePresenterImpli(Context context, Activity activity,HomeView homeView) {
        this.homeView=homeView;
        this.context=context;
        this.activity=activity;
        homeInteractor = new HomeInteractorImpil();
    }

    @Override
    public void setAllNote(ArrayList<NoteItem> noteItems,String keyword) {
        homeInteractor.setAllNote(noteItems,keyword,this);
    }

    @Override
    public void setAllNavagationItem(ArrayList<NavigationDrawerItem> navigationDrawerItems) {
        homeInteractor.setAllNavigationItem(navigationDrawerItems,this);
    }

    @Override
    public void getFingerprintAutherAccess() {
        homeInteractor.getFingerprintAutherAccess(this);
    }

    @Override
    public void saveNote(String title, String userName, String password, String other) {
        homeInteractor.saveNote(title,userName,password,other,this);
    }

    @Override
    public void setSearchAutoComplteText(List<String> titleList) {
        homeInteractor.setSearchAutoComplteText(titleList,this);
    }

    @Override
    public void deleteNote(long id) {
        homeInteractor.deleteNote(id,this);
    }

    @Override
    public void writeBackup(Context context, Activity activity, File backupfile, String filename) {
        homeInteractor.setWriteBackup(context,activity,backupfile,filename,this);
    }

    @Override
    public void readBackup(Context context, Activity activity) {
        homeInteractor.ReadBackup(context,activity);
    }

    @Override
    public void resetBackup(String oldFilePath, String outFileName, Activity activity) {
        homeInteractor.setResetBackup(oldFilePath,outFileName,activity,this);
    }


    @Override
    public void onsetAllNote() {
        homeView.onFinishedSetAllNote();
    }

    @Override
    public void onsetAllNavigationItems() {
        homeView.onFinishedNavigationItems();
    }

    @Override
    public void ongetFingerprintAutherAccess(boolean isAccess) {
        homeView.onFinishedGetFingerprintAccess(isAccess);
    }

    @Override
    public void onTitleEmpty() {
        homeView.onNoteSaveTitleEmpty();
    }

    @Override
    public void onSaveSuccess() {
        homeView.onNoteSaveSuccess();
    }

    @Override
    public void onSaveFail() {
        homeView.onNoteSaveFail();
    }

    @Override
    public void onSetSearchAutoComplteText() {
        homeView.onFinisheSetSearchAutoComplteText();
    }

    @Override
    public void onNoteDeleteSuccess() {
        homeView.onNoteDeleteSuccess();
    }

    @Override
    public void onNoteDeleteFail() {
        homeView.onNoteDeleteFail();
    }

    @Override
    public void onWriteBackupFinished(String saveFileName) {
        homeView.onFinishWriteBackup(saveFileName);
    }

    @Override
    public void onResetBackupFinished() {
        homeView.onFinishResetBackup();
    }

    @Override
    public void onResetBackupError(String error) {
        homeView.onErrorResetBackup(error);
    }


}
