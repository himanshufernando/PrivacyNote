package tkhub.project.PrivacyNote.data.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Himanshu on 8/3/2016.
 */
public class SecurityDB extends RealmObject {

    @PrimaryKey
    private int id;
    private String color;
    private String sport;
    private String year;
    private String city;
    private int allowe;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public String getSport() {
        return sport;
    }
    public void setSport(String sport) {
        this.sport = sport;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public int getAllowe() {
        return allowe;
    }
    public void setAllowe(int allowe) {
        this.allowe = allowe;
    }
}
