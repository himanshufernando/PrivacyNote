package tkhub.project.PrivacyNote.DB;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Himanshu on 8/3/2016.
 */
public class ResetDB extends RealmObject {

    @PrimaryKey
    private int id;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
