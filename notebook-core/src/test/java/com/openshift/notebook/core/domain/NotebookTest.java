package com.openshift.notebook.core.domain;

import static org.fest.assertions.api.Assertions.assertThat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class NotebookTest {

	@Test
	public void testToJson() throws Exception {
		final String[] tags = { "test", "xyz" };
		Notebook notebook = createNewNotebook("test_user", "test notebook",
				"this is a test notebook", tags);
		String json = notebook.toJson();
		System.out.println(json);
		JSONObject jsonObject = new JSONObject(json);
		assertThat(jsonObject).isNotNull();
		assertThat(jsonObject.get("author")).isEqualTo("test_user");
		assertThat(jsonObject.get("name")).isEqualTo("test notebook");
		assertThat(jsonObject.get("description")).isEqualTo(
				"this is a test notebook");
		String[] actualTags = toStringArray((JSONArray) jsonObject.get("tags"));
		assertThat(actualTags).hasSize(2).isEqualTo(tags);
	}

	@Test
	public void testToJsonWithNotes() throws Exception {
		final String[] tags = { "test", "xyz" };
		Notebook notebook = createNewNotebook("test_user", "test notebook",
				"this is a test notebook", tags);
		Note note1 = NoteBuilder.note().withText("test note 1")
				.withTitle("test note title").withTags(tags).build();
		Note note2 = NoteBuilder.note().withText("test note 2")
				.withTitle("test note title").withTags(tags).build();
		Note[] notes = { note1, note2 };
		notebook.setNotes(notes);
		String json = notebook.toJson();
		JSONObject jsonObject = new JSONObject(json);
		assertThat(jsonObject).isNotNull();
		assertThat(jsonObject.get("author")).isEqualTo("test_user");
		assertThat(jsonObject.get("name")).isEqualTo("test notebook");
		assertThat(jsonObject.get("description")).isEqualTo(
				"this is a test notebook");
		String[] actualTags = toStringArray((JSONArray) jsonObject.get("tags"));
		assertThat(actualTags).hasSize(2).isEqualTo(tags);

		JSONArray notesJsonArray = (JSONArray) jsonObject.get("notes");
		Note[] noteArray = toNoteArray(notesJsonArray);

		Note note1Copy = NoteBuilder.note().withText("test note 1")
				.withTitle("test note title").withTags(tags).build();
		Note note2Copy = NoteBuilder.note().withText("test note 2")
				.withTitle("test note title").withTags(tags).build();

		System.out.println(json);

		assertThat(noteArray).hasSize(2).contains(note1Copy, note2Copy);
	}

	@Test
	public void testFromJsonToNotebook() {
		final String[] tags = { "test", "xyz" };
		Notebook notebook = createNewNotebook("test_user", "test notebook",
				"this is a test notebook", tags);
		Note note1 = NoteBuilder.note().withText("test note 1")
				.withTitle("test note title").withTags(tags).build();
		Note note2 = NoteBuilder.note().withText("test note 2")
				.withTitle("test note title").withTags(tags).build();
		Note[] notes = { note1, note2 };
		notebook.setNotes(notes);
		String json = notebook.toJson();

		Notebook fromJsonToNotebook = Notebook.fromJsonToNotebook(json);
		assertThat(fromJsonToNotebook).isNotNull().isEqualTo(notebook);

	}

	private Notebook createNewNotebook(String author, String name, String desc,
			String[] tags) {
		Notebook notebook = NotebookBuilder.notebook().withAuthor(author)
				.withDescription(desc).withName(name).withTags(tags).build();
		return notebook;
	}

	private static String[] toStringArray(JSONArray jsonArray) throws Exception {
		if (jsonArray == null) {
			return new String[0];
		}
		int len = jsonArray.length();
		String[] arr = new String[len];
		for (int i = 0; i < len; i++) {
			arr[i] = jsonArray.get(i).toString();
		}
		return arr;
	}

	private static Note[] toNoteArray(JSONArray jsonArray) throws Exception {
		if (jsonArray == null) {
			return new Note[0];
		}
		int len = jsonArray.length();
		Note[] arr = new Note[len];
		for (int i = 0; i < len; i++) {
			JSONObject jsonObject = (JSONObject) jsonArray.get(i);
			arr[i] = toNote(jsonObject);
		}
		return arr;
	}

	private static Note toNote(JSONObject jsonObject) throws Exception {
		return NoteBuilder.note().withText(jsonObject.getString("text"))
				.withTitle(jsonObject.getString("title"))
				.withTags(toStringArray(jsonObject.getJSONArray("tags")))
				.build();
	}
}
