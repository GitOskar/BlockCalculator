package com.example.blockcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity implements SettingsDialog.SettingsDialogListener {


    private double NAN = -1.0, arcRefund = NAN, maxPr=0, currOnLvl=0, prOnPlace=0, refund=0;
    private EditText maxPRBox, currOnLvlBox, prOnPlaceBox, refundBox;
    private TextView screen;
    private Button button, refreshButton, settingsButton;
    private final String SHARED_PREFS = "mySharedPrefs", ARC_PREFS = "arcSharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        refreshButton = (Button) findViewById(R.id.refreshbutton);
        settingsButton = (Button) findViewById(R.id.settingsbutton);
        screen = (TextView) findViewById(R.id.screen);
        maxPRBox = (EditText) findViewById(R.id.maxPRBox);
        currOnLvlBox = (EditText) findViewById(R.id.currOnLvlBox);
        prOnPlaceBox = (EditText) findViewById(R.id.actualOnPlaceBox);
        refundBox = (EditText) findViewById(R.id.refundBox);
        loadData();
    }

    public void calculateOnClick(View view)
    {
        try {
            maxPr = Double.parseDouble(maxPRBox.getText().toString());
            currOnLvl = Double.parseDouble(currOnLvlBox.getText().toString());
            prOnPlace = Double.parseDouble(prOnPlaceBox.getText().toString());
            refund = Double.parseDouble(refundBox.getText().toString());
        } catch (NumberFormatException e)
        {
            screen.setText("Enter all data");
            return;
        }

        double blockCost = Math.ceil((maxPr - currOnLvl + prOnPlace) / 2.0);

        if (currOnLvl + blockCost >= maxPr)
        {
            screen.setText("You cannot block this place");
            return;
        }

        int profit = (int) (Math.ceil(arcRefund * refund - blockCost));

        if (profit >= 0) {
            screen.setText("Block cost: "
                    + blockCost
                    + "\nProfit: "
                    + profit);
        }
        else
        {
            screen.setText("Block cost: "
                    + blockCost
                    + "\nLoss: "
                    + profit);
        }
    }

    public void refreshOnClick(View view)
    {
        screen.setText("");
        maxPRBox.setText("");
        refundBox.setText("");
        prOnPlaceBox.setText("");
        currOnLvlBox.setText("");
        loadData();
    }

    public void settingsOnClick(View view) {
            SettingsDialog settingsDialog = new SettingsDialog();
            settingsDialog.show(getSupportFragmentManager(), "settingsDialog");
    }

    public void setArcRefund(double arcRefund) {
        this.arcRefund = arcRefund;
    }

    @Override
    public void applyText(String arcRefund) {
        try {
            this.arcRefund = Double.parseDouble(arcRefund);
        } catch (NumberFormatException e)
        {
            SettingsDialog settingsDialog = new SettingsDialog();
            settingsDialog.show(getSupportFragmentManager(), "settingsDialog");
            return;
        }
        if (this.arcRefund < 1.0 || this.arcRefund > 6.0)
        {
            SettingsDialog settingsDialog = new SettingsDialog();
            settingsDialog.show(getSupportFragmentManager(), "settingsDialog");
        }
        saveData();
    }

    public void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(ARC_PREFS, (float) arcRefund);
        editor.commit();
    }

    public void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        arcRefund = (double)sharedPreferences.getFloat(ARC_PREFS, (float)NAN);

        if (arcRefund == NAN)
        {
            SettingsDialog settingsDialog = new SettingsDialog();
            settingsDialog.show(getSupportFragmentManager(), "settingsDialog");
        }
    }
}