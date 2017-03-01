package tkhub.project.PrivacyNote.Layout;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
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

import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.Servies.FingerprintHandler;

/**
 * Created by Himanshu on 10/16/2016.
 */

public class Login extends Activity {

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


    private FingerprintManager.CryptoObject mCryptoObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
                fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
                if (!fingerprintManager.isHardwareDetected()) {
                    Intent intent = new Intent(Login.this, Password.class);
                    Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Login.this, R.anim.animation, R.anim.animation2).toBundle();
                    finish();
                    startActivity(intent, bndlanimation);
                }
                else if (!keyguardManager.isKeyguardSecure()) {
                    Toast.makeText(this, "Lock screen security not enabled in Settings", Toast.LENGTH_LONG).show();
                    return;
                }

                else  if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Fingerprint authentication permission not enabled", Toast.LENGTH_LONG).show();
                    return;
                }
                else if (!fingerprintManager.hasEnrolledFingerprints()) {
                    Toast.makeText(this, "Register at least one fingerprint in Settings", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    generateKey();

                    if (cipherInit()) {
                        mCryptoObject = new FingerprintManager.CryptoObject(mCipher);
                        FingerprintHandler helper = new FingerprintHandler(this);
                        helper.startAuth(fingerprintManager, mCryptoObject);

                    }else {
                        Intent intent = new Intent(Login.this, Password.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Login.this, R.anim.animation, R.anim.animation2).toBundle();
                        finish();
                        startActivity(intent, bndlanimation);
                    }
                }



            }catch (NoClassDefFoundError noclass){
                Toast.makeText(this, "No Fingerprint Feature", Toast.LENGTH_LONG).show();
                layoutPass=(RelativeLayout)findViewById(R.id.relativeLayout6);
                layoutPass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Login.this, Password.class);
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Login.this, R.anim.animation, R.anim.animation2).toBundle();
                        finish();
                        startActivity(intent, bndlanimation);
                    }
                });


            }
        }else {
            new Handler().postDelayed(new Runnable() {
                public void run() {

                     Intent intent = new Intent(Login.this, Password.class);
                      Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Login.this, R.anim.animation, R.anim.animation2).toBundle();
                      finish();
                     startActivity(intent, bndlanimation);
                }
            }, 3000);
        }

        layoutPass=(RelativeLayout)findViewById(R.id.relativeLayout6);
        layoutPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Password.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Login.this, R.anim.animation, R.anim.animation2).toBundle();
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
        dialogBox =new Dialog(Login.this);
        dialogBox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBox.setContentView(R.layout.dilaog_progress);
        dialogBox.setCancelable(false);
        dialogBox.show();

        new Handler().postDelayed(new Runnable() {
            public void run() {

                dialogBox.dismiss();
                Intent intent = new Intent(Login.this, Home.class);
                Bundle bndlanimation = ActivityOptions.makeCustomAnimation(Login.this, R.anim.animation, R.anim.animation2).toBundle();
                finish();
                startActivity(intent, bndlanimation);
            }
        }, 3000);



    }



}
