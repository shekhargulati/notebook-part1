package com.openshift.notebook.core.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Document(collection = "notebooks")
public class Notebook {

	@Id
	private String id;

	@NotNull
	private String name;

	@NotNull
	@Size(max = 4000)
	private String description;

	@NotNull
	@DateTimeFormat(style = "M-")
	private Date created = new Date();

	@NotNull
	private String author;

	@NotNull
	@Size(max = 4)
	private String[] tags;

	@Size(max = 10)
	private Note[] notes;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public void setNotes(Note[] notes) {
		this.notes = notes;
	}

	public Note[] getNotes() {
		return notes;
	}

	@Override
	public String toString() {
		return "Notebook [id=" + id + ", name=" + name + ", author=" + author
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(tags);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notebook other = (Notebook) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		}
		else if (!author.equals(other.author))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		}
		else if (!created.equals(other.created))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		}
		else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(tags, other.tags))
			return false;
		return true;
	}

	public String toJson() {
		return new JSONSerializer().include("tags").include("notes.tags").exclude("*.class").serialize(this);
	}

	public static Notebook fromJsonToNotebook(String json) {
		return new JSONDeserializer<Notebook>().use(null, Notebook.class)
				.deserialize(json);
	}

	public static String toJsonArray(Collection<Notebook> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<Notebook> fromJsonArrayToNotebooks(String json) {
		return new JSONDeserializer<List<Notebook>>()
				.use(null, ArrayList.class).use("values", Notebook.class)
				.deserialize(json);
	}

}
