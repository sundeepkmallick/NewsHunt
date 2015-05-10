package com.ecommhunt.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ParseException;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.ecommhunt.R;
import com.ecommhunt.model.Product;
import com.ecommhunt.model.TotalItemsWithGrandTotal;

public class Utils {
	public static final String APP_PREFERENCE = "app_pref";
	public static final String PREF_LANGUAGE = "pref_language";
	public static final String PREF_EMAIL = "pref_email";
	public static final String PREF_FIRST_NAME = "pref_fname";
	public static final String PREF_LAST_NAME = "pref_lname";

	public static void saveLanguagePreference(Context context, String language) {
		SharedPreferences preference = context.getSharedPreferences(
				APP_PREFERENCE, Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putString(PREF_LANGUAGE, language);
		editor.commit();
	}

	public static void saveLoginCredential(Context context, String email,
			String firstName, String lastName) {
		SharedPreferences preference = context.getSharedPreferences(
				APP_PREFERENCE, Context.MODE_PRIVATE);
		Editor editor = preference.edit();
		editor.putString(PREF_EMAIL, email);
		Log.d("In Utils Class", email);
		editor.putString(PREF_FIRST_NAME, firstName);
		editor.putString(PREF_LAST_NAME, lastName);
		editor.commit();
	}

	public static String getPreferenceData(Context context, String param) {
		String data = "";
		SharedPreferences preference = context.getSharedPreferences(
				APP_PREFERENCE, Context.MODE_PRIVATE);
		data = preference.getString(param, "");
		return data;
	}

	public static String getStringDataBasedOnLanguage(Context context,
			int resources) {
		String data = "";
		Resources standardResources = context.getResources();
		AssetManager assets = standardResources.getAssets();
		DisplayMetrics metrics = standardResources.getDisplayMetrics();
		Resources defaultResources = new Resources(assets, metrics, getLocale(
				context, standardResources));
		data = defaultResources.getString(resources);
		return data;
	}

	public static Configuration getLocale(Context context,
			Resources standardResources) {
		Locale locale = null;
		if (getPreferenceData(context, PREF_LANGUAGE).equals(
				context.getResources().getString(R.string.hindi))) {
			locale = new Locale("hi_IN");
		} else if (getPreferenceData(context, PREF_LANGUAGE).equals(
				context.getResources().getString(R.string.hindi))) {
			locale = new Locale("en_US");
		} else {
			locale = new Locale("en_US");
		}
		Locale.setDefault(locale);
		Configuration config = new Configuration(
				standardResources.getConfiguration());
		config.locale = locale;
		return config;
	}

	public static TotalItemsWithGrandTotal calculateTotalItemsGrandPrice(
			ArrayList<Product> skuList, HashMap<String, String> skuHashMapList) {
		TotalItemsWithGrandTotal itemsWithGrandTotal = new TotalItemsWithGrandTotal();
		float grand_price = 0;
		float grand_price_actual = 0;
		int totalQuantity = 0;
		if (skuList != null && skuList.size() > 0 && skuHashMapList != null
				&& skuHashMapList.size() > 0) {
			for (int i = 0; i < skuList.size(); i++) {
				String available_unit_str = skuList.get(i)
						.getProductAvailableUnit();
				String quantity_str = (skuHashMapList.containsKey(skuList
						.get(i).getProductId())) ? skuHashMapList.get(skuList
						.get(i).getProductId()) : "0";
				String offer_price_str = skuList.get(i).getProductOfferPrice();
				String actual_price_str = skuList.get(i).getProductPrice();
				float available_unit = 0;
				int quantity = 0;
				float offer_price = 0;
				float actual_price = 0;
				if (!TextUtils.isEmpty(available_unit_str)) {
					available_unit = Float.parseFloat(available_unit_str);
				}
				if (!TextUtils.isEmpty(quantity_str)) {
					quantity = Integer.parseInt(quantity_str);
				}
				if (!TextUtils.isEmpty(offer_price_str)) {
					offer_price = Float.parseFloat(offer_price_str);
				}
				if (!TextUtils.isEmpty(actual_price_str)) {
					actual_price = Float.parseFloat(actual_price_str);
				}
				if (available_unit > 0 && quantity > 0 && offer_price > 0
						&& available_unit >= quantity) {

					float total_price = offer_price * quantity;
					grand_price = grand_price + total_price;

					float total_price_actual = actual_price * quantity;
					grand_price_actual = grand_price_actual
							+ total_price_actual;

					totalQuantity = totalQuantity + quantity;
				}
			}
		}
		itemsWithGrandTotal.setGrandTotal(grand_price);
		itemsWithGrandTotal.setActualGrandTotal(grand_price_actual);
		itemsWithGrandTotal.setTotalItems(totalQuantity);
		return itemsWithGrandTotal;
	}

	/**
	 * 
	 * @param context
	 * @return true if internet is connected, false otherwise.
	 */
	public static boolean isInternetConnected(Context context) {
		boolean isInternetConnected = false;

		ConnectionUtils conn = new ConnectionUtils(context);
		isInternetConnected = conn.isConnectingToInternet();

		return isInternetConnected;
	}

	/**
	 * 
	 * @param context
	 * @return true if user is logged in, false otherwise.
	 */
	public static boolean isUserLoggedIn(Context context) {

		SharedPreferences preference = context.getSharedPreferences(
				APP_PREFERENCE, Context.MODE_PRIVATE);

		return preference.contains(Utils.PREF_EMAIL);
	}

	public static int parseInt(String value) {
		int parsedValue = 0;
		try {
			if (!TextUtils.isEmpty(value)) {
				Locale theLocale = Locale.getDefault();
				NumberFormat numberFormat = DecimalFormat
						.getInstance(theLocale);
				Number theNumber;
				try {
					theNumber = numberFormat.parse(value);
					return theNumber.intValue();
				} catch (ParseException e) {
					Log.i("TAG", "Handling Exception while parsing to double");
					String valueWithDot = value.replaceAll(",", ".");
					parsedValue = Integer.valueOf(valueWithDot);
				}
			}
		} catch (Exception e) {
			Log.i("TAG", "Exception>> Parse String into Int: " + e.getMessage());
		}
		return parsedValue;
	}

	public static float parseFloat(String value) {
		float parsedValue = 0.0f;
		try {
			if (!TextUtils.isEmpty(value)) {
				Locale theLocale = Locale.getDefault();
				NumberFormat numberFormat = DecimalFormat
						.getInstance(theLocale);
				Number theNumber;
				try {
					theNumber = numberFormat.parse(value);
					return theNumber.floatValue();
				} catch (ParseException e) {
					Log.i("TAG", "Handling Exception while parsing to double");
					String valueWithDot = value.replaceAll(",", ".");
					parsedValue = Float.valueOf(valueWithDot);
				}
			}
		} catch (Exception e) {
			Log.i("TAG",
					"Exception>> Parse String into Float: " + e.getMessage());
		}
		return parsedValue;
	}

	public static double parseDouble(String value) {
		double parsedValue = 0;
		try {
			if (!TextUtils.isEmpty(value)) {
				Locale theLocale = Locale.getDefault();
				NumberFormat numberFormat = DecimalFormat
						.getInstance(theLocale);
				Number theNumber;
				try {
					theNumber = numberFormat.parse(value);
					parsedValue = theNumber.doubleValue();
				} catch (ParseException e) {
					Log.i("TAG", "Handling Exception while parsing to double");
					String valueWithDot = value.replaceAll(",", ".");
					parsedValue = Double.valueOf(valueWithDot);
				}
			}
		} catch (Exception e) {
			Log.i("TAG",
					"Exception>> Parse String into Double: " + e.getMessage());
		}
		return parsedValue;
	}

	public static String parsePrice(String Actualprice) {
		double price = Double.parseDouble(Actualprice);

		long iPart;
		double fPart;

		iPart = (long) price;
		fPart = price - iPart;
		String formattedPrice = String.format(Locale.ENGLISH, "%.2f", fPart);
		if (formattedPrice.equalsIgnoreCase("0.00")) {
			String ActualPrice = Long.toString(iPart);
			return ActualPrice;

		} else
			return Actualprice;
	}

	public static void hideKeyboard(Context context, IBinder iBinder) {
		InputMethodManager in = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(iBinder, 0);
	}

	public static void hideKeyboard(Context context, View view) {
		InputMethodManager in = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		in.hideSoftInputFromWindow(view.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	public static void hideKeyboard(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
	}

	public static String formateDateFromstring(String inputFormat,
			String outputFormat, String inputDate) {
		Date parsed = null;
		String outputDate = "";

		SimpleDateFormat df_input = new SimpleDateFormat(inputFormat,
				java.util.Locale.getDefault());
		SimpleDateFormat df_output = new SimpleDateFormat(outputFormat,
				java.util.Locale.getDefault());

		try {
			parsed = df_input.parse(inputDate);
			outputDate = df_output.format(parsed);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return outputDate;
	}
	
	public static String getSymbol(Context con, String skuCurrency) {
		String symbol = "";

		if (skuCurrency.equalsIgnoreCase("INR")) {
			symbol = con.getResources().getString(R.string.Rs);
		}
		return symbol;
	}
	
	public static String getINRSymbol(Context con) {
		String symbol = "";
		symbol = con.getResources().getString(R.string.Rs);
		return symbol;
	}
}
