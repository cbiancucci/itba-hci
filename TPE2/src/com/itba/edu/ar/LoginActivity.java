package com.itba.edu.ar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import ar.edu.itba.hciapi.api.Api;
import ar.edu.itba.hciapi.api.ApiCallback;
import ar.edu.itba.hciapi.model.SignInResult;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class LoginActivity extends Activity {
	
	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//SharedPreferences data = getPreferences(MODE_PRIVATE);
		//if(data.getString("token", defValue))
		setContentView(R.layout.activity_login);
 
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setupButton();
		
	}
	
	private void setupButton(){
		Button button = (Button) findViewById(R.id.btnLogin);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				EditText editUsr = (EditText) findViewById(R.id.editUsr);
				EditText editPass = (EditText) findViewById(R.id.editPass);
				Api.get().signIn(editUsr.getText().toString(), editPass.getText().toString()
						, new ApiCallback<SignInResult>() {

					@Override
					public void call(SignInResult result, Exception exception) {
						String msg = "";
        				if (exception != null) {
        					msg = exception.getMessage();
        				} else {
        					SharedPreferences saveData = getPreferences(MODE_PRIVATE);
        					SharedPreferences.Editor editor = saveData.edit();
        					editor.putString("token", result.getToken());
        					editor.putString("user", result.getAccount().getUsername());
        					editor.commit();
        					
        					msg += "token: " + result.getToken();
        					msg += " username: " + result.getAccount().getUsername();
        					
        					Intent intent = new Intent( LoginActivity.this, CategoriesActivity.class);
            				startActivity(intent);
        				}
        			
        			}
						
					
				});
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

}
