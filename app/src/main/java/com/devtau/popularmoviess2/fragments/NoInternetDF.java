package com.devtau.popularmoviess2.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import com.devtau.popularmoviess2.R;
import com.devtau.popularmoviess2.util.Logger;
/**
 * Этот диалог показываем пользователю, когда подключения к интернету нет.
 * Пользователь может включить сеть и повторить попытку подключения.
 *
 * Fragment shows to user that there is no internet connection.
 * User can turn the connection on and retry connection.
 */
public class NoInternetDF extends DialogFragment {
    private static final String TAG = NoInternetDF.class.getSimpleName();
    private NoInternetDFListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.no_internet_msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(listener != null) listener.retryConnection();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {/*NOP*/ }
                });
        return builder.create();
    }

    //Метод создаст новый диалог, если его еще нет на экране
    //Creates new dialog if there is none shown to user
    public static boolean show(FragmentManager manager, NoInternetDFListener listener) {
        NoInternetDF dialog = (NoInternetDF) manager.findFragmentByTag(TAG);
        if (dialog == null) {
//            Logger.v(TAG, "NoInternetDF not found. going to create new one");
            dialog = new NoInternetDF();
            dialog.listener = listener;
            dialog.show(manager, TAG);
            return true;
        } else {
            Logger.e(TAG, "NoInternetDF already shown");
            return false;
        }
    }

    public interface NoInternetDFListener{
        void retryConnection();
    }
}