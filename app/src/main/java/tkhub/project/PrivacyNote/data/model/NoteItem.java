package tkhub.project.PrivacyNote.data.model;

/**
 * Created by Himanshu on 8/3/2016.
 */
public class NoteItem {

    public int id;
    public String title;
    public String userName;
    public String password;
    public String other;
    public int allowe;

    public NoteItem(int id, String title, String userName, String password, String other) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.password = password;
        this.other = other;

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
