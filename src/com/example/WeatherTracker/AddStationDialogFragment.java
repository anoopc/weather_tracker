package com.example.WeatherTracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

/**
 * Created by anoopc on 12/3/14.
 */
public class AddStationDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        builder.setView(layoutInflater.inflate(R.layout.add_station_dialog, null));
        builder.setMessage(R.string.add_station);

        builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView textView = (TextView) AddStationDialogFragment.this.
                        getDialog().findViewById(R.id.station_input_text);
                if (textView != null) {
                    String stationCode = textView.getText().toString().trim();
                    if (stationCode.length() > 0) {
                        ((MyActivity)getActivity()).performAddStation(stationCode);
                    } else {
                        MyAppUtility.showToast(
                                getActivity(),
                                getActivity().getApplicationContext().getString(R.string.add_station_failure));
                    }
                }
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddStationDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }
}
