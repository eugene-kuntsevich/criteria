package pojo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @Column(name = "city_id")
    private Long cityId;

    private String name;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Set<Localization> localizations;

    public City() {
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Localization> getLocalizations() {
        return localizations;
    }

    public void setLocalizations(Set<Localization> localizations) {
        this.localizations = localizations;
    }
}
