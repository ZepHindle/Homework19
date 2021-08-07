package com.chersoft.homework19.data.repository;

import com.chersoft.homework19.data.model.AnimeQuoteModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Реализация интерфейса IAnimeRepository.
 */
public class AnimeRepository implements IAnimeRepository {

    private static final String URL = "https://animechan.vercel.app/api/quotes";
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public Single<ArrayList<AnimeQuoteModel>> loadDataRx() {
        return Single.fromCallable(new Callable<ArrayList<AnimeQuoteModel>>() {
            @Override
            public ArrayList<AnimeQuoteModel> call() throws Exception {
                Request request = new Request.Builder()
                        .url(URL)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()){
                        throw new IOException("Network error with " + response.code() + " code.");
                    } else {
                        String json = response.body().string();
                        return parse(json);
                    }
                }
            }

            private ArrayList<AnimeQuoteModel> parse(String string) throws IOException {
                ObjectMapper mapper = new ObjectMapper();
                AnimeQuoteModel [] array = mapper.readValue(string, AnimeQuoteModel[].class);
                return new ArrayList<>(Arrays.asList(array));
            }
        });
    }
}
