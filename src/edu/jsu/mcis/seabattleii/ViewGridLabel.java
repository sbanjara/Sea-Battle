package edu.jsu.mcis.seabattleii;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class ViewGridLabel extends JLabel {
    
    /*
     * This class represents the View for a single Square in a Grid (either in a
     * Primary Grid or a Tracking Grid).  It includes a method for rotating the
     * Square, for Squares which belong to Ships that are deployed vertically.
     */
    
    private final int x, y; // The Square's X and Y coordinates within the Grid
    
    public ViewGridLabel(AbstractView parent, int x, int y) {
        
        super("", SwingConstants.CENTER);
        
        /* Initialize X and Y */
        
        this.x = x;
        this.y = y;
        
        /* Initialize JLabel Properties */
        
        this.setPreferredSize(new Dimension(32, 32));
        this.setForeground(Color.WHITE);
        this.setOpaque(true);
        this.setVisible(true);
        
    }
    
    /* Rotate Label */
    
    public void rotateImage(int angle) {
        
	int width = this.getIcon().getIconWidth();
	int height = this.getIcon().getIconHeight();
	BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	Graphics2D bg = bi.createGraphics();
        bg.rotate(Math.toRadians(angle), width/2, height/2);
        this.getIcon().paintIcon(null, bg, 0, 0);
	bg.dispose();
	this.setIcon(new ImageIcon(bi));
	this.setPreferredSize(new Dimension(this.getIcon().getIconHeight(), this.getIcon().getIconWidth()));
    
    }
    
    /* Getters for X and Y */

    public int getGridLabelX() {
        return x;
    }

    public int getGridLabelY() {
        return y;
    }
    
}