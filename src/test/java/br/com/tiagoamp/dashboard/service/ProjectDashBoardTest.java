package br.com.tiagoamp.dashboard.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import br.com.tiagoamp.dashboard.model.DashboardException;

public class ProjectDashBoardTest {

	Path xmlFile = Paths.get("resources/inputTestsFiles/example_tests.xml");
	Path jsonFile = Paths.get("resources/inputTestsFiles/example_tests.json");
	
	@Test
	public void testGenerateDashBoard_shouldGenerateDashboard() {		
		try {
			ProjectDashBoard dashboardFromXml = new ProjectDashBoard(xmlFile, xmlFile.getParent());
			dashboardFromXml.generateDashBoard();			
			assertTrue("No exception expected from xml input file!", true);
			ProjectDashBoard dashboardFromJson = new ProjectDashBoard(jsonFile, jsonFile.getParent());
			dashboardFromJson.generateDashBoard();			
			assertTrue("No exception expected from json input file!", true);
		} catch (DashboardException e) {
			e.printStackTrace();
			fail("No exception expected!");
		}		
	}
	
	@Test
	public void testSaveOutputFile_shouldSaveDashboardFiles() {		
		try {
			ProjectDashBoard dashboardFromXml = new ProjectDashBoard(xmlFile, xmlFile.getParent());
			dashboardFromXml.generateDashBoard();
			dashboardFromXml.saveOutputFiles();
			assertTrue("No exception expected!", true);		
		} catch (DashboardException e) {
			e.printStackTrace();
			fail("No exception expected!");
		}	
		
	}

}
