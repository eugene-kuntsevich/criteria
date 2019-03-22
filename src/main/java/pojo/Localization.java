package pojo;

import javax.persistence.*;

@Entity
@Table(name = "localizations")
public class Localization {
    @Id
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "lang_id")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Localization() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
