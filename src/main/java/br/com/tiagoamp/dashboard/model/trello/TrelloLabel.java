package br.com.tiagoamp.dashboard.model.trello;

public class TrelloLabel {
	
	private String id;
	private String name;
	private transient int sprintNumberRepresented;
	
	
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
	public int getSprintNumberRepresented() {
		return sprintNumberRepresented;
	}
	public void setSprintNumberRepresented(int i) {
		this.sprintNumberRepresented = i;
	}
	
}
