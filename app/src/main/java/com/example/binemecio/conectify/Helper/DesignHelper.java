package com.example.binemecio.conectify.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;

/**
 * Created by binemecio on 1/5/2019.
 */

public class DesignHelper {

    public interface callbackLoadString
    {
        public void getResultString(String result);
    }

    public interface callbackLoadInt
    {
        public void getResultInt(int result);
    }

    public interface callbackSimple
    {
        public void notificate();
    }



    public static void showSimpleDialog(Activity activity, String title, String message, String positiveButton, callbackSimple result)
    {
        Dialog dialog;
        AlertDialog.Builder builder = getSimpleBuilder(activity,title,message,positiveButton,result);
        dialog = builder.create();
        dialog.show();
    }

    public static void showSimpleDialogWithImage(Activity activity,int resource, String title,String message,String positiveButton, callbackSimple result)
    {
        Dialog dialog;
        AlertDialog.Builder builder = getSimpleBuilder(activity,title,message,positiveButton,result);
        builder.setIcon(resource);
        dialog = builder.create();
        dialog.show();
    }

    private static AlertDialog.Builder getSimpleBuilder(Activity activity, String title,String message,String positiveButton, callbackSimple result)
    {
        Helpers helper = new Helpers();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if(!helper.isNullOrWhiteSpace(title)) builder.setTitle(title);
        if(!helper.isNullOrWhiteSpace(message)) builder.setMessage(message);
        builder.setPositiveButton(positiveButton,(dialog1, which) -> { result.notificate(); });
        builder.setNegativeButton("Cancelar",null);
        return builder;
    }
}