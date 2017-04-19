package tkhub.project.PrivacyNote.model.securityquestion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import tkhub.project.PrivacyNote.data.database.AppuserDB;
import tkhub.project.PrivacyNote.data.database.SecurityDB;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class SecurityQuesInteractorImpil implements SecurityQuesInteractor {


    @Override
    public List<String> getColorsList() {
        String[] arrColors = {"Blue", "Green", "Red", "Yellow", "Purple", "Pink", "Black", "Orange", "White", "Brown", "Grey", "Gold", "Turquoise", "Violet", "Magenta", "Silver", "Indigo",
                "Lavender", "Vermilion", "Teal", "Beige", "Rose"};
        Collections.sort(Arrays.asList(arrColors));
        return Arrays.asList(arrColors);
    }

    @Override
    public List<String> getGamesList() {
        String[] arrGames = {"Basketball", "Tennis", "Baseball", "Football", "Cricket", "Badminton", "American football", "Golf", "Rugby football", "Volleyball", "Ice hockey", "Ice skating", "Gymnastics", "Bowling", "Skiing", "Boxing",
                "Softball", "Martial arts", "Surfing", "Netball", "Water polo", "Figure skating", "Polo", "Snowboarding", "Walking", "Squash", "Motorsport", "Field hockey", "Rugby", "Table tennis", "Skateboarding", "Lacrosse", "Karate", "Climbing", "Equestrianism", "Roller skating", "Triathlon", "Running",
                "Swimming", "Cycling", "Wrestling"};
        Collections.sort(Arrays.asList(arrGames));
        return Arrays.asList(arrGames);
    }

    @Override
    public List<String> getYearsList() {
        List<String> yearList = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        yearList.add("Select Your Born Year");
        for (int a = year-12; a > 1940; a--) {
            yearList.add(String.valueOf(a));
        }
        return yearList;
    }

    @Override
    public void securityQuesAdd(final String color, final String game, final String year, final String city, final int type, final OnSecurityQuesAddFinishedListener onSecurityQuesAddFinishedListener) {
        final Realm realm = Realm.getDefaultInstance();
        if (color.equals("Select Your Color")) {
            onSecurityQuesAddFinishedListener.onColorEmptyError();
        } else if (game.equals("Select Your Sport")) {
            onSecurityQuesAddFinishedListener.onGamesEmptyError();
        } else if (year.equals("Select Your Born Year")) {
            onSecurityQuesAddFinishedListener.onYearEmptyError();
        } else if (city.equals("") || city.isEmpty()) {
            onSecurityQuesAddFinishedListener.onCityEmptyError();
        } else {
            if(type==1){
                final Long tableSize = realm.where(SecurityDB.class).count();
                realm.executeTransactionAsync(new Realm.Transaction() {
                    @Override
                    public void execute(Realm bgRealm) {
                        Object primaryKeyValue = tableSize + 1;
                        SecurityDB securityDB = bgRealm.createObject(SecurityDB.class, primaryKeyValue);
                        securityDB.setColor(color);
                        securityDB.setSport(game);
                        securityDB.setYear(year);
                        securityDB.setCity(city);
                        securityDB.setAllowe(1);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        onSecurityQuesAddFinishedListener.onAddSuccess();
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        onSecurityQuesAddFinishedListener.onAddFail(error.toString());
                    }
                });

            }else {
                Long checkAllData = realm.where(SecurityDB.class).equalTo("color", color).equalTo("sport", game).equalTo("year", year).equalTo("city", city).count();
                if(checkAllData==0){
                    Long colorCount = realm.where(SecurityDB.class).equalTo("color", color).count();
                    if (colorCount == 0) {
                        onSecurityQuesAddFinishedListener.onColorError();
                    } else {
                        Long sportCount = realm.where(SecurityDB.class).equalTo("sport", game).count();
                        if (sportCount == 0) {
                            onSecurityQuesAddFinishedListener.onGamesError();
                        } else {
                            Long yearCount = realm.where(SecurityDB.class).equalTo("year", year).count();
                            if (yearCount == 0) {
                                onSecurityQuesAddFinishedListener.onYearError();
                            } else {
                                Long cityCount = realm.where(SecurityDB.class).equalTo("city", city).count();
                                if (cityCount == 0) {
                                    onSecurityQuesAddFinishedListener.onCityError();
                                }
                            }
                        }
                    }
                }else {
                    deleteAllUsers();
                    onSecurityQuesAddFinishedListener.onResetSuccess();
                }
            }

        }
    }

    @Override
    public void deleteAllUsers() {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<AppuserDB> results = realm.where(AppuserDB.class).findAll();
                results.deleteAllFromRealm();
            }
        });
    }
}
