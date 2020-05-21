package com.vio.githusearchtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.vio.githusearchtest.adapter.SearchAdapter;
import com.vio.githusearchtest.model.SearchData;
import com.vio.githusearchtest.pojo.SearchViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    SearchView searchView;
    private SearchViewModel searchViewModel;
    private SearchAdapter adapterSearch;
    private ArrayList<SearchData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = findViewById(R.id.search);
        searchView.setQueryHint("Search Github User");

        recyclerView = findViewById(R.id.rvSearch);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterSearch = new SearchAdapter(recyclerView,data,this);
        adapterSearch.notifyDataSetChanged();
        recyclerView.setAdapter(adapterSearch);

        searchViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewModel.loadEvent(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchViewModel.loadEvent(newText);
                return false;
            }
        });

        searchViewModel.getData().observe(this, searchUserItems -> {
            if (searchUserItems.size() != 0) {
                adapterSearch.setData(searchUserItems);
            } else {
                Toast.makeText(MainActivity.this, "No Result", Toast.LENGTH_LONG).show();
            }
        });
    }
}
