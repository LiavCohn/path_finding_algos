import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.Timer;

public class MyPanel extends JPanel{
	
	public MyCube[][] grid = null;
	public int width= 550;
	public int rows = 35;
	public int GAP = width/rows;
	private JFrame parentWindow;
	public static MyCube start;
	public static MyCube end;
	public static String algo;
	private JLabel timerLabel;
	private static Timer timer;
	private LocalDateTime startTime;
	public static volatile boolean flag = true;
	
	
	public MyPanel(JFrame parentWindow,JLabel timerLabel, Timer timer,LocalDateTime startTime)
	{
		this.parentWindow = parentWindow;
		this.timerLabel = timerLabel;
		this.timer = timer;
		this.startTime = startTime;
		setBackground(Color.WHITE);
		
		
		this.addMouseListener(new MouseAdapter() 
		{
		    @Override
		    public void mouseClicked(MouseEvent e) 
		    {
		        int x = e.getX();
		        int y = e.getY();
		        int row = y/GAP;
		        int col = x/GAP;
		        //System.out.println("row- "+row+" col- "+col);
		        MyCube node = MyPanel.this.grid[row][col];
		        
		        if(start == null && node != end)
		        {
		        	start = node;
		        	start.makeStart();
		        	repaint();
		        }
		        else if(end == null && node != start)
		        {

		        	end = node;
		        	end.makeEnd();
		        	repaint();
		        }
		        else if(node != end && node !=start)
		        {
		        	node.makeBarrier();
		        	repaint();
		        }
		        		        
		    }

		});
	}
	public MyPanel() {} 
	
    
	  @Override
	public void paintComponent(Graphics g)
	   {
	      super.paintComponent(g);
	      if(grid == null)
	      {
	    	  this.grid = new MyCube[rows][rows];
		      for(int i=0; i<rows; i++)
		      {
		    	  for(int j=0; j<rows; j++)
		    	  {
		    		  grid[i][j] = new MyCube(j*GAP,i*GAP,(j+1)*GAP,(i+1)*GAP,Color.BLACK,false,i,j,rows);
		    		  grid[i][j].draw(g);
		    	  }
		    	  
		      }
	      }
	      else
	      {
		      for(int i=0; i<rows; i++)
		      {
		    	  for(int j=0; j<rows; j++)
		    	  {
		    		  grid[i][j].draw(g);
		    	  }
		    	  
		      }
	      }


	   }
		   
	public void setAlgo(String s)
	{
		if(s.equals("BFS"))
			algo = "BFS";
		
		else if(s.equals("DFS"))
			algo = "DFS";
	}

	public void reset() 
	{
		timer.stop();
		start = null;
		end = null;
		grid = null;
		startTime = null;
		timerLabel.setText("00:00:00");
		flag = false;
				
		repaint();
	}

	private void stopTimer()
	{
		
		timer.stop();
		startTime = null;
	}
	
}

