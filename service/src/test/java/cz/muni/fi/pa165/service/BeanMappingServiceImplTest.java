package cz.muni.fi.pa165.service;

import cz.fi.muni.pa165.dto.CompetitionDTO;
import cz.fi.muni.pa165.entity.Competition;
import cz.fi.muni.pa165.entity.Sport;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author jiritobias
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class BeanMappingServiceImplTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanMappingService beanMappingService;

    @Test
    public void testMapTo() {
        Sport sport = new Sport();
        sport.setName("Polo");

        Competition competition = new Competition();
        competition.setSport(sport);

        CompetitionDTO competitionDTO = beanMappingService.mapTo(competition, CompetitionDTO.class);
        assertThat(competitionDTO.getSport())
                .isNotNull();
    }

}