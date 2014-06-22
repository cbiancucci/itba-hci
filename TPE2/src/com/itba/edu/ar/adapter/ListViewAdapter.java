package com.itba.edu.ar.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itba.edu.ar.model.Subcategory;

public class ListViewAdapter extends ArrayAdapter {
	Activity context;
	List<Subcategory> items;
	int layoutId;
	int textId;

	public ListViewAdapter(Activity context, int layoutId, int textId, 
			List<Subcategory> items) {
		super(context, layoutId, items);

		this.context = context;
		this.items = items;
		this.layoutId = layoutId;
		this.textId = textId;
	}

	public View getView(int pos, View convertView, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View row = inflater.inflate(layoutId, null);
		TextView label = (TextView) row.findViewById(textId);

		label.setText(items.get(pos).getName());

		return (row);
	}
}
