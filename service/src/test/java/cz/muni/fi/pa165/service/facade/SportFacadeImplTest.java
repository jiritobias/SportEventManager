package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.facade.SportFacade;
import cz.muni.fi.pa165.service.SportService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import java.util.List;

/**
 * @author jiritobias
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SportFacadeImplTest extends AbstractTestNGSpringContextTests {
    @Autowired
    private SportFacade sportFacade;
    @Autowired
    private SportService sportService;

    private SportDTO sportDTO;
    private Sport sport;

    @BeforeMethod
    public void setUpData() {

        sport = new Sport();
        String sportName = "Lazy-Tennis";
        sport.setName(sportName);
        sportService.create(sport);

        sportDTO = new SportDTO(sport.getId(), sportName);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        sportService.delete(sport);
    }

    @Test
    public void loadAndDelete() {
        List<SportDTO> all = sportFacade.getAll();

        Assertions.assertThat(all)
                .contains(sportDTO);

        sportFacade.delete(sportDTO);

        all = sportFacade.getAll();

        Assertions.assertThat(all)
                .doesNotContain(sportDTO);
    }

    @Test
    public void createAndLoad() {
        SportDTO sportDTO = new SportDTO(null, "FakeSport");
        Long sport = sportFacade.create(sportDTO);

        SportDTO load = sportFacade.load(sport);

        Assertions.assertThat(load.getName())
                .isEqualTo(sportDTO.getName());

        List<SportDTO> all = sportFacade.getAll();

        Assertions.assertThat(all)
                .contains(load);

        sportFacade.delete(load);

        all = sportFacade.getAll();

        Assertions.assertThat(all)
                .doesNotContain(load);

    }

}