package tkhub.project.PrivacyNote.Layout;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tkhub.project.PrivacyNote.DB.AppuserDB;
import tkhub.project.PrivacyNote.DB.SecurityDB;
import tkhub.project.PrivacyNote.R;

/**
 * Created by Himanshu on 2/13/2017.
 */

public class SecurityQuestion extends Activity {

    MaterialSpinner spinnerColor, spinnerSport, spinnerYear;
    List<String> yearList;
    String color = "", sport = "", year = "", city = "";
    Button done;
    EditText edittextCity;
    int parentLayout;

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_security_question);

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();
        parentLayout = getIntent().getIntExtra("PerantLayout", 0);


        spinnerColor = (MaterialSpinner) findViewById(R.id.spinner_color);
        spinnerSport = (MaterialSpinner) findViewById(R.id.spinner_sport);
        spinnerYear = (MaterialSpinner) findViewById(R.id.spinner_bornyear);

        edittextCity = (EditText) findViewById(R.id.edittext_city);

        done = (Button) findViewById(R.id.btn_done);

        if (parentLayout == 1) {
            done.setText("Done");
        } else {
            done.setText("Reset");
        }

        spinnerColor.setItems("Select Your Color", "Blue", "Green", "Red", "Yellow", "Purple", "Pink", "Black", "Orange", "White", "Brown", "Grey", "Gold", "Turquoise", "Violet", "Magenta", "Silver", "Indigo",
                "Lavender", "Vermilion", "Teal", "Beige", "Rose");


        spinnerSport.setItems("Select Your sport", "Basketball", "Basketball", "Tennis", "Baseball", "Football", "Cricket", "Badminton", "American football", "Golf", "Rugby football", "Volleyball", "Ice hockey", "Ice skating", "Gymnastics", "Bowling", "Skiing", "Boxing",
                "Softball", "Martial arts", "Surfing", "Netball", "Water polo", "Figure skating", "Polo", "Snowboarding", "Walking", "Squash", "Motorsport", "Field hockey", "Rugby", "Table tennis", "Skateboarding", "Lacrosse", "Karate", "Climbing", "Equestrianism", "Roller skating", "Triathlon", "Running",
                "Swimming", "Cycling", "Wrestling");

        setYear();

        spinnerColor.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                color = item;
            }
        });
        spinnerSport.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                sport = item;
            }
        });
        spinnerYear.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                year = item;
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city = edittextCity.getText().toString();
                if (color.equals("")) {
                    Toast.makeText(SecurityQuestion.this, "Please select the color", Toast.LENGTH_LONG).show();
                } else if (sport.equals("")) {
                    Toast.makeText(SecurityQuestion.this, "Please select the sport", Toast.LENGTH_LONG).show();
                } else if (year.equals("")) {
                    Toast.makeText(SecurityQuestion.this, "Please select the born year", Toast.LENGTH_LONG).show();
                } else if (city.equals("") || city.isEmpty()) {
                    Toast.makeText(SecurityQuestion.this, "Please mention the born city", Toast.LENGTH_LONG).show();
                } else {
                    if (parentLayout == 1) {
                        final Long tableSize = mRealm.where(SecurityDB.class).count();
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                Object primaryKeyValue = tableSize + 1;
                                SecurityDB securityDB = realm.createObject(SecurityDB.class, primaryKeyValue);
                                securityDB.setColor(color);
                                securityDB.setSport(sport);
                                securityDB.setYear(year);
                                securityDB.setCity(city);
                                securityDB.setAllowe(1);

                                Toast.makeText(SecurityQuestion.this, "Security Question added successfully", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(SecurityQuestion.this, Home.class);
                                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SecurityQuestion.this, R.anim.animation, R.anim.animation2).toBundle();
                                finish();
                                startActivity(intent, bndlanimation);
                            }
                        });
                    } else {
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                Long ap = mRealm.where(SecurityDB.class).
                                        equalTo("color", color).
                                        equalTo("sport", sport).
                                        equalTo("year", year).
                                        equalTo("city", city).count();

                                if (ap == 0) {
                                    //meesage
                                } else {
                                    final RealmResults<AppuserDB> results = realm.where(AppuserDB.class).findAll();

                                    results.deleteAllFromRealm();
                                   

                                    Intent intent = new Intent(SecurityQuestion.this, Password.class);
                                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(SecurityQuestion.this, R.anim.animation, R.anim.animation2).toBundle();
                                    finish();
                                    startActivity(intent, bndlanimation);
                                }


                            }
                        });
                    }


                }
            }
        });

    }

    public void setYear() {
        yearList = new ArrayList<String>();
        yearList.clear();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        yearList.add("Select Your Born Year");
        for (int a = year; a > 1980; a--) {
            yearList.add(String.valueOf(a));
        }
        spinnerYear.setItems(yearList);
    }
}
