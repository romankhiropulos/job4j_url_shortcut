package ru.job4j.urlshortcut.dto;

public class PersonRegistrationDTO {

    private String name;

    private String password;

    public PersonRegistrationDTO() {
    }

    public PersonRegistrationDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
