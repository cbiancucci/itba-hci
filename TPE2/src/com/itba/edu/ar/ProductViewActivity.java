package com.itba.edu.ar;

import java.io.InputStream;
import java.net.URL;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.itba.edu.ar.model.Product;
import com.itba.edu.ar.parser.ProductParser;

public class ProductViewActivity extends Activity implements ViewFactory {

	ImageSwitcher imageSwitcher;
	float initialX, finalX;
	private int position = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_view);

		// MODIFICAR!!!!!!!
		if (getIntent().hasExtra("product")) {

			final Product product = getIntent().getParcelableExtra("product");

			if (product != null) {
				getActionBar().setTitle(product.getName());
				final TextView price = (TextView) findViewById(R.id.product_price);
				final TextView color = (TextView) findViewById(R.id.colVal);
				final TextView age = (TextView) findViewById(R.id.ageVal);
				final TextView gender = (TextView) findViewById(R.id.genderVal);
				final TextView brand = (TextView) findViewById(R.id.brandVal);
				final TextView material = (TextView) findViewById(R.id.materialVal);
				final TextView occasion = (TextView) findViewById(R.id.occasionVal);
				final TextView size = (TextView) findViewById(R.id.sizeVal);

				// Falta agregar cuando no es calzado, que atributos trae?
				price.setText("$" + product.getPrice());
				color.setText(product.getAttribute("Color"));
				age.setText(product.getAttribute("Edad"));
				gender.setText(product.getAttribute("Genero"));
				brand.setText(product.getAttribute("Marca"));
				material.setText(product.getAttribute("Material-Calzado"));
				occasion.setText(product.getAttribute("Ocasion"));
				size.setText(product.getAttribute("Talle-Calzado"));

				/*
				 * RatingBar ratingBar = (RatingBar)
				 * findViewById(R.id.ratingBar1); LayerDrawable stars =
				 * (LayerDrawable) ratingBar.getProgressDrawable();
				 * stars.getDrawable(2).setColorFilter(Color.YELLOW,
				 * Mode.SRC_ATOP);
				 */
				try {

					imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);

					imageSwitcher.setFactory(this);

					imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(
							this, android.R.anim.fade_in));
					imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
							this, android.R.anim.fade_out));

					imageSwitcher.setImageDrawable(getImageDrawable(product
							.getImageUrl().get(0)));

					imageSwitcher.setOnTouchListener(new OnTouchListener() {

						public boolean onTouch(View v, MotionEvent event) {
							// TODO Auto-generated method stub
							if (event.getAction() == MotionEvent.ACTION_DOWN) {
								initialX = (int) event.getX();
								return true;
							} else if (event.getAction() == MotionEvent.ACTION_UP) {
								finalX = (int) event.getX();
								if (finalX - initialX > 100) {
									position--;
									if (position < 0) {
										position = product.getImageUrl().size() - 1;
									}
									imageSwitcher
											.setImageDrawable(getImageDrawable(product
													.getImageUrl()
													.get(position)));
								} else if (initialX - finalX > 100) {
									position++;
									if (position >= product.getImageUrl()
											.size()) {
										position = 0;
									}
									imageSwitcher
											.setImageDrawable(getImageDrawable(product
													.getImageUrl()
													.get(position)));
								}
								return true;
							}
							return false;
						}
					});
				} catch (Exception ex) {

				}
			}
		}

	}

	private Drawable getImageDrawable(String url) {
		Drawable image;
		URL imageUrl;
		try {
			imageUrl = new URL(url);
			InputStream stream = imageUrl.openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(stream);
			image = new BitmapDrawable(getResources(), bitmap);
		} catch (Exception e) {
			image = null;
		}
		return image;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.only_settings, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent order = new Intent(this, UserSettingActivity.class);
			startActivity(order);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public View makeView() {
		ImageView imageView = new ImageView(this);
		imageView.setBackgroundColor(Color.WHITE);
		imageView.setScaleType(ImageView.ScaleType.CENTER);
		imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return imageView;
	}

}
