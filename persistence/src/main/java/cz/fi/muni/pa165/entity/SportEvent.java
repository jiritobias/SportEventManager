package cz.fi.muni.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Petra Halová on 26.10.17.
 */

@Entity
@Getter
@Setter
@Table(name = "SPORT_EVENT_TABLE")
public class SportEvent extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String place;

    @NotNull
    @Column(nullable = false)
    private Date date;

    @OneToMany(mappedBy="sportsMen")
    private Set<Competition> competitions = new HashSet<>();

    public Set<Competition> getCompetitions() {
        return Collections.unmodifiableSet(competitions);
    }

    public void addCompetition(Competition competition) {
        this.competitions.add(competition);
    }
    public void removeCompetition(Competition competition){
        this.competitions.remove(competition);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SportEvent)) {
            return false;
        }

        SportEvent that = (SportEvent) o;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (place != null ? !place.equals(that.place) : that.place != null) {
            return false;
        }
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
