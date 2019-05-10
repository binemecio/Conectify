package com.example.binemecio.conectify.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
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
        dialog.setOnCancelListener(dialog1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.finishAndRemoveTask();
            }
            else
            {
                activity.finishAffinity();
            }
        });
        dialog.show();
    }

    public static void showSimpleDialog(Activity activity, String title, String message, String positiveButton, String negativeButton, callbackSimple resultOk, callbackSimple resultCancel)
    {
        Dialog dialog;
        AlertDialog.Builder builder = getSimpleBuilder(activity,title,message,positiveButton,negativeButton,resultOk);
        dialog = builder.create();
        dialog.setOnCancelListener(dialog1 -> {
            activity.finish();
        });
        dialog.show();
    }

    public static void showSimpleDialog(Activity activity, String title, String message, callbackSimple result)
    {
        Dialog dialog;
        AlertDialog.Builder builder = getSimpleBuilder(activity,title,message,result);
        dialog = builder.create();
        dialog.setOnCancelListener(dialog1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                activity.finishAndRemoveTask();
            }
            else
            {
                activity.finishAffinity();
            }
        });
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
        builder.setCancelable(false);
        if(!helper.isNullOrWhiteSpace(title)) builder.setTitle(title);
        if(!helper.isNullOrWhiteSpace(message)) builder.setMessage(message);
        builder.setPositiveButton(positiveButton,(dialog1, which) -> { result.notificate(); });
        builder.setNegativeButton("Cancelar",(dialog, which) -> {
            activity.finish();
        });
        return builder;
    }

    private static AlertDialog.Builder getSimpleBuilder(Activity activity, String title,String message,String positiveButton, String negativeButton, callbackSimple result)
    {
        Helpers helper = new Helpers();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        if(!helper.isNullOrWhiteSpace(title)) builder.setTitle(title);
        if(!helper.isNullOrWhiteSpace(message)) builder.setMessage(message);
        builder.setPositiveButton(positiveButton,(dialog1, which) -> { result.notificate(); });
        builder.setNegativeButton(negativeButton,(dialog, which) -> {
        });
        return builder;
    }

    private static AlertDialog.Builder getSimpleBuilder(Activity activity, String title,String message, callbackSimple result)
    {
        Helpers helper = new Helpers();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        if(!helper.isNullOrWhiteSpace(title)) builder.setTitle(title);
        if(!helper.isNullOrWhiteSpace(message)) builder.setMessage(message);
        builder.setPositiveButton("OK",(dialog1, which) -> { result.notificate(); });

        return builder;
    }
}
