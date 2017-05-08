package tkhub.project.PrivacyNote.data.database;

import io.realm.RealmObject;

/**
 * Created by Himanshu on 8/3/2016.
 */
public class ChoiceDB extends RealmObject {

    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
