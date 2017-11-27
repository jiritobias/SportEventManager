package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author jiritobias
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSportsMenDTO {

    @NonNull
    private String password;
    @NonNull
    private String email;
    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    @NonNull
    private Gendre gendre;
    @NonNull
    private Date birthdate;
    private String phone;
    private String address;
    @NonNull
    private Role role;
    @NonNull
    private Long sportsmanId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateSportsMenDTO)) {
            return false;
        }

        CreateSportsMenDTO that = (CreateSportsMenDTO) o;

        if (!password.equals(that.password)) {
            return false;
        }
        if (!email.equals(that.email)) {
            return false;
        }
        if (!firstname.equals(that.firstname)) {
            return false;
        }
        if (!lastname.equals(that.lastname)) {
            return false;
        }
        if (gendre != that.gendre) {
            return false;
        }
        if (!birthdate.equals(that.birthdate)) {
            return false;
        }
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) {
            return false;
        }
        return address != null ? address.equals(that.address) : that.address == null;
    }

    @Override
    public int hashCode() {
        int result = password.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + gendre.hashCode();
        result = 31 * result + birthdate.hashCode();
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
