package com.itba.edu.ar;

import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.itba.edu.ar.adapter.ListViewAdapter;
import com.itba.edu.ar.model.Category;
import com.itba.edu.ar.model.Subcategory;
import com.itba.edu.ar.parser.SubcategoryParser;

public class SubcategoryFragment extends Fragment {

	private TextToSpeech mTts;
	private List<Subcategory> subcategories;
	private void initMap() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initMap();
		View rootView = inflater.inflate(R.layout.category_fragment_layout,
				container, false);

		mTts = new TextToSpeech(getActivity(),
				new TextToSpeech.OnInitListener() {
					@Override
					public void onInit(int status) {
						mTts.setLanguage(new Locale("spa", "ESP"));
					}
				});
		
		Category cat = getArguments().getParcelable(
				getActivity().getString(R.string.category_key));

		//List<Subcategory> subcategories = new SubcategoryParser()
		subcategories = new SubcategoryParser()
				.getSubcategoriesFromCategory(cat);

		ListView listView = (ListView) rootView.findViewById(R.id.list);
		listView.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
		listView.setSelector(R.drawable.listitem_background);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapter, View v,
					int position, long arg3) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), ProductListActivity.class);
				Subcategory subcat = (Subcategory) adapter.getAdapter()
						.getItem(position);
				intent.putExtra("subcategory", subcat);
				startActivity(intent);
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> adapter, View arg1,
                    int pos, long id) {
            	mTts.speak(((Subcategory)adapter.getAdapter().getItem(pos)).getName(), TextToSpeech.QUEUE_FLUSH, null);
				return true;
            }
        }); 
		listView.setAdapter(new ListViewAdapter(getActivity(),
				R.layout.category_fragment_layout, R.id.text1, subcategories));

		return rootView;
	}
	
	private static final int SPEAK = 20141;

	public List<Subcategory> getSubCategories(){
		return subcategories;
	}
	
}
