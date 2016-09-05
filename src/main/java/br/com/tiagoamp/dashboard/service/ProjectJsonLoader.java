package br.com.tiagoamp.dashboard.service;

import java.io.IOException;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import br.com.tiagoamp.dashboard.model.Item;
import br.com.tiagoamp.dashboard.model.Project;
import br.com.tiagoamp.dashboard.model.Sprint;
import br.com.tiagoamp.dashboard.model.Status;
import br.com.tiagoamp.dashboard.model.trello.TrelloLabel;
import br.com.tiagoamp.dashboard.model.trello.TrelloList;

public class ProjectJsonLoader implements ProjectLoader {

	public Project parse(Path file) throws IOException, ParseException {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(file.toFile(), Map.class);
		
		Project project = new Project();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String pname = (String) map.get("name");
		String[] splitted = pname.split(";");
		project.setName(splitted[0]);
		project.setInitialDate(sdf.parse(splitted[1]));
		
		// sprints
		List<Sprint> sprints = new ArrayList<>();				
		List<LinkedHashMap<String, String>> labelList = (List<LinkedHashMap<String, String>>) map.get("labels");
		List<TrelloLabel> trelloLabels = new ArrayList<>();
		for (LinkedHashMap<String, String> labelMap : labelList) {
			TrelloLabel tLabel = new TrelloLabel();
			tLabel.setId(labelMap.get("id"));
			if (labelMap.get("name").equals("")) continue;
			tLabel.setName(labelMap.get("name"));
			
			Sprint sprint = new Sprint();
			String data = tLabel.getName();
			splitted = data.split(";");
			sprint.setNumber(Integer.parseInt(splitted[0].replaceAll("[a-zA-Z]", "").trim()));
			sprint.setInit(sdf.parse(splitted[1]));
			sprint.setEnd(sdf.parse(splitted[2]));
			sprint.setGoal(splitted[3]);
			
			tLabel.setSprintNumberRepresented(sprint.getNumber());
			trelloLabels.add(tLabel);
			sprints.add(sprint);
		}
								
		project.setSprints(sprints);
		
		// backlog
		List<Item> backlog = new ArrayList<>();
		
		List<LinkedHashMap<String, String>> listsList = (List<LinkedHashMap<String, String>>) map.get("lists");
		List<TrelloList> trelloLists = new ArrayList<>();
		for (LinkedHashMap<String, String> labelMap : listsList) {
			TrelloList tList = new TrelloList();
			tList.setId(labelMap.get("id"));
			if (labelMap.get("name").equals("")) continue;
			tList.setName(labelMap.get("name"));
			trelloLists.add(tList);
		}
		
		List<LinkedHashMap<String, Object>> cardsList = (List<LinkedHashMap<String, Object>>) map.get("cards");
		for (LinkedHashMap<String, Object> cardMap : cardsList) {
			Item item = new Item();
			String data = (String) cardMap.get("name");
			splitted = data.split(";");
			item.setId(splitted[0]);
			item.setDescription(splitted[1]);
			item.setPoints(Float.parseFloat(splitted[2].replaceAll("[a-zA-Z]", "").trim()));
			
			List<String> labelCardList = (List<String>) cardMap.get("idLabels"); // labels
			for (String idLabel : labelCardList) {
				for (TrelloLabel tLabel : trelloLabels) {
					if (idLabel.equals(tLabel.getId())) {
						item.setSprintNumber(tLabel.getSprintNumberRepresented());
						break;
					}
				}
			}
			
			String idList = (String) cardMap.get("idList");
			for (TrelloList tList : trelloLists) {
				if (idList.equals(tList.getId())) {
					item.setStatus(Status.valueOf(tList.getName()));
					break;
				}
			}
			
			if (item.getStatus() == Status.DONE) {
				data = (String) cardMap.get("due"); // end date
				item.setEnd(sdf.parse(data));
			}
				
			LinkedHashMap<String, Object> badges = (LinkedHashMap<String, Object>) cardMap.get("badges");
			int checkItems = (int) badges.get("checkItems");
			int checkItemsChecked = (int) badges.get("checkItemsChecked");
			item.setPercent((checkItemsChecked * 100)/checkItems); // percent complete
			
			backlog.add(item);
		}		
		project.setBacklog(backlog);
		
		return project;
	}

	public void printInfo(Path file) throws ParseException, IOException {
		try {
			Project project = this.parse(file);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			// project
			System.out.println("Name: " + project.getName());
			System.out.println("Init: " + sdf.format(project.getInitialDate()));
			
			// sprints
			System.out.println("= SPRINTS = ");
			for (Sprint sprint : project.getSprints()) {
				System.out.println("Sprint number: " + sprint.getNumber());
				System.out.println("Init: " + sdf.format(sprint.getInit()));
				System.out.println("End:" + sdf.format(sprint.getEnd()));
				System.out.println("Goal:" + sprint.getGoal());
			}
			
			// backlog
			System.out.println("= ITENS =");
			for (Item item : project.getBacklog()) {
				System.out.println("id:" + item.getId());
				System.out.println("Description:" + item.getDescription());
				System.out.println("Points:" + item.getPoints());
				System.out.println("Sprint number:" + item.getSprintNumber());
				System.out.println("Status: " + item.getStatus());
			}
		} catch (ParseException | IOException e) {
			e.printStackTrace();
			throw e;
		}				
	}
	
}
