package mySQL;

import model.Show;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.IShowDAO;

public class ShowDAO implements IShowDAO {
	protected final static boolean DEBUG = true;
	
	public void createRecord(Show show){
		final String QUERY = "Insert Into Show: "
				+ ("(id, rating, seasons, name, director, writer, mainActor)" 
				+ "VALUES (null, ?, ?, ?, ?, ?, ?)");
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(QUERY);){
			stmt.setInt(1, show.getRating());
			stmt.setInt(2, show.getSeasons());
			stmt.setString(3, show.getName());
			stmt.setString(4, show.getDirector());
			stmt.setString(5, show.getWriter());
			stmt.setString(6, show.getMainActor());
			if (DEBUG) {
				System.out.println(stmt.toString());
			}
			stmt.executeUpdate();
			
		} catch (SQLException ex){
			System.out.println("createRecord SQLException: " + ex.getMessage());
		}
	}
	
	public Show retrieveRecordById(int id) {
		final String QUERY = "select id, rating, seasons, name, director, writer, mainActor, " 
				+ "from show where id = " + id;
		
		Show show = null;
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(QUERY)){
			if (DEBUG) {
				System.out.println(stmt.toString());
			}
			ResultSet rs = stmt.executeQuery(QUERY);
			
			if (rs.next()){
				show = new Show(
						rs.getInt("id"),
						rs.getInt("rating"),
						rs.getInt("seasons"), 
						rs.getString("name"),
						rs.getString("director"),
						rs.getString("writer"),
						rs.getString("mainActor"));
			}
		}catch (SQLException ex) {
			System.out.println("retrieveRecordById SQLException: " + ex.getMessage());
		}
		
		return show;
	}
	
	public List<Show> retrieveAllRecords(){
		final List<Show> myList = new ArrayList<>();
		final String QUERY = "select id, rating, seasons, name, director, writer, mainActor from show";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(QUERY)){
			if (DEBUG){
				System.out.println(stmt.toString());
			}
			ResultSet rs = stmt.executeQuery(QUERY);
			
			while(rs.next()){
				myList.add(new Show(
						rs.getInt("id"),
						rs.getInt("rating"),
						rs.getInt("seasons"),
						rs.getString("name"),
						rs.getString("director"),
						rs.getString("writer"),
						rs.getString("mainActor")));
				
			}
		} catch (SQLException ex){
			System.out.println("retrieveAllRecords SQLException: " + ex.getMessage());
		}
		return myList;
	}
	
	public Show retrieveRecordByName(String name){
		final String QUERY = "select id, rating, seasons, name, director, writer, mainActor, " 
				+ "from show where name = " + name;
		
		Show show = null;
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(QUERY)){
			stmt.setString(1, name);
			if(DEBUG){
				System.out.println(stmt.toString());
			}
			ResultSet rs = stmt.executeQuery(QUERY);
			
			if (rs.next()){
				show = new Show(
						rs.getInt("id"),
						rs.getInt("rating"),
						rs.getInt("seasons"),
						rs.getString("name"),
						rs.getString("director"),
						rs.getString("writer"),
						rs.getString("mainActor"));						
			}
		} catch (SQLException ex){
			System.out.println("retrieveRecordByName SQLException: " + ex.getMessage());
		}
		
		return show;
	}

	public void updateRecord(Show updatedShow){
		final String QUERY = "update show set rating=?, seasons=?, name=?, director=?, writer=?, mainActor=? where id=?";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(QUERY)){
				stmt.setInt(1, updatedShow.getRating());
				stmt.setInt(2, updatedShow.getSeasons());
				stmt.setString(3, updatedShow.getName());
				stmt.setString(4, updatedShow.getDirector());
				stmt.setString(5, updatedShow.getWriter());
				stmt.setString(6, updatedShow.getMainActor());
				if(DEBUG){
					System.out.println(stmt.toString());
				}
				stmt.executeUpdate();
		} catch (SQLException ex){
			System.out.println("updateRecord SQLException: " + ex.getMessage());
		}
	}
	
	public void deleteRecord(int id){
		final String QUERY = "delete from show where id=?";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(QUERY)){
			stmt.setInt(1, id);
			if (DEBUG){
				System.out.println(stmt.toString());
			}
			stmt.executeUpdate();
		} catch(SQLException ex){
			System.out.println("deleteRecord SQLException: " + ex.getMessage());
		}
	}
	
	public void deleteRecord(Show show){
		final String QUERY = "delete from show where show = ?";
		
		try (Connection con = DBConnection.getConnection();
				PreparedStatement stmt = con.prepareStatement(QUERY)){
			stmt.setInt(1, show.getId());
			if (DEBUG){
				System.out.println(stmt.toString());
			}
			stmt.executeUpdate();
		} catch (SQLException ex){
			System.out.println("deleteRecord SQLException: " + ex.getMessage());
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for(Show show : retrieveAllRecords()){
			sb.append(show.toString()).append("\n");
		}
		return sb.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
