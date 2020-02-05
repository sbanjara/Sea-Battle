package edu.jsu.mcis.seabattleii;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import javax.swing.*;
import java.applet.*;

public class ViewMainWindow extends JFrame implements AbstractView {
    
    /* Names for JPanel Cards */
    
    private static final String PLAYER_1 = "Player 1";
    private static final String PLAYER_2 = "Player 2";
    private static final String EMPTY_CARD = "Empty";
    
    /* Names for Icon Files */
    
    private static final String ICON_ROOT = "/resources/images/";
    private static final String BANNER_ICON_FILE = "banner.png";
    
    /* Names for Sound Files */
    
    private static final String SOUND_ROOT = "/resources/sounds/";
    private static final String SPLASH_SOUND_FILE = "splash.wav";
    private static final String BOOM_SOUND_FILE = "boom.wav";
    
    /* Sound Enable/Disable Toggle */
    
    private boolean soundEnabled;
    
    /* Audio Clips */
    
    AudioClip splash, boom;
    
    /* Grid Objects (Players 1 and 2) */
    
    ViewPrimaryGrid p1, p2;
    ViewTrackingGrid t1, t2;
    
    /* Grid Containers (Players 1 and 2) */
    
    ViewGridContainer c1, c2, c3, c4;
    
    
    JPanel container, cards;
    
    /* Labels for status row and title banner */
    
    JLabel banner, status;
    
    /* Player ID of current player */
    
    int currentPlayer;
    
    /* CONSTRUCTOR */
    
    public ViewMainWindow(DefaultController controller, ViewPrimaryGrid p1, ViewTrackingGrid t1, ViewPrimaryGrid p2, ViewTrackingGrid t2) {
        
        /* Set Window Title */
        
        super("Sea Battle II");
        
        /* Initialize Objects */
        this.p1 = p1;
        this.p2 = p2;
        
        this.t1 = t1;
        this.t2 = t2;
        
        
        initComponents();

        /* Set Default Options */
        
        currentPlayer = 1;
        soundEnabled = true;
        
        /* Start with Player 1 Card */
        
        showCard(PLAYER_1);
        
    }
    
    /* Initialize GUI Components */
    
    private void initComponents() {
        
        /* Set JPanel Properties */
        
        container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(Color.BLACK);
        
        /* Create Title Banner */
        
        banner = new JLabel("", SwingConstants.CENTER);
        banner.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
        banner.setIcon(new ImageIcon(getClass().getResource(ICON_ROOT + BANNER_ICON_FILE)));
        
        /* Create Status Line */
        
        status = new JLabel("", SwingConstants.CENTER);
        status.setPreferredSize(new Dimension(0, 32));
        status.setForeground(Color.WHITE);
        
        /* Create Sound Clips */
        
        splash = Applet.newAudioClip(getClass().getResource(SOUND_ROOT + SPLASH_SOUND_FILE));
        boom = Applet.newAudioClip(getClass().getResource(SOUND_ROOT + BOOM_SOUND_FILE));
        
        /* Create Grid Containers */
        
        c1 = new ViewGridContainer(p1); /* Player 1 Primary */
        c1.setTitle("Player 1: Primary Grid");
        
        c2 = new ViewGridContainer(t1); /* Player 1 Tracking */
        c2.setTitle("Player 1: Tracking Grid");
        
        c3 = new ViewGridContainer(p2); /* Player 2 Primary */
        c3.setTitle("Player 2: Primary Grid");
        
        c4 = new ViewGridContainer(t2); /* Player 2 Tracking */
        c4.setTitle("Player 2: Tracking Grid");
        
        /* 
         * Create cards.  These are the containers for the players' Grids, which
         * are created as a stack of "cards" that can be enabled and brought to
         * the foreground with the "showCard()" method.  As the players take
         * their turns, their respective cards are shown, with an empty card
         * shown between moves so that two players sharing one computer can play
         * the game with their repsective Grids remaining hidden from their
         * opponent.
         *
         * To implement the stack of cards, the containing JPanel uses the
         * CardLayout layout manager; within each card, the containing JPanels
         * use the BorderLayout layout manager, so that the Primary and Tracking
         * Grids can be arranged in the correct order.
         */
        
        cards = new JPanel();
        cards.setLayout(new CardLayout());
        cards.setBackground(Color.BLACK);
        
        /* Player 1 Card (containing Primary and Tracking grids) */
        
        JPanel card1 = new JPanel();
        card1.setLayout(new BorderLayout());
        card1.add(c1, BorderLayout.LINE_START);
        card1.add(c2, BorderLayout.LINE_END);
        card1.setVisible(false);
        
        /* Player 2 Card (containing Primary and Tracking grids) */
        
        JPanel card2 = new JPanel();
        card2.setLayout(new BorderLayout());
        card2.add(c3, BorderLayout.LINE_START);
        card2.add(c4, BorderLayout.LINE_END);
        card2.setVisible(false);
        
        /* Empty Card (to hide opposing players' fleets between turns) */
        
        JPanel empty = new JPanel();
        empty.setLayout(new BorderLayout());
        empty.setBackground(Color.BLACK);
        empty.setVisible(false);
        
        /* Add Cards to CardLayout */
        
        cards.add(card1, PLAYER_1);
        cards.add(card2, PLAYER_2);
        cards.add(empty, EMPTY_CARD);

        /* Create Main Menu Bar */
        
        JMenuBar menuBar = new JMenuBar();
        
        /* "File" Menu, Menu Items, and Listener(s) */
        
        JMenu menuFile = new JMenu("File");
        JMenuItem menuItemSounds = new JMenuItem("Enable/Disable Sounds");
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuFile.add(menuItemSounds);
        menuFile.add(menuItemExit);
        
        menuItemSounds.addActionListener((java.awt.event.ActionEvent e) -> {
            soundEnabled = !soundEnabled;
        });
        
        menuItemExit.addActionListener((java.awt.event.ActionEvent e) -> {
            System.exit(0);
        });
        
        /* "Help" Menu, Menu Items, and Listener(s) */
        
        JMenu menuHelp = new JMenu("Help");
        JMenuItem menuItemAbout = new JMenuItem("About");
        menuHelp.add(menuItemAbout);
        
        menuItemAbout.addActionListener((java.awt.event.ActionEvent e) -> {
            showAboutDialog();
        });
        
        menuBar.add(menuFile);
        menuBar.add(menuHelp);
        
        /* Add Menu Bar to Window */
        
        this.setJMenuBar(menuBar);
        
        /* Assemble Main Window */
        
        container.add(banner, BorderLayout.PAGE_START);
        container.add(cards, BorderLayout.CENTER);
        container.add(status, BorderLayout.PAGE_END);
        
        this.getContentPane().add(container);
        
    }
    
