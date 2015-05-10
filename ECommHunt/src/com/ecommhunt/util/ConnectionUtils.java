package com.ecommhunt.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author nikhil.v Checks Internet Connection
 */
public class ConnectionUtils {

	private Context _context;

	/**
	 * @param context
	 */
	public ConnectionUtils(Context context) {
		this._context = context;
	}

	/**
	 * Get Network status
	 * 
	 * @return
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	/**
	 * @return
	 */
	public boolean isConnectingToInternet() {

		return isNetworkAvailable();/*
									 * ConnectivityManager connectivity =
									 * (ConnectivityManager)
									 * _context.getSystemService
									 * (Context.CONNECTIVITY_SERVICE); if
									 * (connectivity != null) { NetworkInfo[]
									 * info = connectivity.getAllNetworkInfo();
									 * if (info != null) for (int i = 0; i <
									 * info.length; i++) if (info[i].getState()
									 * == NetworkInfo.State.CONNECTED) { return
									 * true; }
									 * 
									 * } return false;
									 */
	}
}