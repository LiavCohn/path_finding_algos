import javax.swing.JFrame;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.awt.event.ItemEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.Timer;


public class DrawFrame extends JFrame implements ItemListener , ActionListener
{
	private JButton[] buttons; // the buttons itself
	
	//private JComboBox<String> colorJCB;

	private JComboBox<String> algoJCB;
	private static final String[] algoListNames={"BFS", "DFS", "A*"};//maybe to set it in the constructor
	private MyPanel dp;
	private JLabel labelCursor;
	private JPanel settingPanel;
	private Timer timer;
	private LocalDateTime startTime;
	private AlgoThread t = null;
	
	public DrawFrame()
	{
		super("Algorithm Visualizer");
		settingPanel = new JPanel();
		settingPanel.setLayout(new FlowLayout());
		
		String[] buttonsNames={"Play","Clear"}; // init the buttons name generically
		buttons = new JButton[buttonsNames.length]; // init the buttons the same size as name of buttons
		for(int i=0;i<buttonsNames.length;i++) // initialize the buttons with the names. 
			{
				buttons[i]=new JButton(buttonsNames[i]);
				buttons[i].addActionListener(this);
				settingPanel.add(buttons[i]);
			}
			
        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(startTime, now);
                labelCursor.setText(format(duration));
            }
        });
		 
		algoJCB= new JComboBox<String>(algoListNames);
		algoJCB.addItemListener(this);
		settingPanel.add(algoJCB);//add to panel
		
		add(settingPanel,BorderLayout.NORTH);
		labelCursor= new JLabel("00:00:00");
		add(labelCursor,BorderLayout.SOUTH);
		dp = new MyPanel(this,labelCursor,timer,startTime);
		add(dp,BorderLayout.CENTER);
		dp.setAlgo(algoListNames[algoJCB.getSelectedIndex()]);	
		

	}
	
	//write how many seconds has passed
    private String format(Duration duration) {
    	long hours = duration.toHours();
        long mins = duration.minusHours(hours).toMinutes();
        long seconds = duration.minusMinutes(mins).toMillis() / 1000;
        long millis = duration.minusMillis(seconds).toMillis()/100;
        return String.format("%02d:%02d:%02d", mins, seconds, millis);
    }
	
	public void itemStateChanged(ItemEvent e)
	{

		if(e.getSource()==algoJCB)
			dp.setAlgo(algoListNames[algoJCB.getSelectedIndex()]);
	}
	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource()==buttons[0])
		{
			startTime = LocalDateTime.now();
			timer.start();
			t = new AlgoThread(algoListNames[algoJCB.getSelectedIndex()], timer,startTime, dp);
			//t = new Thread(algoThread);
			t.start();
			//dp.play();			
			
		}	
		if(e.getSource()==buttons[1])
		{
			//dp.reset();
			if(t != null && t.isAlive())
			{
				System.out.println("Trying to interrupt...");
				t.setRunning(false);
				t.interrupt();
				
				dp.reset();
				t = null;
				System.out.println("Interrupted");
			}
				
		}
	}
}