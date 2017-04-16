package tkhub.project.PrivacyNote.presenter.home;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
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

public class HomePresenterImpli implements HomePresenter,HomeInteractor.OnFinishedListener {


    HomeView homeView;
    HomeInteractor homeInteractor;
    public HomePresenterImpli(HomeView homeView) {
        this.homeView=homeView;
        homeInteractor = new HomeInteractorImpil();
    }

    @Override
    public void setAllNote(Realm realm,ArrayList<NoteItem> noteItems) {
        homeInteractor.setAllNote(realm,noteItems,this);
    }

    @Override
    public void onsetAllNote() {

    }
/*
    PasswordInteractor passwordInteractor;
    PasswordView passwordView;
    Context context;
    Activity activity;
    public HomePresenterImpli(Context context, Activity activity, PasswordView passwordView) {
        this.passwordView=passwordView;
        this.context=context;
        this.activity=activity;
        this.passwordInteractor = new PasswordInteractorImpil();

    }
    @Override
    public void readBackup() {
        passwordInteractor.setReadBackup(context,activity);
    }

    @Override
    public void writeBackup(String oldFilePath, String outFileName, Activity activity) {
        passwordInteractor.setWriteBackup(oldFilePath,outFileName,activity,this);
    }


    @Override
    public void onWriteBackupFinished() {
        passwordView.onFinishWriteBackup();
    }

    @Override
    public void onWriteBackupError(String error) {
        passwordView.onErrorWriteBackup(error);
    }*/
}
