package tkhub.project.PrivacyNote.ui.actitvity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.os.Vibrator;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import io.realm.Realm;
import tkhub.project.PrivacyNote.ui.adapter.NavigationDrawer;
import tkhub.project.PrivacyNote.data.model.NavigationDrawerItem;
import tkhub.project.PrivacyNote.presenter.home.HomePresenter;
import tkhub.project.PrivacyNote.presenter.home.HomePresenterImpli;
import tkhub.project.PrivacyNote.ui.adapter.NoteAdapter;
import tkhub.project.PrivacyNote.data.model.NoteItem;
import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.Servies.FingerprintHandler2;
import tkhub.project.PrivacyNote.Servies.FingerprintHandler3;
import tkhub.project.PrivacyNote.data.database.AppuserDB;
import tkhub.project.PrivacyNote.data.database.NoteDB;
import tkhub.project.PrivacyNote.ui.font.TextViewFontAwesome;
import tkhub.project.PrivacyNote.ui.view.HomeView;

/**
 * Created by Himanshu on 8/3/2016.
 */
public class HomeActivity extends Activity implements Animation.AnimationListener, HomeView {


    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_WRITE = 123;
    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_READ = 124;

    Dialog dialogBox, dialogBoxedit;
    private Realm mRealm;

    @BindView(R.id.list_news)
    RecyclerView eventList;
    @BindView(R.id.drawer_layout)
    DrawerLayout dLayout;
    @BindView(R.id.listView_navigation)
    ListView navigationList;

    @BindView(R.id.relativeLayoutnoteadd)
    RelativeLayout layoutAdd;

    @BindView(R.id.relativeLayoutlistnote)
    RelativeLayout layoutView;

    @BindView(R.id.relativeLayoutserachbutton)
    RelativeLayout searchBtn;

    @BindView(R.id.relativeLayoutSearch)
    RelativeLayout layoutSearch;

    @BindView(R.id.relativeLayout16)
    RelativeLayout serachClose;

    @BindView(R.id.relativeLayout3)
    RelativeLayout layoutAbout;

    @BindView(R.id.autoCompleteTextView)
    AutoCompleteTextView autoText;

    @BindView(R.id.editText_other)
    EditText other;

    @BindView(R.id.editText_title)
    EditText title;

    @BindView(R.id.editText_user)
    EditText username;

    @BindView(R.id.editText_pass2)
    EditText password;


    HomePresenter homePresenter;


    NavigationDrawer navigationDrawerAdapter;
    NoteAdapter notAdapter;

    ArrayList<NoteItem> noteItems = new ArrayList<NoteItem>();
    ArrayList<NavigationDrawerItem> navigationDrawerItems = new ArrayList<NavigationDrawerItem>();

    RelativeLayout layoutEdit;


    TextViewFontAwesome t1, t2, t3, t4;
    TextView message;

    String passwordToBeconfirmd = "";

    public long noteId;
    public String noteTitle;
    public String noteUserName;
    public String notePassword;
    public String noteOther;

    String pass;

    int confrimStatues = 0;
    int resetStatues = 0;

    int status = 0;

    boolean backStatus = false;

    private static final String KEY_NAME = "my_key";

    Dialog dialogBoxPassword;

    @Inject
    KeyStore mKeyStore;
    @Inject
    KeyGenerator mKeyGenerator;
    @Inject
    Cipher mCipher;
    @Inject

    List<String> titleList;

    Typeface tf;
    Vibrator mVibrator;

    KeyguardManager keyguardManager;
    FingerprintManager fingerprintManager;
    long deleteID;

    private FingerprintManager.CryptoObject mCryptoObject;

    private File EXPORT_REALM_PATH;
    private String EXPORT_REALM_FILE_NAME = "privacynotebackup.realm";
    private String IMPORT_REALM_FILE_NAME = "default.realm";


