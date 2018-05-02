/**
 * DeadPixels - the file that makes it look like you have dead pixels
 * As a bonus, hide the script, make a shortcut to it, change the icon to the IE icon and the name to 'Internet Explorer'
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import static java.awt.GraphicsDevice.WindowTranslucency.*;
import java.util.HashSet;
import java.lang.Math;
import java.awt.Color;

//main class
public class DeadPixel extends JFrame {
	private static final long serialVersionUID = 1L;
	
	//constructor for a point
	//point = location for pixel
	public DeadPixel(Point xy) {
        super("ShapedWindow");
		inst((int)xy.getX(),(int)xy.getY());
	}

	//constructor for coordinates
	//x = x position
	//y = y position
	public DeadPixel(int x, int y) {
        super("ShapedWindow");
		inst(x,y);
	}
		
	//instantiate the optionsssss
	//x = x position
	//y = y position
	private void inst(int x, int y){
        //resize into shape
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new Rectangle(200,200));
            }
        });
        
        setAlwaysOnTop(true);	//make sure it's always on time
        setUndecorated(true);
        setSize(1,1);			//make 1x1 pixel
        setLocationRelativeTo(null);
        setLocation(x,y);		//make position based on input
        
        getContentPane().setBackground(Color.WHITE);	//want white. changeable if needed
    }
	
	//gives screen dimensions
	public static Dimension get_screen_dim() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	//get total number of pixels  in screen
	public static int get_screen_num_pixels() {
		Dimension screenSize = get_screen_dim();
		return ((int) (screenSize.getWidth() * screenSize.getHeight()));
	}
	
	//make sure window transparency is A-OK
	public static void check_env() {
        // Determine what the GraphicsDevice can support.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        //If shaped windows aren't supported, exit.
        if (!gd.isWindowTranslucencySupported(PERPIXEL_TRANSPARENT)) {
            System.err.println("Shaped windows are not supported");
            System.exit(0);
        }
		
	}
	
	//get input - but we probably only want 10
	//tried 300 once and had to restart because I heard my computer silently saying 'kill me'
	//no, I cannot explain the logistics of that
	public static int get_num(String[] args) {
		int nm = 10;
    	
    	try {
    		if (args.length > 0) {
    			int in_nm = Integer.parseInt(args[0]);
    			
    			//plz don't fill more than half the screen
    			if (in_nm < (get_screen_num_pixels()/2)){
    				nm = get_screen_num_pixels()/2;
    			}
    			else {
    				nm = in_nm;
    			}
    		}
    	}
    	catch(Exception e) {
    		nm = 1;
    	}
    	
    	return nm;
	}
	
	//get me some pointsssss
	//also want them to be all different
	//nm = number of points
	//return = hash of points
	public static HashSet<Point> get_points(int nm){
		HashSet<Point> pt_hash = new HashSet<Point>();
    	int width = (int) (get_screen_dim().getWidth());
    	int height = (int) (get_screen_dim().getHeight());
    	
    	while(pt_hash.size() < nm) {
    		int tmp_width = ((int) (Math.random()*width));
    		int tmp_height = ((int) (Math.random()*height));
    		
    		Point pt = new Point(tmp_width, tmp_height);
    		pt_hash.add(pt);
    		
    	}
    	
    	return pt_hash;
	}

	//MAINNNNN
    public static void main(String[] args) {
    	check_env();								//ok? kk
    	int nm = get_num(args);						//get that number
    	HashSet<Point> pt_hash = get_points(nm);	//get that hashhh

        // Create the GUI on the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	for (Point p: pt_hash) {
            		//DO IT!!
            		
            		DeadPixel sw = new DeadPixel(((int)p.getX()),((int)p.getY()));
            		
            		// Display the window.
            		sw.setVisible(true);
            		
            	}
            }
        });
        
        
    }
}