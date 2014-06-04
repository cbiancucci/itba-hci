package com.itba.edu.ar.adapter;

import com.digitalaria.gama.carousel.Carousel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.itba.edu.ar.R;

public class ImageAdapter extends BaseAdapter {
	 
	private int[] imageList = { R.drawable.cocacola1, R.drawable.cocacola,
			R.drawable.cocacola2 };
	
	private Context ctx;

	public ImageAdapter(Context c) {
		ctx = c; 
	}

	public int getCount() {

		return imageList.length;
	}

	public Object getItem(int arg0) {

		return arg0;
	}

	public long getItemId(int arg0) {

		return arg0;
	}

	public View getView(int arg0, View arg1, ViewGroup arg2) {

		ImageView iView = new ImageView(ctx);
		iView.setImageResource(imageList[arg0]);
		iView.setScaleType(ImageView.ScaleType.FIT_XY);
		iView.setLayoutParams(new Gallery.LayoutParams(200, 150));
		return iView;
	}
}