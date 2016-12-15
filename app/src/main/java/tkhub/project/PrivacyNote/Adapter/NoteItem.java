package tkhub.project.PrivacyNote.Adapter;

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


}
