package ru.job4j.urlshortcut.model;

import ru.job4j.urlshortcut.exception.Operation;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "site")
public class Site {

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

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "site", fetch = FetchType.LAZY)
    private final Set<Url> urls = new HashSet<>();

    public Site() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Url> getUrls() {
        return urls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Site site = (Site) o;
        return id.equals(site.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
