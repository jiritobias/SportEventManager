package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.*;
import cz.fi.muni.pa165.entity.User;
import cz.fi.muni.pa165.enums.Gendre;
import cz.fi.muni.pa165.enums.Role;
import cz.fi.muni.pa165.facade.SportsMenFacade;
import cz.muni.fi.pa165.service.SportsmenService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.dozer.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Petra Halová on 28.11.17.
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SportsmenFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SportsMenFacade sportsMenFacade;

    @Autowired
    private SportsmenService sportsmenService;

    private User sportman;
    private CreateSportsMenDTO createSportsMenDTO;
    private Long sportsManId;

    @BeforeMethod
    public void setUp() {
        sportman = new User();
        sportman.setAddress("Death Star 1");
        sportman.setEmail("darthVader@darkside.com");
        sportman.setFirstname("Darth");
        sportman.setLastname("Vader");
        sportman.setGendre(Gendre.MAN);
        sportman.setRole(Role.SPORTSMEN);
        sportman.setPasswordHash("666");
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.MARCH, 1, 1, 1, 1);
        Date date = cal.getTime();
        sportman.setBirthdate(date);
        sportman.setPhone("12");

        createSportsMenDTO = new CreateSportsMenDTO("password",
                sportman.getEmail(), sportman.getFirstname(), sportman.getLastname(), sportman.getGendre(),
                sportman.getBirthdate(), sportman.getPhone(), sportman.getAddress(), sportman.getRole());

        sportsManId = sportsMenFacade.createSportsMen(createSportsMenDTO);
    }

    @AfterMethod
    public void tearDown() {
        try {
            sportsMenFacade.delete(sportsMenFacade.load(sportsManId));
        } catch (Exception e) {
        }
    }

    @Test
    public void testCreateSportsMen() {
        Assertions.assertThat(sportsManId)
                .isNotNull();
    }

    @Test
    public void testFindByBirthDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(2000, Calendar.FEBRUARY, 1, 1, 1, 1);
        Date date = cal.getTime();

        List<SportsMenDTO> found = sportsMenFacade.findByBirthDay(date);
        Assertions.assertThat(found).isEmpty();

        cal = Calendar.getInstance();
        cal.set(2000, Calendar.MARCH, 1, 1, 1, 1);
        date = cal.getTime();

        found = sportsMenFacade.findByBirthDay(date);
        SportsMenDTO load = sportsMenFacade.load(sportsManId);
        Assertions.assertThat(found).hasSize(1);
        Assertions.assertThat(found.get(0)).isEqualToComparingFieldByField(load);
    }

    @Test
    public void testResetPassword() {
        SportsMenDTO dto = sportsMenFacade.load(sportsManId);
        String newPassword = sportsMenFacade.resetPassword(new ResetPasswordDTO(sportsManId, createSportsMenDTO.getEmail()));
        SportsMenDTO load = sportsMenFacade.load(sportsManId);

        Assertions.assertThat(dto.getPasswordHash()).isNotEqualTo(load.getPasswordHash());
        sportman.setPasswordHash(load.getPasswordHash());
        Assertions.assertThat(sportsmenService.authenticate(sportman, newPassword)).isTrue();
    }

    @Test
    public void testChangePassword() {
        SportsMenDTO sportsMenDTO = sportsMenFacade.load(sportsManId);
        String oldPasswordHash = sportsMenDTO.getPasswordHash();
        ChangePasswordDTO passwordDTO = new ChangePasswordDTO(sportsManId, createSportsMenDTO.getPassword(), "newPassword");

        sportsMenFacade.changePassword(passwordDTO);

        String newPasswordHash = sportsMenFacade.load(sportsManId).getPasswordHash();
        Assertions.assertThat(newPasswordHash).isNotEqualTo(oldPasswordHash);

        sportman.setPasswordHash(newPasswordHash);
        Assertions.assertThat(sportsmenService.authenticate(sportman, "newPassword")).isTrue();
    }

    @Test
    public void testDelete() {
        SportsMenDTO dto = sportsMenFacade.load(sportsManId);
        Assertions.assertThat(dto.getId()).isNotNull();

        sportsMenFacade.delete(dto);
        Assertions.assertThatThrownBy(() -> sportsMenFacade.load(sportsManId)).isInstanceOf(MappingException.class);
    }

    @Test
    public void testLoad() {
        SportsMenDTO sportsMenDTO = sportsMenFacade.load(sportsManId);
        Assertions.assertThat(sportsMenDTO.getFirstname()).isEqualTo(createSportsMenDTO.getFirstname());
        Assertions.assertThat(sportsMenDTO.getLastname()).isEqualTo(createSportsMenDTO.getLastname());
    }

    @Test
    public void testGetAll() {
        List<SportsMenDTO> all = sportsMenFacade.getAll();
        SportsMenDTO sportsMenDTO = sportsMenFacade.load(sportsManId);
        Assertions.assertThat(all).hasSize(1).contains(sportsMenDTO);
    }
}
