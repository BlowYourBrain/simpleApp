package com.company.regular.simpleapp.activity;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class Application extends android.app.Application {
	@Override
	public void onCreate() {
		super.onCreate();
		setupPicasso();
	}


	private void setupPicasso() {
		Picasso.Builder builder = new Picasso.Builder(this);
		builder.downloader(new OkHttp3Downloader(this, Integer.MAX_VALUE));
		Picasso build = builder.build();
//		build.setIndicatorsEnabled(true);
//		build.setLoggingEnabled(true);
		Picasso.setSingletonInstance(build);
	}
}
