package com.example.homework4.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.homework4.R;


public class SetProfileFragment extends Fragment {

    EditText weightNumber;
    RadioGroup gender;
    RadioButton female, male;
    Button  weightSetFinish;
    String genderType, value = null;
    int weight;
    GListener gListener;


    public SetProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        View view = inflater.inflate(R.layout.fragment_set_gender, container, false);

        weightNumber = view.findViewById(R.id.weightNumber);
        gender = view.findViewById(R.id.gender);
        female = view.findViewById(R.id.female);
        male = view.findViewById(R.id.male);


        //Initialized values
        female.setChecked(true);
        genderType = "Female";

        view.findViewById(R.id.weightCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gListener.cancelSetGender();
            }
        });

        view.findViewById(R.id.weightSetFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = weightNumber.getText().toString();
                if(value.isEmpty()){
                    Toast.makeText(getActivity(), "Insert a weight to Continue", Toast.LENGTH_SHORT).show();
                } else {
                    weight = Integer.valueOf(value);

                    gListener.sendGenderAndWeight(genderType,weight);
                }
            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.female){
                    genderType = "Female";
                } else if (i == R.id.male) {
                    genderType = "Male";
                }
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof GListener){
            gListener = (GListener) context;
        }else{
            throw  new RuntimeException(context.toString()+" Must Implement GListener");
        }
    }

    public  interface GListener{
        void sendGenderAndWeight(String gender, int weight);
        void cancelSetGender();
    }
}

