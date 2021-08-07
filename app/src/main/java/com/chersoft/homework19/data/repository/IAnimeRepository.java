package com.chersoft.homework19.data.repository;

import com.chersoft.homework19.data.model.AnimeQuoteModel;

import java.util.ArrayList;

import io.reactivex.Single;

/**
 * Интерфейс для взаимодействия с API.
 */
public interface IAnimeRepository {

    /**
     * Выполняет метод get, загружая 10 случайных цитат.
     * @return список из десяти элементов случайных цитат
     */
    Single<ArrayList<AnimeQuoteModel>> loadDataRx();
}
