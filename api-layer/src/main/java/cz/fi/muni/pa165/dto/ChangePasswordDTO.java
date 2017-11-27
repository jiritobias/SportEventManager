package cz.fi.muni.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class ChangePasswordDTO {
    @NonNull
    private Long id;
    @NonNull
    private String oldPassword;
    @NonNull
    private String newPassword;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChangePasswordDTO)) return false;

        ChangePasswordDTO that = (ChangePasswordDTO) o;

        if (oldPassword != null ? !oldPassword.equals(that.oldPassword) : that.oldPassword != null) return false;
        return newPassword != null ? newPassword.equals(that.newPassword) : that.newPassword == null;
    }

    @Override
    public int hashCode() {
        int result = oldPassword != null ? oldPassword.hashCode() : 0;
        result = 31 * result + (newPassword != null ? newPassword.hashCode() : 0);
        return result;
    }
}
