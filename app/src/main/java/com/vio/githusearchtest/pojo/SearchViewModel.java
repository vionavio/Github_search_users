package com.vio.githusearchtest.pojo;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vio.githusearchtest.BuildConfig;
import com.vio.githusearchtest.koneksi.Client;
import com.vio.githusearchtest.koneksi.Service;
import com.vio.githusearchtest.model.SearchData;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<ArrayList<SearchData>> Data = new MutableLiveData<>();

    public void loadEvent(String query) {
        try {
            String apiKey = BuildConfig.API_KEY;
            Service service = Client.getClient().create(Service.class);
            Call<SearchResponse> eventCall = service.searchUser(apiKey, query);
            eventCall.enqueue(new Callback<SearchResponse>() {

                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                    if (!response.isSuccessful()) {
                        Log.d("Response success", response.message());
                    } else if (response.body() != null) {
                        ArrayList<SearchData> items = new ArrayList<>(response.body().getItems());
                        Data.postValue(items);
                    }
                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    Log.e("failure", t.toString());
                }
            });
        } catch (Exception e) {
            Log.d("api key", String.valueOf(e));
        }
    }

    public LiveData<ArrayList<SearchData>> getData() {
        return Data;
    }

}

