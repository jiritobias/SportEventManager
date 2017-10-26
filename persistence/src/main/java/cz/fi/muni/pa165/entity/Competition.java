package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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
}
