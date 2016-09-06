package br.com.tiagoamp.dashboard.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.tiagoamp.dashboard.model.Project;

public class ProjectJsonLoaderTest {
	
	private ProjectLoader jsonLoader;
	private Path jsonFilePath; 
	
	@Before
	public void setup() {
		jsonLoader = ProjectLoaderFactory.getProjectLoader("json");
		jsonFilePath = Paths.get("resources/inputTestsFiles/example_tests.json");		
	}
	
	@After
	public void tearDown() {
		jsonLoader = null;
	}

	
	@Test
	public void testParse_shouldGenerateValidProject() {
		try {
			Project project = jsonLoader.parse(jsonFilePath);
			assertTrue(project != null);
			assertTrue("No exception expected!", true);
		} catch (Exception e) {
			e.printStackTrace();
			fail("No exception expected!");
		}
	}
	
}
