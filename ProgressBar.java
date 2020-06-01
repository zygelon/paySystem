package progressbar;
import image.*;
import l_a_f.*;
import javax.swing.border.Border;
import java.awt.*;
import javax.swing.*;

public class ProgressBar
{
	int num=0,max=100;
	JFrame f;
	JProgressBar progressBar;
	Container content;
	JPanel pane = new JPanel();

	public ProgressBar(String progress_name,String showMessage)
	{
		f = new JFrame(progress_name);
		f.setSize(400,75);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setLayout(null);
		f.setVisible(true);
		l_a_f.LookAndFeel laf = new l_a_f.LookAndFeel(f);
		//content = f.getContentPane();
		image.Logo image = new image.Logo(f);



		progressBar = new JProgressBar(0,2000);
		pane.add(progressBar);
		progressBar.setStringPainted(true);
		progressBar.setIndeterminate(false);
		f.setContentPane(pane);
		Iterate();
		JOptionPane.showMessageDialog(f,""+showMessage);
		f.setVisible(false);
	}

	public void Iterate()
	{
		while (num < 2000)
		{
			progressBar.setValue(num);
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException ie)
			{
				JOptionPane.showMessageDialog(f,"Progress Interrupted");
			}
			num += 95;

		}
	}

	public static void main(String args[])
	{
		// new ProgressBar("Connecting To Database","Connected To Database");
	}
}