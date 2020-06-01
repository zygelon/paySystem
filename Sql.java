package sql;
import image.*;
import l_a_f.*;
import progressbar.*;
import funct.*;
import java.sql.*;
import javax.swing.*;
public class Sql
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";


	Connection conn = null;
	Statement stmt = null;
	boolean status = false;

	funct.Functions function =  new funct.Functions();
	
	JFrame frame = new JFrame();
	l_a_f.LookAndFeel laf = new l_a_f.LookAndFeel(frame);
	image.Logo image = new image.Logo(frame);


	void Main()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			//progressbar.ProgressBar p1 = new progressbar.ProgressBar("Connecting to Database","Connected To Database");
			// System.out.println("Connecting to Database");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			//progressbar.ProgressBar p2 = new progressbar.ProgressBar("Checking For Presence Of Database","Database Found");
			// System.out.println("Checking for presence of database");
			progressbar.ProgressBar p1 = new progressbar.ProgressBar("Connecting to Database","Connected To Database");

		}
		catch(SQLException seex)
		{
			seex.printStackTrace();
			JOptionPane.showMessageDialog(frame,"Error SQL(46)\nStart Xampp\nIf Problem Persists Please Contact Developer");
		}
		catch(Exception except)
		{
			except.printStackTrace();
			JOptionPane.showMessageDialog(frame,"Error SQL(51)\nStart Xampp\nIf Problem Persists Please Contact Developer");
		}
	}
	
	public Sql()
	{
		try
			{	
				Main();
      			stmt = conn.createStatement();
  				String sql = "show databases like 'java'";

		      	ResultSet rs = stmt.executeQuery(sql);
		      while (rs.next())
	      		{
		      		//System.out.println("Db Found");
		      		
		      		stmt = conn.createStatement();
			      	String usedb = "USE java";

			      	stmt.executeUpdate(usedb);


		      		status = true;
	      		}
		      	
		      	if (status == false)
		      	{
		      		JOptionPane.showMessageDialog(frame,"Database Not Found !!!");

		      		// create database
					function.createDatabase("java");

			      	stmt = conn.createStatement();
			      	String usedb = "USE java;";
			      	stmt.executeUpdate(usedb);
			      	
			      	//create data table
					function.createTable("java.data"," `table_id` INT NOT NULL DEFAULT '1' , `id` INT NOT NULL AUTO_INCREMENT , `name` VARCHAR(100) NOT NULL , `price` INT NOT NULL , `purchased_quantity` INT NOT NULL , PRIMARY KEY (`id`)");

					//create admin table
					function.createTable("java.admin"," `table_id` INT NOT NULL DEFAULT '1' , `id` INT NOT NULL AUTO_INCREMENT , `username` VARCHAR(100) NOT NULL , `password` VARCHAR(100) NOT NULL , PRIMARY KEY (`id`)");

					//insert into admin table defualt values
					function.insertQuery("java","admin","`table_id`, `id`, `username`, `password`","0, 1, 'admin', 'admin@123'");

					//create user table
					function.createTable("java.user"," `table_id` INT NOT NULL DEFAULT '1' , `id` INT NOT NULL AUTO_INCREMENT , `username` VARCHAR(100) NOT NULL , `password` VARCHAR(100) NOT NULL , PRIMARY KEY (`id`)");

					// insert into user table defualt values
			      	function.insertQuery("java","user","`table_id`, `id`, `username`, `password`","0, 1, 'user', 'user@123'");

			      	stmt = conn.createStatement();
			      	String alteruser1 = "ALTER TABLE `user` ADD `IsActive` VARCHAR(1) NOT NULL AFTER `password`;";
			      	stmt.executeUpdate(alteruser1);

			      	stmt = conn.createStatement();
			      	String alteruser2 = "ALTER TABLE `user` ADD `name` VARCHAR(100) NOT NULL AFTER `id`;";
			      	stmt.executeUpdate(alteruser2);

			      	stmt = conn.createStatement();
			      	String update2 = "UPDATE java.user SET name = 'Jayashankar Jayan', IsActive = '0' WHERE id = 1;";
			      	stmt.executeUpdate(update2);

		      	}
			}

			catch(SQLException se)
			{
				JOptionPane.showMessageDialog(frame,"Error SQL(115)\nStart Xampp\nIf Problem Persists Please Contact Developer");
				se.printStackTrace();
				System.exit(0);
			}
			catch(Exception exce)
			{
				JOptionPane.showMessageDialog(frame,"Error SQL(121)\nIf Problem Persists Please Contact Developer");
				exce.printStackTrace();
				System.exit(0);
			}

		finally	
		{
			try
		    {
		        if(stmt!=null)
		        {
		            conn.close();
		        }
		    }
		    catch(SQLException se)
		    {
			    try
			    {
			    	if(conn!=null)
			    	{
			            conn.close();
			    	}
			    }
			    catch(SQLException s)
			    {
			        s.printStackTrace();
			        JOptionPane.showMessageDialog(frame,"Error SQL(150)\nIf Problem Persists Please Contact Developer");
			    }		      
			}
			// System.out.println("Goodbye!");
		}		
	}


	public static void main(String[] args)
	{
		new Sql();
	}
}