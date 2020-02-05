package edu.jsu.mcis.seabattleii;

import java.awt.*;

public class SeaBattleII {
    
    /*
     * The main method creates and initializes the MVC objects.  The main
     * window view takes over once the model has been fully initialized.
     */

    public static void main(String[] args) {
        
        /* Create MVC Objects */
        
        DefaultController controller = new DefaultController();     // Controller
        
        DefaultModel m = new DefaultModel();                        // Model
        
        ViewPrimaryGrid v1 = new ViewPrimaryGrid(controller, 1);    // View (Player 1 Primary Grid)
        ViewPrimaryGrid v2 = new ViewPrimaryGrid(controller, 2);    // View (Player 2 Primary Grid)
        
        ViewTrackingGrid t1 = new ViewTrackingGrid(controller, 1);  // View (Player 1 Tracking Grid)
        ViewTrackingGrid t2 = new ViewTrackingGrid(controller, 2);  // View (Player 2 Tracking Grid)
        
        /* Register Model with Controller */
        
        controller.addModel(m);
        
        /* Register Views with Controller */
        
        controller.addView(v1);
        controller.addView(v2);
        controller.addView(t1);
        controller.addView(t2);
        
        /* Create and Register Main Window */
        
        EventQueue.invokeLater(() -> {
        
            ViewMainWindow window = new ViewMainWindow(controller, v1, t1, v2, t2);
            controller.addView(window);
            
            /* Set JFrame Properties */
            
            window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            window.setResizable(false);
            window.pack();
            window.setVisible(true);
            
            /* Initialize Model */
            
            m.initDefaults();
            
        });
        
    }
    
}