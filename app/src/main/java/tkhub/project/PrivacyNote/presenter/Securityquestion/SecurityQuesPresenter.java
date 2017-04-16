package tkhub.project.PrivacyNote.presenter.Securityquestion;

import io.realm.Realm;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SecurityQuesPresenter {
    void setColors();
    void setGames();
    void setYears();
    void setSecurityQues(String color, String game, String year, String city, Realm realm,int type);
    void deleteAllUsers(Realm realm);
}
