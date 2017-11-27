package cz.fi.muni.pa165.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SportEventDTO {
    @NonNull
    private Long id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SportEventDTO)) {
            return false;
        }

        SportEventDTO that = (SportEventDTO) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
