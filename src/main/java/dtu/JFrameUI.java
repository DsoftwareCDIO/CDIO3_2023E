package dtu;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout.Alignment;

import javafx.scene.text.Font;
import javafx.stage.PopupWindow.AnchorLocation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
class bgImage extends JPanel{
    Image img;
    public bgImage(LayoutManager lm) {
        super(lm);
        img = Toolkit.getDefaultToolkit().createImage("src\\\\pictures\\\\Board.png");
    }
    public void paintComponent(Graphics g) {      
        super.paintComponent(g);  
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    } 
}
class moneyImage extends JPanel{
    Image img;
    public moneyImage() {
        img = Toolkit.getDefaultToolkit().createImage("src\\\\pictures\\\\monopolybuckswhite.png");
    }
    public void paintComponent(Graphics g) {      
        super.paintComponent(g);  
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    } 
}

class plImage extends JPanel{
    HashMap<String, Image> images;
    String playerName;
    public plImage(String playerName) {
        this.playerName = playerName;
        images = new HashMap<>();
        images.put("Cat", Toolkit.getDefaultToolkit().createImage("src\\\\pictures\\\\katcirkel.png"));
        images.put("Car", Toolkit.getDefaultToolkit().createImage("src\\\\pictures\\\\bilcirkel.png"));
        images.put("Ship", Toolkit.getDefaultToolkit().createImage("src\\\\pictures\\\\bådcirkel.png"));
        images.put("Dog", Toolkit.getDefaultToolkit().createImage("src\\\\pictures\\\\hundcirkel.png"));
    }
    public void paintComponent(Graphics g) {      
        super.paintComponent(g);  
        g.drawImage(images.get(playerName), 0, 0, getWidth(), getHeight(), this);
    } 
}
class pl extends JLabel{
    Image img;
    protected int scale;
    public pl() {
    }
    public void paintComponent(Graphics g) {      
        super.paintComponent(g);  
        g.drawOval(0, 0, getWidth(), getHeight());
    } 
}


public class JFrameUI {
    private static pl[] players;
    private static HashMap<String, JLabel> playerMoney = new HashMap<>();

    // Temp main method to test JFrame
    public static void main(String[] args){
        testDraw(new String[]{"Cat", "Car", "Dog", "Ship"});
        //drawBoard();
        
    }

    public static void testDraw(String[] playerNames) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame();
        // Set to full screen constantly
        //frame.setUndecorated(true);
        frame.setResizable(false);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setLayout(new BorderLayout());
        frame.setBackground(Color.BLACK);
        
        // Program ends when JFrame closes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Color backGroundColor = new Color(0, 0, 0);

        // Set background image as board
        JPanel back = new JPanel();
        JPanel left = new JPanel(new BorderLayout());
        JPanel right = new JPanel(new BorderLayout());
        int scale = (int)(screenSize.getHeight()-screenSize.getHeight()/15);
        back.setPreferredSize(new Dimension(scale, scale));
        left.setPreferredSize(new Dimension((int)(screenSize.getWidth()-scale)/2, scale));
        right.setPreferredSize(new Dimension((int)(screenSize.getWidth()-scale)/2, scale));
        right.setBackground(backGroundColor);
        left.setBackground(backGroundColor);
        
        JPanel backImage = new bgImage(new GridBagLayout());
        backImage.setPreferredSize(new Dimension(scale, scale));
        
        int playerWidth = 180;
        JPanel leftPlayerPanel = new JPanel(new BorderLayout());
        leftPlayerPanel.setPreferredSize(new Dimension(playerWidth, scale));
        leftPlayerPanel.setBackground(backGroundColor);

        JPanel rightPlayerPanel = new JPanel(new BorderLayout());
        rightPlayerPanel.setPreferredSize(new Dimension(playerWidth, scale));
        rightPlayerPanel.setBackground(backGroundColor);

