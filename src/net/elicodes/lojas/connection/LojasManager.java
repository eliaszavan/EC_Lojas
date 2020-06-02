package net.elicodes.lojas.connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.elicodes.lojas.Main;
import net.elicodes.lojas.methods.LojasMethods;

public class LojasManager {
	
	private LojasConnection sql;
	
	public LojasManager(Main main, LojasConnection sql) {
		this.sql = sql;
	}
	
	public boolean exists(String s) {
		try {
			PreparedStatement stm = this.sql.getConnection()
					.prepareStatement("SELECT * FROM `lojas` WHERE `player` = ?");
			stm.setString(1, s);
			return stm.executeQuery().next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void delete(String s) {
		try {
			PreparedStatement stm = this.sql.getConnection()
					.prepareStatement("DELETE FROM `lojas` WHERE `player` = ?");
			stm.setString(1, s);
			stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void get(String s) {
		try {
			PreparedStatement stm = this.sql.getConnection()
					.prepareStatement("SELECT * FROM `lojas` WHERE `player` = ?");
			stm.setString(1, s);
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				String location = rs.getString("location");
				boolean b = new Boolean(rs.getString("boolean"));
				Map<String, Boolean> hash = new HashMap<String, Boolean>();
				hash.put(location, b);
				LojasMethods.lojas.put(s, hash);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void set(String s, String l, boolean b) {
		try {
			PreparedStatement stm = LojasManager.this.sql.getConnection().prepareStatement(
					"INSERT INTO `lojas`(`player`, `location`, `boolean`) VALUES (?,?,?)");
			stm.setString(1, s);
			stm.setString(2, l);
			stm.setString(3, ""+b);
			stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getPlayers() {
		ArrayList<String> players = new ArrayList<>();
		try {
			PreparedStatement stm = this.sql.getConnection()
					.prepareStatement("SELECT * FROM `lojas`");
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				players.add(rs.getString("player"));
			}
			return players;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void updatePlayer(String s, String l, boolean b) {
		try {
			PreparedStatement stm = this.sql.getConnection().prepareStatement("UPDATE `lojas` SET `location` = ? WHERE `player` = ? ");
			stm.setString(1, l);
			stm.setString(2, s);
			stm.executeUpdate();
			stm = this.sql.getConnection().prepareStatement("UPDATE `lojas` SET `boolean` = ? WHERE `player` = ? ");
			stm.setString(1, ""+b);
			stm.setString(2, s);
			stm.executeUpdate();
			stm.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