    /* Show Specified Card */
    
    private void showCard(String name) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, name);
    }
    
    /* Show "About" Dialog Box */
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this, "Sea Battle II Version 1.0\nCS 232 (Computer Programming II), Fall 2018");
    }
    
    /* Get Player ID of opposing player */
    
    private int otherPlayer() {
        return (currentPlayer == 1 ? 2 : 1);
    }
    
    /*
     * This method is invoked by the Controller when changes to the Model must
     * be reflected in the View.  The change is indicated by the constant
     * identifiers defined in the Controller; the data is given as the original
     * and/or new values in the parameter list.  Controller notifications which
     * do not apply to this View are ignored.
     *
     * (In the case of the main window, the Model changes must be presented to
     * the player as dialog boxes or status line updates to inform the player.
     * Changes to the Primary and Tracking Grids are handled by their respective
     * Views.)
     */
    
    @Override
    public void modelPropertyChange(final PropertyChangeEvent e) {
        
        /* Update Status Line */
    
        if (e.getPropertyName().equals(DefaultController.STATUS)) {
            String newStatus = e.getNewValue().toString();
            if (!status.getText().equals(newStatus))
                status.setText(newStatus);
        }
        
        /* Shot Missed (Players 1 and 2) */
        
        else if ( (e.getPropertyName().equals(DefaultController.PLAYER1_SHOT_MISSED) && currentPlayer == 1) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER2_SHOT_MISSED) && currentPlayer == 2) ) {
            
            /* Play Sound (if sounds enabled) */
            
            if (soundEnabled)
                splash.play();
            
            /* Present Dialog Message */
            
            JOptionPane.showMessageDialog(this, "Your shot was a MISS!");
            
            /* Switch to empty card; prompt player to begin next turn */
            
            showCard(EMPTY_CARD);
            JOptionPane.showMessageDialog(this, "Player " + otherPlayer() + ": Click \"OK\" to begin your turn ...");
            
            /* Switch to next player; show corresponding Card */
            
            currentPlayer = otherPlayer();
            showCard((currentPlayer == 1 ? PLAYER_1 : PLAYER_2));
            
        }
        
        /* Shot Hit (Players 1 and 2) */
        
        else if ( (e.getPropertyName().equals(DefaultController.PLAYER2_SHIP_HIT) && currentPlayer == 1) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER1_SHIP_HIT) && currentPlayer == 2) ) {
            
            /* Play Sound (if sounds enabled) */
            
            if (soundEnabled)
                boom.play();
            
            /* Present Dialog Message */
            
            String ship = e.getNewValue().toString();
            JOptionPane.showMessageDialog(this, "Your shot HIT a " + ship + "!");
            
            /* Switch to empty card; prompt player to begin next turn */
            
            showCard(EMPTY_CARD);
            JOptionPane.showMessageDialog(this, "Player " + otherPlayer() + ": Click \"OK\" to begin your turn ...");
            
            /* Switch to next player; show corresponding Card */
            
            currentPlayer = otherPlayer();
            showCard((currentPlayer == 1 ? PLAYER_1 : PLAYER_2));
            
        }
        
        /* Shot Hit and Sunk opponent's ship (Players 1 and 2) */
        
        else if ( (e.getPropertyName().equals(DefaultController.PLAYER2_SHIP_SUNK) && currentPlayer == 1) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER1_SHIP_SUNK) && currentPlayer == 2) ) {
            
            /* Play Sound (if sounds enabled) */
            
            if (soundEnabled)
                boom.play();
            
            /* Present Dialog Message */
            
            String ship = e.getNewValue().toString();
            JOptionPane.showMessageDialog(this, "Your shot SANK a " + ship + "!");
            
            /* Switch to empty card; prompt player to begin next turn */
            
            showCard(EMPTY_CARD);
            JOptionPane.showMessageDialog(this, "Player " + otherPlayer() + ": Click \"OK\" to begin your turn ...");
            
            /* Switch to next player; show corresponding Card */
            
            currentPlayer = otherPlayer();
            showCard((currentPlayer == 1 ? PLAYER_1 : PLAYER_2));
            
        }
        
        /* Game Over (Player 1 and 2) */
        
        else if ( (e.getPropertyName().equals(DefaultController.PLAYER2_GAME_OVER) && currentPlayer == 1) ||
                  (e.getPropertyName().equals(DefaultController.PLAYER1_GAME_OVER) && currentPlayer == 2) ) {
            
            /* Play Sound (if sound is enabled) */
            
            if (soundEnabled)
                boom.play();
            
            /* Present Dialog Message */
            
            showCard(EMPTY_CARD);
            JOptionPane.showMessageDialog(this, "Congratulations!!!!\nPlayer " + currentPlayer + " won the game.");
            
        }
        
    
    }
    
    
}
