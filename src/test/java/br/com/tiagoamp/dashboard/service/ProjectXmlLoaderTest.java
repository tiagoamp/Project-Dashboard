package br.com.tiagoamp.dashboard.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import br.com.tiagoamp.dashboard.model.Project;

public class ProjectXmlLoaderTest {
	
	private ProjectLoader xmlLoader;
	private Path xmlFilePath; 
	
	@Before
	public void runBefore() {
		xmlLoader = ProjectLoaderFactory.getProjectLoader("xml");
		xmlFilePath = Paths.get("resources/inputTestsFiles/example_tests.xml");
	}
	
	@After
	public void runAfter() {
		xmlLoader = null;
	}

	
	@Test
	public void testParse() {
		try {
			Project project = xmlLoader.parse(xmlFilePath);
			assertTrue(project != null);
			assertTrue("No exception expected!", true);
		} catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
			e.printStackTrace();
			fail("No exception expected!");
		}
	}

	@Test
	public void testPrintXmlInfo() {
		xmlLoader.printInfo(xmlFilePath);
		assertTrue("No exception expected!", true);
	}

}
