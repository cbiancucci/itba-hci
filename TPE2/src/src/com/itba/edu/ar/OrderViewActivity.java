package com.itba.edu.ar;

import java.util.ArrayList;
import java.util.List;

import com.itba.edu.ar.adapter.ProductsAdapter;
import com.itba.edu.ar.model.Item;
import com.itba.edu.ar.model.Order;
import com.itba.edu.ar.model.Product;
import com.itba.edu.ar.parser.OrderDetailParser;
import com.itba.edu.ar.utils.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderViewActivity extends Activity{
	public  String url = "http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=GetOrderById";
	Order orden;
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_view);
		
		if (Utils.isNetworkAvailable(OrderViewActivity.this)){
			
			if(getIntent().hasExtra("order")){
				Bundle extras = getIntent().getExtras();
				final Integer id = extras.getInt("order");
				if(id != null){
					getActionBar().setTitle(getText(R.string.order)+ " Numero :" + id.toString());
					SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
					String user = pref.getString("user", "none");
					if(!user.equals("none"))
						url = url + "&username=" + user;
					String token = pref.getString("token", "none");
					if(!token.equals("none"))
						url = url + "&authentication_token=" + token;
					url = url + "&id=" + id;

					
					new OrderDetailTask().execute(url);
				}
			}
			else
				Toast.makeText(getApplicationContext(), "No hay order", Toast.LENGTH_LONG).show();;
		}
		else{
			Toast.makeText(getApplicationContext(),R.string.no_network, Toast.LENGTH_LONG).show();
		}
	}
	class OrderDetailTask extends AsyncTask<String, Void, Void>{
		ProgressDialog pDialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(OrderViewActivity.this);
			pDialog.setMessage(getString(R.string.load_order));
			pDialog.show();
		}
		@Override
		protected Void doInBackground(String... params) {
			orden = new OrderDetailParser().getData(params[0]);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}
			
			if(orden == null){
				Toast.makeText(getApplicationContext(), getString(R.string.no_data_api), Toast.LENGTH_LONG).show();
			}
			else
				setAdapterToListview();
		}
	}
	public void setAdapterToListview(){
		List<Product> productos = new ArrayList<Product>();
		TextView price = (TextView) findViewById(R.id.priceVal);
		TextView quantity = (TextView) findViewById(R.id.quantityVal);
		TextView shipped = (TextView) findViewById(R.id.shippedVal);
		TextView proccesed = (TextView) findViewById(R.id.proccesedVal);
		TextView send = (TextView) findViewById(R.id.deliveredVal);
		TextView received = (TextView) findViewById(R.id.receivedVal);
		Integer acumPrice = 0;
		Integer acumQuantity = 0;
		for(Item item : orden.getItems()){
			productos.addAll(item.getProducts());
			acumPrice += item.getPrice();
			acumQuantity += item.getQuantity();
		}
		price.setText(acumPrice.toString());
		quantity.setText(acumQuantity.toString());
		if(orden.getShippedDate()!= null)
			shipped.setText(orden.getShippedDate());
		if(orden.getProcessedDate() != null)
			proccesed.setText(orden.getProcessedDate());
		if(orden.getDeliveredDate() != null)
			send.setText(orden.getDeliveredDate());
		if(orden.getReceivedDate() != null)
			received.setText(orden.getReceivedDate());
		ListView listView = (ListView) findViewById(R.id.orderlist);
		ProductsAdapter objAdapter = new ProductsAdapter(OrderViewActivity.this,
				R.layout.product_item, productos);
		
		listView.setDescendantFocusability(ListView.FOCUS_BLOCK_DESCENDANTS);
		listView.setSelector(R.drawable.listitem_background);
		
		listView.setAdapter(objAdapter);
	}
	
}
