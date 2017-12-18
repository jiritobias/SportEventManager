package cz.muni.fi.pa165.restapi.hateoas;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(value = "login")
@JsonPropertyOrder({"role"})
public class LoginResource extends ResourceSupport {
    private Role role;
    private String username;
    private String psswd;

    public Role getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getPsswd() {
        return psswd;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPsswd(String psswd) {
        this.psswd = psswd;
    }
}
