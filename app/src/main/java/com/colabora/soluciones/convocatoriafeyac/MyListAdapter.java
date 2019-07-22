package com.colabora.soluciones.convocatoriafeyac;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.colabora.soluciones.convocatoriafeyac.Modelos.Concepto;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<Concepto> {

    //the list values in the List of type hero
    List<Concepto> conceptoList;

    //activity context
    Context context;

    //the layout resource file for the list items
    int resource;

    //constructor initializing the values
    public MyListAdapter(Context context, int resource, List<Concepto> conceptoList) {
        super(context, resource, conceptoList);
        this.context = context;
        this.resource = resource;
        this.conceptoList = conceptoList;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        TextView textViewName = view.findViewById(R.id.listNombreConcepto);
        TextView textViewPrecio = view.findViewById(R.id.listPrecioConcepto);

        //getting the hero of the specified position
        Concepto concepto = conceptoList.get(position);


        textViewName.setText(concepto.getNombre());
        textViewPrecio.setText(concepto.getPrecio());

        //finally returning the view
        return view;
    }

}
