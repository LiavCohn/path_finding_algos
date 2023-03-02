import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import javax.swing.Timer;

public class AlgoThread extends Thread
{
	MyPanel panel;
	String algo;
	Timer timer;
	LocalDateTime startTime;
	public volatile  boolean running;
	
	public AlgoThread(String algo , Timer timer,LocalDateTime startTime, MyPanel panel)
	{
		this.panel = panel;
		this.algo = algo;
		this.timer = timer;
		this.startTime = startTime;
		this.running = true;
	}

	@Override
	public void run()
	{
		
		
		if(algo.equals("BFS"))
			bfs();
		
		if(algo.equals("DFS"))
			dfs();
		
		if(algo.equals("A*"))
			aStar();
		
		
	}
	public void setRunning(boolean isRunning)
	{
		this.running = isRunning;
	}
	public void aStar()
	{
		PriorityQueue<MyCube> open = new PriorityQueue<>(new NodeComparator());
		PriorityQueue<MyCube> close = new PriorityQueue<>(new NodeComparator());
		
		if(panel.start != null)
		{
			panel.start.f_score = panel.start.g_score + panel.start.calcH(panel.end);
			open.add(panel.start);
		}
		else return;
			
		
		while(!open.isEmpty())
		{
			MyCube curr = open.poll();
			if(curr == panel.end)
			{
				reconstruct(curr);
	        	return;
			}
			curr.updateNeighbors(panel.grid);
			
			//iterate neighbors
			for(MyCube neighbor : curr.neighbors)
			{
				int weight = curr.g_score + 1;
				int h = neighbor.calcH(panel.end);
				if(!open.contains(neighbor) && !close.contains(neighbor))
				{
					if(neighbor.parent == null)
		        		neighbor.parent = curr;
					if(neighbor == panel.start)
						continue;
		        	if(neighbor == panel.end)
		        	{
		        		reconstruct(neighbor); 
		        		return;
		        	}
					
					neighbor.parent = curr;
					neighbor.g_score = weight;
					neighbor.f_score = weight + h;
					open.add(neighbor);
		        	neighbor.makeVisited();
		        	panel.repaint();
		        		
		        	try {
							Thread.sleep(80);
					} catch (InterruptedException e) 
		        	{
						Thread.currentThread().interrupt();
						// TODO Auto-generated catch block
						//e.printStackTrace();
						return;
						
						
					}
				}
				else
				{
					if(weight + h < neighbor.f_score)
					{
						neighbor.parent = curr;
						neighbor.g_score = curr.g_score + 1;
						neighbor.f_score = neighbor.g_score + neighbor.calcH(panel.end);
						if(close.contains(neighbor))
						{
							open.add(neighbor);
							close.remove(neighbor);
						}
					}
				}
			}
			open.remove(curr);
			close.add(curr);
		}
	}
	public void bfs()
	{	
		System.out.println("In Bfs algo");
	    Queue<MyCube> q = new LinkedList<>();
	    if(panel.start != null)
	    	q.add(panel.start);
	        
	    else return;
	        
	                
	    while(!(q.isEmpty()) && (!Thread.currentThread().isInterrupted()))
	    {
	
	        	//System.out.println(q.size());
	    	MyCube curr = q.poll();
	        	
	        curr.updateNeighbors(panel.grid);
	        	
	        if(curr == panel.end)
	        {
	        	reconstruct(curr);
	        	return;
	        }
	        	
	        for(MyCube neighbor : curr.neighbors)
	        {
	        	if(neighbor.parent == null)
	        		neighbor.parent = curr;
	        	if (neighbor.isVisited())
	        		continue;
	        	if(neighbor == panel.start)
	        		continue;
	        		
	        	if(neighbor == panel.end)
	        	{
	        		reconstruct(neighbor); 
	        		return;
	        	}
	        		 		
	        		
	        	neighbor.makeVisited();
	        	panel.repaint();
	        		
	        	try {
						Thread.sleep(80);
				} catch (InterruptedException e) 
	        	{
					Thread.currentThread().interrupt();
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return;
					
					
				}
	        	q.add(neighbor);
	
	        }
	     }	       
	}
	
	public void dfs() 
	{
		System.out.println("In Dfs algo");
	    Stack<MyCube> s = new Stack<>();
	    if(panel.start != null)
	    	s.push(panel.start);
	        
	    else return;
	    
	    while(!s.isEmpty() && (!Thread.currentThread().isInterrupted()))
	    {
	    	MyCube curr = s.pop();
	    	curr.updateNeighbors(panel.grid);
	    	
	        if(curr == panel.end)
	        {
	        	reconstruct(curr);
	        	return;
	        }
	        
	        for(MyCube neighbor : curr.neighbors)
	        {
	        	if(neighbor.parent == null)
	        		neighbor.parent = curr;
	        	

	        	
	        	if(neighbor == panel.start)
	        		continue;
	        		
	        	if(neighbor == panel.end)
	        	{
	        		reconstruct(neighbor); 
	        		return;
	        	}
	        		 		
	        	if (neighbor.isVisited())
	        		continue;	
	        	
	        	else
	        	{
	        		neighbor.makeVisited();
	        		panel.repaint();
	        		s.push(neighbor);
	        	}
	        		
	        	try {
						Thread.sleep(80);
				} catch (InterruptedException e) 
	        	{
					Thread.currentThread().interrupt();
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return;
					
					
				}	
	        }
	        
	    }
	}
	
	public void reconstruct(MyCube curr)
	{
		curr = curr.parent;
		
		while(curr != panel.start)
		{
			curr.makePath();
			panel.repaint();
    		try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				//e.printStackTrace();
				return;
			}
			curr = curr.parent;
		}
		stopTimer();
		System.out.println("Done!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			//e.printStackTrace();
			//Thread.currentThread().interrupt();
			return;
		}
		
		return;
	}
	private void stopTimer()
	{
		
		timer.stop();
		startTime = null;
	}
}
