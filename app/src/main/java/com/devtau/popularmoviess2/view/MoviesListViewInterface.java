package com.devtau.popularmoviess2.view;

import android.database.Cursor;
/**
 * Вью-интерфейс, обеспечивающий общение презентера с пользователем
 * View interface to communicate with user
 */
public interface MoviesListViewInterface extends View {
    boolean showNoInternetDF();
    boolean showProgressBarDF();
    boolean dismissProgressBarDF();
    void initList();
    void showMessage(String msg);
    void swapCursor(Cursor cursor);
}
