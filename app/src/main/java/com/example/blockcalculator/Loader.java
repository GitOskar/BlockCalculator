package com.example.blockcalculator;

import android.content.SharedPreferences;

public class Loader
{
    SharedPreferences sharedPreferences;
    private final String ARC_PREFS = "arcSharedPrefs";
    public static final float NAN = -1.0f;

    public Loader(SharedPreferences sharedPreferences)
    {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveData(float arcRefund)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(ARC_PREFS, arcRefund);
        editor.commit();
    }

    public float loadData()
    {
        return sharedPreferences.getFloat(ARC_PREFS, NAN);
    }
}
