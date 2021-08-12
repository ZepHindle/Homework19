package com.chersoft.homework19.presentation.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chersoft.homework19.R;
import com.chersoft.homework19.databinding.RecyclerViewItemBinding;
import com.chersoft.homework19.data.model.AnimeQuoteModel;

import java.util.ArrayList;

/**
 * Адаптер для RecyclerView на главное активности.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>{

    /**
     * Listener, выполняющийся при нажатии на кнопку "показать цитату".
     */
    public interface ShowQuoteButtonListener{
        /**
         * Выполняется при нажатии на кнопку "показать цитату"
         * @param model цитата, соответствующая кнопку
         */
        void onShowQuote(AnimeQuoteModel model);
    }

    private final ArrayList<AnimeQuoteModel> modelList;
    private final ShowQuoteButtonListener listener;

    /**
     * Создает адаптер.
     * @param modelList список цитат
     * @param listener listener, выполняющийся при нажатии на кнопку "показать цитату"
     */
    public MainRecyclerAdapter(ArrayList<AnimeQuoteModel> modelList, ShowQuoteButtonListener listener){
        this.modelList = modelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(modelList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private RecyclerViewItemBinding binding;

        public ViewHolder(View view){
            super(view);
            binding = RecyclerViewItemBinding.bind(view);
        }

        public void bind(final AnimeQuoteModel model, ShowQuoteButtonListener listener){
            binding.animeTextView.setText(model.getAnime());
            binding.characterTextView.setText(model.getCharacter());
            binding.buttonShowQuote.setOnClickListener(view -> {
                listener.onShowQuote(model);
            });
        }
    }
}
