package user;
import image.*;
import l_a_f.*;
import sql.*;
import bill.*;
import java.sql.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class User implements ActionListener
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "";


	Connection conn = null;
	Statement stmt = null;
	boolean status = false;

	funct.Functions function = new funct.Functions();	

	JFrame aconsole = new JFrame("Employee Login");
	JTextField uname;
	static JPasswordField pass; 
	JButton ab;
	boolean logged_in = false;

	public User()
	{

		sql.Sql database = new sql.Sql();
		LoginCheck();
		
		aconsole.setVisible(true);
		aconsole.setSize(250,190);
		aconsole.setLocationRelativeTo(null);
		aconsole.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aconsole.setLayout(null);
		l_a_f.LookAndFeel laf = new l_a_f.LookAndFeel(aconsole);
		SessionCheck();
		image.Logo image = new image.Logo(aconsole);

		aconsole.setResizable(false);
		
		JLabel al1 = new JLabel("Username");
		al1.setBounds(20,20,100,20);// x axis, y axis, width, height
		aconsole.add(al1);

		uname = new JTextField();
		uname.setBounds(85,21,100,20);
		aconsole.add(uname);

		JLabel al2 = new JLabel("Password");
		al2.setBounds(20,60,100,20);
		aconsole.add(al2);

		pass = new JPasswordField();
		pass.setBounds(85,61,100,20);
		aconsole.add(pass);pass.addActionListener(this);

		ab = new JButton("Sign In");
		ab.setBounds(60,101,100,40);
		aconsole.add(ab);ab.addActionListener(this);

		aconsole.setBackground(Color.black);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if (ae.getSource() == ab || ae.getSource() == pass)
		{
			try
			{
				Main();
				stmt = conn.createStatement();

				String sql = "SELECT username,password FROM java.user";
				ResultSet rs = stmt.executeQuery(sql);
				//STEP 5: Extract data from result set
				
				while(rs.next())
				{
					//Retrieve by column name
					String username = rs.getString("username");
					String pas = rs.getString("password");
					char[] password = pas.toCharArray();

					String pass2 = new String (pass.getPassword());

					//Display values
					// System.out.print("Name\n" + name+"\n");
					if ((String.valueOf(uname.getText()).equals(username)))
					{
						if (pas.equals(pass2))
						{
							JOptionPane.showMessageDialog(aconsole,"Log In Successful");
							bill.Billing bill = new bill.Billing();
							function.updateQuery("java","user","IsActive","1","password","user@123");
							aconsole.setVisible(false);
						}
						else
						{
							JOptionPane.showMessageDialog(aconsole,"Wrong Password");
						}
					}
					else
					{
						JOptionPane.showMessageDialog(aconsole,"Wrong Username");
					}
				}
				rs.close();
		   	}
		   
		   catch(SQLException se)
		   {
				//Handle errors for JDBC
				se.printStackTrace();
		   }

		   catch(Exception e)
		   {
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }

		   finally
		   {
		      //finally block used to close resources
		      try
		      {
				if(stmt!=null)
				{
					conn.close();		         	
				}
		      }

		      catch(SQLException se)
		      {
		      	// do nothing
		      }
		      
		      try
		      {
		         if(conn!=null)
		            conn.close();
		      }
		      catch(SQLException se)
		      {
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   // System.out.println("Goodbye!");
		}
	}

	void Main()
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver");

		// System.out.println("Connecting to Database");
		conn = DriverManager.getConnection(DB_URL,USER,PASS);

		// System.out.println("Checking for presence of database");	
		}
		catch(SQLException seex)
		{

		}
		catch(Exception except)
		{

		}
	}

	void SessionCheck()
	{
		
	try
		{
			Main();

			stmt = conn.createStatement();

			String isactive = "SELECT isActive from java.user";

			ResultSet r = stmt.executeQuery(isactive);

			while (r.next())
			{
				int res = r.getInt("IsActive");
				if (res == 1)
				{
					aconsole.setVisible(false);
					bill.Billing bill = new bill.Billing();
					JOptionPane.showMessageDialog(aconsole,"User Already Active");
				}
				else
				{
					JOptionPane.showMessageDialog(aconsole,"You need to Log In");
				}
			}
		}
		catch(SQLException sex)
		{

		}
		catch(Exception excep)
		{

		}
	
	}

	void LoginCheck()
	{
		main();
		try
		{
			stmt = conn.createStatement();
			String u = "SELECT * FROM java.user;";
			ResultSet rs = stmt.executeQuery(u);
			while (rs.next())
			{
				String isActive = rs.getString("isActive");
				
				if (isActive == "1")
				{
					bill.Billing bill = new bill.Billing();
				}
				else if (isActive == "0")
				{
					JOptionPane.showMessageDialog(aconsole,"Login Required");
					// function.updateQuer	y("java","user","IsActive","1","password","user@123");
					new User();
				}
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

		// System.out.println("User.java\nUpdate Value\t"+String.valueOf(select));
	}

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
	public static void main(String args[])
	{
		new User();
	}
}