package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jiritobias
 */
@Entity
@Table(name = "Users")
public class User extends BaseEntity {

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String passwordHash;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = EMAIL_REGEX)
    @NotNull
    private String email;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @Column
    private Date birthdate;

    @Pattern(regexp = "\\+?\\d+")
    private String phone;

    @NotNull
    private String address;

    @NotNull
    @Enumerated
    private Role role;

    @Enumerated
    private Gendre gendre;


    @ManyToMany(mappedBy = "sportsMen")
    private Set<Competition> competitions = new HashSet<>();

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Gendre getGendre() {
        return gendre;
    }

    public void setGendre(Gendre gendre) {
        this.gendre = gendre;
    }

    public Set<Competition> getCompetitions() {
        return Collections.unmodifiableSet(competitions);
    }

    public void setCompetitions(Set<Competition> competitions) {
        this.competitions = competitions;
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
        if (o == null || !(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        if (!id.equals(user.id)) {
            return false;
        }
        if (!passwordHash.equals(user.passwordHash)) {
            return false;
        }
        if (!email.equals(user.email)) {
            return false;
        }
        if (!firstname.equals(user.firstname)) {
            return false;
        }
        return lastname.equals(user.lastname);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + passwordHash.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
