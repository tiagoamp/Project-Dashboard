package br.com.tiagoamp.dashboard.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Represents a project.
 * 
 * @author tiagoamp
 */
public class Project {

	private String code;
	private String name;
	private Date initialDate;
	private Date endDate;
	private List<Item> backlog;
	private List<Sprint> sprints;
	private float estimatedTotalPoints; // sum of itens values
	
	
	@Override
	public String toString() {
		return name + " - " + "Sprints: " + sprints.size() + " - " + "Backlog: " + backlog.size() + "itens";
	}
	
	public void print() {
		System.out.printf("Project: [%s] %s \n", code, name);
		System.out.printf("Sprints: %d \n", sprints.size());
		System.out.printf("Init: %s \n", DateFormat.getDateInstance().format(initialDate));
		System.out.printf("End: %s \n", DateFormat.getDateInstance().format(endDate));
		System.out.printf("Total estimated pointes: %d \n", estimatedTotalPoints);
		System.out.printf("Sprints: %d \n", sprints.size());
		for (Sprint sprint : sprints) {
			System.out.println("---");
			sprint.print();
		}
		System.out.printf("Backlog: %d itens \n", backlog.size());
		for (Item item : backlog) {
			System.out.println("---");
			item.print();
		}
	}
	
	public int getTotalDaysInSprints() {
		int qtTotalDaysInSprints = 0;
		for (Sprint sprint : sprints) {
			long diff = sprint.getEnd().getTime() - sprint.getInit().getTime();
			qtTotalDaysInSprints += (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
		}
		return qtTotalDaysInSprints;
	}
	
	public float getTotalPointsInSprint(int sprintNumber) {
		float sprintPoints = 0F;
		for (Item item : backlog) {
			if (item.getSprintNumber() == sprintNumber) {
				sprintPoints += item.getPoints();
			}
		}
		return sprintPoints;
	}
	
	public List<Item> getItensPerStatus(Status status) {
		List<Item> lista = new ArrayList<>();
		for (Item item : backlog) {
			if (item.getStatus() == status) lista.add(item);
		}
		return lista;
	}
	
	public Map<Sprint, List<Item>> getItensPerSprints() {
		Map<Sprint, List<Item>> result = new HashMap<>();
		for (Sprint sprint : sprints) {
			List<Item> itens = new ArrayList<>();
			for (Item item : backlog) {
				if (item.getSprintNumber() == sprint.getNumber()) {
					itens.add(item);
				}
			}
			result.put(sprint, itens);
		}
		return result;
	}
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getInitialDate() {
		return initialDate;
	}
	public void setInitialDate(Date initialDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(initialDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
		this.initialDate = cal.getTime();
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		this.endDate = cal.getTime();
	}
	public float getEstimatedTotalPoints() {
		return estimatedTotalPoints;
	}
	public void setEstimatedTotalPoints(float estimatedTotalPoints) {
		this.estimatedTotalPoints = estimatedTotalPoints;
	}
	public List<Item> getBacklog() {
		return backlog;
	}
	public void setBacklog(List<Item> backlog) {
		this.backlog = backlog;
	}
	public List<Sprint> getSprints() {
		return sprints;
	}
	public void setSprints(List<Sprint> sprints) {
		this.sprints = sprints;
	}	
	
}
