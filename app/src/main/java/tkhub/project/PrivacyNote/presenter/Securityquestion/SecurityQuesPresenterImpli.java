package tkhub.project.PrivacyNote.presenter.Securityquestion;

import io.realm.Realm;
import tkhub.project.PrivacyNote.model.securityquestion.SecurityQuesInteractor;
import tkhub.project.PrivacyNote.model.securityquestion.SecurityQuesInteractorImpil;
import tkhub.project.PrivacyNote.ui.view.SecurityQuesView;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class SecurityQuesPresenterImpli implements SecurityQuesPresenter, SecurityQuesInteractor.OnSecurityQuesAddFinishedListener {


    private SecurityQuesView securityQuesView;
    SecurityQuesInteractor securityQuesInteractor;

    public SecurityQuesPresenterImpli(SecurityQuesView view) {
        this.securityQuesView = view;
        this.securityQuesInteractor = new SecurityQuesInteractorImpil();
    }


    @Override
    public void setColors() {
        securityQuesView.setColors(securityQuesInteractor.getColorsList());
    }

    @Override
    public void setGames() {
        securityQuesView.setGames(securityQuesInteractor.getGamesList());
    }

    @Override
    public void setYears() {
        securityQuesView.setYears(securityQuesInteractor.getYearsList());

    }

    @Override
    public void setSecurityQues(String color, String game, String year, String city, int type) {
        securityQuesInteractor.securityQuesAdd(color, game, year, city,type, this);
    }

    @Override
    public void deleteAllUsers() {
        securityQuesInteractor.deleteAllUsers();
    }

    @Override
    public void onColorEmptyError() {
        securityQuesView.setColorEmptyError();
    }

    @Override
    public void onGamesEmptyError() {
        securityQuesView.setGameEmptyError();
    }

    @Override
    public void onYearEmptyError() {
        securityQuesView.setYearEmptyError();
    }

    @Override
    public void onCityEmptyError() {
        securityQuesView.setCityEmptyError();
    }

    @Override
    public void onColorError() {
        securityQuesView.setSelectColorError();
    }

    @Override
    public void onGamesError() {
        securityQuesView.setSelectGameError();
    }

    @Override
    public void onYearError() {
        securityQuesView.setSelectYearError();
    }

    @Override
    public void onCityError() {
        securityQuesView.setSelectCityError();
    }

    @Override
    public void onAddFail(String reason) {
        securityQuesView.setAddFail(reason);
    }

    @Override
    public void onAddSuccess() {
        securityQuesView.setAddSuccess();
    }


    @Override
    public void onResetSuccess() {
        securityQuesView.setResetSuccess();
    }


}
