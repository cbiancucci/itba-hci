package com.itba.edu.ar;

import java.util.List;

import com.itba.edu.ar.adapter.OrderAdapter;
import com.itba.edu.ar.model.Order;
import com.itba.edu.ar.parser.OrderParser;
import com.itba.edu.ar.utils.Utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class OrderActivity extends Activity{
	
	
	List<Order> arrayOfList;
	ListView listView;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list);
		getActionBar().setHomeButtonEnabled(true);
		listView = (ListView) findViewById(R.id.orderlist);
		
		if (Utils.isNetworkAvailable(OrderActivity.this)) {
			SharedPreferences mypref = getSharedPreferences("login",MODE_PRIVATE);
			if (mypref.getString("token", "none").equals("none")){
				FragmentManager fragmentManager = getFragmentManager();
				MissingUserFragment fragment = new MissingUserFragment();
				fragment.show(fragmentManager, "dialog");
			}
			else{
				String jsonUrl = "http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=GetAllOrders";
				jsonUrl = jsonUrl + "&username=" + mypref.getString("user", "usuario");
				jsonUrl = jsonUrl + "&authentication_token=" + mypref.getString("token", "none");
				//Toast.makeText(getApplicationContext(), jsonUrl, Toast.LENGTH_LONG).show();
				new OrderTask().execute(jsonUrl);
			}
		} else {
			Toast.makeText(getApplicationContext(),R.string.no_network, Toast.LENGTH_LONG).show();
		}
	}
	
	class OrderTask extends AsyncTask<String, Void, Void>{

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(OrderActivity.this);
			pDialog.setMessage(getString(R.string.load_orders));
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... params) {
			arrayOfList = new OrderParser().getData(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == arrayOfList || arrayOfList.size() == 0) {
				showToast("No data found from web!!!");
				OrderActivity.this.finish();
			} else {
				setAdapterToListview();
			}

		}
	}
	
	public void setAdapterToListview() {
		OrderAdapter objAdapter = new OrderAdapter(OrderActivity.this,
				R.layout.order_item, arrayOfList);
		
		listView.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
		listView.setSelector(R.drawable.listitem_background);
	
		listView.setAdapter(objAdapter);
	}
	public void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
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
}
