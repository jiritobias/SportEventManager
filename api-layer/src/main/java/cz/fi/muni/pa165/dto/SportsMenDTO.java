package cz.fi.muni.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;

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

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
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
        if (!super.equals(o)) {
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
        return passwordHash != null ? passwordHash.equals(that.passwordHash) : that.passwordHash == null;
    }
}
