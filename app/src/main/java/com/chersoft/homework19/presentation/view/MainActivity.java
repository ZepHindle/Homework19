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

import java.util.ArrayList;

/**
 * Главная активность приложения.
 */
public class MainActivity extends AppCompatActivity implements com.chersoft.homework19.presentation.view.View {

    private ViewModel viewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.getQuotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.onGetRandomQuotesButtonPressed();
            }
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
        viewModel.setView(this);
    }

    private void setUpBindings(){
        viewModel.getProgressLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.progressBar.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        });
        viewModel.getListLiveData().observe(this, new Observer<ArrayList<AnimeQuoteModel>>() {
            @Override
            public void onChanged(ArrayList<AnimeQuoteModel> animeQuoteModels) {
                MainRecyclerAdapter adapter = new MainRecyclerAdapter(animeQuoteModels, new MainRecyclerAdapter.ShowQuoteButtonListener() {
                    @Override
                    public void onShowQuote(AnimeQuoteModel model) {
                        viewModel.onShowQuoteButtonPressed(model);
                    }
                });
                binding.mainRecyclerView.setAdapter(adapter);
                binding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        });
    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startSecondActivity(AnimeQuoteModel model) {
        startActivity(QuoteActivity.getIntent(this, model));
    }
}