package com.example.blockcalculator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class SettingsDialog extends AppCompatDialogFragment
{
    private EditText arcRefundbox;
    private SettingsDialogListener settingsDialogListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Set your arc multiplay")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        settingsDialogListener.applyText(arcRefundbox.getText().toString());
                    }
                });

        arcRefundbox = (EditText) view.findViewById(R.id.arcbonusbox);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context)
    {
        super.onAttach(context);

        try {
            settingsDialogListener = (SettingsDialogListener) context;
        } catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString() + "Must implement interface") ;
        }
    }



}
