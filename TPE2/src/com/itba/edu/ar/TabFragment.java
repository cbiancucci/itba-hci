package com.itba.edu.ar;

import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itba.edu.ar.adapter.ListViewAdapter;

public class TabFragment extends ListFragment {

	HashMap<String, String[]> subcategories; 
	
	private void initMap(){
		String[] listAccesorios = { "Anteojos", "Aros", "Billeteras", "Carteras",
				"Cinturones", "Collares", "Gorras", "Llaveros", "Mochilas",
				"Pulseras", "Relojes" };
		String[] listCalzado = { "Alpargatas", "Balerinas", "Borcegos", "Botas",
				"Mocasines", "Ojotas", "Pantuflas", "Sandalias", "Zapatillas",
				"Zapatos", "Zuecos" };
		String[] listIndumentaria = { "Blazers", "Buzos", "Calzas", "Campera",
				"Camisas", "Cardigans", "Chalecos", "Jeans", "Pantalones",
				"Pilotos", "Polleras", "Remeras", "Sacos", "Sweaters", "Vestidos" };
		
		subcategories = new HashMap<String, String[]>();
		
		subcategories.put("ACCESORIOS", listAccesorios);
		subcategories.put("CALZADO", listCalzado);
		subcategories.put("INDUMENTARIA", listIndumentaria);
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initMap();
		View rootView = inflater.inflate(R.layout.category_fragment_layout,
				container, false);

		if(getArguments() == null) {
			Log.e("", "Arguments NULL");
		}
		if(savedInstanceState == null) {
			Log.e("", "Bundle NULL");
		}
		String[] listItems = subcategories.get(getArguments().get(getActivity().getString(R.string.category_key)));
		// Para que se vean todas las imagenes
		boolean[] listImages = new boolean[listItems.length];
		for (int i = 0; i < listImages.length; i++) {
			listImages[i] = true;
		}

		setListAdapter(new ListViewAdapter(getActivity(),
				R.layout.category_fragment_layout, R.id.text1, R.id.image1,
				listItems, listImages));
		return rootView;
	}
}
