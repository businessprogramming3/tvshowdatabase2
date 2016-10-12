package model;

import java.util.List;

public interface IShowDAO {

	void createRecord(Show show);
	
	Show retrieveRecordById(int id);
	
	Show retrieveRecordByName(String name);
	
	//Show retrieveRecordByRating(int rating);
	
	//List<Show> retrieveRecordsByDirector(String director);
	
	//List<Show> retrieveRecordsByWriter(String writer);
	
	//List<Show> retrieveRecordsByActor(String actor);
	
	//List<Show> retrieveAllRecords();
	
	void updateRecord(Show updatedShow);
	
	void deleteRecord(int id);
	
	
}