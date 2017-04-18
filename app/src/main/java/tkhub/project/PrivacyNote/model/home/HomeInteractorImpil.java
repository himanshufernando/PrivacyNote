package tkhub.project.PrivacyNote.model.home;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import tkhub.project.PrivacyNote.R;
import tkhub.project.PrivacyNote.data.database.SecurityDB;
import tkhub.project.PrivacyNote.data.model.NavigationDrawerItem;
import tkhub.project.PrivacyNote.data.database.NoteDB;
import tkhub.project.PrivacyNote.data.model.NoteItem;

/**
 * Created by Himanshu on 4/5/2017.
 */

public class HomeInteractorImpil implements HomeInteractor {
    @Override
    public void setAllNote(Realm realm,ArrayList<NoteItem> noteItems,String keyword ,OnFinishedListener onFinishedListener) {
        noteItems.clear();
        if(keyword.equals("")){
            for (NoteDB no : realm.where(NoteDB.class).equalTo("allowe", 0).findAll()) {
                noteItems.add(new NoteItem(no.getId(), no.getTitle(), no.getUserName(), no.getPassword(), no.getOther()));
            }
        }else {
            for (NoteDB no : realm.where(NoteDB.class).beginsWith("title", keyword, Case.INSENSITIVE).equalTo("allowe", 0).findAll()) {
                noteItems.add(new NoteItem(no.getId(), no.getTitle(), no.getUserName(), no.getPassword(), no.getOther()));
            }
        }
        onFinishedListener.onsetAllNote();
    }

    @Override
    public void setAllNavigationItem(Realm realm, ArrayList<NavigationDrawerItem> navigationDrawerItems, OnFinishedListener onFinishedListener) {
        if(navigationDrawerItems.isEmpty()){
            navigationDrawerItems.add(new NavigationDrawerItem("Home", R.string.icon_navigation_home));
            navigationDrawerItems.add(new NavigationDrawerItem("Backup", R.string.icon_navigation_backup));
            if (realm.where(SecurityDB.class).count()>0) {
                navigationDrawerItems.add(new NavigationDrawerItem("Password Reset", R.string.icon_navigation_reset));
            } else {
            }
            navigationDrawerItems.add(new NavigationDrawerItem("About", R.string.icon_navigation_about));
        }else {
        }
        onFinishedListener.onsetAllNavigationItems();
    }


}
