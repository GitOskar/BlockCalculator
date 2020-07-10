package com.example.blockcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {

    double arcRefund = 1.9, maxPr=0, currOnLvl=0, prOnPlace=0, refund=0;
    EditText maxPRBox, currOnLvlBox, prOnPlaceBox, refundBox;
    TextView screen;
    Button button, refreshButton, settingsButton;

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
    }
}