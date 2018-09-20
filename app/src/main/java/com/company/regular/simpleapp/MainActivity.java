package com.company.regular.simpleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.company.regular.simpleapp.retrofit.GetFileQuery;
import com.company.regular.simpleapp.retrofit.ImageEntryModel;
import com.company.regular.simpleapp.retrofit.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GetFileQuery query = RetrofitBuilder.getFileQuery();
		query.getFile().enqueue(new Callback<ImageEntryModel>() {
			@Override
			public void onResponse(Call<ImageEntryModel> call, Response<ImageEntryModel> response) {
				Log.d("debugkey", "response code: " + response.code());
				Log.d("debugkey", response.body().getImageEntry().get(0).getImageUrl());
			}


			@Override
			public void onFailure(Call<ImageEntryModel> call, Throwable t) {
				Log.d("debugkey", "fucked up");
				t.printStackTrace();
			}
		});
	}


}
