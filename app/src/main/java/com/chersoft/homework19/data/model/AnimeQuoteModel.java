package com.chersoft.homework19.data.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Класс, представляющий цитату из аниме.
 */
public class AnimeQuoteModel implements Serializable {
    private String character;
    private String anime;
    private String quote;

    /**
     * Конструктор класса AnimeQuoteModel.
     */
    public AnimeQuoteModel(){
        this.anime = this.character = this.quote = null;
    }

    /**
     * Конструктор класса AnimeQuoteModel.
     * @param characterName имя персонажа
     * @param animeName имя аниме
     * @param quote цитата
     */
    public AnimeQuoteModel(String characterName, String animeName, String quote){
        this.character = characterName;
        this.anime = animeName;
        this.quote = quote;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getCharacter() {
        return character;
    }

    public String getAnime() {
        return anime;
    }

    public String getQuote() {
        return quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimeQuoteModel that = (AnimeQuoteModel) o;
        return Objects.equals(character, that.character) &&
                Objects.equals(anime, that.anime) &&
                Objects.equals(quote, that.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(character, anime, quote);
    }
}