        for (int i = 0; i < playerNames.length; i++) {
            JPanel panel = i % 2 == 0 ? leftPlayerPanel : rightPlayerPanel;
            JPanel newPanel = new JPanel(new FlowLayout(i > 1 ? FlowLayout.LEFT : FlowLayout.LEADING, 0, 15));
            JPanel img = new plImage(playerNames[i]);
            JPanel moneyImg = new moneyImage();
            JLabel moneyText = new JLabel("0");
            newPanel.setPreferredSize(new Dimension(playerWidth, (int)(playerWidth*1.7)));
            newPanel.setBackground(backGroundColor);
            img.setBackground(backGroundColor);
            img.setPreferredSize(new Dimension(playerWidth, playerWidth));
            moneyImg.setPreferredSize(new Dimension(playerWidth/3, playerWidth/3));
            moneyImg.setBackground(backGroundColor);
            moneyText.setForeground(Color.WHITE);
            moneyText.setFont(new java.awt.Font("Arial", java.awt.Font.ROMAN_BASELINE, 80));
            if (i > 1) {
                newPanel.add(moneyText);
                newPanel.add(moneyImg);
                newPanel.add(img);
            } else {
                newPanel.add(img);
                newPanel.add(moneyText);
                newPanel.add(moneyImg);
            }
            panel.add(newPanel, i > 1 ? BorderLayout.SOUTH : BorderLayout.NORTH);

            playerMoney.put(playerNames[i], moneyText);
        }

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 7;
        c.gridy = 7;
        c.gridheight = 100;
        c.gridwidth = 100;
        for (int i = 0; i < 49; i++) {
            JLabel gridElement = new JLabel("Test");
            gridElement.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            backImage.add(gridElement, c);
        }
        /* for (JPanel panel : new JPanel[]{leftPlayerPanel, rightPlayerPanel}) {
            JPanel top = new JPanel(new FlowLayout());
            JPanel topImage = new plImage();
            top.setPreferredSize(new Dimension(playerWidth, playerWidth));
            topImage.setPreferredSize(new Dimension(playerWidth, playerWidth));
            top.add(topImage);
            panel.add(top, BorderLayout.NORTH);

            JPanel bottom = new JPanel(new FlowLayout());
            JPanel bottomImage = new plImage();
            bottom.setPreferredSize(new Dimension(playerWidth, playerWidth));
            bottomImage.setPreferredSize(new Dimension(playerWidth, playerWidth));
            bottom.add(bottomImage);
            panel.add(bottom, BorderLayout.SOUTH);
        } */

        left.add(leftPlayerPanel, BorderLayout.WEST);
        right.add(rightPlayerPanel, BorderLayout.EAST);


        // Create exit button
        /* JButton button = new JButton("X");
        button.setBackground(Color.RED);
        button.setForeground(Color.WHITE);
        button.setSize(50, 50);
        button.addActionListener(e -> {
            frame.dispose();
        });
        right.add(button, BorderLayout.EAST);
        
        
        // Put exit button i ntop right corner
        JPanel exitBtn = new JPanel();
        exitBtn.setBackground(Color.BLACK);
        exitBtn.add(button, FlowLayout.RIGHT);
        frame.add(exitBtn, FlowLayout.LEADING); */

        /*
        players = new pl[playerNames.length];
        for (int player = 0; player < playerNames.length; player++) {
            pl p = new pl();
            p.setForeground(Color.RED);
            p.setSize(100,100);               
            c.anchor = GridBagConstraints.EAST;
            frame.add(p, c);
        } */
        // Show frame
        back.add(backImage, BorderLayout.CENTER);
        frame.add(left, BorderLayout.WEST);
        frame.add(right, BorderLayout.EAST);
        frame.add(back, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static void updateMoneyInAccount(int money, String player, boolean gained) {
        try {
            playerMoney.get(player).setForeground(gained ? Color.GREEN : Color.RED);
            Thread.sleep(1000);
            playerMoney.get(player).setText("" + money);
            Thread.sleep(1000);
            playerMoney.get(player).setForeground(Color.WHITE);
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }

    public static JFrame drawBoard(String[] playerNames) {
        //Create objects
        JFrame container = new JFrame();
        JLabel boardLabel = new JLabel();
        ImageIcon boardImage = new ImageIcon("src\\pictures\\Board.png");
        ImageIcon containerIcon = new ImageIcon("src\\pictures\\Icon.png");

        //Set container behavior
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLayout(null);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        container.setSize(screenSize.width, screenSize.height);
        container.setVisible(true);
        container.setResizable(true);
        container.setTitle("Monopoly Junior");
        container.setIconImage(containerIcon.getImage());
        boardLabel.setIcon(boardImage);
        boardLabel.setBounds(0, 0, container.getWidth(), container.getHeight());
        container.add(boardLabel);
        /*container.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e){
                
            }
        });*/
        return container;
    }

    public static void movePlayer(int position, Player player) {

    }
}