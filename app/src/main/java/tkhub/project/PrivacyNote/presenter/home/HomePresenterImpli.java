package tkhub.project.PrivacyNote.presenter.home;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import tkhub.project.PrivacyNote.data.model.NavigationDrawerItem;
import tkhub.project.PrivacyNote.data.model.NoteItem;
import tkhub.project.PrivacyNote.model.home.HomeInteractor;
import tkhub.project.PrivacyNote.model.home.HomeInteractorImpil;
import tkhub.project.PrivacyNote.model.password.PasswordInteractor;
import tkhub.project.PrivacyNote.model.password.PasswordInteractorImpil;
import tkhub.project.PrivacyNote.presenter.password.PasswordPresenter;
import tkhub.project.PrivacyNote.ui.view.HomeView;
import tkhub.project.PrivacyNote.ui.view.PasswordView;

/**
 * Created by Himanshu on 4/4/2017.
 */

public class HomePresenterImpli implements HomePresenter,HomeInteractor.OnFinishedListener {


    HomeView homeView;
    HomeInteractor homeInteractor;
    public HomePresenterImpli(HomeView homeView) {
        this.homeView=homeView;
        homeInteractor = new HomeInteractorImpil();
    }

    @Override
    public void setAllNote(Realm realm,ArrayList<NoteItem> noteItems,String keyword) {
        homeInteractor.setAllNote(realm,noteItems,keyword,this);
    }

    @Override
    public void setAllNavagationItem(Realm realm, ArrayList<NavigationDrawerItem> navigationDrawerItems) {
        homeInteractor.setAllNavigationItem(realm,navigationDrawerItems,this);
    }

    @Override
    public void onsetAllNote() {
        homeView.onFinishedSetAllNote();
    }

    @Override
    public void onsetAllNavigationItems() {
        homeView.onFinishedNavigationItems();
    }

}
