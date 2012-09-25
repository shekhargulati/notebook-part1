package com.openshift.notebook.core.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.openshift.notebook.core.config.DevMongoDBFactoryConfig;
import com.openshift.notebook.core.config.MongoDbConfig;
import com.openshift.notebook.core.domain.Notebook;
import com.openshift.notebook.core.domain.NotebookBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DevMongoDBFactoryConfig.class,MongoDbConfig.class })
@ActiveProfiles("dev")
public class NotebookRepositoryTest {

	@Inject
	NotebookRepository notebookRepository;

	@Inject
	MongoTemplate mongoTemplate;

	@Before
	public void setup() {
		mongoTemplate.dropCollection(Notebook.class);
	}

	@Test
	public void shouldSaveNotebookAndReturnCountAsOne() {
		notebookRepository.save(createNewNotebook());
		assertEquals(1, notebookRepository.count());
	}

	@Test
	public void shouldSaveMultipleNotebooks() {
		notebookRepository.save(Arrays.asList(createNewNotebook(),
				createNewNotebook()));
		assertEquals(2, notebookRepository.count());
	}

	@Test
	public void testFindAllSort() {
		notebookRepository.save(Arrays.asList(createNewNotebook(),
				createNewNotebook()));
		Iterable<Notebook> notebooks = notebookRepository.findAll();
		Iterator<Notebook> iterator = notebooks.iterator();
		int count = 0;
		while (iterator.hasNext()) {
			iterator.next();
			count++;
		}
		assertEquals(2, count);
	}

	@Test
	public void testFindOne() {
		String[] tags = { "test" };
		Notebook notebook1 = createNewNotebook("shekhargulati", "Test Notebook", "A test Notebook", tags);
		notebookRepository.save(notebook1);
		notebookRepository.save(createNewNotebook("test_user", "Test Notebook 123", "A test Notebook123", tags));
		
		Notebook findOne = notebookRepository.findOne(notebook1.getId());
		assertEquals(notebook1, findOne);
		
	}

	@Test
	public void testExists() {
		Notebook notebook1 = createNewNotebook();
		notebookRepository.save(notebook1);
		assertTrue(notebookRepository.exists(notebook1.getId()));
	}

	@Test
	public void testDeleteID() {
		Notebook notebook1 = createNewNotebook();
		notebookRepository.save(notebook1);
		notebookRepository.delete(notebook1);
		
		Notebook findOne = notebookRepository.findOne(notebook1.getId());
		assertNull(findOne);
	}


	private Notebook createNewNotebook() {
		String[] tags = { "test" };
		return createNewNotebook("shekhargulati", "Test Notebook", "A test Notebook", tags);
	}

	private Notebook createNewNotebook(String author, String name, String desc,
			String[] tags) {
		Notebook notebook = NotebookBuilder.notebook().withAuthor(author)
				.withDescription(desc).withName(name).withTags(tags).build();
		return notebook;
	}
}
