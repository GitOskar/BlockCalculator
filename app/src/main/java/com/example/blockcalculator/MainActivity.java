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

public class MainActivity extends AppCompatActivity implements SettingsDialogListener
{
    private float arcRefund;
    private EditText maxPRBox, currOnLvlBox, prOnPlaceBox, refundBox;
    private TextView screen;
    private Button button, refreshButton, settingsButton;
    private final String SHARED_PREFS = "mySharedPrefs", ARC_PREFS = "arcSharedPrefs";
    Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Loader loader = new Loader(getSharedPreferences(SHARED_PREFS, MODE_PRIVATE));
        button = (Button) findViewById(R.id.button);
        refreshButton = (Button) findViewById(R.id.refreshbutton);
        settingsButton = (Button) findViewById(R.id.settingsbutton);
        screen = (TextView) findViewById(R.id.screen);
        maxPRBox = (EditText) findViewById(R.id.maxPRBox);
        currOnLvlBox = (EditText) findViewById(R.id.currOnLvlBox);
        prOnPlaceBox = (EditText) findViewById(R.id.actualOnPlaceBox);
        refundBox = (EditText) findViewById(R.id.refundBox);
        arcRefund = loader.loadData();

        if (arcRefund == Loader.NAN)
            settingsDialog();
    }

    public void calculateOnClick(View view)
    {
        float maxPr, currOnLvl, prOnPlace, refund;
        try {
            maxPr = Float.parseFloat(maxPRBox.getText().toString());
            currOnLvl = Float.parseFloat(currOnLvlBox.getText().toString());
            prOnPlace = Float.parseFloat(prOnPlaceBox.getText().toString());
            refund = Float.parseFloat(refundBox.getText().toString());
        } catch (NumberFormatException e)
        {
            screen.setText("Enter all data");
            return;
        }

        int blockCost = BlockCalculator.block(maxPr, currOnLvl, prOnPlace);

        if (currOnLvl + blockCost >= maxPr)
        {
            screen.setText("You cannot block this place");
            return;
        }

        int profit = BlockCalculator.profit(blockCost, arcRefund, refund);

        if (profit >= 0)
            screen.setText("Block cost: " + blockCost + "\nProfit: " + profit);
        else
            screen.setText("Block cost: " + blockCost + "\nLoss: " + profit);
    }

    public void refreshOnClick(View view)
    {
        screen.setText("");
        maxPRBox.setText("");
        refundBox.setText("");
        prOnPlaceBox.setText("");
        currOnLvlBox.setText("");
    }

    public void settingsOnClick(View view)
    {
        settingsDialog();
    }

    @Override
    public void applyText(String arcRefund) {
        try {
            this.arcRefund = Float.parseFloat(arcRefund);
        } catch (NumberFormatException e)
        {
            settingsDialog();
            return;
        }
        if (this.arcRefund < 1.0 || this.arcRefund > 6.0)
        {
            settingsDialog();
        }
        loader = new Loader(getSharedPreferences(SHARED_PREFS, MODE_PRIVATE));
        loader.saveData(this.arcRefund);
    }

    private void settingsDialog()
    {
        SettingsDialog settingsDialog = new SettingsDialog();
        settingsDialog.show(getSupportFragmentManager(), "settingsDialog");
    }
}