package com.example.menukorvet.Exception;

import android.util.Log;

import java.util.Arrays;

public abstract class MenuException {

    public static final String TAG_EXCEPTION = "ExceptionMENU";

    public static void logException(Object obj, Exception exp) {
        Log.e(TAG_EXCEPTION, obj.getClass().getName() + "\n" +
                            exp.toString() + "\n" +
                            Arrays.toString(exp.getStackTrace()));
    }
}
