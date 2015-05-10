package com.ecommhunt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.ecommhunt.util.AnimationUtils;
import com.ecommhunt.util.OnSwipeTouchListener;
import com.ecommhunt.util.Utils;

public class SelectLanguageActivity extends Activity {
	
	RelativeLayout relativeLayoutParent;
	Context context;
	Spinner spiLang;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_language);
		
		context = SelectLanguageActivity.this;
		relativeLayoutParent = (RelativeLayout) findViewById(R.id.relativeLayoutParent);
		relativeLayoutParent.setOnTouchListener(new OnSwipeTouchListener(context) {
		    @Override
		    public void onSwipeLeft() {
		    	Intent intent = new Intent(context, DashboardActivity.class);
				startActivity(intent);
				AnimationUtils.AnimationFwdActivity(SelectLanguageActivity.this);
				Utils.saveLanguagePreference(context, spiLang.getSelectedItem().toString());
		    }
		    
		    @Override
		    public void onSwipeRight() {
		    	Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
				AnimationUtils.AnimationBckActivity(SelectLanguageActivity.this);
				finish();
		    }
		});
		
		spiLang = (Spinner) findViewById(R.id.spinnerSelectYourLanguage);
	}

}
