package br.com.tiagoamp.dashboard.model;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents an item of product backlog.
 * @author tiagoamp
 */
public class Item {
	
	public Item() {		
	}
	
	public Item(String id, String desc, int point, Date init, Date end, int sprintNumber, Status status, int percent) {
		this.id = id;
		this.description = desc;
		this.init = init;
		this.end = end;
		this.sprintNumber = sprintNumber;
		this.status = status;
		this.percent = percent;
	}

	private String id;
	private String description;
	private float points;
	private Date init;
	private Date end;
	private int sprintNumber;
	private Status status;
	private int percent;
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item){
			if ( ((Item) obj).getId() == this.getId() ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return id + " - " + description + " - " + status;
	}
	
	public void print() {
		System.out.printf("\t Item: %s - %s \n", id, description);
		System.out.printf("\t Status: %s \n", status);
		System.out.printf("\t Points: %d \n", points);
		System.out.printf("\t Sprint Number: %d \n", sprintNumber);
		System.out.printf("\t Init: %s \n", init == null ? "?" : DateFormat.getDateInstance().format(init));
		System.out.printf("\t End: %s \n", end == null ? "?" : DateFormat.getDateInstance().format(end));
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPoints() {
		return points;
	}
	public void setPoints(float points) {
		this.points = points;
	}
	public Date getInit() {
		return init;
	}
	public void setInit(Date init) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(init);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		this.init = cal.getTime();
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(end);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		this.end = cal.getTime();
	}
	public int getSprintNumber() {
		return sprintNumber;
	}
	public void setSprintNumber(int sprintNumber) {
		this.sprintNumber = sprintNumber;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public int getPercent() {
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
		
}
