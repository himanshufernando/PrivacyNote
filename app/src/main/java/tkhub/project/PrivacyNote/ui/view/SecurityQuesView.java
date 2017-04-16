package tkhub.project.PrivacyNote.ui.view;

import java.util.List;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface SecurityQuesView {
    void setColors(List<String> colors);
    void setGames(List<String> games);
    void setYears(List<String> years);

    void setColorEmptyError();
    void setGameEmptyError();
    void setYearEmptyError();
    void setCityEmptyError();

    void setSelectColorError();
    void setSelectGameError();
    void setSelectYearError();
    void setSelectCityError();

    void setAddSuccess();
    void setAddFail(String reason);

    void setResetSuccess();
    void setResetFail(String reason);
}
