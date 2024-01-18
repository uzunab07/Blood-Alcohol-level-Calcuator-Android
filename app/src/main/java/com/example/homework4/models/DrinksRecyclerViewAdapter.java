package com.example.homework4.models;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework4.R;

import java.util.ArrayList;


public class DrinksRecyclerViewAdapter extends RecyclerView.Adapter<DrinksRecyclerViewAdapter.DrinkViewHolder> {
    IDrinksListener iDrinksListener;
   public ArrayList<Drink> drinks;
    public DrinksRecyclerViewAdapter(ArrayList<Drink> data,IDrinksListener iDrinksListener){
        this.iDrinksListener = iDrinksListener;
        this.drinks = data;
    }
    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drink_row_item,parent,false   );

        DrinkViewHolder drinkViewHolder = new DrinkViewHolder(view,drinks,this.iDrinksListener);
        return drinkViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder holder, int position) {
        Drink drink = drinks.get(position);
        holder.textViewNDrinkSize.setText(String.valueOf(drink.getSize1())+"oz");
        holder.textVieDrinkAlcohol.setText(String.valueOf(drink.getAlcoholPercentage())+"% Alcohol");
        holder.textViewDateAdded.setText("Added "+drink.getDate());
        holder.position = position;
        holder.drink = drink;

    }

    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public static class DrinkViewHolder extends RecyclerView.ViewHolder{

        ImageButton delete;

        TextView textViewNDrinkSize;
        TextView textVieDrinkAlcohol;
        TextView textViewDateAdded ;

        int position;
        Drink drink;
        IDrinksListener iDrinksListener;
        public DrinkViewHolder(@NonNull View itemView,ArrayList<Drink> drinks,IDrinksListener iDrinksListener) {
            super(itemView);
     this.iDrinksListener = iDrinksListener;
          delete = itemView.findViewById(R.id.imageButtonDelete);

          textViewNDrinkSize =  itemView.findViewById(R.id.textViewDrinkSize);
          textVieDrinkAlcohol = itemView.findViewById(R.id.textViewAlcoholPercentage);
          textViewDateAdded = itemView.findViewById(R.id.textViewDateAdded);

          delete.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  drinks.remove(drink);
                  Log.d("demo", "onClick: remaning drinks are"+drinks.size());
                  iDrinksListener.refreshRecycleView();
              }
          });
        }
    }
    public interface IDrinksListener{
        public void refreshRecycleView();
    }
}
