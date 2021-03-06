package com.example.coursework2.UI.recognition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.coursework2.R;
import com.example.coursework2.databinding.ActivityRecognitzingBinding;
import com.example.coursework2.model.RecognizedItem;
import com.example.coursework2.viewmodel.RecognizedItemsViewModel;
import com.example.coursework2.viewmodel.RecognizedItemsViewModelFactory;

import java.util.List;

public class RecognitionActivity extends AppCompatActivity  implements OnRecognitionListener{

    // ViewModel
    private RecognizedItemsViewModel recognizedItemsViewModel;
    private RecognizedItemsAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognitzing);
        ActivityRecognitzingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_recognitzing);
        recognizedItemsViewModel = new ViewModelProvider(this, new RecognizedItemsViewModelFactory(this)).get(RecognizedItemsViewModel.class);
        binding.setRecognitionmodel(recognizedItemsViewModel);
        binding.setLifecycleOwner(this);
        recyclerView = findViewById(R.id.recognizedItems);
        progressBar = findViewById(R.id.rec_progress_bar);

        // Observers
        recognizedItemsViewModel.getItems().observe(this, new Observer<List<RecognizedItem>>() {
            @Override
            public void onChanged(List<RecognizedItem> recognizedItems) {
                if (recognizedItems != null) {
                    adapter.setRecognizedItems(recognizedItems);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        ConfigureRecyclerView();
        progressBar.setVisibility(View.VISIBLE);
        recognizedItemsViewModel.getRecognizedItems();
    }

    // Init recycler view & adding data to it
    private void ConfigureRecyclerView() {
        // Liva data can't be passed via the constructor
        adapter = new RecognizedItemsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void onRecognizedItemClick(int position) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerView.setItemViewCacheSize(0);
    }
}