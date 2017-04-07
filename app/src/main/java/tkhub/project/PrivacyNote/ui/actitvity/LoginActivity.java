package tkhub.project.PrivacyNote.ui.actitvity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.inject.Inject;

import io.realm.Realm;
import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.Servies.FingerprintHandler;
import tkhub.project.PrivacyNote.data.database.ShowcastDB;

/**
 * Created by Himanshu on 10/16/2016.
 */

public class LoginActivity extends Activity {

    private static final String KEY_NAME = "my_key";

    @Inject
    KeyStore mKeyStore;
    @Inject
    KeyGenerator mKeyGenerator;
    @Inject
    Cipher mCipher;
    @Inject

    KeyguardManager keyguardManager;
    FingerprintManager fingerprintManager;

    RelativeLayout layoutPass;

    private Realm mRealm;


    private FingerprintManager.CryptoObject mCryptoObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
               if (!fingerprintManager.isHardwareDetected()) {
                    Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.animation, R.anim.animation2).toBundle();
                    finish();
                    startActivity(intent, bndlanimation);
                }
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
                }else {
                     Toast.makeText(this, "Use your Fingerprint to login", Toast.LENGTH_SHORT).show();
                     final Long tableSize = mRealm.where(ShowcastDB.class).count();

                     if(tableSize==0){
                         new android.support.v7.app.AlertDialog.Builder(LoginActivity.this)
                                 .setMessage("Please use your Fingerprint to login")
                                 .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         return;
                                     }
                                 })
                                 .create()
                                 .show();
                     }else {
                         System.out.println("sdssdsd");
                     }


                     mRealm.executeTransaction(new Realm.Transaction() {
                         @Override
                         public void execute(Realm realm) {
                             ShowcastDB showcast = realm.createObject(ShowcastDB.class);
                             showcast.setId(1);
                             showcast.setCount(5);
                         }
                     });


                    generateKey();

                    if (cipherInit()) {
                        mCryptoObject = new FingerprintManager.CryptoObject(mCipher);
                        FingerprintHandler helper = new FingerprintHandler(this);
                        helper.startAuth(fingerprintManager, mCryptoObject);

                    }
                }



            }catch (NoClassDefFoundError noclass){
                Toast.makeText(this, "No Fingerprint Feature", Toast.LENGTH_LONG).show();
                layoutPass=(RelativeLayout)findViewById(R.id.relativeLayout6);
                layoutPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.animation, R.anim.animation2).toBundle();
                        finish();
                        startActivity(intent, bndlanimation);
                    }
                });


            }
        }else {
            new Handler().postDelayed(new Runnable() {
                public void run() {

                     Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                      Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.animation, R.anim.animation2).toBundle();
                      finish();
                     startActivity(intent, bndlanimation);
                }
            }, 3000);
        }

        layoutPass=(RelativeLayout)findViewById(R.id.relativeLayout6);
        layoutPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, PasswordActivity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.animation, R.anim.animation2).toBundle();
                finish();
                startActivity(intent, bndlanimation);
            }
        });
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
        }else {

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
            } catch ( CertificateException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException("Failed to init Cipher", e);
            }
        }else {
            return false;
        }


    }

    public void sucsessAccess() {

        final Dialog dialogBox;
        dialogBox =new Dialog(LoginActivity.this);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.setContentView(R.layout.dilaog_progress);
        dialogBox.setCancelable(false);
        dialogBox.show();

        new Handler().postDelayed(new Runnable() {
            public void run() {

                dialogBox.dismiss();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(LoginActivity.this, R.anim.animation, R.anim.animation2).toBundle();
                finish();
                startActivity(intent, bndlanimation);
            }
        }, 3000);



    }



}
