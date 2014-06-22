package com.itba.edu.ar;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itba.edu.ar.adapter.ProductsAdapter;
import com.itba.edu.ar.model.Product;
import com.itba.edu.ar.parser.ProductParser;
import com.itba.edu.ar.utils.Utils;

public class ProductListActivity extends Activity {
	private static final String jsonURL = "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsBySubcategoryId&id=1&page_size=10";

	List<Product> arrayOfList;
	ListView listView;
	ProgressBar pBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_list_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		listView = (ListView) findViewById(R.id.listview);
		pBar = (ProgressBar) findViewById(R.id.progress);
		

		if (Utils.isNetworkAvailable(ProductListActivity.this)) {
			new MyTask().execute(jsonURL);
		} else {
			showToast(getString(R.string.no_network));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	class MyTask extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(String... params) {
			arrayOfList = new ProductParser().getProductList(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (null != pBar && pBar.getVisibility() == View.VISIBLE) {
				pBar.setVisibility(View.GONE);
			}

			if (null == arrayOfList || arrayOfList.size() == 0) {
				showToast("No data found from web!!!");
				ProductListActivity.this.finish();
			} else {
				setAdapterToListview();
			}

		}
	}

	public void setAdapterToListview() {
		ProductsAdapter objAdapter = new ProductsAdapter(ProductListActivity.this,
				R.layout.product_item, arrayOfList);
		
		listView.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
		listView.setSelector(R.drawable.listitem_background);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(ProductListActivity.this, ProductViewActivity.class);
				startActivity(intent);
			}
		});
		
		listView.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}
}