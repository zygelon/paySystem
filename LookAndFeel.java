package l_a_f;
import javax.swing.*;

public class LookAndFeel
{
	//static JFrame f = new JFrame();
	public LookAndFeel(JFrame framename)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException c)
		{

		}
		catch(InstantiationException c)
		{

		}
		catch(IllegalAccessException c)
		{

		}
		catch(UnsupportedLookAndFeelException c)
		{

		}
		SwingUtilities.updateComponentTreeUI(framename);

	}
		public static void main(String args[])
		{
			//new LookAndFeel();
		}
}	