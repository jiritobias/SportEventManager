package cz.muni.fi.pa165.service.facade;

import cz.fi.muni.pa165.dto.SportDTO;
import cz.fi.muni.pa165.entity.Sport;
import cz.fi.muni.pa165.facade.SportFacade;
import cz.muni.fi.pa165.service.BeanMappingService;
import cz.muni.fi.pa165.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Martin Smid
 */
@Service
@Transactional
public class SportFacadeImpl implements SportFacade {

    @Autowired
    private SportService sportService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public void delete(SportDTO dto) {
        Sport sport = sportService.findById(dto.getId());
        sportService.delete(sport);
    }

    @Override
    public SportDTO load(Long id) {
        return beanMappingService.mapTo(sportService.findById(id), SportDTO.class);
    }

    @Override
    public List<SportDTO> getAll() {
        return beanMappingService.mapTo(sportService.findAll(), SportDTO.class);
    }

    @Override
    public Long create(SportDTO sportDTO) {
        Sport sport = new Sport();
        sport.setName(sportDTO.getName());
        sportService.create(sport);
        return sport.getId();
    }
}
