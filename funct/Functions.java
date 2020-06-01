package funct;
import progressbar.*;
import java.sql.*;

public class Functions
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";


	Connection conn = null;
	Statement stmt = null;

	private void main()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//progressbar.ProgressBar p = new progressbar.ProgressBar("Connecting to Database","Connected To Database");
			// System.out.println("Connecting to Database");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			// System.out.println("Checking for presence of database");	
		}
		catch(SQLException seex)
		{
			seex.printStackTrace();
			System.out.println("Functions");
		}
		catch(Exception except)
		{
			except.printStackTrace();
		}
	}

	//create database
	public void createDatabase(String dbname)
	{
		main();
		try
		{
			stmt = conn.createStatement();
			String db = "CREATE DATABASE "+dbname+";";
			progressbar.ProgressBar p = new progressbar.ProgressBar("Creating Database","Database Created");
			stmt.executeUpdate(db);
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}

	//create table
	public void createTable(String db_and_tablename,String col_names_and_attributes)
	{
		main();

		try
		{
			stmt = conn.createStatement();
			String ct = "CREATE TABLE "+db_and_tablename+" ("+col_names_and_attributes+");";
			// System.out.println(ct);
			progressbar.ProgressBar p = new progressbar.ProgressBar("Creating Table "+db_and_tablename,"Table Successfully Created");
			stmt.executeUpdate(ct);
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	//select query
	public void selectQuery(String dbname, String tablename, String colname)
	{
		main();

		try
		{
			stmt = conn.createStatement();
			String u = "SELECT "+colname+" FROM "+dbname+"."+tablename+";";
			ResultSet rs = stmt.executeQuery(u);
			while (rs.next())
			{
				String dbrs = rs.getString(colname);
			}
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	//insert query
	public void insertQuery(String dbname,String tablename, String colname, String values)
	{
		main();

		try
		{
			stmt = conn.createStatement();
			String i = "INSERT INTO "+dbname+"."+tablename+"("+colname+") VALUES ("+values+");"; 
			// System.out.println(i);
			stmt.executeUpdate(i);
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	//update query
	public void updateQuery(String dbname,String tablename, String colname, String value,String uniquecolname, String uniquecoldentifier)
	{
		main();

		try
		{
			stmt = conn.createStatement();
			String u = "UPDATE "+dbname+"."+tablename+" SET "+colname+" = '"+value+"' WHERE "+uniquecolname+" = '"+uniquecoldentifier+"';";
			// System.out.println(u);
			stmt.executeUpdate(u);
			// System.out.println("Updated");
		}
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public static void main(String args[])
	{
		// new Functions();
	}
}