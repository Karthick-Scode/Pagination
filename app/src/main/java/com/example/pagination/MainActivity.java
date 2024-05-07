package com.example.pagination;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView pageRecycler;
    MyListAdapter adapter;
    private List<Model.data> dataList = new ArrayList<>();
    private int currentPage = 1; // Initial page number
    private int totalPages; // Total pages available

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pageRecycler = findViewById(R.id.pageRecycler);

        pageRecycler.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        pageRecycler.addItemDecoration(divider);

        // Initialize adapter with empty data list
        adapter = new MyListAdapter(this, dataList);
        pageRecycler.setAdapter(adapter);

        // Load initial data
        loadData(currentPage);

        // Add scroll listener to RecyclerView
        pageRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == dataList.size() - 2) {
                    // Load next page if the user has scrolled to the end
                    if (currentPage < totalPages) {
                        currentPage++;
                        loadData(currentPage);
                    }
                }
            }
        });
        
//        getDataList();

    }

    private void loadData(int page) {
        Call<Model> call = RetrofitClient.getInstance().getAPI().getAllData(page);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Model model = response.body();
                    totalPages = model.getTotal_pages();
                    dataList.addAll(model.getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Model> call, @NonNull Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void getDataList() {
//        Call<Model> call = RetrofitClient.getInstance().getAPI().getAllData(1);
//        call.enqueue(new Callback<Model>() {
//            @Override
//            public void onResponse(@NonNull Call<Model> call, @NonNull Response<Model> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Model.data> mainCategories = response.body().getData();
//
//                    adapter = new MyListAdapter(MainActivity.this, mainCategories);
//                    pageRecycler.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Model> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Failed to fetch main categories", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}