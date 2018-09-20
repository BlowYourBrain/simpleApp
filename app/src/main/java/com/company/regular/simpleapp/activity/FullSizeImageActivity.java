package com.company.regular.simpleapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.company.regular.simpleapp.R;

public class FullSizeImageActivity extends AppCompatActivity {

	private static final String IMAGE_URL_KEY = "imageurlkey";


	public static void startActivity(Context context, String imageUrl) {
		Intent intent = new Intent(context, FullSizeImageActivity.class);
		intent.putExtra(IMAGE_URL_KEY, imageUrl);
		context.startActivity(intent);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_size_image);
	}
}
