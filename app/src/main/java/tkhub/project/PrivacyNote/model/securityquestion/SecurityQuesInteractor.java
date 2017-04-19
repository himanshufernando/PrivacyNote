package tkhub.project.PrivacyNote.model.securityquestion;

import android.content.Context;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SecurityQuesInteractor {
    List<String> getColorsList();

    List<String> getGamesList();

    List<String> getYearsList();

    interface OnSecurityQuesAddFinishedListener {
        void onColorEmptyError();

        void onGamesEmptyError();

        void onYearEmptyError();

        void onCityEmptyError();

        void onColorError();

        void onGamesError();

        void onYearError();

        void onCityError();

        void onAddFail(String reason);

        void onAddSuccess();

        void onResetSuccess();
    }

    void securityQuesAdd(String color, String game, String year, String city,int type, OnSecurityQuesAddFinishedListener onSecurityQuesAddFinishedListener);

    void deleteAllUsers();
}
