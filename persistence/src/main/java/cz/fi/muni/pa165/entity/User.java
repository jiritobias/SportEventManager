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

@Entity
@Table(name = "Users")
public class User extends BaseEntity {

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passwordHash;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = EMAIL_REGEX)
    @NotNull
    private String email;

    @NotNull
    @Column
    private String firstname;

    @NotNull
    @Column
    private String lastname;

    @Column
    private Date birthdate;

    @Pattern(regexp = "\\+?\\d+")
    @Column
    private String phone;

    @NotNull
    @Column
    private String address;

    @NotNull
    @Enumerated
    @Column
    private Role role;

    @Column
    @Enumerated
    private Gendre gendre;


    @ManyToMany
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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) {
            return false;
        }
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
        if (gendre != user.gendre) {
            return false;
        }
        return competitions != null ? competitions.equals(user.competitions) : user.competitions == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (gendre != null ? gendre.hashCode() : 0);
        result = 31 * result + (competitions != null ? competitions.hashCode() : 0);
        return result;
    }
}
