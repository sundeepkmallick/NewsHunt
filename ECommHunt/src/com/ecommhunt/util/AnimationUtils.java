package com.ecommhunt.util;

import android.app.Activity;

import com.ecommhunt.R;

/**
 * @author nikhil.v
 * Sets Animation 
 */
public class AnimationUtils {

	/**
	 * @param activity
	 */
	public static void AnimationFwdActivity(Activity activity) {
		
		activity.overridePendingTransition(R.anim.slide_left_in,
				R.anim.slide_left_out);
	}

	/**
	 * @param activity
	 */
	public static void AnimationBckActivity(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_right_in,
				R.anim.slide_right_out);
	}
	

	
}
