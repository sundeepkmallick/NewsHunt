package com.ecommhunt.util;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

public class ShareUtils {
	
	
	/**
	 * @param context
	 * General Share
	 */
	public static void GeneralShare(Context context, String strsubject,
			String strbody, String ImagePath) {

		Intent intent = new Intent(Intent.ACTION_SEND);
	  	intent.setType("text/plain");
	  	intent.putExtra(Intent.EXTRA_TEXT,"Hey u may like "+strsubject +" now at "+ strbody + ". Add to your wish list. " + ImagePath+" . And try out our mobile app : "+" Link to app");
	  	intent.putExtra(android.content.Intent.EXTRA_SUBJECT,
	  			"Offers from our app");
	    	context.startActivity(Intent.createChooser(intent,
	    "Share our product with"));
	}
	
	/**
	 * @param context
	 * whatsappShare
	 */
	public void whatsappShare(Context context,String strsubject,String strbody) {

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, strsubject);
		intent.putExtra(Intent.EXTRA_TEXT, strbody);
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
		for (final ResolveInfo app : activityList) {
			if ("com.whatsapp.ContactPicker".equals(app.activityInfo.name)) {
				final ActivityInfo activity = app.activityInfo;
				final ComponentName name = new ComponentName(
						activity.applicationInfo.packageName, activity.name);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				intent.setComponent(name);
				context.startActivity(Intent
						.createChooser(intent, "Share with"));
				break;
			}
		}
	}


	/**
	 * @param context
	 * hangoutsShare
	 */
	public void hangoutsShare(Context context,String strsubject,String strbody) {

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT, strsubject);
		intent.putExtra(Intent.EXTRA_TEXT, strbody);

		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
		for (final ResolveInfo app : activityList) {
			if ("com.google.android.apps.hangouts.phone.ShareIntentActivity"
					.equals(app.activityInfo.name)) {
				final ActivityInfo activity = app.activityInfo;
				final ComponentName name = new ComponentName(
						activity.applicationInfo.packageName, activity.name);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				intent.setComponent(name);
				context.startActivity(Intent
						.createChooser(intent, "Share with"));

				break;
			}
		}
	}


	/**
	 * @param context
	 * facebookShare
	 */
	public void facebookShare(Context context,String strsubject,String strbody) {

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT,strsubject);
		intent.putExtra(Intent.EXTRA_TEXT,strbody);
		
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
		for (final ResolveInfo app : activityList) {
			if ("com.facebook.composer.shareintent.ImplicitShareIntentHandler"
					.equals(app.activityInfo.name)) {
				final ActivityInfo activity = app.activityInfo;
				final ComponentName name = new ComponentName(
						activity.applicationInfo.packageName, activity.name);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				intent.setComponent(name);
				context.startActivity(Intent
						.createChooser(intent, "Share with"));

				break;
			}
		}
	}

	/**
	 * @param context
	 * twitterShare
	 */
	public void twitterShare(Context context,String strsubject,String strbody) {

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT,strsubject);
		intent.putExtra(Intent.EXTRA_TEXT,strbody);

		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
		for (final ResolveInfo app : activityList) {
			if ("com.twitter.android.composer.ComposerActivity"
					.equals(app.activityInfo.name)) {
				final ActivityInfo activity = app.activityInfo;
				final ComponentName name = new ComponentName(
						activity.applicationInfo.packageName, activity.name);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				intent.setComponent(name);
				context.startActivity(Intent
						.createChooser(intent, "Share with"));

				break;
			}
		}
	}

	/**
	 * @param context
	 * gmailShare
	 */
	public void gmailShare(Context context,String strsubject,String strbody) {

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(android.content.Intent.EXTRA_SUBJECT,strsubject);
		intent.putExtra(Intent.EXTRA_TEXT,strbody);

		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> activityList = pm.queryIntentActivities(intent, 0);
		for (final ResolveInfo app : activityList) {
			if ("com.google.android.gm.ComposeActivityGmail"
					.equals(app.activityInfo.name)) {
				final ActivityInfo activity = app.activityInfo;
				final ComponentName name = new ComponentName(
						activity.applicationInfo.packageName, activity.name);
				intent.addCategory(Intent.CATEGORY_LAUNCHER);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				intent.setComponent(name);
				context.startActivity(Intent
						.createChooser(intent, "Share with"));

				break;
			}
		}
	}
}
