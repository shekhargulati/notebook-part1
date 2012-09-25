package com.openshift.notebook.core.config;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openshift.notebook.core.repository.NotebookRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DevMongoDBFactoryConfig.class,MongoDbConfig.class })
@ActiveProfiles("dev")
public class MongoDbConfigTest {

	@Inject
	MongoTemplate mongoTemplate;
	
	@Inject
	NotebookRepository notebookRepository;
	
	@Test
	public void shouldCheckIfContextIsLoadedProperly() {
		assertNotNull(mongoTemplate);
		assertNotNull(notebookRepository);
	}

}
