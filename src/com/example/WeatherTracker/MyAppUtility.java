package com.example.WeatherTracker;

import android.app.Activity;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.HashMap;

/**
 * Created by anoopc on 12/3/14.
 */
public class MyAppUtility {
    public static void showToast(Activity activity, String messageString) {
        if (activity == null) {
            return;
        }
        Toast.makeText(
                activity.getApplicationContext(),
                messageString,
                Toast.LENGTH_SHORT).show();
    }
}
