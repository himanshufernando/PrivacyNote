package tkhub.project.PrivacyNote.Layout;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.mikhaellopez.circularimageview.CircularImageView;

import tkhub.project.PrivacyNote.R;

/**
 * Created by Himanshu on 8/12/2016.
 */
public class About extends Activity implements Animation.AnimationListener {

    CircularImageView imagLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);
        imagLogo = (CircularImageView)findViewById(R.id.imageView_logo);

        final Animation animMovelaypot = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animationtk);
        animMovelaypot.setAnimationListener(this);
        imagLogo.startAnimation(animMovelaypot);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(About.this, Home.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(About.this, R.anim.animation, R.anim.animation2).toBundle();
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
}
