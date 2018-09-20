package com.company.regular.simpleapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
	private static final String BASE_URL = "https://drive.google.com/";

	private static final Retrofit retrofit = new Retrofit
			.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(BASE_URL)
			.build();


	public static GetFileQuery getFileQuery() {
		return retrofit.create(GetFileQuery.class);
	}


}
