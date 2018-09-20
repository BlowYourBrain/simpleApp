package com.company.regular.simpleapp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetFileQuery {
	@GET("uc?authuser=0&id=17Byg-VqX40E21F9GgMnYq-9ya18kFri_&export=download/")
	Call<ImageEntryModel> getFile();
}
