package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * @author jiritobias
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SportsMenDTO {

    @NonNull
    private Long id;
    @NonNull
    @Size(max = 50)
    private String firstname;
    @NonNull
    private String lastname;
    @NonNull
    private String passwordHash;
    @NonNull
    private String email;
    private Date birthdate;
    private String phone;
    @NonNull
    private String address;
    @NonNull
    private Role role;
    private Gendre gendre;
    private List<CompetitionDTO> competitions;

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (gendre != null ? gendre.hashCode() : 0);
        result = 31 * result + (competitions != null ? competitions.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SportsMenDTO)) {
            return false;
        }

        SportsMenDTO that = (SportsMenDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) {
            return false;
        }
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) {
            return false;
        }
        if (passwordHash != null ? !passwordHash.equals(that.passwordHash) : that.passwordHash != null) {
            return false;
        }
        if (email != null ? !email.equals(that.email) : that.email != null) {
            return false;
        }
        if (birthdate != null ? !birthdate.equals(that.birthdate) : that.birthdate != null) {
            return false;
        }
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) {
            return false;
        }
        if (address != null ? !address.equals(that.address) : that.address != null) {
            return false;
        }
        if (role != that.role) {
            return false;
        }
        if (gendre != that.gendre) {
            return false;
        }
        return competitions != null ? competitions.equals(that.competitions) : that.competitions == null;
    }
}
