package ru.job4j.urlshortcut.model;

import ru.job4j.urlshortcut.exception.Operation;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "url")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "Id must be non null", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    @Min(value = 1, message = "Year must be more than 0", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    private Long id;

    @NotBlank(message = "Short url must not be empty", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class
    })
    @Column(name = "short_url")
    private String shortUrl;

    @NotBlank(message = "Long url must not be empty", groups = {
            Operation.OnUpdate.class, Operation.OnDelete.class, Operation.OnCreate.class
    })
    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "total_calls")
    private Long totalCalls;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;

    public Url() {
    }

    public Url(Long id) {
        this.id = id;
    }

    public Url(Long id, String shortUrl, String longUrl, Site site) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.site = site;
    }

    public Url(Long id, String shortUrl, String longUrl, Long totalCalls, Site site) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.totalCalls = totalCalls;
        this.site = site;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getTotalCalls() {
        return totalCalls;
    }

    public void setTotalCalls(Long totalCalls) {
        this.totalCalls = totalCalls;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Url url = (Url) o;
        return id.equals(url.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Url.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("shortUrl='" + shortUrl + "'")
                .add("longUrl='" + longUrl + "'")
                .add("totalCalls=" + totalCalls)
                .add("site=" + site)
                .toString();
    }
}
