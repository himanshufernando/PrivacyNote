package tkhub.project.PrivacyNote.data.database;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Himanshu on 8/3/2016.
 */
public class NoteDB extends RealmObject {

    @PrimaryKey
    private long id;
    private String title;
    private String userName;
    private String password;
    private String other;
    private Date adDate;
    private int allowe;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getAllowe() {
        return allowe;
    }

    public void setAllowe(int allowe) {
        this.allowe = allowe;
    }

    public Date getAdDate() {
        return adDate;
    }

    public void setAdDate(Date adDate) {
        this.adDate = adDate;
    }
}
