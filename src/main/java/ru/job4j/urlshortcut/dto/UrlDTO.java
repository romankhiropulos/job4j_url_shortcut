package ru.job4j.urlshortcut.dto;

import java.util.Objects;

public class UrlDTO {

    private String shortUrl;

    private String longUrl;

    public UrlDTO() { }

    public UrlDTO(String code) {
        this.shortUrl = code;
    }

    public UrlDTO(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrlDTO urlDTO = (UrlDTO) o;
        return Objects.equals(shortUrl, urlDTO.shortUrl) && Objects.equals(longUrl, urlDTO.longUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shortUrl, longUrl);
    }
}
