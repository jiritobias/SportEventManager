package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @JoinTable(name="SPORT_TABLE")
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

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Competition)) return false;

        Competition that = (Competition) o;

        if (!getSport().equals(that.getSport())) return false;
        return getSportsMen().equals(that.getSportsMen());
    }

    @Override
    public int hashCode() {
        int result = getSport().hashCode();
        result = 31 * result + getSportsMen().hashCode();
        return result;
    }

}
