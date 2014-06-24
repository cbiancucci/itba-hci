package com.itba.edu.ar;

import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.itba.edu.ar.adapter.SubcategoryAdapter;
import com.itba.edu.ar.model.Category;
import com.itba.edu.ar.model.Subcategory;
import com.itba.edu.ar.parser.SubcategoryParser;
import com.itba.edu.ar.utils.Utils;

public class SubcategoriesActivity extends Activity {

	private TextToSpeech mTts;
	private List<Subcategory> subcategories;
	ProgressBar pBar;
	ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subcategories_layout);
		listView = (ListView) findViewById(R.id.subcategories);
		pBar = (ProgressBar) findViewById(R.id.subcategory_bar);
		
		mTts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				mTts.setLanguage(new Locale("spa", "ESP"));
			}
		});

		if (getIntent().hasExtra("category")) {
			
			if (Utils.isNetworkAvailable(SubcategoriesActivity.this)) {
				Category category = getIntent().getParcelableExtra("category");
				setTitle(category.getName());
				new MyTask(category).execute();
			} else {
				showToast(getString(R.string.no_network));
			}	
		}
	}
	
	class MyTask extends AsyncTask<String, Void, Void> {

		Category category;
		
		public MyTask(Category cat) {
			category = cat;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(String... params) {
			subcategories = new SubcategoryParser().getSubcategoriesFromCategory(category);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (null != pBar && pBar.getVisibility() == View.VISIBLE) {
				pBar.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
			}

			if (null == subcategories || subcategories.size() == 0) {
				showToast(getString(R.string.no_data_api));
				SubcategoriesActivity.this.finish();
			} else {
				setAdapterToListview();
			}

		}
	}
	
public void setAdapterToListview() {
	listView.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
	listView.setSelector(R.drawable.listitem_background);
	listView.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapter, View v,
				int position, long arg3) {
			Intent intent = new Intent(getApplicationContext(),
					ProductListActivity.class);
			Subcategory subcat = (Subcategory) adapter.getAdapter()
					.getItem(position);
			intent.putExtra("subcategory", subcat);
			startActivity(intent);
		}
	});

	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

		public boolean onItemLongClick(AdapterView<?> adapter,
				View arg1, int pos, long id) {
			mTts.speak(
					((Subcategory) adapter.getAdapter().getItem(pos))
							.getName(), TextToSpeech.QUEUE_FLUSH, null);
			return true;
		}
	});
	listView.setAdapter(new SubcategoryAdapter(this,
			R.layout.list_item_layout, R.id.name,
			subcategories));
}

	public void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
	}

	public List<Subcategory> getCategories() {
		return subcategories;
	}
}