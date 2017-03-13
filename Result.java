
import java.sql.Connection;
import java.time.LocalDate;
import java.sql.*;

public class Result implements ActiveDomainObject{

	private Exercise exercise;
	private double weight, distance, duration;
	private int id, repetitions, sets;
	private LocalDate date;

	public Result(int id, Exercise e, double weight, double dist, double dur, int reps, int sets, LocalDate date) {
		this.id = id;
		this.exercise = e;
		this.weight = weight;
		this.distance = dist;
		this.duration = dur;
		this.repetitions = reps;
		this.sets = sets;
		this.date = date;
	}
	public Result(Exercise e, double weight, double dist, double dur, int reps, int sets, LocalDate date) {
		this(-1, e, weight, dist, dur, reps, sets, date);
	}

	@Override
	public void initialize(Connection conn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Connection conn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Connection conn) {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery();
		} catch (Exception e){
			System.out.println("db error during select of Goal " + e);
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
				stmt.executeUpdate("UPDATE result SET exercise="+exercise.id+", weight="weight+", distance="
				+distance+", duration="+duration+", repetitions="+repetitions+", sets="+sets+", date="+java.sql.date.valueOf(date)
				+", WHERE id="+id);
			} else {
				stmt.executeUpdate("INSERT INTO result VALUES(NULL,"+exercise.id+","+weight+","+distance+","+duration+","
				+repetitions+","+sets+","+java.sql.Date.valueOf(date)+")");
				id = last_insert_id();
			}
		} catch (Exception e){
			System.out.println("db error during saving of result");
			return;
		}
	}

}
