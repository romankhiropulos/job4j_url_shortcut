package ru.job4j.urlshortcut.model;


import ru.job4j.urlshortcut.exception.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private Long id;

    @NotBlank(message = "Name must not be empty", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class, Operation.OnCreate.class
    })
    private String name;

    @NotBlank(message = "Password must not be null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class, Operation.OnCreate.class
    })
    @Size(min = 6, max = 15, groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class, Operation.OnCreate.class
    })
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    public Person() {
    }

    public static Person of(String name, String password, Role role, Site site) {
        Person user = new Person();
        user.name = name;
        user.role = role;
        user.password = password;
        user.site = site;
        return user;
    }

    public static Person of(Long id, String name, String password) {
        Person user = new Person();
        user.id = id;
        user.name = name;
        user.password = password;
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return this.getId() == person.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}

