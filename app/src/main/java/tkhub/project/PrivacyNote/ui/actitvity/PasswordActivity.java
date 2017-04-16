package tkhub.project.PrivacyNote.ui.actitvity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.data.database.AppuserDB;
import tkhub.project.PrivacyNote.data.database.ResetDB;
import tkhub.project.PrivacyNote.data.database.ShowcastDB;
import tkhub.project.PrivacyNote.presenter.password.PasswordPresenter;
import tkhub.project.PrivacyNote.presenter.password.PasswordPresenterImpli;
import tkhub.project.PrivacyNote.ui.font.TextViewFontAwesome;
import tkhub.project.PrivacyNote.ui.view.PasswordView;

/**
 * Created by Himanshu on 8/4/2016.
 */
public class PasswordActivity extends Activity implements Animation.AnimationListener, PasswordView {

    @BindView(R.id.btn1)
    Button one;
    @BindView(R.id.btn2)
    Button two;
    @BindView(R.id.btn3)
    Button three;
    @BindView(R.id.btn4)
    Button four;
    @BindView(R.id.btn5)
    Button five;
    @BindView(R.id.btn6)
    Button six;
    @BindView(R.id.btn7)
    Button seven;
    @BindView(R.id.btn8)
    Button eight;
    @BindView(R.id.btn9)
    Button nine;
    @BindView(R.id.btn0)
    Button zero;
    @BindView(R.id.btnreset)
    Button reset;
    @BindView(R.id.btnBack)
    Button back;
    @BindView(R.id.btnrestore)
    Button restore;
    @BindView(R.id.textView_icon_1)
    TextViewFontAwesome t1;
    @BindView(R.id.textView_icon_2)
    TextViewFontAwesome t2;
    @BindView(R.id.textView_icon_3)
    TextViewFontAwesome t3;
    @BindView(R.id.textView_icon_4)
    TextViewFontAwesome t4;
    @BindView(R.id.textViewMessage)
    TextView message;


    private Vibrator mVibrator;
    String passwordToBeconfirmd = "";
    String password = "";

    private Realm mRealm;


    int passwordStatues = 0;
    int confrimStatues = 0;
    int resetStatues = 0;
    int status;
    int resetype;
    private String IMPORT_REALM_FILE_NAME = "default.realm";

    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_READ = 124;

    PasswordPresenter passwordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);

        setTypeface();
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        resetype = getIntent().getIntExtra("resetype", 0);

        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        passwordPresenter = new PasswordPresenterImpli(this,PasswordActivity.this,this);

        setPasswordStatues();


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

                    Intent intent = new Intent(PasswordActivity.this, SecurityQuestionActivity.class);
                    intent.putExtra("PerantLayout", 2);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(PasswordActivity.this, R.anim.animation, R.anim.animation2).toBundle();
                    finish();
                    startActivity(intent, bndlanimation);

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

    private void setTypeface() {
        Typeface tf = Typeface.createFromAsset(getAssets(), "Font/Comfortaa-Light.ttf");
        one.setTypeface(tf);
        two.setTypeface(tf);
        three.setTypeface(tf);
        four.setTypeface(tf);
        five.setTypeface(tf);
        six.setTypeface(tf);
        seven.setTypeface(tf);
        eight.setTypeface(tf);
        nine.setTypeface(tf);
        zero.setTypeface(tf);
        reset.setTypeface(tf);
        back.setTypeface(tf);
        restore.setTypeface(tf);
    }

    private void setPasswordStatues() {
        final Long tableSize = mRealm.where(AppuserDB.class).count();
        if (tableSize == 0) {
            message.setText("Please set the Password");
            message.setTextColor(getResources().getColor(R.color.iconRed));
            passwordStatues = 0;
        } else {
            passwordStatues = 1;
        }
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
                                Toast.makeText(PasswordActivity.this, "Password added successfully", Toast.LENGTH_LONG).show();
                                password = "";
                                confrimStatues = 1;
                                passwordToBeconfirmd = "";
                                message.setText("");
                                Intent intent;

                                if (resetype == 1) {
                                    intent = new Intent(PasswordActivity.this, HomeActivity.class);
                                } else {
                                    intent = new Intent(PasswordActivity.this, SecurityQuestionActivity.class);
                                    intent.putExtra("PerantLayout", 1);
                                }


                                ShowcastDB showcast = realm.createObject(ShowcastDB.class);
                                showcast.setId(1);
                                showcast.setCount(5);


                                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(PasswordActivity.this, R.anim.animation, R.anim.animation2).toBundle();
                                finish();
                                startActivity(intent, bndlanimation);

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
                                Toast.makeText(PasswordActivity.this, "Password reset successfully", Toast.LENGTH_LONG).show();
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


    public void sucsessAccess() {

        final Dialog dialogBox;
        dialogBox = new Dialog(PasswordActivity.this);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.setContentView(R.layout.dilaog_progress);
        dialogBox.setCancelable(false);
        dialogBox.show();

        new Handler().postDelayed(new Runnable() {
            public void run() {

                dialogBox.dismiss();


                Intent intent = new Intent(PasswordActivity.this, HomeActivity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(PasswordActivity.this, R.anim.animation, R.anim.animation2).toBundle();
                finish();
                startActivity(intent, bndlanimation);
            }
        }, 5000);


    }

    @OnClick(R.id.btnrestore)
    public void onClick(View v) {
        passwordPresenter.readBackup();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_READ) {
            if (grantResults.length > 0) {
                boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (locationAccepted) {
                    passwordPresenter.readBackup();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                }
            }
        } else {

        }

    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PasswordActivity.this);
        alertDialog.setTitle("Exit");
        alertDialog.setMessage("Are you sure you want Exit ?");
        alertDialog.setIcon(R.drawable.fingerprint);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
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


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            passwordPresenter.writeBackup(filePath, IMPORT_REALM_FILE_NAME, PasswordActivity.this);
        } else {

        }

    }
    @Override
    public void onFinishWriteBackup() {
        new android.support.v7.app.AlertDialog.Builder(PasswordActivity.this)
                .setTitle("Restore Successfully")
                .setMessage("Database restore successfully,please restart the app and reset your password")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Process.killProcess(Process.myPid());
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onErrorWriteBackup(String error) {
        new android.support.v7.app.AlertDialog.Builder(PasswordActivity.this)
                .setTitle("Restore Unsuccessfully")
                .setMessage(error)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                })
                .create()
                .show();
    }
}
