package tkhub.project.PrivacyNote.ui.actitvity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import tkhub.project.PrivacyNote.presenter.about.AboutPresenter;
import tkhub.project.PrivacyNote.presenter.about.AboutPresenterImpli;
import tkhub.project.PrivacyNote.ui.view.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import tkhub.project.PrivacyNote.R;

/**
 * Created by Himanshu on 8/12/2016.
 */
public class AboutActivity extends Activity implements Animation.AnimationListener,AboutView {


    @BindView(R.id.textView12) TextView versionName;
    @BindView(R.id.imageView_logo) CircularImageView imagLogo;

    private AboutPresenter aboutPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        ButterKnife.bind(this);

        OnActivityLoad();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AboutActivity.this, HomeActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(AboutActivity.this, R.anim.animation, R.anim.animation2).toBundle();
        finish();
        startActivity(intent, bndlanimation);

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    @Override
    public void setAppVersion(String appversion) {
        versionName.setText(appversion);
    }


    public void setLogoAnimation() {
        final Animation animMovelaypot = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animationtk);
        animMovelaypot.setAnimationListener(this);
        imagLogo.startAnimation(animMovelaypot);
    }

    public void OnActivityLoad(){
        aboutPresenter =new AboutPresenterImpli(this,getApplicationContext());
        aboutPresenter.OnCurrentVersion();
        setLogoAnimation();
    }

}
