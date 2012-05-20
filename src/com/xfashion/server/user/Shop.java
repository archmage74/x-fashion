package com.xfashion.server.user;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.xfashion.server.notepad.ArticleAmount;
import com.xfashion.server.notepad.Notepad;
import com.xfashion.shared.notepad.NotepadDTO;

@PersistenceCapable
public class Shop {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	Set<Notepad> notepads;
	
	@Persistent
	Set<ArticleAmount> articles;
	
	public Shop() {
		notepads = new HashSet<Notepad>();
		articles = new HashSet<ArticleAmount>();
	}
	
	public Key getKey() {
		return key;
	}
    
	public String getKeyAsString() {
		return KeyFactory.keyToString(key);
	}

	public Set<Notepad> getNotepads() {
		return notepads;
	}

	public Set<ArticleAmount> getArticles() {
		return articles;
	}

	public Set<NotepadDTO> createNotepadDTOs() {
		Set<NotepadDTO> dtos = new HashSet<NotepadDTO>();
		for (Notepad notepad : notepads) {
			dtos.add(notepad.createDTO());
		}
		return dtos;
	}

}
