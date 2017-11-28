package cz.fi.muni.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Petra Halová
 */
@Entity
@Getter
@Setter
@Table(name = "COMPETITION_TABLE")
public class Competition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Sport sport;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> sportsMen = new HashSet<>();

    public Set<User> getSportsMen() {
        return Collections.unmodifiableSet(sportsMen);
    }

    public void addSportman(User sportman) {
        sportsMen.add(sportman);
    }

    public void removeSportman(User sportman) {
        sportsMen.remove(sportman);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Competition that = (Competition) o;

        if (!getSport().equals(that.getSport())) return false;
        return getSportsMen() != null ? getSportsMen().equals(that.getSportsMen()) : that.getSportsMen() == null;
    }

    @Override
    public int hashCode() {
        int result = getSport().hashCode();
        result = 31 * result + (getSportsMen() != null ? getSportsMen().hashCode() : 0);
        return result;
    }
}
