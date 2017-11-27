package cz.muni.fi.pa165.service;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Martin Smid
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Autowired
    private Mapper mapper;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> matToClass) {
        List<T> mappedList = new ArrayList<>();
        for (Object obj : objects) {
            mappedList.add(mapper.map(obj, matToClass));
        }
        return mappedList;
    }

    @Override
    public <T> T mapTo(Object obj, Class<T> mapToClass) {
        return mapper.map(obj, mapToClass);
    }

    @Override
    public Mapper getMapper() {
        return mapper;
    }
}
