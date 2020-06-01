package bill;
import image.*;
import l_a_f.*;
import funct.*;
import java.sql.*;
import java.util.regex.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class Billing implements ActionListener,KeyListener
{
	final String JDBC_DRIVER = "com.mysql.JDBC.Driver";
	final  String DB_URL = "jdbc:mysql://localhost/";

	final String USER = "root";
	String PASS = "";

	Connection conn = null;
	Statement stmt = null,stmt1 = null;

	funct.Functions function = new funct.Functions();

	JTextField one,two,four,six;
	JTextArea tt,big,gt,five;
	JButton b,clr,exit,save,logout;
	String fo;
	int add = 0,to,fou;
	boolean str = true,status = false;
	static JFrame f = new JFrame("Billing System");
	JMenuItem	newMenuItem,openMenuItem,saveMenuItem,printMenuItem,exitMenuItem,selMenuItem,aboutMenuItem,adminMenuItem;
	JFileChooser  fileDialog = new JFileChooser();
	java.io.File file;
	int returnVal,qty_collect,remaining_qty;
	char onekey;

	public Billing()
	{
	    JMenuBar menuBar = new JMenuBar();
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    JDialog.setDefaultLookAndFeelDecorated(true);


		f.setSize(900,380); // width and height
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		image.Logo image = new image.Logo(f);

		l_a_f.LookAndFeel laf = new l_a_f.LookAndFeel(f);

		// File Menu, F - Mnemonic
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		menuBar.add(editMenu);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		menuBar.add(helpMenu);

		// File->New, N - Mnemonic
	    newMenuItem = new JMenuItem("New           Alt + N", KeyEvent.VK_N);
	    fileMenu.add(newMenuItem);newMenuItem.addActionListener(this);

	    openMenuItem = new JMenuItem("Open            Alt + O", KeyEvent.VK_O);
	    fileMenu.add(openMenuItem);openMenuItem.addActionListener(this);

	    saveMenuItem = new JMenuItem("Save            Alt + S", KeyEvent.VK_S);
	    fileMenu.add(saveMenuItem);saveMenuItem.addActionListener(this);

	    adminMenuItem = new JMenuItem("Admin Console     Alt + M",KeyEvent.VK_M);
	    fileMenu.add(adminMenuItem);adminMenuItem.addActionListener(this);

	    printMenuItem = new JMenuItem("Print           Alt + P", KeyEvent.VK_P);
	    fileMenu.add(printMenuItem);printMenuItem.addActionListener(this);

	    exitMenuItem = new JMenuItem("Exit           Alt + F4", KeyEvent.VK_X);
	    fileMenu.add(exitMenuItem);exitMenuItem.addActionListener(this);


	    selMenuItem = new JMenuItem("Clear All         Alt + C", KeyEvent.VK_C);
	    editMenu.add(selMenuItem);selMenuItem.addActionListener(this);

	    aboutMenuItem = new JMenuItem("About The Developer         Alt + B", KeyEvent.VK_B);
	    helpMenu.add(aboutMenuItem);aboutMenuItem.addActionListener(this);

	     f.setJMenuBar(menuBar);

		JLabel  l1 = new JLabel("Product Name :");
		l1.setBounds(20,50,100,40);// x axis, y axis, width, height
		f.add(l1);

		one = new JTextField();
		one.setBounds(115,60,100,20);
		f.add(one);one.addKeyListener(this);

	    JLabel l2 = new JLabel("Cost :");
	    l2.setBounds(260,50,100,40);
		f.add(l2);

		two = new JTextField();
		two.setBounds(300,60,100,20);
		f.add(two);
		two.setEditable(false);

		JLabel l3 = new JLabel("Total Cost :");
		l3.setBounds(20,100,100,40);
		f.add(l3);

		tt = new JTextArea(15,15);
		tt.setBounds(115,110,100,20);
		f.add(tt);
		tt.setEditable(false);

		b = new JButton("Evaluate");
		b.setBounds(20,220,100,40);
		f.add(b);b.addActionListener(this);

		JLabel l4 = new JLabel("List :");
		l4.setBounds(500,100,100,40);
		f.add(l4);

		big = new JTextArea("Name : Price: Quantity\n",200,400);
		//f.add(big);
		big.setEditable(false);
		
		JScrollPane jsp = new JScrollPane(big);
		jsp.setBounds(540,100,300,200);
		jsp.setVisible(true);
		f.add(jsp);

		JLabel l5 = new JLabel("Ordered Quantity");
		l5.setBounds(680,50,100,40);
		f.add(l5);

		four = new JTextField();
		four.setBounds(780,60,100,20);
		f.add(four);four.addActionListener(this);

		clr = new JButton("Clear");
		clr.setBounds(240,220,100,40);
		f.add(clr);clr.addActionListener(this);

		JLabel l6 = new JLabel("Grand Total :");
		l6.setBounds(20,150,100,40);
		f.add(l6);

		gt = new JTextArea();
		gt.setBounds(115,160,100,20);
		f.add(gt);
		gt.setEditable(false);

		JLabel l7 = new JLabel("Product List :");
		l7.setBounds(640,100,100,20);
		//f.add(l7);


		five = new JTextArea(200,400);
		
		five.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(five);
		scroll.setBounds(730,100,300,200);
		scroll.setVisible(true);
		//f.add(scroll);

		JLabel l8 = new JLabel("Available Quanity");
		l8.setBounds(440,50,100,40);
		f.add(l8);

		six = new JTextField();
		six.setBounds(540,60,100,20);
		f.add(six);
		six.setEditable(false);
		six.addActionListener(this);

		try
		{

		Class.forName("com.mysql.jdbc.Driver");

		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		
		stmt = conn.createStatement();
		String empnamedb = "SELECT name FROM java.user;";
		ResultSet rs = stmt.executeQuery(empnamedb);;
		while(rs.next())
		{
	        //Retrieve by column name
	        String empname = rs.getString("name");
	        JLabel welcome = new JLabel("Welcome, "+empname);
			welcome.setBounds(740,0,200,40);
			f.add(welcome);

			logout = new JButton("Log Out");
			logout.setBounds(800,30,80,20);
			f.add(logout);logout.addActionListener(this);
	        //Display values
			
      	}
      	rs.close();
      }
		catch(SQLException se)
		{
      		//Handle errors for JDBC
      		JOptionPane.showMessageDialog(f,"Error\nBilling (220)");
      		System.out.println("Billing.java");
      		
   		}
   		catch(Exception ex)
   		{
      	//Handle errors for Class.forName
      	// JOptionPane.showMessageDialog(f,"Error 2");
   			JOptionPane.showMessageDialog(f,"Error\nBilling(228)");
   		}
   		finally
   		{
	      //finally block used to close resources
	      try
	      {
	         if(stmt!=null)
	            conn.close();
	      }
	      catch(SQLException se)
	      {
	      	JOptionPane.showMessageDialog(f,"Error\nBilling(240)");
	      }// do nothing
	      try
	      {
	         if(conn!=null)
	            conn.close();
	      }
	      catch(SQLException se)
	      {
	         JOptionPane.showMessageDialog(f,"Error\nBilling(249)");
	      }//end finally try
   		}

		

		
		f.setBackground(Color.black);
	    // f.getContentPane().setBackground( Color.black);
	    // l1.setForeground(Color.RED);
	    // l2.setForeground(Color.WHITE);
	    // l3.setForeground(Color.WHITE);
	    // l4.setForeground(Color.WHITE);
	    // l5.setForeground(Color.WHITE);
	    // l6.setForeground(Color.WHITE);


	    exit = new JButton("Exit");
	    exit.setBounds(350,220,100,40);
	    f.add(exit);exit.addActionListener(this);

	    save = new JButton("Save");
	    
	    save.setBounds(130,220,100,40);
	    f.add(save);save.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		try
		{

		Class.forName("com.mysql.jdbc.Driver");

		conn = DriverManager.getConnection(DB_URL, USER, PASS);

		five.setText(null);
		stmt = conn.createStatement();
		String prodname = "SELECT name FROM java.data;";
		ResultSet rs = stmt.executeQuery(prodname);;
		while(rs.next())
		{
	        //Retrieve by column name
	        String proddispname = rs.getString("name");

	        //Display values
			five.append("\t"+proddispname+"\n");
      	}
      	rs.close();
      }
		catch(SQLException se)
		{
      		//Handle errors for JDBC
      		JOptionPane.showMessageDialog(f,"Error 189");
      		
   		}
   		catch(Exception ex)
   		{
      	//Handle errors for Class.forName
      	// JOptionPane.showMessageDialog(f,"Error 2");
   			JOptionPane.showMessageDialog(f,"Error 196");
   		}
   		finally
   		{
	      //finally block used to close resources
	      try
	      {
	         if(stmt!=null)
	            conn.close();
	      }
	      catch(SQLException se)
	      {
	      	JOptionPane.showMessageDialog(f,"Error 208");
	      }// do nothing
	      try
	      {
	         if(conn!=null)
	            conn.close();
	      }
	      catch(SQLException se)
	      {
	         JOptionPane.showMessageDialog(f,"Error 217");
	      }//end finally try
   		}

   		
   		if (e.getSource() == logout)
   		{
   			int logconfo = JOptionPane.showConfirmDialog(f,"Do you wish to log out ?");
   			if (logconfo == JOptionPane.YES_OPTION)
				{
					//Code for Log out
					// String logoutpass = JOptionPane.showInputDialog(f,"Enter your password");
					// System.out.println(logoutpass);

					function.updateQuery("java","user","IsActive","0","password","user@123");
					//user.User loginagain = new user.User();
					JOptionPane.showMessageDialog(f,"Logout Sucessfull");
					System.exit(0);

				}
			else
			{
				//Do Nothing
			}
   		}
		// Code for Number Validation	
		
		// Code for evaluation
		if (e.getSource() == b || e.getSource() == six)
		{
			NumberValidation();
			Evaluate();
			
			sql.Sql sq = new sql.Sql();

			//funct.Functions ins = new funct.Functions("java","data","name,price,purchased_quantity","'"+one.getText()+"','"+two.getText()+"','"+four.getText()+"'");
		}

		// On the press of the Save button
		if (e.getSource() == save)
		{
			FileIO();
		}


		if (e.getSource() == clr)
		{
			ClearAll();	
		}

		if (e.getSource() == exit)
		{
			System.exit(0);
		}

		// Menu Bar Actions

		if (e.getSource() == openMenuItem)
		{
			returnVal = fileDialog.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION)
            {
               File selectedFile = fileDialog.getSelectedFile();
		       JOptionPane.showMessageDialog(f,"File selected "+selectedFile.getName());
		       // one.setText(fileDialog.getCurrentDirectory().toString());
            }
		}

		if (e.getSource() == newMenuItem)
		{
			new Billing();
		}

		if (e.getSource() == adminMenuItem)
		{
			admin.Admin adm = new admin.Admin();
		}

		if(e.getSource() == exitMenuItem)
		{
			System.exit(0);
		}

		if (e.getSource() == saveMenuItem)
		{
			JOptionPane.showMessageDialog(f,"Error\nMenu Item Not Enabled\nContact the developer for more details");
		}

		if (e.getSource() == printMenuItem)
		{
			JOptionPane.showMessageDialog(f,"Error\nMenu Item Not Enabled\nContact the developer for more details");
		}

		if (e.getSource() == selMenuItem)
		{
			one.setText(null);
			two.setText(null);
			four.setText(null);
			tt.setText(null);
			gt.setText(null);
			six.setText(null);
			big.setText("Name : Price: Quantity\n");
		}

		if (e.getSource() == aboutMenuItem)
		{
			JOptionPane.showMessageDialog(f,"Error\nMenu Item Not Enabled\nContact the developer for more details");	
		}

		
	}

	public void keyTyped(KeyEvent k)
	{
		
	}

	public void keyReleased(KeyEvent k)
	{
		//System.out.println(k);

		for (int i=0;i < one.getText().length();i++)
		{
			onekey =k.getKeyChar();
		}
			function.selectQuery("java","data WHERE name like '"+onekey+"%'","name");
			Main();
			try
			{
				System.out.println("Creating statement...");
				stmt = conn.createStatement();

				String sql = "SELECT * FROM java.data having(name) like '"+onekey+"%'";
				ResultSet rs = stmt.executeQuery(sql);
				//STEP 5: Extract data from result set
				while(rs.next())
				{
					 //Retrieve by column name
					 String name = rs.getString("name");
					 String price = rs.getString("price");
					 String available_qty = rs.getString("available_qty");

					 //Display values
					 one.setText(""+name);
					 two.setText(""+price);
					 six.setText(""+available_qty);
					 break;

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
		
			//System.out.println(keyev);
	}
	public void keyPressed(KeyEvent k)
	{
		
	}

	public static void main(String args[])
	{
		new Billing();
	}

	void ClearAll()
	{
		one.setText(null);
		two.setText(null);
		four.setText(null);
		six.setText(null);
	//S	tt.setText(null);
	//	gt.setText(null);
	//	big.setText("Name : Price: Quantity\n");	
	}

	void FileIO()
	{
		try
			{
				int output = JOptionPane.showConfirmDialog(f,"Confirm Box","Do you wish to save the file ?",JOptionPane.YES_NO_OPTION);
				if (output == JOptionPane.YES_OPTION)
				{
				
					// Save the File 	
					FileWriter fw = new FileWriter("Bill Data.txt");
					big.write(fw);
					fw.close();	
					JOptionPane.showMessageDialog(f,"File Saved");


					//Display the content that has been saved as a message box
					try
					{
						FileReader fr = new FileReader("Bill Data.txt");
						int character;				
						
						while ((character = fr.read()) != -1)
						{
							System.out.print((char) character);
						}
						JOptionPane.showMessageDialog(f,big.getText());
						fr.close();				
					}
					
					catch(IOException exc)
					{
						exc.printStackTrace();
					}
				}
			else
			{
				JOptionPane.showMessageDialog(f,"File not saved");
			}
		}
		catch(Exception ex)
		{
			System.out.println(ex);
			JOptionPane.showMessageDialog(f,"\tError\n"+ex);
		}
	}

	void NumberValidation()
	{
		try
		{
			fo = one.getText();
			to = Integer.parseInt(two.getText());
			fou = Integer.parseInt(four.getText());
		}
		catch(NumberFormatException nfe)
		{
			JOptionPane.showMessageDialog(f,"Check Your Input");
		}
	}

	void StringValidation()
	{
		if (!(one.getText().matches("[a-zA-Z]+")))
		{
			JOptionPane.showMessageDialog(f,"Error");
		}
	}

	void Evaluate()
	{
					
		tt.setText(null);
		tt.append(""+(to*fou));
		big.append("\n"+fo+" : "+to+" : "+fou);

		add += Integer.parseInt(tt.getText());
		gt.setText(null);
		gt.append(""+add);

		Main();
		try
		{
			// System.out.println("Creating statement...");
			stmt = conn.createStatement();

			String sql = "SELECT * FROM java.data WHERE(name) = '"+one.getText()+"'";
			ResultSet rs = stmt.executeQuery(sql);
			//STEP 5: Extract data from result set
			while(rs.next())
			{
				 //Retrieve by column name
				 String name = rs.getString("name");
				 String price = rs.getString("price");
				 int available_qty = rs.getInt("available_qty");

				 //Display values
				 // one.setText(""+name);
				 // two.setText(""+price);
				 // six.setText(""+available_qty);
				 qty_collect = Integer.parseInt(four.getText());
				 // JOptionPane.showMessageDialog(f,""+qty_collect);
				 remaining_qty=available_qty - qty_collect;

				 if (remaining_qty <= 0)
				 {

				 	int alert = JOptionPane.showConfirmDialog(f,"You can order only order "+available_qty+". Do you wish to continue?");
		   			if (alert == JOptionPane.YES_OPTION)
					{

						function.updateQuery("java","data","available_qty",""+(available_qty),"name",""+name);
						six.setText("0");
						four.setText(""+available_qty);

						JOptionPane.showMessageDialog(f,"Quantity of "+name+" needs to be updated");
					 	function.updateQuery("java","data","available_qty","0","name",""+name);
					 	admin.Admin log = new admin.Admin();

					}
					else
					{
						// do nothing
					}
				 	six.setText("0");
				 }
				 else
				 {
					 function.updateQuery("java","data","available_qty",""+(remaining_qty),"name",""+name);
					 six.setText(""+remaining_qty) ;
				 }
				 if (available_qty <= 0)
				 {
				 	one.setText(null);
					two.setText(null);
					four.setText(null);
					tt.setText(null);
					gt.setText(null);
					six.setText(null);
					big.setText("Name : Price: Quantity\n");
				 	f.setVisible(false);
				 	JOptionPane.showMessageDialog(f,"Quantity of "+name+" needs to be updated");
				 	function.updateQuery("java","data","available_qty","0","name",""+name);
				 	admin.Admin log = new admin.Admin();
				 }
				 break;

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
	
		//System.out.println(keyev);
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
}