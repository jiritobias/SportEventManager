package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COMPETITION_TABLE")
public class Competition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Sport sport;

    @ManyToMany
    private Set<User> sportsMen = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getSportsMen() {
        return Collections.unmodifiableSet(sportsMen);
    }

    public void addSportman(User sportman) {
        sportsMen.add(sportman);
    }

    public void removeSportman(User sportman) {
        sportsMen.remove(sportman);
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Competition that = (Competition) o;

        if (!id.equals(that.id)) {
            return false;
        }
        if (sport != null ? !sport.equals(that.sport) : that.sport != null) {
            return false;
        }
        return sportsMen != null ? sportsMen.equals(that.sportsMen) : that.sportsMen == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (sport != null ? sport.hashCode() : 0);
        return result;
    }
}
