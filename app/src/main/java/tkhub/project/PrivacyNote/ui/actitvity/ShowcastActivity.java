package tkhub.project.PrivacyNote.ui.actitvity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.codemybrainsout.onboarder.AhoyOnboarderActivity;
import com.codemybrainsout.onboarder.AhoyOnboarderCard;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.data.database.ShowcastDB;

/**
 * Created by Himanshu on 10/15/2016.
 */

public class ShowcastActivity extends AhoyOnboarderActivity {

    private Realm mRealm;
    Long tableSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        mRealm= Realm.getDefaultInstance();
        tableSize = mRealm.where(ShowcastDB.class).count();

        if(tableSize==0){
            AhoyOnboarderCard ahoyOnboarderCard1 = new AhoyOnboarderCard("Note", "A brief record of something written down to assist the memory or for future reference.", R.drawable.note);
            AhoyOnboarderCard ahoyOnboarderCard2 = new AhoyOnboarderCard("Privacy Note", "Note with Privacy, no internet no syncing 100% secure", R.drawable.logo);
            AhoyOnboarderCard ahoyOnboarderCard3 = new AhoyOnboarderCard("Privacy Note", "Note your privacy data such as password, and use it when you are needed.", R.drawable.logo);
            AhoyOnboarderCard ahoyOnboarderCard4 = new AhoyOnboarderCard("Fingerprint", "Use your fingerprint as a app password if your devices is capable.", R.drawable.fingerprint);

            ahoyOnboarderCard1.setBackgroundColor(R.color.black_transparent);
            ahoyOnboarderCard2.setBackgroundColor(R.color.black_transparent);
            ahoyOnboarderCard3.setBackgroundColor(R.color.black_transparent);
            ahoyOnboarderCard4.setBackgroundColor(R.color.black_transparent);

            List<AhoyOnboarderCard> pages = new ArrayList<>();

            pages.add(ahoyOnboarderCard1);
            pages.add(ahoyOnboarderCard2);
            pages.add(ahoyOnboarderCard3);
            pages.add(ahoyOnboarderCard4);

            for (AhoyOnboarderCard page : pages) {
                page.setTitleColor(R.color.white);
                page.setDescriptionColor(R.color.grey_200);
            }

            setFinishButtonTitle("Finish");
            showNavigationControls(true);
            setGradientBackground();

            Typeface face = Typeface.createFromAsset(getAssets(), "Font/Comfortaa-Light.ttf");
            setFont(face);

            setOnboardPages(pages);
        }else {
            Intent intent = new Intent(ShowcastActivity.this, LoginActivity.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ShowcastActivity.this, R.anim.animation, R.anim.animation2).toBundle();
            finish();
            startActivity(intent, bndlanimation);
        }


    }

    @Override
    public void onFinishButtonPressed() {


        Intent intent = new Intent(ShowcastActivity.this, LoginActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(ShowcastActivity.this, R.anim.animation, R.anim.animation2).toBundle();
        finish();
        startActivity(intent, bndlanimation);
    }
}
