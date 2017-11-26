package cz.muni.fi.pa165.service;

import cz.muni.fi.pa165.service.config.ServiceConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@ContextConfiguration(classes = ServiceConfiguration.class)
public class BaseServiceTest extends AbstractTestNGSpringContextTests {

}
