package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.entity.Sport;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class SportServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SportService sportService;

    @Test
    public void testCreateAndLoad() {
        Sport sport = new Sport();
        sport.setName("Hockey");

        sportService.create(sport);

        Sport load = sportService.findById(sport.getId());

        Assertions
                .assertThat(sport)
                .isEqualTo(load);

        sportService.delete(sport); // restore the state
    }

    @Test
    public void testFindAll() {
        Sport sport = new Sport();
        sport.setName("Biatlon");

        sportService.create(sport);

        List<Sport> all = sportService.findAll();

        Assertions
                .assertThat(all)
                .contains(sport);

        sportService.delete(sport); // restore the state
    }

    @Test
    public void testDelete() {
        Sport sport = new Sport();
        sport.setName("Fotball");

        sportService.create(sport);

        List<Sport> all = sportService.findAll();

        Assertions
                .assertThat(all)
                .contains(sport);

        sportService.delete(sport);

        all = sportService.findAll();

        Assertions
                .assertThat(all)
                .doesNotContain(sport);

        sportService.delete(sport); // restore the state
    }

    @Test
    public void testFindByName() {
        Sport sport = new Sport();
        sport.setName("Volleyball");

        sportService.create(sport);

        Sport basketball = sportService.findByName("Volleyball");

        Assertions.assertThat(basketball)
                .isNotNull()
                .isEqualTo(sport);

        sportService.delete(sport); // restore the state
    }
}