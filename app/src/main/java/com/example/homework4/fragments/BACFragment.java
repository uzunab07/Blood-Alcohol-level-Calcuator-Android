package com.example.homework4.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.homework4.R;
import com.example.homework4.models.Drink;
import com.example.homework4.models.Profile;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class BACFragment extends Fragment {
    TextView currentWeight, numOfDrinks, BACLevel, BACStatus;
    ArrayList<Drink> drinks = new ArrayList<>();
    Profile user;
    Button addDrinks,viewDrinks;
    String numberOfDrinks,gender=null;
    int  weight = 0;
    double BACl = 0;
    private static final DecimalFormat df = new DecimalFormat("0.000");
    public BACFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

       currentWeight =  view.findViewById(R.id.currentWeight);
       BACLevel = view.findViewById(R.id.BACLevel);
       BACStatus = view.findViewById(R.id.BACStatus);
       numOfDrinks = view.findViewById(R.id.numOfDrinks);
       addDrinks = view.findViewById(R.id.addDrinks);
       BACStatus.setBackgroundColor(getResources().getColor(R.color.safe));
       viewDrinks = view.findViewById(R.id.viewDrinks);
       addDrinks.setEnabled(false);

       viewDrinks.setEnabled(false);


       //Returning from Setting the weight and Gender ArrayList of drinks should be empty and User should not be set yet
       //Returning from adding drinks  ArrayList of drinks can o not be empty but the user Profile should already been set here after each return I recalculate de BAC again
       if(user!=null && this.drinks.size()==0){
           addDrinks.setEnabled(true);
           viewDrinks.setEnabled(true);

           currentWeight.setText(""+user.weight+"lbs ("+user.gender+")");
           BACLevel.setText("0.000");
           BACStatus.setText("You're safe.");
           BACStatus.setBackgroundColor(getResources().getColor(R.color.safe));
           numOfDrinks.setText("0");
       }else if(this.drinks.size()!=0 && user!=null){
           addDrinks.setEnabled(true);
           viewDrinks.setEnabled(true);
           numOfDrinks.setText(String.valueOf(drinks.size()));
           currentWeight.setText(""+user.weight+"lbs ("+user.gender+")");
           BACLevel.setText(String.valueOf( df.format(BACCalculator(this.drinks,user))));
       }


        view.findViewById(R.id.buttonWeightSet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drinks = new ArrayList<>();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerView,new com.example.homework4.fragments.SetProfileFragment(),"SetGenderFragment")
                        .addToBackStack(null)
                        .commit();

            }
        });

       view.findViewById(R.id.addDrinks).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (user == null){
                   Toast.makeText(getActivity(), "No profile yet", Toast.LENGTH_SHORT).show();
               } else {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.containerView,new com.example.homework4.fragments.AddDrinkFragment(),"AddDrinkFragment")
                        .addToBackStack(null)
                        .commit();
//
               }
           }
       });

       view.findViewById(R.id.viewDrinks).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if (drinks.isEmpty()){
                   Toast.makeText(getActivity(), "No drinks yet", Toast.LENGTH_SHORT).show();
               } else {
                 getActivity().getSupportFragmentManager()
                         .beginTransaction()
                         .replace(R.id.containerView,ViewDrinksFragment.newInstance(drinks),"ViewDrinkFragment")
                         .addToBackStack(null)
                         .commit();
               }
           }
       });
       view.findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               drinks.clear();
               user = null;
               currentWeight.setText("N/A");
               BACLevel.setText("0.000");
               BACStatus.setText("You're safe.");
               BACStatus.setBackgroundColor(getResources().getColor(R.color.safe));
               numOfDrinks.setText("0");
               addDrinks.setEnabled(false);
               viewDrinks.setEnabled(false);
           }
       });
        return view;
    }

    public void settingValuesFromGenderFragment(Profile profile){
        this.user  = profile;
    }
    public void settingValuesFromAddDrinkFragment(Drink drink){
        this.drinks.add(drink);
    }

    public void settingNewDrinkList(ArrayList<Drink> drinks){
        this.drinks = new ArrayList<>();
        this.drinks = drinks;
    }

    public double BACCalculator(ArrayList<Drink> drinks, Profile profile){

        Profile profiles = profile;
        double r;
        if(profiles.isGender() == "Male"){
            r = 0.73;
        } else {
            r = 0.66;
        }
        BACl = 0;
        for (Drink drunks:drinks) {
            BACl = BACl + (((drunks.getSize1() * drunks.getAlcoholPercentage()) / 100) * 5.14) / (profiles.getWeight() * r);
        }

        if(BACl <= 0.08){
            BACStatus.setText("You're safe.");
            BACStatus.setBackgroundColor(getResources().getColor(R.color.safe));
            addDrinks.setEnabled(true);
        } else if (BACl > 0.08 && BACl <= 0.2){
            BACStatus.setText("Be careful.");
            BACStatus.setBackgroundColor(getResources().getColor(R.color.careful));
            addDrinks.setEnabled(true);
        } else if (BACl > 0.2){
            BACStatus.setText("Over the limit!");
            BACStatus.setBackgroundColor(getResources().getColor(R.color.overLimit));
            Toast.makeText(getActivity(), "No more drinks for you.", Toast.LENGTH_SHORT).show();
            addDrinks.setEnabled(false);
        }
        return BACl;

    }
}