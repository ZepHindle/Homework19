package com.chersoft.homework19.presentation.viewmodel;

import androidx.lifecycle.MutableLiveData;

import com.chersoft.homework19.data.model.AnimeQuoteModel;
import com.chersoft.homework19.data.repository.IAnimeRepository;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ViewModel, соответствующая главной активности.
 */
public class ViewModel extends androidx.lifecycle.ViewModel {

    private final IAnimeRepository repository;
    private final MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<ArrayList<AnimeQuoteModel>> listLiveData = new MutableLiveData<>(new ArrayList<>(0));
    private final MutableLiveData<AnimeQuoteModel> secondActivityLifeData = new MutableLiveData<>(null);
    private final MutableLiveData<String> toastLifeData = new MutableLiveData<>(null);
    private Disposable disposable;

    /**
     * Создает VM с выбранной реализацией репозитория.
     * @param repository реализация репозитория
     */
    public ViewModel(IAnimeRepository repository){
        this.repository = repository;
    }

    public MutableLiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }

    public MutableLiveData<ArrayList<AnimeQuoteModel>> getListLiveData() {
        return listLiveData;
    }

    public MutableLiveData<AnimeQuoteModel> getSecondActivityLifeData() {
        return secondActivityLifeData;
    }

    public MutableLiveData<String> getToastLifeData() {
        return toastLifeData;
    }

    /**
     * Вызывается при нажатии на кнопку "показать" цитату.
     * @param model цитата, соответствующая кнопке
     */
    public void onShowQuoteButtonPressed(AnimeQuoteModel model){
        secondActivityLifeData.postValue(model);
    }

    /**
     * Вызывается при нажатии на кнопку "загружить случайные цитаты".
     */
    public void onGetRandomQuotesButtonPressed(){
        //stopDisposable();
        disposable = repository.loadDataRx()
                .doOnSubscribe(d -> {
                    progressLiveData.postValue(true);
                })
                .doAfterTerminate(() -> progressLiveData.postValue(false))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((animeQuoteModels, throwable) -> {
                    if (animeQuoteModels != null){
                        listLiveData.postValue(animeQuoteModels);
                    } else if (throwable != null){
                        toastLifeData.postValue(throwable.getMessage());
                    }
                });
    }

    /**
     * Вызывается при получении ViewModel.
     */
    public void onCreate(){
        secondActivityLifeData.setValue(null);
        toastLifeData.setValue(null);
    }

    private void stopDisposable(){
        if (disposable != null && !disposable.isDisposed()) disposable.dispose();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        stopDisposable();
    }
}
