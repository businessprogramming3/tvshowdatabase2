package File;

import model.Show;
import model.IShowDAO;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class ShowDAO implements IShowDAO {
	protected String fileName = null;
	final List<Show> myShows;


public ShowDAO(){
	Properties props = new Properties();
	try {
		props.load(new FileInputStream("src/File/db.properties"));
		this.fileName = props.getProperty("DB_FILENAME");
	} catch (FileNotFoundException e)  {
		e.printStackTrace();
	} catch (IOException e){
		e.printStackTrace();
	}
	
	this.myShows = new ArrayList<>();
	try {
		Files.createFile(Paths.get(fileName));
	} catch (FileAlreadyExistsException fae){
		;
	} catch (IOException ioe){
		System.out.println("Create file error with " + ioe.getMessage());
	}
	readList();
}

public void createRecord(Show show){
	myShows.add(show);
	writeList();
}

public Show retrieveRecordById(int id){
	for (Show show : myShows){
		if(show.getId() == id){
			return show;
		}
	}
	return null;
}

public List<Show> retrieveAllRecords(){
	return myShows;
}
//created retrieve record by rating method
public Show retrieveRecordByRating(int rating){
	int count = 0;
	while(count <= myShows.size()){
		for (Show show : myShows){
			if(show.getRating() == rating){
				return show;
			}
		}
	}
	return null;
}

public Show retrieveRecordByName(String name){
	for (Show show : myShows){
		if(show.getName().equalsIgnoreCase(name)){
			return show;
		}
	}
	return null;
}

public List<Show> retrieveRecordsByDirector(String director){
	for (Show show : myShows){
		if (show.getDirector().equalsIgnoreCase(director)){
			return myShows;
		}
	}
	return null;
}

public List<Show> retrieveRecordsByWriter(String writer){
	for (Show show : myShows){
		if (show.getWriter().equalsIgnoreCase(writer)){
			return myShows;
		}
	}
	return null;
}

public List<Show> retrieveRecordsByActor(String actor){
	for (Show show : myShows){
		if (show.getMainActor().equalsIgnoreCase(actor)){
			return myShows;
		}
	}
	return null;
}

public void updateRecord(Show updatedShow){
	for (Show show : myShows){
		if (show.getId() == updatedShow.getId()){
			show.setName(updatedShow.getName());
			show.setDirector(updatedShow.getDirector());
			show.setRating(updatedShow.getRating());
			show.setSeasons(updatedShow.getSeasons());
			show.setWriter(updatedShow.getWriter());
			show.setMainActor(updatedShow.getMainActor());
			break;
		}
		writeList();
	}
}

public void deleteRecord(int id){
	for (Show show : myShows){
		if(show.getId() == id){
			myShows.remove(show);
			break;
		}
	}
	writeList();
}

private void readList(){
	Path path = Paths.get(fileName);
	try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)){
		String line;
		while ((line = reader.readLine()) != null){
			String[] data = line.split(",");
			int id = Integer.parseInt(data[0]);
			int rating = Integer.parseInt(data[1]);
			int seasons = Integer.parseInt(data[2]);
			String name = data[3];
			String director = data[4];
			String writer = data[5];
			String mainActor = data[6];
			Show show = new Show(id, rating, seasons, name, director, writer, mainActor);
			myShows.add(show);
		}
	}catch (IOException ioe){
		System.out.println("Read file error with " + ioe.getMessage());
	}
}

private void writeList(){
	Path path = Paths.get(fileName);
	try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)){
		for (Show show: myShows){
			writer.write(String.format("%d, %d, %d, %s, %s, %s, %s, %.2f\n",
					show.getId(), 
					show.getRating(),
					show.getSeasons(), 
					show.getName(),
					show.getDirector(),
					show.getWriter(),
					show.getMainActor()));
		}
	}catch (IOException ioe){
		System.out.println("Write file error with " + ioe.getMessage());
	}
}

public String toString(){
	StringBuilder sb = new StringBuilder();
	
	for(Show show : myShows){
		sb.append(String.format("%5d : %d, %d, %s, %s, %s, %s, %.2f\n",
				show.getId(),
				show.getRating(),
				show.getSeasons(),
				show.getName(),
				show.getDirector(),
				show.getWriter(),
				show.getMainActor()));
	}
	return sb.toString();
}



}