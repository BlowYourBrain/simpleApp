package com.company.regular.simpleapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.company.regular.simpleapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class FullSizeImageActivity extends AppCompatActivity {

	private static final String IMAGE_URL_KEY = "imageurlkey";
	private ImageView mImageView;


	public static void startActivity(Context context, String imageUrl) {
		Intent intent = new Intent(context, FullSizeImageActivity.class);
		intent.putExtra(IMAGE_URL_KEY, imageUrl);
		context.startActivity(intent);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_size_image);
		mImageView = findViewById(R.id.image);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			String imageUrl = bundle.getString(IMAGE_URL_KEY);
			setupImageView(imageUrl, mImageView);
		}
	}


	private void setupImageView(String imageUrl, @NonNull ImageView imageView) {
		if (imageUrl != null) {
			Picasso.get()
					.load(imageUrl)
					.networkPolicy(NetworkPolicy.OFFLINE)
					.placeholder(R.drawable.ic_baseline_photo_24px)
					.into(imageView, new Callback() {
						@Override
						public void onSuccess() {
							Log.d("debugkey", "succes!");
						}


						@Override
						public void onError(Exception e) {
							Log.d("debugkey", "failed to load");
							e.printStackTrace();
						}
					});
		}
	}
}
