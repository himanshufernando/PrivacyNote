package tkhub.project.PrivacyNote.presenter.login;

import android.content.Context;

import io.realm.Realm;
import tkhub.project.PrivacyNote.model.about.AboutInteractor;
import tkhub.project.PrivacyNote.model.about.AboutInteractorImpil;
import tkhub.project.PrivacyNote.model.login.LoginInteractor;
import tkhub.project.PrivacyNote.model.login.LoginInteractorImpil;
import tkhub.project.PrivacyNote.presenter.about.AboutPresenter;
import tkhub.project.PrivacyNote.ui.view.AboutView;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class LoginPresenterImpli implements LoginPresenter {

    LoginInteractor loginInteractor;
    public LoginPresenterImpli() {
        this.loginInteractor=new LoginInteractorImpil();
    }

    @Override
    public void OnShowcastDatabaseUpdate(Realm mRealm) {
        loginInteractor.updateShowcastDatabase(mRealm);
    }




}
