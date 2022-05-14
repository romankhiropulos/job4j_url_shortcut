package ru.job4j.urlshortcut.dto;

import java.util.Objects;

public class LongUrlDTO {

    private String url;

    public LongUrlDTO() {
    }

    public LongUrlDTO(String longUrl) {
        this.url = longUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LongUrlDTO that = (LongUrlDTO) o;
        return Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
