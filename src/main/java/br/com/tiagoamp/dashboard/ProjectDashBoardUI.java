package br.com.tiagoamp.dashboard;

import java.nio.file.Path;
import java.nio.file.Paths;

import br.com.tiagoamp.dashboard.model.DashboardException;
import br.com.tiagoamp.dashboard.service.ProjectDashBoard;

/**
 * Dashboard User Interface.
 * 
 */
public class ProjectDashBoardUI {
	
	public static void main(String[] args) {
		
		try {
			if (args.length < 2) {
				throw new DashboardException("Usage: \n java <inputFilePath> <outputDirPath>");				
			}
		
			Path inputFile = Paths.get(args[0]);
			Path outputDirFile = Paths.get(args[1]);
						
			ProjectDashBoard dashboard = new ProjectDashBoard(inputFile, outputDirFile);
			dashboard.generateDashBoard();
			dashboard.saveOutputFiles();
			
		} catch (DashboardException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		}
				
		System.out.println("Dashborad generated!");
		System.exit(0);
	}
}
