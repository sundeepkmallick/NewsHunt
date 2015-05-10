package com.ecommhunt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.ecommhunt.util.AnimationUtils;
import android.widget.FrameLayout;

import com.ecommhunt.util.OnSwipeTouchListener;

public class MainActivity extends Activity {
	
	FrameLayout frameLayoutParent;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = MainActivity.this;
		
		frameLayoutParent = (FrameLayout) findViewById(R.id.frameLayoutParent);
		
		frameLayoutParent.setOnTouchListener(new OnSwipeTouchListener(context) {
		    @Override
		    public void onSwipeLeft() {
		    	Intent intent = new Intent(context, SelectLanguageActivity.class);
				startActivity(intent);
				AnimationUtils.AnimationFwdActivity(MainActivity.this);
		    }
		});
	}

}
