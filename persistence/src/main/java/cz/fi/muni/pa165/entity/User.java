package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.*;

/**
 * @author jiritobias
 */
@Entity
@Getter
@Setter
@Table(name = "Users")
public class User extends BaseEntity {

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String passwordHash;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = EMAIL_REGEX)
    @NotNull
    private String email;

    @Column(nullable = false)
    @NotNull
    private String firstname;

    @Column(nullable = false)
    @NotNull
    private String lastname;

    @Column
    private Date birthdate;

    @Pattern(regexp = "\\+?\\d+")
    private String phone;

    @Column(nullable = false)
    @NotNull
    private String address;

    @NotNull
    @Enumerated
    private Role role;

    @Enumerated
    private Gendre gendre;

    @ManyToMany(mappedBy = "sportsMen")
    private Set<Competition> competitions = new HashSet<>();

    public Set<Competition> getCompetitions() {
        return Collections.unmodifiableSet(competitions);
    }

    public void removeFromCompetition(Competition competition) {
        this.competitions.remove(competition);
        competition.removeSportman(this);
    }

    public void addToCompetition(Competition competition) {
        this.competitions.add(competition);
        competition.addSportman(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        if (passwordHash != null ? !passwordHash.equals(user.passwordHash) : user.passwordHash != null) {
            return false;
        }
        if (email != null ? !email.equals(user.email) : user.email != null) {
            return false;
        }
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null) {
            return false;
        }
        if (lastname != null ? !lastname.equals(user.lastname) : user.lastname != null) {
            return false;
        }
        if (birthdate != null ? !birthdate.equals(user.birthdate) : user.birthdate != null) {
            return false;
        }
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) {
            return false;
        }
        if (address != null ? !address.equals(user.address) : user.address != null) {
            return false;
        }
        if (role != user.role) {
            return false;
        }
        return gendre == user.gendre;
    }

    @Override
    public int hashCode() {
        int result = passwordHash != null ? passwordHash.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (gendre != null ? gendre.hashCode() : 0);
        return result;
    }

    public void setBirthdate(Date birthdate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(birthdate);
        startCal.set(Calendar.MILLISECOND, 0);
        this.birthdate = startCal.getTime();
    }
}
