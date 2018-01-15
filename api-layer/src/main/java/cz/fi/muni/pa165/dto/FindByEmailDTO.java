package cz.fi.muni.pa165.dto;

import lombok.*;

/**
 * @author Petra Halova on 14.1.18.
 */
@Getter
@Setter
@AllArgsConstructor
@Data
public class FindByEmailDTO {
  @NonNull
    String email;
}
