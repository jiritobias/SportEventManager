package cz.fi.muni.pa165.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Martin Šmíd
 */
@Entity
@Getter
@Setter
public class Sport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    public Sport(Long id) {
        this.id = id;
    }

    public Sport() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null ||  !(o instanceof Sport)) {
            return false;
        }

        Sport sport = (Sport) o;

        return name.equals(sport.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}