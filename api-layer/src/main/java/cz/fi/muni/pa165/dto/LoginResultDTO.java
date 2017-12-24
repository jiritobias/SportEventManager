package cz.fi.muni.pa165.dto;

import cz.fi.muni.pa165.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class LoginResultDTO {
    private Role role;
    private boolean result;
}
