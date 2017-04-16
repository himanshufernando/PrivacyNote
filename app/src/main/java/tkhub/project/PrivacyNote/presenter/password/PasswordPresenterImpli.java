package tkhub.project.PrivacyNote.presenter.password;

import android.app.Activity;
import android.content.Context;

import io.realm.Realm;
import tkhub.project.PrivacyNote.model.login.LoginInteractor;
import tkhub.project.PrivacyNote.model.login.LoginInteractorImpil;
import tkhub.project.PrivacyNote.model.password.PasswordInteractor;
import tkhub.project.PrivacyNote.model.password.PasswordInteractorImpil;
import tkhub.project.PrivacyNote.presenter.login.LoginPresenter;
import tkhub.project.PrivacyNote.ui.view.PasswordView;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class PasswordPresenterImpli implements PasswordPresenter,PasswordInteractor.OnFinishedListener {

    PasswordInteractor passwordInteractor;
    PasswordView passwordView;
    Context context;
    Activity activity;
    public PasswordPresenterImpli(Context context, Activity activity,PasswordView passwordView) {
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
    }
}
