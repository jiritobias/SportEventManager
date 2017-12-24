package cz.muni.fi.pa165.restapi.hateoas;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.dto.ChangePasswordDTO;
import cz.fi.muni.pa165.dto.SportsMenDTO;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import java.util.Date;

/**
 * Resource for User entity when changing the password
 *
 * @author Martin Smid
 */
@Relation(value = "user", collectionRelation = "users")
@JsonPropertyOrder({"id"})
public class UserNewPasswordResource extends ResourceSupport {

    @JsonProperty("id")
    private long dtoId;
    private String newPassword;

    public UserNewPasswordResource(ChangePasswordDTO passwordDTO) {
        this.dtoId = passwordDTO.getId();
        this.newPassword = passwordDTO.getNewPassword();
    }

    public long getDtoId() {
        return dtoId;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
