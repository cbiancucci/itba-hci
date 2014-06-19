package com.itba.edu.ar.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.itba.edu.ar.OrderActivity;
import com.itba.edu.ar.model.Category;
import com.itba.edu.ar.model.Order;

public class OrderParser {
	Order ord;
	List<Order> listArray;

	public List<Order> getData(String url) {

		listArray = new ArrayList<Order>();

		try {
			
			Order od = new Order();
			od.setAddres("BLABLA");
			od.setLatitude(1);
			od.setLongitude(3);
			od.setDeliveredDate("Entregado");
			od.setReceivedDate("BLA");
			od.setProcessedDate("ProccessedDate");
			od.setShippedDate("ShippedDate");
			od.setStatus(1);
			Order oda = new Order();
			oda.setAddres("BLEBLE");
			oda.setLatitude(5);
			oda.setLongitude(300);
			oda.setDeliveredDate("Entregado");
			oda.setReceivedDate("BLA");
			oda.setProcessedDate("ProccessedDate");
			oda.setShippedDate("ShippedDate");
			oda.setStatus(1);
			listArray.add(oda);
			listArray.add(od);
			
			DefaultHttpClient defaultClient = new DefaultHttpClient();
			HttpGet httpGetRequest = new HttpGet(url);
			HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent(), "UTF-8"));

			String json = reader.readLine();
			JSONObject jsonObject = new JSONObject(json);
			
			JSONArray arr = (JSONArray) jsonObject.get("orders");
			for (int i = 0; i < arr.length(); i++) {
			    JSONObject order = (JSONObject) arr.get(i);
			    ord = new Order();
			    ord.setId( (Integer)order.get("id"));
			    ord.setStatus( (Integer)order.get("status"));
			    JSONObject address = (JSONObject) order.get("addess");
			    ord.setAddres((String) address.get("name"));
			    ord.setDeliveredDate((String) order.get("deliveredDate"));
			    ord.setProcessedDate((String) order.get("processedDate"));
			    ord.setShippedDate((String) order.get("shippedDate"));
			    ord.setReceivedDate((String) order.get("receivedDate"));
			    
			    ord.setLatitude((Integer) order.get("latitude"));
			    ord.setLongitude((Integer)order.get("longitude"));
			    
			    listArray.add(ord);
			}
	

		} catch (Exception e) {
			e.printStackTrace();
			Order ob = new Order();
			ob.setAddres(e.toString());
			listArray.add(ob);
		}

		return listArray;
	}
}
