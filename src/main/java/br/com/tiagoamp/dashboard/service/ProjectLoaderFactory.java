package br.com.tiagoamp.dashboard.service;

public class ProjectLoaderFactory {

	public static ProjectLoader getProjectLoader(String fileType) {
		switch(fileType) {
			case "xml":
				return new ProjectXmlLoader();
			case "json":
				return new ProjectJsonLoader();
		}
		return null;
	}
	
}
