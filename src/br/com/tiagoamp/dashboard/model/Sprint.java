package br.com.tiagoamp.dashboard.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Sprint {
	
	public Sprint() {		
	}
	
	public Sprint(int number, String goal, Date init, Date end) {
		this.number = number;
		this.goal = goal;
		this.init = init;
		this.end = end;
	}
	
	private int number;
	private String goal;
	private Date init;
	private Date end;
	
	
	public void print() {
		System.out.printf("\t Sprint: %s - %s \n", number, goal);
		System.out.printf("\t Init: %s \n", init == null ? "?" : DateFormat.getDateInstance().format(init));
		System.out.printf("\t End: %s \n", end == null ? "?" : DateFormat.getDateInstance().format(end));
	}
	
	/**
	 * Return daily ratio increment. <br/>
	 * Formula: r = p/n
	 * 
	 * @param qtDays
	 * @param points
	 * 
	 * @return r 
	 */
	public float getDailyRatio(float points) {		
		return points / (float)this.getQtDaysInSprint();
	}
		
	public int getQtDaysInSprint() {
		long diff = this.getEnd().getTime() - this.getInit().getTime();
		return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
	}
	
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public Date getInit() {
		return init;
	}
	public void setInit(Date init) {
		this.init = init;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
		
}
