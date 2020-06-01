package image;
import javax.swing.*;
import java.awt.*;

public class Logo
{
	//JFrame f = new JFrame();
	public Logo(JFrame frame)
	{
		ImageIcon img = new ImageIcon("Billing logo.jpg");
		frame.setIconImage(img.getImage());
	}

	public static void main(String[] args)
	{
	//	new Logo(f);
	}
}