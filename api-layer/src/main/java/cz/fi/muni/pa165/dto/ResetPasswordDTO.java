package cz.fi.muni.pa165.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordDTO {
    @NonNull
    private Long id;
    @NonNull
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResetPasswordDTO)) return false;

        ResetPasswordDTO that = (ResetPasswordDTO) o;

        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}
