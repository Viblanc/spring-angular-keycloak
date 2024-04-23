package com.github.viblanc.gamebacklog.gamebacklog.exception;

public class SearchRequestException extends RuntimeException {
    public SearchRequestException(Throwable e) {
        super("Search request to IGDB API failed", e);
    }
}
