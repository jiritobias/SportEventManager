package cz.fi.muni.pa165.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * @author Petra Halová
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddSportsMenDTO {
    @NonNull
    private Long sportsMan;
    @NonNull
    private Long competition;
}
