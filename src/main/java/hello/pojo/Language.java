package hello.pojo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "languages")
public class Language {
    @Id
    @Column(name = "lang_id")
    private Long langId;

    @Column(name = "name_lng")
    private String nameLng;

    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL)
    private Set<Localization> localizations = new HashSet<>();

    public Language() {
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public String getNameLng() {
        return nameLng;
    }

    public void setNameLng(String nameLng) {
        this.nameLng = nameLng;
    }

    public Set<Localization> getLocalizations() {
        return localizations;
    }

    public void setLocalizations(Set<Localization> localizations) {
        this.localizations = localizations;
    }
}
