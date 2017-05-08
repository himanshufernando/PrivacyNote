package tkhub.project.PrivacyNote.ui.view;

import java.util.ArrayList;

import tkhub.project.PrivacyNote.data.model.NoteItem;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface HomeView {
    void onFinishedSetAllNote();

    void onFinishedNavigationItems();

    void onFinishedGetFingerprintAccess(boolean isAccess);

    void onNoteSaveTitleEmpty();

    void onNoteSaveSuccess();

    void onNoteSaveFail();

    void onFinisheSetSearchAutoComplteText();

    void onNoteDeleteSuccess();

    void onNoteDeleteFail();

    void onFinishWriteBackup(String savefilename);

    void onFinishResetBackup();

    void onErrorResetBackup(String error);

    void onGetUserChoies(int result);

}
