package trening;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Exercise implements ActiveDomainObject{
	
	private int id;
	private String name, description;
	private ArrayList<Category> categories;
	private Goal goal;

	public Exercise(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.categories = new ArrayList<>();
	}
	
	public Exercise(String name, String description) {
		this.id = -1;
		this.name = name;
		this.description = description;
	}
	
	//finner alle kategorier som er knyttet til øvelsen
	public void getCategories(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("select c.id, c.name from category as c "
					+ "JOIN category_exercise as e ON(c.id=e.category) where e.exercise="+id);
			categories.clear();
			while (result.next()){
				id = result.getInt("c.id");
				name = result.getString("c.name");
				categories.add(new Category(id, name));
			}
		} catch (Exception e) {
			System.out.println("db error during handling of select category from category_exercise: "+e);
			return;
		}
		
	}
	
	public void setGoal(Goal goal){
		this.goal = goal;
	}

	public Exercise(String name) {
		this.name = name;
	}
	
	public void initialize(Connection conn) {
		try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select description from Exercise where name=" + this.name);
            while (rs.next()) {
                this.description = rs.getString("description");
            }

        } catch (Exception e) {
            System.out.println("db error during select of exercise= "+e);
            return;
        }
		
	}

	@Override
	public void refresh(Connection conn) {
		initialize(conn);
		
	}

	@Override
	public void save(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			if (id != -1){
				stmt.executeUpdate("UPDATE exercise SET name="+name+", description="+description+", WHERE id="+id);
			} else {
				stmt.executeUpdate("INSERT INTO exercise VALUES (NULL,"+name+","+description+")");
			}
		} catch (Exception e) {
			System.out.println("db error during saving of the exercise");
			return;
		}
		
	}

}
