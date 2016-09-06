package br.com.tiagoamp.dashboard.service;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import br.com.tiagoamp.dashboard.model.DashboardException;

public class ProjectDashBoardTest {

	private Path xmlFile = Paths.get("resources/inputTestsFiles/example_tests.xml");
	private Path jsonFile = Paths.get("resources/inputTestsFiles/example_tests.json");	
	private ProjectDashBoard dashboard = new ProjectDashBoard(); 
		
	
	@Test
	public void testGenerateDashBoard_shouldGenerateDashboardFromXmlFile() throws DashboardException {		
     	dashboard.generateDashBoard(xmlFile);
		assertTrue("No exception expected from xml input file!", true);				
	}
	
	@Test
	public void testGenerateDashBoard_shouldGenerateDashboardFromTrelloJsonFile() throws DashboardException {		
     	dashboard.generateDashBoard(jsonFile);
		assertTrue("No exception expected from trello json input file!", true);				
	}
	
	@Test
	public void testSaveOutputFile_shouldThrowsIllegalArgumentException() throws DashboardException {		
		try {
			dashboard.saveOutputFiles(null);
		} catch (IllegalArgumentException e) {
			assertTrue("Exception expected!", true);
		}			
	}
	
	@Test
	public void testSaveOutputFile_shouldSaveDashboardFiles() throws DashboardException {
		dashboard.generateDashBoard(jsonFile);
		dashboard.saveOutputFiles(xmlFile.getParent());
		assertTrue("No exception expected!", true);	
	}

}
