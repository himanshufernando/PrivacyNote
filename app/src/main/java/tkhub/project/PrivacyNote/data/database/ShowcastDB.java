package tkhub.project.PrivacyNote.data.database;

import io.realm.RealmObject;

/**
 * Created by Himanshu on 8/3/2016.
 */
public class ShowcastDB extends RealmObject {

    private int id;
    private int count;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
