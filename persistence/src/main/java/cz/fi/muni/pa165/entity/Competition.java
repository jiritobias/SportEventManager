package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Set<User> sportsMen = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getSportsMen() {
        return sportsMen;
    }

    public void setSportsMen(Set<User> sportsMen) {
        this.sportsMen = sportsMen;
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

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        return sportsMen != null ? sportsMen.equals(that.sportsMen) : that.sportsMen == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (sportsMen != null ? sportsMen.hashCode() : 0);
        return result;
    }
}
