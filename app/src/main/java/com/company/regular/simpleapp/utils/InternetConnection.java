package com.company.regular.simpleapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * InternetConnection будет пробуждать isNetworkOnline(boolean) каждый раз, когда меняется состояние сети
 */
public class InternetConnection extends BroadcastReceiver {

	public interface ConnectivityListener {
		void isNetworkOnline(boolean isOnline);
	}


	private ConnectivityListener mConnectivityListener;


	public InternetConnection(ConnectivityListener listener) {
		mConnectivityListener = listener;
	}


	@Override
	public void onReceive(Context context, Intent intent) {
		if (mConnectivityListener != null) {
			mConnectivityListener.isNetworkOnline(isOnline(context));
		}
	}


	private boolean isOnline(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		return networkInfo != null && networkInfo.isConnectedOrConnecting();
	}
}
