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


import com.itba.edu.ar.model.Order;

public class OrderParser {
	Order ord;
	List<Order> listArray;

	public List<Order> getData(String url) {

		listArray = new ArrayList<Order>();

		try {
			

			
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
			   
			    ord.setStatus( (String)order.get("status"));
			    
//			    JSONObject address = (JSONObject) order.getJSONObject("address");
//			    if(address.equals(null))
//			    	ord.setAddres("No hay direccion");
//			    else
//			    	ord.setAddres((String) address.get("name"));
			   
//			    ord.setDeliveredDate((String) order.get("deliveredDate"));
//			    ord.setProcessedDate((String) order.get("processedDate"));
//			    ord.setShippedDate((String) order.get("shippedDate"));
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
