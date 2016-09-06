package br.com.tiagoamp.dashboard.service;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.com.tiagoamp.dashboard.model.Project;

public interface ProjectLoader {
	
	/**
	 * Parses de file (xml or json) and instantiate a project with its attributes.
	 * 
	 * @param filePath
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParseException
	 */
	public Project parse(Path filePath) throws ParserConfigurationException, SAXException, IOException, ParseException;
		
}
