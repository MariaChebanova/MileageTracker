package test;

import database.DBConnection;

public class TestDatabase {

	public static void main (String[] args) {
		DBConnection dbc = new DBConnection();
		
		System.out.println(dbc.checkLogin("dzmuda", "Hello123"));
		System.out.println(dbc.checkLogin("none", "none"));
	}
}
