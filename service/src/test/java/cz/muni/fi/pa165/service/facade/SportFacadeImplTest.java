package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.facade.SportFacade;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.SportService;
import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.assertj.core.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Martin Smid
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
public class SportFacadeImplTest extends AbstractTestNGSpringContextTests {

    @Mock
    private BeanMappingService beanMappingService;
    @Mock
    private SportService sportService;

    @Autowired
    @InjectMocks
    private SportFacade sportFacade;

    private SportDTO sportDTO;
    private List<SportDTO> sportDTOList;
    private Sport sport;
    private List<Sport> sportList;

    @BeforeMethod
    public void setUpData() {
        sport = new Sport();
        sport.setName("Basketball");
        sport.setId(1L);

        sportList = new ArrayList<>();
        sportList.add(sport);

        sportDTO = new SportDTO(1L, "Basketball");

        sportDTOList = new ArrayList<>();
        sportDTOList.add(sportDTO);
    }

    @BeforeClass
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDelete() {
        sportFacade.delete(sportDTO);

        verify(sportService).delete(sport);
    }

    @Test
    public void testLoad() {
        when(sportService.findById(sportDTO.getId())).thenReturn(sport);
        when(beanMappingService.mapTo(sport, SportDTO.class)).thenReturn(sportDTO);

        SportDTO dto = sportFacade.load(sportDTO.getId());

        verify(sportService).findById(sportDTO.getId());
        Assertions.assertThat(dto.getName()).isEqualTo(sportDTO.getName());
    }

    @Test
    public void testGetAll() {
        when(sportService.findAll()).thenReturn(sportList);
        when(beanMappingService.mapTo(sportList, SportDTO.class)).thenReturn(sportDTOList);

        List<SportDTO> dtos = sportFacade.getAll();

        verify(sportService).findAll();
        Assertions.assertThat(dtos).isEqualTo(sportDTOList);
    }

    @Test
    public void testCreate() {
        sportFacade.create(sportDTO);

        verify(sportService).create(sport);
    }
}