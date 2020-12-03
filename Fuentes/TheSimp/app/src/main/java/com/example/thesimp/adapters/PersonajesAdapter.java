package com.example.thesimp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.thesimp.R;
import com.example.thesimp.dto.Frases;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PersonajesAdapter extends ArrayAdapter<Frases> {
    private List<Frases> frases;
    private Activity activity;
    public PersonajesAdapter(@NonNull Activity context, int resource, @NonNull List<Frases> objects) {
        super(context, resource, objects);
        this.frases = objects;
        this.activity = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_frases,null,true);
        //TextView nombreTxt = rowView.findViewById(R.id.nombre_txt);
        //TextView fraseTxt = rowView.findViewById(R.id.frase_txt);
        //ImageView imagPer = rowView.findViewById(R.id.image_personaje);
        //nombreTxt.setText(frases.get(position).getCharacter());
        //fraseTxt.setText(frases.get(position).getQuote());
        //ImageView image = rowView.findViewById(R.id.image_personaje);
        //Picasso.get().load(this.frases.get(position).getImage()).resize(100,100).centerCrop().into(image);

        TextView nombreTxt = rowView.findViewById(R.id.nombre_txt);
        nombreTxt.setText(frases.get(position).getName());
        //nombreTxt.setText(frases.get(position).getCharacter());

        return rowView;

    }
}
