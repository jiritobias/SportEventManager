package cz.fi.muni.pa165.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthenticateDTO {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
