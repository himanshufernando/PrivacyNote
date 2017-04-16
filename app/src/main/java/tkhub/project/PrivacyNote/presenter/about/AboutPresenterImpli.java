package tkhub.project.PrivacyNote.presenter.about;

import android.content.Context;

import tkhub.project.PrivacyNote.model.about.AboutInteractor;
import tkhub.project.PrivacyNote.model.about.AboutInteractorImpil;
import tkhub.project.PrivacyNote.ui.view.AboutView;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class AboutPresenterImpli implements AboutPresenter,AboutInteractor.OnLoginFinishedListener {

    private AboutView aboutView;
    Context context;
    AboutInteractor aboutInteractor;

    public AboutPresenterImpli(AboutView aboutview,Context context) {
        this.aboutView=aboutview;
        this.context =context;
        this.aboutInteractor = new AboutInteractorImpil();

    }

    @Override
    public void OnCurrentVersion() {
      aboutInteractor.getCurrentVersion(this,context);
    }

    @Override
    public void onFinishCurrentVersion(String result) {
        aboutView.setAppVersion(result);
    }
}
