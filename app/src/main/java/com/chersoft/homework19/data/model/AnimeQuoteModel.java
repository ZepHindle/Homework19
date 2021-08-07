package com.chersoft.homework19.data.model;

import java.io.Serializable;

/**
 * Класс, представляющий цитату из аниме.
 */
public class AnimeQuoteModel implements Serializable {
    private String character;
    private String anime;
    private String quote;

    public AnimeQuoteModel(){
        this.anime = this.character = this.quote = null;
    }

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
}
