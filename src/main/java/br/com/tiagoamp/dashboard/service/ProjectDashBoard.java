package br.com.tiagoamp.dashboard.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import br.com.tiagoamp.dashboard.model.DashboardException;
import br.com.tiagoamp.dashboard.model.Project;

/**
 * 
 * Represents a project dashboard. <br/>
 * Controller of dashboard generation and events.
 * 
 * @author tiagoamp
 *
 */
public class ProjectDashBoard {
	
	private static Path pageLayoutFile = Paths.get("resources/layout/dashboard_layout.html");
	private static Path standardOutput = Paths.get("resources/output");
	private String[] types = {"xml","json"};
	
	private ProjectLoader loader;
	
	private Project project;
	private Path outputDir;		
	private String pageHtmlCode;
	private String chartsJsCode;
	
	public ProjectDashBoard() {		
	}
			
	public void generateDashBoard(Path inputFile) throws DashboardException {
		String fileType = null;
		for (String type : types) {
			if (inputFile.toString().endsWith(type)) {
				fileType = type;
				break;
			}
		}		
		if (fileType == null) {
			throw new DashboardException("Unrecognized file type!");			
		}		
		loader = ProjectLoaderFactory.getProjectLoader(fileType);
				
		try {
			project = loader.parse(inputFile);					
		} catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
			throw new DashboardException("Error in file parsing! \n" + "Details: \n" + e);
		}
		
		try {
			pageHtmlCode = new ProjectHtmlPageGenerator(project, pageLayoutFile).generateHtmlPageCode();
			chartsJsCode = new ProjectChartsGenerator(project).generateChartsCode();
		} catch (IOException e) {
			throw new DashboardException("Error generating dahsboard page! \n" + "Details: \n" + e);
		}		
	}
			
	public void saveOutputFiles(Path outputDir) throws DashboardException {
		if (outputDir == null) throw new IllegalArgumentException("Output dir cannot be null!");
		this.outputDir = outputDir;
		try {
			byte[] buf = pageHtmlCode.getBytes();
			FileOutputStream fos = new FileOutputStream(standardOutput.resolve("dashboard.html").toFile(),false);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(buf);
			bos.flush();
			fos.close(); bos.close();
			buf = chartsJsCode.getBytes();
			Path jsFile = Paths.get(standardOutput + File.separator + "js" + File.separator + "charts.js");
			fos = new FileOutputStream(jsFile.toFile(),false);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
			bos.flush();
			fos.close(); bos.close();
			
			// copying files
			class DashboardVisitor extends SimpleFileVisitor<Path> {
				public DashboardVisitor(Path s, Path d) {
					source = s;
					destination = d;
				}
				private Path source, destination;
				
				public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
					Path newd = destination.resolve(source.relativize(path));
					Files.copy(path, newd, StandardCopyOption.REPLACE_EXISTING);
					return FileVisitResult.CONTINUE;	
				}
				
				public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes fileAttributes) throws IOException{
					if (path.compareTo(standardOutput) == 0) return FileVisitResult.CONTINUE;
					Path newd = destination.resolve(source.relativize(path));
					try {
						Files.copy(path, newd);
					} catch (FileAlreadyExistsException e) {
						return FileVisitResult.CONTINUE;
					}					
					return FileVisitResult.CONTINUE;
				}
			};
			Files.walkFileTree(standardOutput, new DashboardVisitor(standardOutput, outputDir));			
			
		} catch (IOException e) {
			throw new DashboardException("Error writing dashboard page! \n" + "Details: \n" + e);
		}				
	}
	
	
	public Path getOutputFile() {
		return outputDir;
	}
	public void setOutputFile(Path outputFile) {
		this.outputDir = outputFile;
	}
	public String getPageHtmlCode() {
		return pageHtmlCode;
	}
	public void setPageHtmlCode(String pageHtmlCode) {
		this.pageHtmlCode = pageHtmlCode;
	}
	public String getChartsJsCode() {
		return chartsJsCode;
	}
	public void setChartsJsCode(String chartsJsCode) {
		this.chartsJsCode = chartsJsCode;
	}	
	
}