    boolean userChoice = false;
    AlertDialog dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);

        ButterKnife.bind(this);
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        eventList.setLayoutManager(mLayoutManager);
        eventList.setItemAnimator(new DefaultItemAnimator());


        tf = Typeface.createFromAsset(getAssets(), "Font/Comfortaa-Light.ttf");


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            EXPORT_REALM_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        } else {
            EXPORT_REALM_PATH = new File(Environment.getExternalStorageDirectory() + "/Documents");
        }


        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        onActivityLoard();


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dialogBox != null) {
            if (dialogBox.isShowing()) {
                dialogBox.dismiss();
            } else {
            }
        } else {

        }


    }

    @OnClick(R.id.fab2)
    public void onSaveClick(View view) {
        homePresenter.saveNote(title.getText().toString(), username.getText().toString(), password.getText().toString(), other.getText().toString());
    }

    @OnClick(R.id.relativeLayoutserachbutton)
    public void onSerachClick(View view) {
        layoutSearch.setVisibility(View.VISIBLE);
        searchBtn.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.relativeLayout16)
    public void onSerachCloseClick(View view) {
        layoutSearch.setVisibility(View.INVISIBLE);
        searchBtn.setVisibility(View.VISIBLE);
        homePresenter.setAllNote(noteItems, "");
    }

    @OnClick(R.id.fab)
    public void onAddFloatingActionButtonClick(View view) {
        layoutAdd.setVisibility(View.VISIBLE);
        layoutView.setVisibility(View.INVISIBLE);
        backStatus = true;
    }


    @OnClick(R.id.relativeLayout3)
    public void onClick(View view) {
        dLayout.openDrawer(Gravity.LEFT);
    }

    @OnItemClick(R.id.listView_navigation)
    public void onItemClick(int position) {
        if (position == 0) {
            dLayout.closeDrawer(Gravity.LEFT);
        } else if (position == 1) {
            homePresenter.writeBackup(this, HomeActivity.this, EXPORT_REALM_PATH, EXPORT_REALM_FILE_NAME);
        } else if (position == 2) {
            homePresenter.getFingerprintAutherAccess();
        } else if (position == 3) {
            Intent i = new Intent(HomeActivity.this, AboutActivity.class);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.animation, R.anim.animation2).toBundle();
            finish();
            startActivity(i, bndlanimation);
        }
    }

    @OnTextChanged(value = R.id.autoCompleteTextView, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void afterEmailInput(Editable editable) {
        homePresenter.setAllNote(noteItems, editable.toString());
    }


    public void showNoteDialog(Context con) {

        dialogBox = new Dialog(con);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.setContentView(R.layout.dilaog_note);
        dialogBox.setCancelable(true);
        dialogBox.show();


        TextView titel = (TextView) dialogBox.findViewById(R.id.textView_dailog_title);
        TextView user = (TextView) dialogBox.findViewById(R.id.textView_dailog_user);
        TextView pass = (TextView) dialogBox.findViewById(R.id.textView_dailog_pass);
        TextView other = (TextView) dialogBox.findViewById(R.id.textView_dailog_other);

        RelativeLayout layoutuser = (RelativeLayout) dialogBox.findViewById(R.id.relativeLayout_dailog_username);
        RelativeLayout layoutpass = (RelativeLayout) dialogBox.findViewById(R.id.relativeLayout_dailog_password);
        RelativeLayout layoutother = (RelativeLayout) dialogBox.findViewById(R.id.relativeLayout_dailog_other);
        layoutEdit = (RelativeLayout) dialogBox.findViewById(R.id.relativeLayoutnoteedit);

        titel.setText(noteTitle);
        user.setText("User Name : " + noteUserName);
        pass.setText("Password : " + notePassword);
        other.setText("Other : " + noteOther);

        layoutuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Username", noteUserName);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeActivity.this, "Appuser Name Copied", Toast.LENGTH_LONG).show();
            }
        });

        layoutpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Password", notePassword);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeActivity.this, "Password Copied", Toast.LENGTH_LONG).show();
            }
        });

        layoutother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Other", noteOther);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(HomeActivity.this, "Other details Copied", Toast.LENGTH_LONG).show();
            }
        });

        layoutEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBox.dismiss();
                dialogBoxedit = new Dialog(HomeActivity.this);
                dialogBoxedit.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogBoxedit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBoxedit.setContentView(R.layout.dilaog_note_edit);
                dialogBoxedit.setCancelable(true);
                dialogBoxedit.show();

                TextView titel = (TextView) dialogBoxedit.findViewById(R.id.textView_dailog_title_edit);
                final EditText user = (EditText) dialogBoxedit.findViewById(R.id.edittext_dailog_user);
                final EditText pass = (EditText) dialogBoxedit.findViewById(R.id.edittext_dailog_pass);
                final EditText other = (EditText) dialogBoxedit.findViewById(R.id.edittext_dailog_other);

                RelativeLayout layoutotsave = (RelativeLayout) dialogBoxedit.findViewById(R.id.relativeLayouteditsave);


                titel.setText(noteTitle);
                user.setText(noteUserName);
                pass.setText(notePassword);
                other.setText(noteOther);


                layoutotsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {


                                NoteDB noteedit = mRealm.where(NoteDB.class).equalTo("id", noteId).findFirst();
                                noteedit.setUserName(user.getText().toString());
                                noteedit.setPassword(pass.getText().toString());
                                noteedit.setOther(other.getText().toString());
                                Toast.makeText(HomeActivity.this, "Save Successfully", Toast.LENGTH_LONG).show();

                                dialogBoxedit.dismiss();

                                homePresenter.setAllNote(noteItems, "");


                            }
                        });
                    }
                });


            }
        });


    }

    public void lodeNote(Long id, String title, String userName, String password, String other) {
        noteId = id;
        noteTitle = title;
        noteUserName = userName;
        notePassword = password;
        noteOther = other;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Toast.makeText(this, "Fingerprint Access Needed", Toast.LENGTH_LONG).show();
                keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
                status = 1;
                homePresenter.getChoice();

                if (!keyguardManager.isKeyguardSecure()) {
                    Toast.makeText(this, "Lock screen security not enabled in Settings", Toast.LENGTH_LONG).show();
                    return;
                }

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Fingerprint authentication permission not enabled", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    Toast.makeText(this, "Register at least one fingerprint in Settings", Toast.LENGTH_LONG).show();
                    return;
                }


                generateKey();

                if (cipherInit()) {
                    mCryptoObject = new FingerprintManager.CryptoObject(mCipher);
                    FingerprintHandler2 helper = new FingerprintHandler2(this);
                    helper.startAuth(fingerprintManager, mCryptoObject);

                }
            } catch (NoClassDefFoundError noclass) {
                Toast.makeText(this, "No Fingerprint Feature", Toast.LENGTH_LONG).show();


                status = 1;
                passwordDialog(this);
            }
        } else {
            status = 1;
            passwordDialog(this);
        }


    }

    public void accesPermiton() {

        if(dlg == null){
        }else {
            dlg.dismiss();
        }

        showNoteDialog(HomeActivity.this);

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void accesPermitonforDelete(final long id) {


        try {
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            status = 2;
            homePresenter.getChoice();
            if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(this, "Lock screen security not enabled in Settings", Toast.LENGTH_LONG).show();
                return;
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Fingerprint authentication permission not enabled", Toast.LENGTH_LONG).show();
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    Toast.makeText(this, "Register at least one fingerprint in Settings", Toast.LENGTH_LONG).show();
                    return;
                }
            }


            generateKey();

            if (cipherInit()) {
                deleteID = id;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mCryptoObject = new FingerprintManager.CryptoObject(mCipher);
                }
                FingerprintHandler3 helper = new FingerprintHandler3(this);
                helper.startAuth(fingerprintManager, mCryptoObject);

            }

        } catch (NoClassDefFoundError noclass) {
            deleteID = id;
            Toast.makeText(this, "No Fingerprint Feature", Toast.LENGTH_LONG).show();
            status = 2;
            passwordDialog(this);
        }


    }


    public void deleteNote() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
        alertDialog.setTitle("Delete");
        alertDialog.setMessage("Are you sure you want delete this ?");
        alertDialog.setIcon(R.drawable.fingerprint);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                homePresenter.deleteNote(deleteID);
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
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        layoutSearch.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onBackPressed() {

        if (backStatus) {
            layoutView.setVisibility(View.VISIBLE);
            layoutAdd.setVisibility(View.INVISIBLE);
            backStatus = false;
            homePresenter.setAllNote(noteItems, "");
            homePresenter.setSearchAutoComplteText(titleList);
        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
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


    }

    public boolean cipherInit() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                try {
                    mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Failed to get Cipher", e);
            }

            try {
                mKeyStore.load(null);
                SecretKey key = null;
                try {
                    key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                } catch (UnrecoverableKeyException e) {
                    e.printStackTrace();
                }
                mCipher.init(Cipher.ENCRYPT_MODE, key);
                return true;
            } catch (CertificateException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException("Failed to init Cipher", e);
            }
        } else {
            return false;
        }


    }

    protected void generateKey() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                mKeyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                throw new RuntimeException("Failed to get KeyGenerator instance", e);
            }

            try {
                mKeyStore.load(null);
                mKeyGenerator.init(new
                        KeyGenParameterSpec.Builder(KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());
                mKeyGenerator.generateKey();
            } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | CertificateException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {

        }


    }

    public void passwordDialog(Context con) {

        dialogBoxPassword = new Dialog(con);
        dialogBoxPassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBoxPassword.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBoxPassword.setContentView(R.layout.activity_password);
        dialogBoxPassword.setCancelable(true);
        dialogBoxPassword.show();

        final Vibrator mVibrator;


        mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        t1 = (TextViewFontAwesome) dialogBoxPassword.findViewById(R.id.textView_icon_1);
        t2 = (TextViewFontAwesome) dialogBoxPassword.findViewById(R.id.textView_icon_2);
        t3 = (TextViewFontAwesome) dialogBoxPassword.findViewById(R.id.textView_icon_3);
        t4 = (TextViewFontAwesome) dialogBoxPassword.findViewById(R.id.textView_icon_4);


        message = (TextView) dialogBoxPassword.findViewById(R.id.textViewMessage);

        Button one = (Button) dialogBoxPassword.findViewById(R.id.btn1);
        one.setTypeface(tf);

        Button two = (Button) dialogBoxPassword.findViewById(R.id.btn2);
        two.setTypeface(tf);

        ////
        Button three = (Button) dialogBoxPassword.findViewById(R.id.btn3);
        three.setTypeface(tf);

        Button four = (Button) dialogBoxPassword.findViewById(R.id.btn4);
        four.setTypeface(tf);

        Button five = (Button) dialogBoxPassword.findViewById(R.id.btn5);
        five.setTypeface(tf);

        Button six = (Button) dialogBoxPassword.findViewById(R.id.btn6);
        six.setTypeface(tf);

        Button seven = (Button) dialogBoxPassword.findViewById(R.id.btn7);
        seven.setTypeface(tf);

        Button eight = (Button) dialogBoxPassword.findViewById(R.id.btn8);
        eight.setTypeface(tf);

        Button nine = (Button) dialogBoxPassword.findViewById(R.id.btn9);
        nine.setTypeface(tf);

        Button zero = (Button) dialogBoxPassword.findViewById(R.id.btn0);
        zero.setTypeface(tf);

        Button reset = (Button) dialogBoxPassword.findViewById(R.id.btnreset);
        reset.setTypeface(tf);

        Button back = (Button) dialogBoxPassword.findViewById(R.id.btnBack);
        back.setTypeface(tf);

        pass = "";
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                pass = pass + "1";
                checkPassword();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                pass = pass + "2";
                checkPassword();
            }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                pass = pass + "3";
                checkPassword();
            }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                pass = pass + "4";
                checkPassword();
            }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                pass = pass + "5";
                checkPassword();
            }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                pass = pass + "6";
                checkPassword();
            }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                pass = pass + "7";
                checkPassword();
            }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                pass = pass + "8";
                checkPassword();
            }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                pass = pass + "9";
                checkPassword();
            }
        });

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                pass = pass + "0";
                checkPassword();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this, PasswordActivity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.animation, R.anim.animation2).toBundle();
                finish();
                startActivity(intent, bndlanimation);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVibrator.vibrate(50);
                try {
                    if (password.length() == 1) {
                        t1.setText(R.string.icon_circle);
                        t2.setText(R.string.icon_circle);
                        t3.setText(R.string.icon_circle);
                        t4.setText(R.string.icon_circle);
                        pass = "";
                    } else {
                        pass = pass.substring(0, pass.length() - 1);
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

        if (pass.length() == 1) {
            t1.setText(R.string.icon_fill_circle);
            t2.setText(R.string.icon_circle);
            t3.setText(R.string.icon_circle);
            t4.setText(R.string.icon_circle);

        } else if (pass.length() == 2) {
            t1.setText(R.string.icon_fill_circle);
            t2.setText(R.string.icon_fill_circle);
            t3.setText(R.string.icon_circle);
            t4.setText(R.string.icon_circle);
        } else if (pass.length() == 3) {
            t1.setText(R.string.icon_fill_circle);
            t2.setText(R.string.icon_fill_circle);
            t3.setText(R.string.icon_fill_circle);
            t4.setText(R.string.icon_circle);
        } else if (pass.length() == 4) {
            t1.setText(R.string.icon_fill_circle);
            t2.setText(R.string.icon_fill_circle);
            t3.setText(R.string.icon_fill_circle);
            t4.setText(R.string.icon_fill_circle);


            if (resetStatues == 1) {
                AppuserDB toEdit = mRealm.where(AppuserDB.class).equalTo("id", 1).findFirst();
                if (toEdit.getPassword().equals(password)) {
                    if (passwordToBeconfirmd.equals("")) {
                        passwordToBeconfirmd = pass;
                        message.setText("Confirm your password");
                        message.setTextColor(getResources().getColor(R.color.iconBlue));
                        pass = "";

                    } else {
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                AppuserDB toEdit = mRealm.where(AppuserDB.class).equalTo("id", 1).findFirst();
                                toEdit.setPassword(pass);

                                Toast.makeText(HomeActivity.this, "Password reset successfully", Toast.LENGTH_LONG).show();
                                pass = "";
                                confrimStatues = 1;
                                passwordToBeconfirmd = "";
                                message.setText("");

                                if (status == 1) {
                                    dialogBoxPassword.dismiss();
                                    showNoteDialog(HomeActivity.this);
                                } else if (status == 2) {
                                    dialogBoxPassword.dismiss();
                                    deleteNote();
                                }


                            }
                        });
                    }

                } else {
                    message.setText("password doeas not match");
                    message.setTextColor(getResources().getColor(R.color.iconRed));
                    t1.setText(R.string.icon_circle);
                    t2.setText(R.string.icon_circle);
                    t3.setText(R.string.icon_circle);
                    t4.setText(R.string.icon_circle);
                    pass = "";
                    mVibrator.vibrate(300);
                }

            } else {
                Long ap = mRealm.where(AppuserDB.class).equalTo("password", pass).count();
                if (ap != 0) {
                    if (status == 1) {
                        dialogBoxPassword.dismiss();
                        showNoteDialog(HomeActivity.this);
                    } else if (status == 2) {
                        dialogBoxPassword.dismiss();
                        deleteNote();
                    }
                } else {
                    message.setText("Password wrong");
                    message.setTextColor(getResources().getColor(R.color.iconRed));
                    t1.setText(R.string.icon_circle);
                    t2.setText(R.string.icon_circle);
                    t3.setText(R.string.icon_circle);
                    t4.setText(R.string.icon_circle);
                    pass = "";
                    mVibrator.vibrate(300);
                }
            }


        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_WRITE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted) {
                        homePresenter.writeBackup(this, HomeActivity.this, EXPORT_REALM_PATH, EXPORT_REALM_FILE_NAME);
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE_READ:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (locationAccepted) {
                        homePresenter.readBackup(this, HomeActivity.this);
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String filePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            homePresenter.resetBackup(filePath, IMPORT_REALM_FILE_NAME, HomeActivity.this);
        } else {
            dLayout.closeDrawer(Gravity.LEFT);
        }
    }

    private void onActivityLoard() {
        titleList = new ArrayList<String>();
        notAdapter = new NoteAdapter(this, noteItems);
        navigationDrawerAdapter = new NavigationDrawer(this, navigationDrawerItems);

        homePresenter = new HomePresenterImpli(this, HomeActivity.this, this);

        homePresenter.setAllNote(noteItems, "");
        homePresenter.setAllNavagationItem(navigationDrawerItems);
    }

    @Override
    public void onFinishedSetAllNote() {
        eventList.setAdapter(notAdapter);
    }

    @Override
    public void onFinishedNavigationItems() {
        navigationList.setAdapter(navigationDrawerAdapter);
    }

    @Override
    public void onFinishedGetFingerprintAccess(boolean isAccess) {
        if (isAccess) {
            homePresenter.readBackup(this, HomeActivity.this);
        } else {
            Intent intent = new Intent(HomeActivity.this, SecurityQuestionActivity.class);
            intent.putExtra("PerantLayout", 2);
            Bundle bndlanimation = ActivityOptions.makeCustomAnimation(HomeActivity.this, R.anim.animation, R.anim.animation2).toBundle();
            finish();
            startActivity(intent, bndlanimation);
        }
    }

    @Override
    public void onNoteSaveTitleEmpty() {
        Toast.makeText(HomeActivity.this, "Title is empty", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNoteSaveSuccess() {
        Toast.makeText(HomeActivity.this, "Title added successfully", Toast.LENGTH_LONG).show();
        title.setText("");
        username.setText("");
        password.setText("");
        other.setText("");

        layoutView.setVisibility(View.VISIBLE);
        layoutAdd.setVisibility(View.INVISIBLE);
        backStatus = false;

        homePresenter.setAllNote(noteItems, "");
        homePresenter.setSearchAutoComplteText(titleList);

    }

    @Override
    public void onNoteSaveFail() {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage("Saving Fail,Try agin")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onFinisheSetSearchAutoComplteText() {
        ArrayAdapter<String> titleAdapterList = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, titleList);
        autoText.setAdapter(titleAdapterList);
    }

    @Override
    public void onNoteDeleteSuccess() {
        homePresenter.setAllNote(noteItems, "");
    }

    @Override
    public void onNoteDeleteFail() {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage("Note Delete Fail,Try agin")
                .setPositiveButton("OK", null)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void onFinishWriteBackup(String savefilename) {
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("Backup Successful")
                .setMessage(savefilename)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dLayout.closeDrawer(Gravity.LEFT);
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onFinishResetBackup() {
        new AlertDialog.Builder(HomeActivity.this)
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
    public void onErrorResetBackup(String error) {
        new AlertDialog.Builder(HomeActivity.this)
                .setTitle("Restore Fail")
                .setMessage(error + ",Please try again")
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
    public void onGetUserChoies(int result) {


        if(result == -1){
            Toast.makeText(this, "Fingerprint Access Needed", Toast.LENGTH_LONG).show();
        }else if (result == 0) {
            setLoginOption();
        } else if (result == 2) {
            passwordDialog(this);
        } else if (result == 3) {
            Toast.makeText(this, "Fingerprint Access Needed", Toast.LENGTH_LONG).show();
        }
    }

    public void setLoginOption() {

        View checkBoxView = View.inflate(this, R.layout.checkbox, null);
        CheckBox checkBox = (CheckBox) checkBoxView.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userChoice = isChecked;
            }
        });
        checkBox.setText("Remember my choice");


        AlertDialog.Builder userChoicesBuilder = new AlertDialog.Builder(this);
        dlg = userChoicesBuilder.create();
        userChoicesBuilder.setMessage("Do you want to use password logging instead of fingerprint ?")
                .setView(checkBoxView)
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (userChoice) {
                            homePresenter.setChoice(2);
                        } else {
                        }
                        passwordDialog(HomeActivity.this);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (userChoice) {
                            homePresenter.setChoice(3);
                        } else {
                        }
                        Toast.makeText(HomeActivity.this, "Fingerprint Access Needed", Toast.LENGTH_LONG).show();
                        return;
                    }
                }).show();
    }
}
