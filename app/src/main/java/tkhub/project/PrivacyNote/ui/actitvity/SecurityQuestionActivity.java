package tkhub.project.PrivacyNote.ui.actitvity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.data.database.AppuserDB;
import tkhub.project.PrivacyNote.data.database.SecurityDB;
import tkhub.project.PrivacyNote.presenter.Securityquestion.SecurityQuesPresenter;
import tkhub.project.PrivacyNote.presenter.Securityquestion.SecurityQuesPresenterImpli;
import tkhub.project.PrivacyNote.ui.view.SecurityQuesView;

/**
 * Created by Himanshu on 2/13/2017.
 */

public class SecurityQuestionActivity extends Activity implements SecurityQuesView{


    @BindView(R.id.spinner_color) MaterialSpinner spinnerColor;
    @BindView(R.id.spinner_sport) MaterialSpinner spinnerSport;
    @BindView(R.id.spinner_bornyear) MaterialSpinner spinnerYear;

    @BindView(R.id.edittext_city) EditText edittextCity;
    @BindView(R.id.btn_done) Button done;

    SecurityQuesPresenter securityQuesPresenter;


    List<String> yearList;
    String color = "", sport = "", year = "", city = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_security_question);
        ButterKnife.bind(this);

        Realm.init(this);


        setAddButtonLayout();


        securityQuesPresenter = new SecurityQuesPresenterImpli(this);

        securityQuesPresenter.setColors();
        securityQuesPresenter.setGames();
        securityQuesPresenter.setYears();


    }


    @Override
    public void onBackPressed() {
        if(getLayoutStatus() == 1){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(SecurityQuestionActivity.this);
            alertDialog.setTitle("Exit");
            alertDialog.setMessage("Are you sure you want Exit ? if you exit you nedd to set password again");
            alertDialog.setIcon(R.drawable.fingerprint);
            alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    securityQuesPresenter.deleteAllUsers();
                    finish();
                    System.exit(0);
                }
            });
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }else {
            Intent intent = new Intent(SecurityQuestionActivity.this, HomeActivity.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SecurityQuestionActivity.this, R.anim.animation, R.anim.animation2).toBundle();
            finish();
            startActivity(intent, bndlanimation);
        }
    }

    private int getLayoutStatus(){
       return getIntent().getIntExtra("PerantLayout", 0);
    }
    private void setAddButtonLayout(){
        if(getLayoutStatus()==1){done.setText("Done");
        }else {done.setText("Reset");
        }
    }

    private void setUserErorrMessage(String title,String message){
        new android.support.v7.app.AlertDialog.Builder(SecurityQuestionActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }

    @OnClick(R.id.btn_done)
    public void onClick(View view) {
        securityQuesPresenter.setSecurityQues(spinnerColor.getText().toString(),
                spinnerSport.getText().toString(),spinnerYear.getText().toString(),edittextCity.getText().toString(),getLayoutStatus());
    }

    @Override
    public void setColors(List<String> colors) {
        spinnerColor.setItems(colors);
    }

    @Override
    public void setGames(List<String> games) {
        spinnerSport.setItems(games);
    }

    @Override
    public void setYears(List<String> years) {
        spinnerYear.setItems(years);
    }

    @Override
    public void setColorEmptyError() {
        Toast.makeText(SecurityQuestionActivity.this, "Please select the color", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setGameEmptyError() {
        Toast.makeText(SecurityQuestionActivity.this, "Please select the sport", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setYearEmptyError() {
        Toast.makeText(SecurityQuestionActivity.this, "Please select the born year", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setCityEmptyError() {
        Toast.makeText(SecurityQuestionActivity.this, "Please mention the born city", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setSelectColorError() {
        setUserErorrMessage("Favorite Color","You'r favorite color is wrong, Please try again");
    }

    @Override
    public void setSelectGameError() {
        setUserErorrMessage("Favorite Sport","You'r favorite sport is wrong, Please try again");
    }

    @Override
    public void setSelectYearError() {
        setUserErorrMessage("Born Year","You'r born year is wrong, Please try again");
    }

    @Override
    public void setSelectCityError() {
        setUserErorrMessage("Born City","You'r born city is wrong, Please try again");

    }

    @Override
    public void setAddSuccess() {
        Toast.makeText(SecurityQuestionActivity.this, "Security Question added successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(SecurityQuestionActivity.this, HomeActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SecurityQuestionActivity.this, R.anim.animation, R.anim.animation2).toBundle();
        finish();
        startActivity(intent, bndlanimation);
    }

    @Override
    public void setAddFail(String reason) {
        setUserErorrMessage("Security Question",reason);
    }

    @Override
    public void setResetSuccess() {
        Intent intent = new Intent(SecurityQuestionActivity.this, PasswordActivity.class);
        intent.putExtra("resetype",1);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SecurityQuestionActivity.this, R.anim.animation, R.anim.animation2).toBundle();
        finish();
        startActivity(intent, bndlanimation);
    }

    @Override
    public void setResetFail(String reason) {

    }
}
