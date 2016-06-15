package br.com.tiagoamp.dashboard.service;

import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import br.com.tiagoamp.dashboard.model.Item;
import br.com.tiagoamp.dashboard.model.Project;
import br.com.tiagoamp.dashboard.model.Sprint;
import br.com.tiagoamp.dashboard.model.Status;

public class ProjectHtmlPageGenerator {
	
	public ProjectHtmlPageGenerator(Project project, Path layoutHtml) {
		this.project = project;
		this.pageLayoutFile = layoutHtml;	
	}
	
	private Project project;
	private Path pageLayoutFile;
	
	
	public String generateHtmlPageCode() throws IOException {
		Document doc = Jsoup.parse(pageLayoutFile.toFile(), "UTF-8");
		Element elem = doc.getElementById("nm_proj");
		elem.html(project.getName());
		
		// PROJECT INFO
		elem = doc.getElementById("tbl_proj");
		
		// BACKLOG		
		elem = doc.getElementById("tbl_backlog");
		
		for (Item item : project.getBacklog()) {
			StringBuilder strBStatus = new StringBuilder();
			strBStatus.append("<tr> " + 
							"<td>"+item.getId()+"</td>" +
							"<td>"+item.getDescription()+"</td>" +
							"<td>"+item.getPoints()+"</td>");
			switch (item.getStatus()) {
			case TO_DO:
				strBStatus.append("<td>" + "<span class='label label-default'>TO DO</span>" + "</td>");
				break;
			case DOING:
				strBStatus.append("<td>" + "<span class='label label-primary'>DOING</span>" + "</td>");
				break;
			case TESTS:
				strBStatus.append("<td>" + "<span class='label label-info'>TESTS</span>" + "</td>");
				break;
			case DONE:
				strBStatus.append("<td>" + "<span class='label label-success'>DONE</span>" + "</td>");
				break;
			case BLOCKED:
				strBStatus.append("<td>" + "<span class='label label-danger'>BLOCKED</span>" + "</td>");
				break;				
			default:
				break;
			}
			/*strBStatus.append("<td>"+(item.getStatus() == Status.DONE ? "X" : "")  +"</td>" +
						"</tr>");*/
			strBStatus.append("<td><div class='progress-bar' role='progressbar' aria-valuemin='0' aria-valuemax='100' " +
					"style='width:" + item.getPercent() + "%;'>" + item.getPercent() + "%" + "</div></td>");
			strBStatus.append("</tr>");			
			elem.append(strBStatus.toString());
		}	
		
		List<Item> listaToDo = new ArrayList<>();
		List<Item> listaDoing = new ArrayList<>();
		List<Item> listaTests = new ArrayList<>();
		List<Item> listaDone = new ArrayList<>();
		List<Item> listaBlocked = new ArrayList<>();
		
		for (Item item : project.getBacklog()) {
			if (item.getStatus() == Status.TO_DO) listaToDo.add(item); 
			else if (item.getStatus() == Status.DOING) listaDoing.add(item);
			else if (item.getStatus() == Status.TESTS) listaTests.add(item);
			else if (item.getStatus() == Status.DONE) listaDone.add(item);
			else if (item.getStatus() == Status.BLOCKED) listaBlocked.add(item);
		}
		
		// SPRINTS		
		elem = doc.getElementById("tbl_sprints");
				
		for (Sprint sprint : project.getSprints()) {
			elem.append("<tr> " + 
							"<td>"+sprint.getNumber()+"</td>" +
							"<td>"+sprint.getGoal()+"</td>" +
							"<td>"+DateFormat.getDateInstance().format(sprint.getInit())+"</td>" +
							"<td>"+DateFormat.getDateInstance().format(sprint.getEnd())+"</td>" +
						"</tr>");
		}	
				
		// KANBAN
		elem = doc.getElementById("tbl_kanban");
				
		if (!listaToDo.isEmpty()) {
			elem = elem.getElementById("k_todo");
			for (Item item : listaToDo) {				
				elem.append("<div class=\"itempost\"><span class='label label-warning'>" + item.getId() + " - " + item.getDescription() + "</span></div>");
			}
		}
		if (!listaDoing.isEmpty()) {
			elem = doc.getElementById("tbl_kanban");
			elem = elem.getElementById("k_doing");
			for (Item item : listaDoing) {				
				elem.append("<div class=\"itempost\"><span class='label label-warning'>" + item.getId() + " - " + item.getDescription() + "</span></div>");
			}
		}
		if (!listaTests.isEmpty()) {
			elem = doc.getElementById("tbl_kanban");
			elem = elem.getElementById("k_tests");
			for (Item item : listaTests) {				
				elem.append("<div class=\"itempost\"><span class='label label-warning'>" + item.getId() + " - " + item.getDescription() + "</span></div>");
			}
		}
		if (!listaDone.isEmpty()) {
			elem = doc.getElementById("tbl_kanban");
			elem = elem.getElementById("k_done");
			for (Item item : listaDone) {				
				elem.append("<div class=\"itempost\"><span class='label label-warning'>" + item.getId() + " - " + item.getDescription() + "</span></div>");
			}
		}		
		if (!listaBlocked.isEmpty()) {
			elem = doc.getElementById("tbl_kanban");
			elem = elem.getElementById("k_blocked");
			for (Item item : listaBlocked) {				
				elem.append("<div class=\"itempost\"><span class='label label-warning'>" + item.getId() + " - " + item.getDescription() + "</span></div>");
			}
		}
		
		return doc.toString();		
	}

}
