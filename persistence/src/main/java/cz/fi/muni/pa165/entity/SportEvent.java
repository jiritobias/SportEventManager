package cz.fi.muni.pa165.entity;

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

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Competition> getCompetitions() {
        return Collections.unmodifiableSet(competitions);
    }

    public void addCompetition(Competition competition) {
        competitions.add(competition);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Competition)) return false;

        SportEvent that = (SportEvent) o;

        if (!getName().equals(that.getName())) return false;
        if (!getPlace().equals(that.getPlace())) return false;
        if (!getDate().equals(that.getDate())) return false;
        return getCompetitions().equals(that.getCompetitions());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getPlace().hashCode();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getCompetitions().hashCode();
        return result;
    }
}
