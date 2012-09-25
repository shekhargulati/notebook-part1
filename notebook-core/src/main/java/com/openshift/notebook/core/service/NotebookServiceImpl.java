package com.openshift.notebook.core.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.openshift.notebook.core.domain.Notebook;
import com.openshift.notebook.core.repository.NotebookRepository;

@Service
public class NotebookServiceImpl implements NotebookService {

	@Inject
	NotebookRepository notebookRepository;
	
	@Override
	public Notebook createNewNotebook(Notebook notebook) {
		notebookRepository.save(notebook);
		return notebook;
	}

	@Override
	public Notebook findNotebook(String id) {
		return notebookRepository.findOne(id);
	}

	@Override
	public long numberOfNotebooks() {
		return notebookRepository.count();
	}

	@Override
	public Notebook updateNotebook(Notebook notebook) {
		notebookRepository.save(notebook);
		return notebook;
	}

	@Override
	public void deleteNotebook(Notebook notebook) {
		notebookRepository.delete(notebook);
	}

	@Override
	public List<Notebook> findAllNotebooks() {
		return notebookRepository.findAll();
	}

}
