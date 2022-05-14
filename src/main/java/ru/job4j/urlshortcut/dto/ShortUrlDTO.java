package ru.job4j.urlshortcut.dto;

public class ShortUrlDTO {

    private String url;

    public ShortUrlDTO() {
    }

    public ShortUrlDTO(String shortUrl) {
        this.url = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
