package com.example.homework4.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework4.MainActivity;
import com.example.homework4.R;
import com.example.homework4.models.*;
import com.example.homework4.models.Drink;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewDrinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewDrinksFragment extends Fragment implements DrinksRecyclerViewAdapter.IDrinksListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String DRINK_LIST = "LIST";

    RecyclerView recyclerView;
    Button close;
    RecyclerView.LayoutManager layoutManager;
    DrinksRecyclerViewAdapter recyclerViewAdapter;
    VDListener vdListener;
    ImageButton buttonDscDate,buttonAscDate,buttonDscAlcohol,buttonAscAlcohol;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");

    // TODO: Rename and change types of parameters
    private ArrayList<Drink> drinks;

    public ViewDrinksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param drinks Parameter 1.
     * @return A new instance of fragment ViewDrinksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewDrinksFragment newInstance(ArrayList<Drink> drinks) {
        ViewDrinksFragment fragment = new ViewDrinksFragment();
        Bundle args = new Bundle();
        args.putSerializable(DRINK_LIST, drinks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            drinks = (ArrayList<Drink>) getArguments().getSerializable(DRINK_LIST);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ( (MainActivity) getActivity()).getSupportActionBar().setTitle("View Drinks");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_drinks_recycler, container, false);



        recyclerView = view.findViewById(R.id.recyclerView);
        close = view.findViewById(R.id.buttonViewClose);
        buttonDscAlcohol = view.findViewById(R.id.buttonDscAlcohol);
        buttonAscAlcohol = view.findViewById(R.id.buttonAscAlcohol);
        buttonDscDate = view.findViewById(R.id.buttonDscDate);
        buttonAscDate = view.findViewById(R.id.buttonAscDate);
        recyclerView.setHasFixedSize(true);



        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new DrinksRecyclerViewAdapter(drinks,this);
        recyclerView.setAdapter(recyclerViewAdapter);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        buttonDscDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(drinks, new Comparator<Drink>() {
                    Date date1, date2;
                    @Override
                    public int compare(Drink drink1, Drink drink2) {
                        try {
                            date1= simpleDateFormat.parse(drink1.getDate());
                             date2 = simpleDateFormat.parse(drink2.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return date1.compareTo(date2);
                    }
                });
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        buttonAscDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(drinks, new Comparator<Drink>() {
                    Date date1, date2;
                    @Override
                    public int compare(Drink drink1, Drink drink2) {
                        try {
                            date1= simpleDateFormat.parse(drink1.getDate());
                            date2 = simpleDateFormat.parse(drink2.getDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return (-1)*date1.compareTo(date2);
                    }
                });
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        buttonAscAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(drinks, new Comparator<Drink>() {
                    @Override
                    public int compare(Drink drink1, Drink drink2) {
                        return (drink1.getAlcoholPercentage().intValue() - drink2.getAlcoholPercentage().intValue());
                    }
                });
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

        buttonDscAlcohol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(drinks, new Comparator<Drink>() {
                    @Override
                    public int compare(Drink drink1, Drink drink2) {
                        return -1*(drink1.getAlcoholPercentage().intValue() - drink2.getAlcoholPercentage().intValue());

                    }
                });
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });



        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof VDListener){
            vdListener = (VDListener) context;
        }else{
            throw  new RuntimeException(context.toString()+" Must Implement VDListener");
        }
    }

    @Override
    public void refreshRecycleView() {
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public interface VDListener{
        void sendDrinkList(ArrayList<Drink> drink);
    }
}