package com.chersoft.homework19.presentation.view;

import com.chersoft.homework19.data.model.AnimeQuoteModel;

/**
 * Интерфейс для манипуляции через ViewModel.
 */
public interface View {
    /**
     * Выводит сообщение на экран.
     * @param text текст сообщения
     */
    void toast(String text);

    /**
     * Запускает активность, отображающую цитату.
     * @param model цитата
     */
    void startSecondActivity(AnimeQuoteModel model);
}
