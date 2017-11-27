package cz.fi.muni.pa165.dto;

import lombok.*;
import java.util.Date;

/**
 * @author jiritobias
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateSportEventDTO {
    @NonNull
    private String name;
    @NonNull
    private String place;
    @NonNull
    private Date date;
}
