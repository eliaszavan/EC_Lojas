package net.elicodes.lojas.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LojasConnection {
	
	private Connection connection;
	private String url;
	private String user;
	private String pass;

	public LojasConnection(String ip, String user, String database, String pass) {
		this.url = "jdbc:mysql://" + ip + "/" + database + "?autoReconnect=true";
		this.user = user;
		this.pass = pass;
		this.open();
	}

	public Connection getConnection() {
		return this.connection;
	}

	public void open() {
		try {
			this.connection = DriverManager.getConnection(this.url, this.user, this.pass);
			System.out.println("Enabled mysql connection with settings: " + this.url + " | " + this.user + " | " + " | "
					+ this.pass);
			this.createTables();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (this.connection == null) {
			return;
		}
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void createTables() {
		try {
			PreparedStatement stm = this.connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS `lojas`(`player` VARCHAR(16), `location` TEXT NOT NULL, `boolean` TEXT NOT NULL)");
			stm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
