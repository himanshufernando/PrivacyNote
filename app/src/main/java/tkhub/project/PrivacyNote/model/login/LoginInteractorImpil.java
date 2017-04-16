package tkhub.project.PrivacyNote.model.login;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
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
import tkhub.project.PrivacyNote.model.about.AboutInteractor;
import tkhub.project.PrivacyNote.ui.actitvity.LoginActivity;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class LoginInteractorImpil implements LoginInteractor {
    @Override
    public void updateShowcastDatabase(Realm mRealm) {
        final Long tableSize = mRealm.where(ShowcastDB.class).count();
        if(tableSize==0){
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    ShowcastDB showcast = realm.createObject(ShowcastDB.class);
                    showcast.setId(1);
                    showcast.setCount(5);
                }
            });
        }else {
        }
    }
}
