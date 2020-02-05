package edu.jsu.mcis.seabattleii;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

public class ViewTrackingGrid extends JPanel implements AbstractView {
    
    private final DefaultController controller;
    
    private static final String ICON_ROOT = "/resources/images/";
    private static final String HIT_ICON_FILE = "tracking_hit.png";
    private static final String MISS_ICON_FILE = "tracking_miss.png";
    
    private static final Color GRID_HEADER_BACKGROUND = new Color(5, 23, 35);
    private static final Color GRID_DEFAULT_BACKGROUND = new Color(4, 58, 82);
    private static final Color WINDOW_BACKGROUND = Color.BLACK;
    private static final Color GRID_LINES = new Color(49, 148, 171);

    private ArrayList<ArrayList<ViewGridLabel>> labels;
    
    private ImageIcon iconHit;
    private ImageIcon iconMiss;
    
    private HashMap<String, Integer> selectedSquare;
    
    private final int playerId;
    
    public ViewTrackingGrid(DefaultController controller, int playerId) {
        
        super();
        
        this.controller = controller;        
        this.playerId = playerId;
        
        selectedSquare = new HashMap<>();
        selectedSquare.put("x", -1);
        selectedSquare.put("y", -1);
        
        initComponents();
        
    }
    
    /* Initialize GUI Components */
    
    private void initComponents() {
        
        /* Initialize JPanel Properties */
        
        this.setLayout(new GridLayout(Grid.GRID_SIZE + 1, Grid.GRID_SIZE + 1));
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, GRID_LINES));
        
        /* Construct Icons */
       
        iconHit = new ImageIcon(getClass().getResource(ICON_ROOT + HIT_ICON_FILE));
        iconMiss = new ImageIcon(getClass().getResource(ICON_ROOT + MISS_ICON_FILE));
        
        /* Construct Grid Labels */
        
        labels = new ArrayList<>();

        ArrayList<ViewGridLabel> row;
        ViewGridLabel label;
        
        /* Construct Next Row */
        
        for (int y = 0; y < Grid.GRID_SIZE + 1; ++y) {
            
            row = new ArrayList<>();
            
            /* Construct Colums Within Row */
            
            for (int x = 0; x < Grid.GRID_SIZE + 1; ++x) {
                
                label = new ViewGridLabel(this, x-1, y-1);
                
                /* Set JLabel Properties */
                
                label.setBackground(GRID_DEFAULT_BACKGROUND);
                label.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, GRID_LINES));
                
                /* Add label event listeners */
                
                label.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        selectSquare((ViewGridLabel)(e.getSource()));
                    }
                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        mouseExitSquare((ViewGridLabel)(e.getSource()));
                    }
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        mouseEnterSquare((ViewGridLabel)(e.getSource()));
                    }
                });
                
                /* Add to JPanel and to row list */
                
                this.add(label);
                row.add(label);
                
                /* If this label is in a header row/col, set text, border, and color */
                
                if ((x == 0) || (y == 0)) {
                    
                    label.setBackground(GRID_HEADER_BACKGROUND);
                    
                    if ((y == 0) && (x != 0)) {
                        label.setText("" + (char)('A' + x - 1));
                    }
                    
                    else if ((x == 0) && (y != 0)) {
                        label.setText("" + y);
                    }
                    
                }
                
            }
            
            /* Add completed row to list */
            
            labels.add(row);
            
        }
        
        /* Finally, clear the upper-leftmost cell in header row/col */
        
        labels.get(0).get(0).setBorder(BorderFactory.createEmptyBorder());
        labels.get(0).get(0).setBackground(WINDOW_BACKGROUND);
    
    }
    
    /* ViewGridLabel event handler for MouseEnter (ignores events on headers) */
    
    private void mouseEnterSquare(ViewGridLabel label) {
        
        int x = label.getGridLabelX();
        int y = label.getGridLabelY();
        
        if ((x >= 0) && (y >= 0)) {
            label.setBackground(Color.ORANGE);
        }
        
    }
    
    /* ViewGridLabel event handler for MouseExit (ignores events on headers) */
    
    private void mouseExitSquare(ViewGridLabel label) {
        
        int x = label.getGridLabelX();
        int y = label.getGridLabelY();
        
        if ((x >= 0) && (y >= 0)) {
            label.setBackground(GRID_DEFAULT_BACKGROUND);
        }
        
    }
    
    /*
     * Inform the Controller that a state change has occured which must be
     * reflected in the Model: the player has selected a square in the Tracking
     * Grid.
     */
    
    private void selectSquare(ViewGridLabel label) {
        
        if( label.getIcon() == null ) {
        
            /* Get label's X and Y coordinates */

            int x = label.getGridLabelX();
            int y = label.getGridLabelY();

            /* Is label in grid? (ignores clicks in the headers) */

            if ((x >= 0) && (y >= 0)) {

                /*
                 * If so, place X and Y coordinates into a Map for delivery to the
                 * Controller.
                 */

                selectedSquare.put("x", x);
                selectedSquare.put("y", y);

                /* Invoke correct Controller method for the current player */

                if (playerId == 1)
                    controller.player1ShotFired(selectedSquare);
                else if (playerId == 2)
                    controller.player2ShotFired(selectedSquare);


            }
            
        }
        
        else {
            
            JOptionPane.showMessageDialog(this, "This square has been already hit.\nPlease choose another square.");
            
        }
        
    }
    
    /*
     * This method is invoked by the Controller when changes to the Model must
     * be reflected in the View.  The change is indicated by the constant
     * identifiers defined in the Controller; the data is given as the original
     * and/or new values in the parameter list.  Controller notifications which
     * do not apply to this View are ignored.
     */
    
    @Override
    public void modelPropertyChange(final PropertyChangeEvent e) {
        
        /* Shot into Tracking Grid resulted in a Miss (square represented as a Map containing the X/Y coordinates) */
        
        if ( (e.getPropertyName().equals(DefaultController.PLAYER1_SHOT_MISSED) && playerId == 1) ||
             (e.getPropertyName().equals(DefaultController.PLAYER2_SHOT_MISSED) && playerId == 2) ) {
            
            int x = selectedSquare.get("x");
            int y = selectedSquare.get("y");
            labels.get(y+1).get(x+1).setIcon(iconMiss);
            
        }
        
        /* Shot into Tracking Grid resulted in a Hit (square represented as a Map containing the X/Y coordinates) */
        
        else if ( (e.getPropertyName().equals(DefaultController.PLAYER2_SHIP_HIT) && playerId == 1) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER1_SHIP_HIT) && playerId == 2) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER2_SHIP_SUNK) && playerId == 1) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER1_SHIP_SUNK) && playerId == 2) ) {
            
            int x = selectedSquare.get("x");
            int y = selectedSquare.get("y");
            labels.get(y+1).get(x+1).setIcon(iconHit);
            
        }
        
    }
    
}