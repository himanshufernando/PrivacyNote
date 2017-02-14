package tkhub.project.PrivacyNote.Layout;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import tkhub.project.PrivacyNote.DB.AppuserDB;
import tkhub.project.PrivacyNote.DB.ResetDB;
import tkhub.project.PrivacyNote.Font.TextViewFontAwesome;
import tkhub.project.PrivacyNote.R;

/**
 * Created by Himanshu on 8/4/2016.
 */
public class Password extends Activity implements Animation.AnimationListener {

    Button one, two, three, four, five, six, seven, eight, nine, zero, reset, back;
    private Vibrator mVibrator;

    TextViewFontAwesome t1, t2, t3, t4;
    TextView message;

    String passwordToBeconfirmd = "";
    String password = "";

    RelativeLayout layoutvibrate;

    private Realm mRealm;
    private RealmConfiguration realmConfig;

    int passwordStatues = 0;
    int confrimStatues = 0;
    int resetStatues = 0;
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);


        Typeface tf = Typeface.createFromAsset(getAssets(), "Font/Comfortaa-Light.ttf");
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        t1 = (TextViewFontAwesome) findViewById(R.id.textView_icon_1);
        t2 = (TextViewFontAwesome) findViewById(R.id.textView_icon_2);
        t3 = (TextViewFontAwesome) findViewById(R.id.textView_icon_3);
        t4 = (TextViewFontAwesome) findViewById(R.id.textView_icon_4);

        message = (TextView) findViewById(R.id.textViewMessage);

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();




        final Long tableSize = mRealm.where(AppuserDB.class).count();
        final Long resetTableSize = mRealm.where(ResetDB.class).count();

        SharedPreferences  pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        System.out.println("ssdsdsdssd :"+ pref.getInt("01", 2));

        if (tableSize == 0) {
            message.setText("Please set the Password");
            message.setTextColor(getResources().getColor(R.color.iconRed));
            passwordStatues = 0;
        } else {
            passwordStatues = 1;
        }


        one = (Button) findViewById(R.id.btn1);
        one.setTypeface(tf);

        two = (Button) findViewById(R.id.btn2);
        two.setTypeface(tf);

        ////
        three = (Button) findViewById(R.id.btn3);
        three.setTypeface(tf);

        four = (Button) findViewById(R.id.btn4);
        four.setTypeface(tf);

        five = (Button) findViewById(R.id.btn5);
        five.setTypeface(tf);

        six = (Button) findViewById(R.id.btn6);
        six.setTypeface(tf);

        seven = (Button) findViewById(R.id.btn7);
        seven.setTypeface(tf);

        eight = (Button) findViewById(R.id.btn8);
        eight.setTypeface(tf);

        nine = (Button) findViewById(R.id.btn9);
        nine.setTypeface(tf);

        zero = (Button) findViewById(R.id.btn0);
        zero.setTypeface(tf);

        reset = (Button) findViewById(R.id.btnreset);
        reset.setTypeface(tf);

        back = (Button) findViewById(R.id.btnBack);
        back.setTypeface(tf);


        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                password = password + "1";
                checkPassword();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                password = password + "2";
                checkPassword();
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                password = password + "3";
                checkPassword();
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                password = password + "4";
                checkPassword();
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                password = password + "5";
                checkPassword();
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                password = password + "6";
                checkPassword();
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                password = password + "7";
                checkPassword();
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                password = password + "8";
                checkPassword();
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                password = password + "9";
                checkPassword();
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                password = password + "0";
                checkPassword();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                if (passwordStatues == 0) {
                    message.setTextColor(getResources().getColor(R.color.iconRed));
                    message.setText("You don't have a Password to reset,Please set initial Password");

                    t1.setText(R.string.icon_circle);
                    t2.setText(R.string.icon_circle);
                    t3.setText(R.string.icon_circle);
                    t4.setText(R.string.icon_circle);
                    password = "";
                    passwordToBeconfirmd = "";
                } else {

                }


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(100);
                try {
                    if (password.length() == 1) {
                        t1.setText(R.string.icon_circle);
                        t2.setText(R.string.icon_circle);
                        t3.setText(R.string.icon_circle);
                        t4.setText(R.string.icon_circle);
                        password = "";
                    } else {
                        password = password.substring(0, password.length() - 1);
                        checkPassword();
                    }


                } catch (StringIndexOutOfBoundsException ex) {

                    t1.setText(R.string.icon_circle);
                    t2.setText(R.string.icon_circle);
                    t3.setText(R.string.icon_circle);
                    t4.setText(R.string.icon_circle);

                }


            }
        });
    }


    public void checkPassword() {
        message.setText("");

        if (password.length() == 1) {
            t1.setText(R.string.icon_fill_circle);
            t2.setText(R.string.icon_circle);
            t3.setText(R.string.icon_circle);
            t4.setText(R.string.icon_circle);

        } else if (password.length() == 2) {
            t1.setText(R.string.icon_fill_circle);
            t2.setText(R.string.icon_fill_circle);
            t3.setText(R.string.icon_circle);
            t4.setText(R.string.icon_circle);
        } else if (password.length() == 3) {
            t1.setText(R.string.icon_fill_circle);
            t2.setText(R.string.icon_fill_circle);
            t3.setText(R.string.icon_fill_circle);
            t4.setText(R.string.icon_circle);
        } else if (password.length() == 4) {
            t1.setText(R.string.icon_fill_circle);
            t2.setText(R.string.icon_fill_circle);
            t3.setText(R.string.icon_fill_circle);
            t4.setText(R.string.icon_fill_circle);


            if (passwordStatues == 0) {

                t1.setText(R.string.icon_circle);
                t2.setText(R.string.icon_circle);
                t3.setText(R.string.icon_circle);
                t4.setText(R.string.icon_circle);

                if (passwordToBeconfirmd.equals("")) {
                    passwordToBeconfirmd = password;
                    message.setText("Confirm your password");
                    message.setTextColor(getResources().getColor(R.color.iconBlue));
                    password = "";

                } else {
                    if (passwordToBeconfirmd.equals(password)) {
                        final Long tableSize = mRealm.where(AppuserDB.class).count();
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Object primaryKeyValue = tableSize + 1;
                                AppuserDB userAdd = realm.createObject(AppuserDB.class, primaryKeyValue);
                                userAdd.setPassword(password);
                                Toast.makeText(Password.this, "Password added successfully", Toast.LENGTH_LONG).show();
                                password = "";
                                confrimStatues = 1;
                                passwordToBeconfirmd = "";
                                message.setText("");


                                Intent intent = new Intent(Password.this, SecurityQuestion.class);
                                intent.putExtra("PerantLayout",1);
                                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Password.this, R.anim.animation, R.anim.animation2).toBundle();
                                finish();
                                startActivity(intent, bndlanimation);


                              //  sucsessAccess();
                            }
                        });
                    } else {
                        message.setText("Password does not match the confirm password");
                        message.setTextColor(getResources().getColor(R.color.iconRed));
                        mVibrator.vibrate(300);
                        password = "";

                    }


                }


            } else if (resetStatues == 1) {
                AppuserDB toEdit = mRealm.where(AppuserDB.class).equalTo("id", 1).findFirst();
                if (toEdit.getPassword().equals(password)) {
                    if (passwordToBeconfirmd.equals("")) {
                        passwordToBeconfirmd = password;
                        message.setText("Confirm your password");
                        message.setTextColor(getResources().getColor(R.color.iconBlue));
                        password = "";

                    } else {
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                AppuserDB toEdit = mRealm.where(AppuserDB.class).equalTo("id", 1).findFirst();
                                toEdit.setPassword(password);
                                Toast.makeText(Password.this, "Password reset successfully", Toast.LENGTH_LONG).show();
                                password = "";
                                confrimStatues = 1;
                                passwordToBeconfirmd = "";
                                message.setText("");
                                sucsessAccess();
                            }
                        });
                    }

                } else {
                    message.setText("Password does not match the confirm password");
                    message.setTextColor(getResources().getColor(R.color.iconRed));
                    t1.setText(R.string.icon_circle);
                    t2.setText(R.string.icon_circle);
                    t3.setText(R.string.icon_circle);
                    t4.setText(R.string.icon_circle);
                    password = "";
                    mVibrator.vibrate(300);
                }

            } else {
                Long ap = mRealm.where(AppuserDB.class).equalTo("password", password).count();
                if (ap != 0) {
                    sucsessAccess();
                } else {
                    message.setText("Password wrong");
                    message.setTextColor(getResources().getColor(R.color.iconRed));
                    t1.setText(R.string.icon_circle);
                    t2.setText(R.string.icon_circle);
                    t3.setText(R.string.icon_circle);
                    t4.setText(R.string.icon_circle);
                    password = "";
                    mVibrator.vibrate(300);
                }
            }


        }
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

    public void vibrateLavel(int level) {
        mVibrator.vibrate(level);
    }


    public void sucsessAccess() {

        final Dialog dialogBox;
        dialogBox = new Dialog(Password.this);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.setContentView(R.layout.dilaog_progress);
        dialogBox.setCancelable(false);
        dialogBox.show();

        new Handler().postDelayed(new Runnable() {
            public void run() {

                dialogBox.dismiss();


                Intent intent = new Intent(Password.this, Home.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Password.this, R.anim.animation, R.anim.animation2).toBundle();
                finish();
                startActivity(intent, bndlanimation);
            }
        }, 5000);


    }

}
