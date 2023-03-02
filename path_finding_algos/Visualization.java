import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class Visualization {

		

	   public static void main(String[] args)
	   {
	  
		  DrawFrame application = new DrawFrame();      
	      application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      application.setSize(543, 500);
	      application.setVisible(true);
	      
	      application.setResizable(false);
	   } 


}
