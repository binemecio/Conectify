package app.cloudnicaragua.bluewifi.nicaragua.Helper;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by binemecio on 1/5/2019.
 */

public class Helpers {
    public Helpers() {

    }

    public String toTitleCase(String s) {

        final String ACTIONABLE_DELIMITERS = " '-/"; // these cause the character following
        // to be capitalized

        StringBuilder sb = new StringBuilder();
        boolean capNext = true;

        for (char c : s.toCharArray()) {
            c = (capNext)
                    ? Character.toUpperCase(c)
                    : Character.toLowerCase(c);
            sb.append(c);
            capNext = (ACTIONABLE_DELIMITERS.indexOf((int) c) >= 0); // explicit cast not needed
        }
        return sb.toString();
    }



    public boolean isNullOrWhiteSpace(String string) {
        if (string == null || string.isEmpty() || string.equalsIgnoreCase("null") || (string.length() > 0 && string.trim().length() <= 0))
            return true;
        else
            return false;
    }

    public String getString(String value)
    {
        String data = "";
        if(value != null)
            data = value;

        return data;
    }


    public String getStringNumber(String value,boolean allowZero)
    {
        String data = "";
        if(value != null)
            data = value;

        int entero = this.getInt(data);
        if(entero==0 && !allowZero)
            return "";

        return data;
    }

    public boolean isValidName(String nombre) {
        Pattern patron = Pattern.compile("^[a-zA-Z ]+$");
        if (!patron.matcher(nombre).matches() || nombre.length() > 30) {

            return false;
        }

        return true;
    }

    public boolean isValidPhone(String telefono) {
        if (!Patterns.PHONE.matcher(telefono).matches()) {
            return false;
        }

        return true;
    }

    public boolean isValidEmail(String correo) {
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {

            return false;
        }

        return true;
    }


    public int getInt(String value)
    {
        int data = 0;
        try
        {
            data = Integer.parseInt(value);
        }
        catch (Exception e) {}
        return data;
    }

}
