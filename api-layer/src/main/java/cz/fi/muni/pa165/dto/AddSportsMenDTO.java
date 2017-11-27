package cz.fi.muni.pa165.dto;

import lombok.*;

/**
 * @author Petra Halová
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddSportsMenDTO {
    @NonNull
    private Long sportsMan;
    @NonNull
    private Long competition;
}
