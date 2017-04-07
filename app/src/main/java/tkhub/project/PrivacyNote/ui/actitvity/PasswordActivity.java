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

import io.realm.Realm;
import io.realm.RealmConfiguration;
import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.data.database.AppuserDB;
import tkhub.project.PrivacyNote.data.database.ResetDB;
import tkhub.project.PrivacyNote.ui.font.TextViewFontAwesome;

/**
 * Created by Himanshu on 8/4/2016.
 */
public class PasswordActivity extends Activity implements Animation.AnimationListener {

    Button one, two, three, four, five, six, seven, eight, nine, zero, reset, back,restore;
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
    int resetype;
    private String IMPORT_REALM_FILE_NAME = "default.realm";

    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_READ = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);


        Typeface tf = Typeface.createFromAsset(getAssets(), "Font/Comfortaa-Light.ttf");
        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        resetype =getIntent().getIntExtra("resetype",0);

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
            message.setText("Please set the PasswordActivity");
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


        restore = (Button)findViewById(R.id.btnrestore);
        restore.setTypeface(tf);
        restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPermissionRead()) {
                    requestPermissionRead();
                } else {
                    readeBackup();
                }

            }
        });


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
                    message.setText("You don't have a PasswordActivity to reset,Please set initial PasswordActivity");

                    t1.setText(R.string.icon_circle);
                    t2.setText(R.string.icon_circle);
                    t3.setText(R.string.icon_circle);
                    t4.setText(R.string.icon_circle);
                    password = "";
                    passwordToBeconfirmd = "";
                } else {

                    Intent intent = new Intent(PasswordActivity.this, SecurityQuestionActivity.class);
                    intent.putExtra("PerantLayout",2);
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
                                Toast.makeText(PasswordActivity.this, "PasswordActivity added successfully", Toast.LENGTH_LONG).show();
                                password = "";
                                confrimStatues = 1;
                                passwordToBeconfirmd = "";
                                message.setText("");
                                Intent intent;

                                if(resetype==1){
                                     intent = new Intent(PasswordActivity.this, HomeActivity.class);
                                }else {
                                     intent = new Intent(PasswordActivity.this, SecurityQuestionActivity.class);
                                     intent.putExtra("PerantLayout",1);
                                }

                                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(PasswordActivity.this, R.anim.animation, R.anim.animation2).toBundle();
                                finish();
                                startActivity(intent, bndlanimation);


                              //  sucsessAccess();
                            }
                        });
                    } else {
                        message.setText("PasswordActivity does not match the confirm password");
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
                                Toast.makeText(PasswordActivity.this, "PasswordActivity reset successfully", Toast.LENGTH_LONG).show();
                                password = "";
                                confrimStatues = 1;
                                passwordToBeconfirmd = "";
                                message.setText("");
                                sucsessAccess();
                            }
                        });
                    }

                } else {
                    message.setText("PasswordActivity does not match the confirm password");
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
                    message.setText("PasswordActivity wrong");
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
    private boolean checkPermissionRead() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissionRead() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_READ);
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
    public void readeBackup(){
        new MaterialFilePicker()
                .withActivity(PasswordActivity.this)
                .withRequestCode(1)
                .withFilterDirectories(true) // Set directories filterable (false by default)
                .withHiddenFiles(true) // Show hidden files and folders
                .start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("01",255);
        editor.commit();


        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            copyBundledRealmFile(filePath, IMPORT_REALM_FILE_NAME);

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
        }else {

        }


    }
    private String copyBundledRealmFile(String oldFilePath, String outFileName) {
        try {
            File file = new File(PasswordActivity.this.getFilesDir(), outFileName);

            FileOutputStream outputStream = new FileOutputStream(file);

            FileInputStream inputStream = new FileInputStream(new File(oldFilePath));

            byte[] buf = new byte[1024];
            int bytesRead;



            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
