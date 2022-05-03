package ru.job4j.urlshortcut.dto;

import java.util.Objects;

public class UrlShortDTO {

    private String code;

    public UrlShortDTO() { }

    public UrlShortDTO(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UrlShortDTO that = (UrlShortDTO) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
