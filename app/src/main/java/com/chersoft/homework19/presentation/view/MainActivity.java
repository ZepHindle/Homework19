package com.chersoft.homework19.presentation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chersoft.homework19.data.repository.AnimeRepository;
import com.chersoft.homework19.presentation.viewmodel.ViewModel;
import com.chersoft.homework19.databinding.ActivityMainBinding;
import com.chersoft.homework19.data.model.AnimeQuoteModel;
import com.chersoft.homework19.presentation.view.adapter.MainRecyclerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Главная активность приложения.
 */
public class MainActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getQuotesButton.setOnClickListener(v -> {
            viewModel.onGetRandomQuotesButtonPressed();
        });

        createViewModel();
        setUpBindings();
    }

    private void createViewModel(){
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new ViewModel(new AnimeRepository());
            }
        });
        viewModel = viewModelProvider.get(ViewModel.class);
        viewModel.onCreate();
    }

    private void setUpBindings(){
        viewModel.getProgressLiveData().observe(this, value -> {
            binding.progressBar.setVisibility(value ? View.VISIBLE : View.GONE);
        });
        viewModel.getListLiveData().observe(this, list -> {
            MainRecyclerAdapter adapter = new MainRecyclerAdapter(list, model -> {
                viewModel.onShowQuoteButtonPressed(model);
            });
            binding.mainRecyclerView.setAdapter(adapter);
            binding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        });

        // чтобы в лямбдах не оказались сильные ссылки на Activity
        final WeakReference<MainActivity> thisWeakReference = new WeakReference<>(this);

        viewModel.getSecondActivityLifeData().observe(this, animeQuoteModel -> {
            if (animeQuoteModel == null) return;
            MainActivity context = thisWeakReference.get();
            if (context != null){
                startActivity(QuoteActivity.getIntent(context, animeQuoteModel));
            }
        });

        viewModel.getToastLifeData().observe(this, text -> {
            if (text == null) return;
            MainActivity context = thisWeakReference.get();
            if (context != null){
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}