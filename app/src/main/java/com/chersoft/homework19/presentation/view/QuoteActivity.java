package com.chersoft.homework19.presentation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chersoft.homework19.R;
import com.chersoft.homework19.data.model.AnimeQuoteModel;
import com.chersoft.homework19.databinding.ActivityMainBinding;
import com.chersoft.homework19.databinding.ActivityQuoteBinding;

/**
 * Активность приложения, в которой отображается выбранная цитата.
 */
public class QuoteActivity extends AppCompatActivity {

    private static final String BUNDLE_TAG = "com.chersoft.bundle";

    private ActivityQuoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        binding = ActivityQuoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent args = getIntent();
        if (args != null){
            AnimeQuoteModel model = (AnimeQuoteModel) args.getSerializableExtra(BUNDLE_TAG);
            binding.animeView.setText(model.getAnime());
            binding.characterView.setText(model.getCharacter());
            binding.quoteView.setText(model.getQuote());
        }

    }

    /**
     * Создает интент для запуска этой активности.
     * @param context контекст
     * @param animeQuoteModel цитата для отображения
     * @return интент, необходимый для создания активности
     */
    public static Intent getIntent(Context context, AnimeQuoteModel animeQuoteModel){
        Intent intent = new Intent(context, QuoteActivity.class);
        intent.putExtra(BUNDLE_TAG, animeQuoteModel);
        return intent;
    }
}