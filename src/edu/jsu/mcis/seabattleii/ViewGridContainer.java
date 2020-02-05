package edu.jsu.mcis.seabattleii;

import java.awt.*;
import javax.swing.*;

public class ViewGridContainer extends JPanel {
    
    /*
     * This class represents a container for a Grid (either a Primary Grid or a
     * Tracking Grid).  It includes the Grid itself as well as a JLabel for the
     * Grid's title.
     */
    
    private JLabel title;
    private final JPanel grid;
    
    /* CONSTRUCTOR */
    
    public ViewGridContainer(JPanel grid) {
        
        super();
        
        this.grid = grid;
        
        initComponents();
        
    }
    
    /* Initialize GUI Components */
    
    private void initComponents() {
        
        /* Initialize JPanel properties */
        
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        /* Create Title */
        
        title = new JLabel("", SwingConstants.CENTER);
        title.setPreferredSize(new Dimension(0, 32));
        title.setForeground(Color.WHITE);
        
        /* Add Title and Grid */
        
        add(title, BorderLayout.PAGE_START);
        add(grid, BorderLayout.CENTER);
        
    }
    
    /* Setter for Title Text */
    
    public void setTitle(String t) {
        title.setText(t);
    }
    
}