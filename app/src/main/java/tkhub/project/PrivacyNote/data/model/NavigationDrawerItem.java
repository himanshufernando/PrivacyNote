package tkhub.project.PrivacyNote.data.model;

/**
 * Created by Himanshu on 7/22/2016.
 */
public class NavigationDrawerItem {
    public String mTitle;
    public int mImage;
    int mColor;

    public NavigationDrawerItem(String title, int icon) {
        this.mTitle = title;
        this.mImage = icon;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getmImage() {
        return mImage;
    }

    public void setmImage(int mImage) {
        this.mImage = mImage;
    }

    public int getmColor() {
        return mColor;
    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
    }
}
