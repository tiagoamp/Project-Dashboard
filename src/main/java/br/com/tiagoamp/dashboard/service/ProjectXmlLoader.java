package br.com.tiagoamp.dashboard.service;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.tiagoamp.dashboard.model.Item;
import br.com.tiagoamp.dashboard.model.Project;
import br.com.tiagoamp.dashboard.model.Sprint;
import br.com.tiagoamp.dashboard.model.Status;

/**
 * Loader de xml file to a project object.
 * @author tiagoamp
 */
public class ProjectXmlLoader implements ProjectLoader {
	
	public Project parse(Path xmlFile) throws ParserConfigurationException, SAXException, IOException, ParseException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile.toFile());
		doc.getDocumentElement().normalize();
		
		Project project = new Project();

		Element rootElement = doc.getDocumentElement(); // 'project'
		project.setCode(rootElement.getAttribute("cod"));
		project.setName(rootElement.getElementsByTagName("name").item(0).getTextContent());				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = rootElement.getElementsByTagName("initDate").item(0).getTextContent();
		project.setInitialDate(sdf.parse(strDate));
		strDate = rootElement.getElementsByTagName("endDate").item(0).getTextContent();
		project.setEndDate(sdf.parse(strDate));
				
		List<Sprint> sprints = new ArrayList<>();		
		NodeList nList = doc.getElementsByTagName("sprint"); // sprints
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Sprint sprint = new Sprint();
				sprint.setNumber(Integer.valueOf(eElement.getElementsByTagName("number").item(0).getTextContent()));
				sprint.setGoal(eElement.getElementsByTagName("goal").item(0).getTextContent());
				String dateStr = eElement.getElementsByTagName("initDate").item(0).getTextContent();
				if (!dateStr.isEmpty())	sprint.setInit(sdf.parse(dateStr));
				dateStr = eElement.getElementsByTagName("endDate").item(0).getTextContent();
				if (!dateStr.isEmpty())	sprint.setEnd(sdf.parse(dateStr));
				sprints.add(sprint);				
			}
		}
		project.setSprints(sprints);
		
		List<Item> backlog = new ArrayList<>();		
		nList = doc.getElementsByTagName("item"); // backlog
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Item item = new Item();
				item.setId(eElement.getElementsByTagName("id").item(0).getTextContent());
				item.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
				item.setPoints(Float.parseFloat(eElement.getElementsByTagName("points").item(0).getTextContent()));
				item.setStatus(Status.valueOf(eElement.getElementsByTagName("status").item(0).getTextContent()));
				item.setSprintNumber(Integer.parseInt(eElement.getElementsByTagName("sprintNumber").item(0).getTextContent()));
				String dateStr = eElement.getElementsByTagName("init").item(0).getTextContent();
				if (!dateStr.isEmpty())	item.setInit(sdf.parse(dateStr));
				dateStr = eElement.getElementsByTagName("end").item(0).getTextContent();
				if (!dateStr.isEmpty())	item.setEnd(sdf.parse(dateStr));
				backlog.add(item);
				project.setEstimatedTotalPoints(project.getEstimatedTotalPoints() + item.getPoints());
				item.setPercent(Integer.parseInt(eElement.getElementsByTagName("percent").item(0).getTextContent()));
			}
		}
		project.setBacklog(backlog);
		return project;
	}

	public void printInfo(Path xmlFile) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile.toFile());
			doc.getDocumentElement().normalize();
			
			Element rootElement = doc.getDocumentElement(); // 'project'
			System.out.println("Root element :" + rootElement.getNodeName());
			System.out.println("Codigo projeto: " + rootElement.getAttribute("cod"));
			System.out.println("----------------------------");
			System.out.println("Name : " + rootElement.getElementsByTagName("name").item(0).getTextContent());
			System.out.println("initDate : " + rootElement.getElementsByTagName("initDate").item(0).getTextContent());
			System.out.println("endDate : " + rootElement.getElementsByTagName("endDate").item(0).getTextContent());
			System.out.println("----------------------------");
			NodeList nList = doc.getElementsByTagName("sprint");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);					
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("SPRINT");
					System.out.println("number : " + eElement.getElementsByTagName("number").item(0).getTextContent());
					System.out.println("goal : " + eElement.getElementsByTagName("goal").item(0).getTextContent());
					System.out.println("init : " + eElement.getElementsByTagName("initDate").item(0).getTextContent());
					System.out.println("end : " + eElement.getElementsByTagName("endDate").item(0).getTextContent());
					System.out.println("----------------------------");
				}
			}
			System.out.println("----------------------------");		
			nList = doc.getElementsByTagName("item");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);					
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("ITEM");
					System.out.println("id : " + eElement.getElementsByTagName("id").item(0).getTextContent());
					System.out.println("description : " + eElement.getElementsByTagName("description").item(0).getTextContent());
					System.out.println("points : " + eElement.getElementsByTagName("points").item(0).getTextContent());
					System.out.println("status : " + eElement.getElementsByTagName("status").item(0).getTextContent());
					System.out.println("sprintNumber : " + eElement.getElementsByTagName("sprintNumber").item(0).getTextContent());
					System.out.println("init : " + eElement.getElementsByTagName("init").item(0).getTextContent());
					System.out.println("end : " + eElement.getElementsByTagName("end").item(0).getTextContent());
					System.out.println("----------------------------");
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		} 
	}
			
}
