package com.example.homework4.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.homework4.MainActivity;
import com.example.homework4.R;
import com.example.homework4.models.Drink;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddDrinkFragment extends Fragment {

    Drink drink;
    int drinkSize;
    int alcoholPercentage;
    Calendar date;
    RadioGroup drinkSizes;
    SeekBar seekBar;
    TextView seekProgress;
    ADListener adListener;

    RadioButton radioOneOz;

    public AddDrinkFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ( (MainActivity) getActivity()).getSupportActionBar().setTitle("Add Drinks");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_drink, container, false);
        drinkSizes = view.findViewById(R.id.drinkSizes);
        seekBar = view.findViewById(R.id.seekBar);
        seekProgress = view.findViewById(R.id.seekProgress);
        radioOneOz = view.findViewById(R.id.radioOneOz);

        //Setting Default Values
        radioOneOz.setChecked(true);
        seekBar.setProgress(12);
        seekProgress.setText(String.valueOf(seekBar.getProgress()));
        alcoholPercentage = seekBar.getProgress();
        drinkSize = 1;


        view.findViewById(R.id.cancelDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adListener.cancelAddDrink();
            }
        });

        view.findViewById(R.id.buttonAddDrink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                date = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
                String Date1 = simpleDateFormat.format(date.getTime()).toString();

                Drink drink = new Drink(drinkSize, alcoholPercentage, Date1);

                adListener.sendDrink(drink);
            }
        });

        drinkSizes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedRadioButton) {
                if(checkedRadioButton == R.id.radioFiveOz){
                    drinkSize = 5;
                } else if (checkedRadioButton == R.id.radioOneOz){
                    drinkSize = 1;
                } else if (checkedRadioButton == R.id.radioTwelveOz){
                    drinkSize = 12;
                }

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                seekProgress.setText(String.valueOf(progress));
                alcoholPercentage = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof ADListener){
            adListener = (ADListener) context;
        }else{
            throw  new RuntimeException(context.toString()+" Must Implement ADListener");
        }
    }

   public interface ADListener{
        void sendDrink(Drink drink);
        void cancelAddDrink();

   }
}