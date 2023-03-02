import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.MouseEvent;

public class MyCube extends MyBoundedShape{

	public List<MyCube> neighbors;
	public MyCube parent;
	public int row;
	public int col;
	public int total_rows;
	public int g_score;
	public int f_score;
	public int h_score;
	
	//constructors
	public MyCube() {}
	public MyCube(int x1, int y1, int x2, int y2,Color color, boolean filled,int row,int col, int total_rows)
	 {
		super(x1, y1, x2, y2, color, filled);
		this.row = row;
		this.col = col;
		this.total_rows = total_rows;
		this.neighbors = new LinkedList<>();
	 } 
	public void setParent(MyCube parent)
	{
		this.parent = parent;
	}
	public MyCube getParent()
	{
		return this.parent;
	}
	
	public void draw(Graphics g) 
	{

		g.setColor(getColor());
		if(isFilled())
			g.fillRect(getUpperLeftX(), getUpperLeftY(),getWidth(), getHeight());
		else g.drawRect(getUpperLeftX(), getUpperLeftY(),getWidth(), getHeight());
		
	}
	public boolean isStart()
	{
		return this.getColor() == Color.GREEN;
	}
	public boolean isEnd()
	{
		return this.getColor() == Color.RED;
	}
	public boolean isBarrier()
	{
		return this.getColor() == Color.DARK_GRAY;
	}
	public boolean isVisited()
	{
		return this.getColor() == Color.CYAN;
	}
	public void makeStart()
	{
		this.setColor(Color.GREEN);
		this.setFilled(true);
	}
	public void makeEnd()
	{
		this.setColor(Color.RED);
		this.setFilled(true);
	}
	public void makePath()
	{
		this.setColor(Color.ORANGE);
		this.setFilled(true);
	}
	public void makeVisited()
	{
		this.setColor(Color.CYAN);
		this.setFilled(true);
	}
	public void makeBarrier()
	{
		this.setColor(Color.DARK_GRAY);
		this.setFilled(true);
	}
	public void reset()
	{
		this.setColor(Color.BLACK);
		this.setFilled(false);
	}
	public void updateNeighbors(MyCube[][] grid)
	{
		//down neighbor
		if(row < total_rows-1 && !(grid[row+1][col].isBarrier()))
		{
			neighbors.add(grid[row+1][col]);
		}
		//up neighbor
		if(row > 0 && !(grid[row-1][col].isBarrier()))
		{
			neighbors.add(grid[row-1][col]);
		}
		//left neighbor		
		if(col > 0 && !(grid[row][col-1].isBarrier()))
		{
			neighbors.add(grid[row][col-1])	;
		}
		//right neighbor
		if(col < total_rows-1 && !(grid[row][col+1].isBarrier()))
		{
			neighbors.add(grid[row][col+1]);
		}
		
	}
	public int calcH(MyCube end)
	{		
		return (Math.abs(row - end.row) + Math.abs(col - end.col));
	}

}
