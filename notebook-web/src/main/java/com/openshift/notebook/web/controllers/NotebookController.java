package com.openshift.notebook.web.controllers;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openshift.notebook.core.domain.Notebook;
import com.openshift.notebook.core.service.NotebookService;

@Controller
@RequestMapping("/notebooks")
public class NotebookController {
	
	@Inject
	NotebookService notebookService;
	
	@RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Notebook notebook = Notebook.fromJsonToNotebook(json);
        notebookService.createNewNotebook(notebook);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(notebook.getId(),headers, HttpStatus.CREATED);
    } 

	@RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") String id) {
		
        Notebook notebook = notebookService.findNotebook(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (notebook == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(notebook.toJson(), headers,
                HttpStatus.OK);
    }
	
	@RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
		
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Notebook> result = notebookService.findAllNotebooks();
        return new ResponseEntity<String>(Notebook.toJsonArray(result),
                headers, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/{id}",method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@PathVariable("id") String id,@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Notebook notebook = Notebook.fromJsonToNotebook(json);
        notebook.setId(id);
        if (notebookService.updateNotebook(notebook) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") String id) {
        Notebook notebook = notebookService.findNotebook(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (notebook == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        notebookService.deleteNotebook(notebook);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
}
