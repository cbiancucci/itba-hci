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

import com.itba.edu.ar.model.Category;
import com.itba.edu.ar.model.Product;
import com.itba.edu.ar.model.Subcategory;

public class ProductParser {

	Product prod;
	List<Product> listArray;

	public List<Product> getData(String url) {

		listArray = new ArrayList<Product>();

		try {
			DefaultHttpClient defaultClient = new DefaultHttpClient();
			HttpGet httpGetRequest = new HttpGet(url);
			HttpResponse httpResponse = defaultClient.execute(httpGetRequest);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent(), "UTF-8"));

			String json = reader.readLine();
			JSONObject jsonObject = new JSONObject(json);
			
			JSONArray arr = (JSONArray) jsonObject.get("products");
			for (int i = 0; i < arr.length(); i++) {
			    JSONObject product = (JSONObject) arr.get(i);
			    prod = new Product();
			    prod.setId((Integer) product.get("id"));
			    prod.setName((String) product.get("name"));
			    prod.setPrice((Integer) product.get("price"));
			    JSONArray imageUrl = (JSONArray) product.get("imageUrl");
			    prod.setImageUrl((String) imageUrl.get(0));
			    
			    JSONObject category = (JSONObject) product.get("category");
			    Category c = new Category();
			    c.setId((Integer) category.get("id"));
			    c.setName((String) category.get("name"));
			    prod.setCategory(c);
			    
			    JSONObject subcategory = (JSONObject) product.get("subcategory");
			    Subcategory s = new Subcategory();
			    s.setId((Integer) subcategory.get("id"));
			    s.setName((String) subcategory.get("name"));
			    prod.setSubcategory(s);
			    
			    listArray.add(prod);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listArray;
	}

}
