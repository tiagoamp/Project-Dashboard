package br.com.tiagoamp.dashboard.model.trello;

import org.codehaus.jackson.annotate.JsonProperty;

public class TrelloList {
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("name")
	private String name;
	
	
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
	
	
}
