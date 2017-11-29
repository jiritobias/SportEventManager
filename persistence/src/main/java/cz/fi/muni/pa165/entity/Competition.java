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
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Competition)) {
            return false;
        }

        Competition that = (Competition) o;

        if (sport != null ? !sport.equals(that.sport) : that.sport != null) {
            return false;
        }
        return sportsMen != null ? sportsMen.equals(that.sportsMen) : that.sportsMen == null;
    }

    @Override
    public int hashCode() {
        return 31 *  (sport != null ? sport.hashCode() : 0);
    }
}
