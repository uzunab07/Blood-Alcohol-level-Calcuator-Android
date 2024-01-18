package com.example.homework4;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.homework4.fragments.AddDrinkFragment;
import com.example.homework4.fragments.BACFragment;
import com.example.homework4.fragments.SetProfileFragment;
import com.example.homework4.fragments.ViewDrinksFragment;
import com.example.homework4.models.Drink;
import com.example.homework4.models.Profile;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SetProfileFragment.GListener, AddDrinkFragment.ADListener, ViewDrinksFragment.VDListener {
    TextView currentWeight, numOfDrinks, BACLevel, BACStatus;

    private static final DecimalFormat df = new DecimalFormat("0.000");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.containerView, new BACFragment(),"MainFragment")
                .commit();
    }

    @Override
    public void sendGenderAndWeight(String gender, int weight) {
        Log.d("TAG", "sendGenderAndWeight: "+gender+" ; "+weight);
       BACFragment BACFragmentFragment = (BACFragment) getSupportFragmentManager().findFragmentByTag("MainFragment");

        BACFragmentFragment.settingValuesFromGenderFragment(new Profile(weight,gender));
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void cancelSetGender() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void sendDrink(Drink drink) {
        BACFragment BACFragmentFragment = (BACFragment) getSupportFragmentManager().findFragmentByTag("MainFragment");

        Log.d("TAG", "sendDrink: "+drink);
        BACFragmentFragment.settingValuesFromAddDrinkFragment(drink);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void cancelAddDrink() {
        getSupportFragmentManager().popBackStack();
    }


    @Override
    public void sendDrinkList(ArrayList<Drink> drinks) {
        BACFragment BACFragmentFragment = (BACFragment) getSupportFragmentManager().findFragmentByTag("MainFragment");

        BACFragmentFragment.settingNewDrinkList(drinks);
        getSupportFragmentManager().popBackStack();
    }
}