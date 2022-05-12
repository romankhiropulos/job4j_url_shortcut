package ru.job4j.urlshortcut.dto;

public class UrlStatisticDTO {

    private String name;

    private Long calls;

    public UrlStatisticDTO(String name, Long calls) {
        this.name = name;
        this.calls = calls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCalls() {
        return calls;
    }

    public void setCalls(Long calls) {
        this.calls = calls;
    }
}
