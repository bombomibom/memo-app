package com.kopo.memo.memo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.sqlite.SQLiteConfig;

public class DB {
	private Connection connection;
	private String dbTableName;
	private String dbUrl = "jdbc:mysql://au77784bkjx6ipju.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/w1uvnz6jjchf3f2s" ;
	private String dbUser = "h6dzhjbgi5nhh2o5";
	private String dbPasswd = "lpeq52n5xur4c44w";
	
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DB(String dbTableName) {
		this.dbTableName = dbTableName;
	}
	
	public boolean open() {
		try {
			this.connection = DriverManager.getConnection(this.dbUrl, this.dbUser, this.dbPasswd);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean close() {
		if (this.connection == null) {
			return true;
		}
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	// createTable :: heroku jawsDB mysql로 대체!
	public int createTable() throws Exception {
		this.open();
		String query = "CREATE TABLE " + this.dbTableName + "(idx INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, contents TEXT);";
		Statement statement = this.connection.createStatement();
		int result = statement.executeUpdate(query);
		statement.close();
		this.close();
		return result;
	}
	
	// insertData
	public void insertData(Memo memo) throws Exception {
		this.open();
		try {
			String query = "INSERT INTO " + this.dbTableName + "(title, contents) VALUES (?, ?);";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, memo.title);
			preparedStatement.setString(2, memo.contents);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
	}
	
	// selectData
	public ArrayList<Memo> selectData(){
		ArrayList<Memo> memo = new ArrayList<Memo>();
		this.open();
		try {
			String query = "SELECT * FROM " + this.dbTableName + " WHERE ? ORDER BY IDX DESC";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, 1);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				memo.add(new Memo(resultSet.getInt("idx"), resultSet.getString("title"), resultSet.getString("contents")));
			}
			System.out.println(memo);
			resultSet.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return memo;
	}
	
	// selectDataOne
	public Memo selectDataOne(int idx) {
		Memo memo = null;
		this.open();
		try {
			String query = "SELECT * FROM " + this.dbTableName + " WHERE idx = ?;";
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, idx);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				memo = new Memo(resultSet.getInt("idx"), resultSet.getString("title"), resultSet.getString("contents"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.close();
		return memo;
	}
	
	// updateData
	public int updateData(String idx, String title, String contents) throws Exception {
		this.open();
		String query = "UPDATE " + this.dbTableName + " SET title = ?, contents = ? WHERE idx = ?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setString(1, title);
		preparedStatement.setString(2, contents);
		preparedStatement.setString(3, idx);
		int result = preparedStatement.executeUpdate();
		preparedStatement.close();
		
		this.close();
		return result;
	}
	
	// deleteData
	public int deleteData(int idx) throws Exception {
		this.open();
		
		String query = "DELETE FROM " + this.dbTableName + " WHERE idx = ?;";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		preparedStatement.setInt(1, idx);
		int result = preparedStatement.executeUpdate();
		preparedStatement.close();
		
		this.close();
		return result;
	}
	
	// deleteAllData
	public int deleteAllData() throws Exception {
		this.open();
		
		String query = "DELETE FROM " + this.dbTableName + ";";
		PreparedStatement preparedStatement = this.connection.prepareStatement(query);
		int result = preparedStatement.executeUpdate();
		preparedStatement.close();
		
		this.close();
		return result;
	}
	
}

