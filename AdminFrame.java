package adminFrame;
import image.*;
import java.sql.*;
import funct.*;
import l_a_f.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminFrame implements ActionListener,KeyListener
{
	final String JDBC_DRIVER = "com.mysql.JDBC.Driver";
	final  String DB_URL = "jdbc:mysql://localhost/";

	final String USER = "root";
	String PASS = "";

	Connection conn = null;
	Statement stmt = null,stmt1 = null;

	funct.Functions function = new funct.Functions();

	JFrame aframe = new JFrame("Admin Console");
	JTextField one,two,three,four,five,six;
	JRadioButton addc,updac;
	JButton btn,exit;
	int qty_collect,remaining_qty;
	char onekey;
	String name;

	public AdminFrame()
	{
		aframe.setSize(1050,250);
		aframe.setVisible(true);
		aframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//aframe.setResizable(false);
		aframe.setLocationRelativeTo(null);
		aframe.setLayout(null);
		image.Logo image = new image.Logo(aframe);
		l_a_f.LookAndFeel laf = new l_a_f.LookAndFeel(aframe);

		JLabel l1 = new JLabel("Add Product Name");
		l1.setBounds(20,20,120,20);
		aframe.add(l1);

		one = new JTextField();
		one.setBounds(140,20,200,20);
		aframe.add(one);

		JLabel l2 = new JLabel("|        Add Product Price");
		l2.setBounds(360,20,140,20);
		aframe.add(l2);

		two = new JTextField();
		two.setBounds(500,20,100,20);
		aframe.add(two);

		JLabel l3 = new JLabel("|        Add Product Quantity");
		l3.setBounds(620,20,160,20);
		aframe.add(l3);

		three = new JTextField();
		three.setBounds(780,20,100,20);
		aframe.add(three);three.addActionListener(this);

		JLabel l4 = new JLabel("Select Product Name");
		l4.setBounds(20,80,160,20);
		aframe.add(l4);

		four = new JTextField();
		four.setBounds(140,80,200,20);
		aframe.add(four);four.addKeyListener(this);
		four.setEditable(false);

		JLabel l5 = new JLabel("|        Update Product Price");
		l5.setBounds(380,80,200,20);
		aframe.add(l5);

		five = new JTextField();
		five.setBounds(540,80,100,20);
		aframe.add(five);
		five.setEditable(false);

		JLabel l6 = new JLabel("|        Update Product Quantity");
		l6.setBounds(660,80,180,20);
		aframe.add(l6);

		six = new JTextField();
		six.setBounds(840,80,100,20);
		aframe.add(six);
		six.setEditable(false);six.addActionListener(this);

		addc = new JRadioButton("Add New Product");
		addc.setBounds(120,160,140,20);addc.addActionListener(this);


		updac = new JRadioButton("Update Product Information");
		updac.setBounds(300,160,200,20);


		ButtonGroup radiobtn = new ButtonGroup();
		radiobtn.add(addc);radiobtn.add(updac);updac.addActionListener(this);

		aframe.add(addc);aframe.add(updac);

		btn = new JButton("Proceed");
		btn.setBounds(550,150,100,40);
		aframe.add(btn);btn.addActionListener(this);
		aframe.setBackground(Color.black);

		exit = new JButton("Exit");
		exit.setBounds(720,150,100,40);
		aframe.add(exit);exit.addActionListener(this);
	}

	public void actionPerformed(ActionEvent af)
	{
		if (addc.isSelected())
		{
			one.setEditable(true);
			two.setEditable(true);
			three.setEditable(true);

			four.setEditable(false);
			five.setEditable(false);
			six.setEditable(false);
		}

		if (updac.isSelected())
		{
			one.setEditable(false);
			two.setEditable(false);
			three.setEditable(false);

			four.setEditable(true);
			// five.setEditable(true);
			six.setEditable(true);
		}

		if (af.getSource() == btn || af.getSource() == three)
		{
			if (addc.isSelected())
			{
				function.insertQuery("java","data","name,price,available_qty,purchased_quantity","'"+one.getText()+"','"+two.getText()+"',"+three.getText()+",'0'");

				one.setText(null);
				two.setText(null);
				three.setText(null);
			}

			if (updac.isSelected())
			{
				System.out.println(six.getText());
				 function.updateQuery("java","data","available_qty",""+(six.getText()),"name",""+name);

				four.setText(null);
				five.setText(null);
				six.setText(null);
			}
			
			else
			{
				function.insertQuery("java","data","name,price,available_qty,purchased_quantity","'"+one.getText()+"','"+two.getText()+"',"+three.getText()+",'0'");

				one.setText(null);
				two.setText(null);
				three.setText(null);
			}
		}

		if (af.getSource() == exit)
		{
			System.exit(0);
		}
	}

	public void keyTyped(KeyEvent k)
	{
		
	}

	public void keyReleased(KeyEvent k)
	{
		//System.out.println(k);
		onekey = k.getKeyChar();
		// for (int i=0;i < four.getText().length();i++)
		// {
		// 	onekey = one.getText().charAt(i);	
		// }
			// //funct.Functions keyev = new funct.Functions("java","data WHERE name like '"+onekey+"%'","name");
			Main();
			try
			{
				stmt = conn.createStatement();

				String sql = "SELECT * FROM java.data having(name) like '"+onekey+"%'";
				ResultSet rs = stmt.executeQuery(sql);
				//STEP 5: Extract data from result set
				while(rs.next())
				{
					 //Retrieve by column name
					 name = rs.getString("name");
					 String price = rs.getString("price");
					 String available_qty = rs.getString("available_qty");

					 //Display values
					 four.setText(""+name);
					 five.setText(""+price);
					 six.setText(""+available_qty);


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
		
			// System.out.println(keyev);
		}

		public void keyPressed(KeyEvent k)
		{
			
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
				System.out.println("Error AdminFrame (239)\nStart Xampp\nIf Problem Persists Please Contact Developer");
				seex.printStackTrace();
			}
			catch(Exception except)
			{
				System.out.println("Error AdminFrame (244)\nStart Xampp\nIf Problem Persists Please Contact Developer");
				except.printStackTrace();
			}
		}

	public static void main (String[] args)
	{
			new AdminFrame();
	}
}