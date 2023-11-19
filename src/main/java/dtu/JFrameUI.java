package dtu;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javafx.event.ActionEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
class bgImage extends JPanel{
    Image img;
    public bgImage() {
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

class chanceCardImage extends JPanel{
    HashMap<Integer, Image> images;
    int cardId;
    public chanceCardImage(int cardId) {
        this.cardId = cardId;
        images = new HashMap<>();
        images.put(0, Toolkit.getDefaultToolkit().createImage("src\\\\pictures\\\\Chance-OutOfJail.png"));
        images.put(1, Toolkit.getDefaultToolkit().createImage("src\\\\pictures\\\\Chance-ShipUnique.png"));
        images.put(2, Toolkit.getDefaultToolkit().createImage("src\\\\pictures\\\\Chance-OutOfJail.png"));
        images.put(3, Toolkit.getDefaultToolkit().createImage("src\\\\pictures\\\\Chance-OutOfJail.png"));
    }
    public void paintComponent(Graphics g) {      
        super.paintComponent(g);  
        g.drawImage(images.get(cardId), 0, 0, getWidth(), getHeight(), this);
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
    public void setNewPlayerImg(String player) {
        playerName = player;
        paintComponent(getGraphics());
        this.setVisible(true);
    }
}

class pl extends JPanel {
    protected int scale, x, y; 
    public pl(int scale, int xOffset, int yOffset) {
        this.scale = scale;
        x = xOffset;
        y = yOffset;
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCircle(g, x, y, scale/50);
    }
    public void drawCircle(Graphics cg, int xCenter, int yCenter, int r) {
        cg.fillOval(xCenter, yCenter, 2*r, 2*r);
    }
}


public class JFrameUI {
    private static HashMap<String, Color> playerColors = new HashMap<>();
    private static HashMap<String, pl> players = new HashMap<>();
    private static HashMap<String, JLabel> playerMoney = new HashMap<>();
    private static HashMap<String, JLabel> playerGetOutOfJailCards = new HashMap<>();
    private static HashMap<String, JLabel> playerUniqueCards = new HashMap<>();
    private static HashMap<Integer, plImage> propertyTags = new HashMap<>();
    private static HashMap<String, JButton> rollBtns = new HashMap<>();

    // Temp main method to test JFrame
    public static void main(String[] args){
        testDraw(new String[]{"Cat", "Car", "Dog", "Ship"});
        waitForRoll("Cat");
        waitForRoll("Dog");
        waitForRoll("Ship");
        waitForRoll("Car");
    }

    public static void testDraw(String[] playerNames) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        playerColors.put("Cat", Color.RED);
        playerColors.put("Car", Color.GREEN);
        playerColors.put("Dog", Color.BLACK);
        playerColors.put("Ship", Color.BLUE);

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
        
        JPanel backImage = new bgImage();
        backImage.setPreferredSize(new Dimension(scale, scale));
        
        int playerWidth = 180;
        JPanel leftPlayerPanel = new JPanel(new BorderLayout());
        leftPlayerPanel.setPreferredSize(new Dimension(playerWidth, scale));
        leftPlayerPanel.setBackground(backGroundColor);

        JPanel rightPlayerPanel = new JPanel(new BorderLayout());
        rightPlayerPanel.setPreferredSize(new Dimension(playerWidth, scale));
        rightPlayerPanel.setBackground(backGroundColor);

        JPanel rightRollField = new JPanel(new BorderLayout());
        rightRollField.setPreferredSize(new Dimension((int)(playerWidth*1.5), scale));
        rightRollField.setBackground(backGroundColor);

        JPanel leftRollField = new JPanel(new BorderLayout());
        leftRollField.setPreferredSize(new Dimension((int)(playerWidth*1.5), scale));
        leftRollField.setBackground(backGroundColor);

        for (int i = 0; i < playerNames.length; i++) {
            JPanel panel = i % 2 == 0 ? leftPlayerPanel : rightPlayerPanel;
            JPanel newPanel = new JPanel(new FlowLayout(i > 1 ? FlowLayout.LEFT : FlowLayout.LEADING, 0, 5));
            JPanel img = new plImage(playerNames[i]);
            JPanel moneyImg = new moneyImage();
            JLabel moneyText = new JLabel("0");
            JPanel jailCardImg = new chanceCardImage(0);
            JLabel jailCardText = new JLabel("0");
            JPanel jailPanel = new JPanel(new FlowLayout(i > 1 ? FlowLayout.LEFT : FlowLayout.LEADING, 0, 0));
            JPanel uniqueCardImg = new chanceCardImage(1);
            JLabel uniqueCardText = new JLabel("0");
            JPanel uniquePanel = new JPanel(new FlowLayout(i > 1 ? FlowLayout.LEFT : FlowLayout.LEADING, 0, 0));
            newPanel.setPreferredSize(new Dimension(100, (int)(playerWidth*2.3)));
            newPanel.setBackground(backGroundColor);
            img.setBackground(backGroundColor);
            img.setPreferredSize(new Dimension(playerWidth, playerWidth));
            moneyImg.setPreferredSize(new Dimension(playerWidth/3, playerWidth/3));
            moneyImg.setBackground(backGroundColor);
            moneyText.setForeground(Color.WHITE);
            moneyText.setFont(new java.awt.Font("Arial", java.awt.Font.ROMAN_BASELINE, 80));
            jailCardImg.setPreferredSize(new Dimension(playerWidth/2, playerWidth/4));
            jailCardImg.setBackground(backGroundColor);
            jailCardText.setForeground(Color.WHITE);
            jailCardText.setFont(new java.awt.Font("Arial", java.awt.Font.ROMAN_BASELINE, 60));
            jailPanel.setPreferredSize(new Dimension(playerWidth, playerWidth/3));
            jailPanel.setBackground(backGroundColor);
            jailPanel.add(jailCardText);
            jailPanel.add(jailCardImg);
            uniqueCardImg.setPreferredSize(new Dimension(playerWidth/2, playerWidth/4));
            uniqueCardImg.setBackground(backGroundColor);
            uniqueCardText.setForeground(Color.WHITE);
            uniqueCardText.setFont(new java.awt.Font("Arial", java.awt.Font.ROMAN_BASELINE, 60));
            uniquePanel.setPreferredSize(new Dimension(playerWidth, playerWidth/3));
            uniquePanel.setBackground(backGroundColor);
            uniquePanel.add(uniqueCardText);
            uniquePanel.add(uniqueCardImg);
            if (i > 1) {
                newPanel.add(uniquePanel);
                newPanel.add(jailPanel);
                newPanel.add(moneyText);
                newPanel.add(moneyImg);
                newPanel.add(img);
            } else {
                newPanel.add(img);
                newPanel.add(moneyText);
                newPanel.add(moneyImg);
                newPanel.add(jailPanel);
                newPanel.add(uniquePanel);
            }
            JPanel rollField = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
            rollField.setPreferredSize(new Dimension(1, (int)(playerWidth/1.5)));
            rollField.setBackground(backGroundColor);
            JButton roll = new JButton("Roll");
            roll.setBackground(Color.WHITE);
            roll.setForeground(Color.RED);
            roll.setFont(new java.awt.Font("Arial", java.awt.Font.ROMAN_BASELINE, 60));
            roll.setVisible(false);
            rollField.add(roll);
            
            (i % 2 == 0 ? leftRollField : rightRollField).add(rollField, i > 1 ? BorderLayout.SOUTH : BorderLayout.NORTH);
            panel.add(newPanel, i > 1 ? BorderLayout.SOUTH : BorderLayout.NORTH);

            rollBtns.put(playerNames[i], roll);
            playerMoney.put(playerNames[i], moneyText);
            playerGetOutOfJailCards.put(playerNames[i], jailCardText);
            playerUniqueCards.put(playerNames[i], uniqueCardText);
        }


        int[] xOffsets = new int[]{0, scale/25, 0, scale/25};
        int[] yOffsets = new int[]{0, 0, scale/25, scale/25};
        
        backImage.setLayout(null);
        for (int i = 0; i < playerNames.length; i++) {
            pl player = new pl(scale, xOffsets[i], yOffsets[i]);
            player.setLocation(60, 60);
            player.setSize(new Dimension(scale/10, scale/10));
            player.setBackground(new Color(255, 255, 255, 0));
            player.setForeground(playerColors.get(playerNames[i]));
            players.put(playerNames[i], player);
            backImage.add(player);
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= 2; j++) {
                plImage propertyTag = new plImage("Cat");
                propertyTag.setSize(new Dimension(scale/18, scale/18));
                propertyTag.setBackground(new Color(255, 255, 255, 1));
                propertyTags.put(i*3+j, propertyTag);
                switch ((int)Math.floor(i/2)) {
                    case 0:
                        propertyTag.setLocation(220+(int)(scale/7.6)*(3*i+j-1), 140);
                        break;
                    case 1:
                        propertyTag.setLocation(150+(int)(scale/7.6*5), 220+(int)(scale/7.6)*(i*3+j-7));
                        break;
                    case 2:
                        propertyTag.setLocation(220+(int)(scale/7.6)*(17-i*3-j), 140+(int)(scale/7.6)*5);
                        break;
                    case 3:
                        propertyTag.setLocation(150, 220+(int)(scale/7.6)*(23-3*i-j));
                        break;
                    default:
                        break;
                }
                propertyTag.setVisible(false);
                backImage.add(propertyTag);
            }
        }

        left.add(leftPlayerPanel, BorderLayout.WEST);
        right.add(rightPlayerPanel, BorderLayout.EAST);

        left.add(leftRollField, BorderLayout.EAST);
        right.add(rightRollField, BorderLayout.WEST);


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

    public static void updateGetOutOfJailCards(int cards, String player) {
        playerGetOutOfJailCards.get(player).setText("" + cards);
    }

    public static void updateUniqueCards(boolean gained, String player) {
        playerUniqueCards.get(player).setText(gained ? "1" : "0");
    }

    public static void movePlayer(int position, String player) {
        int x, y;
        int startOffset = 60;
        int fieldSize = 135;
        if (position <= 5) {
            x = position*fieldSize+60;
            y = startOffset;
        }
        else if (position > 5 && position <= 12) {
            x = 6*fieldSize+startOffset;
            y = (position-6)*fieldSize+startOffset;
        }
        else if (position > 12 && position <= 18) {
            x = (18-position)*fieldSize+startOffset;
            y = 6*fieldSize+startOffset;
        }
        else {
            x = startOffset;
            y = (24-position)*fieldSize+startOffset;
        }
        players.get(player).setLocation(x, y);
    }

    public static void updateFieldOwnership(int position, String player) {
        propertyTags.get(position).setNewPlayerImg(player);
    }

    public static boolean pressed = false;
    public static void waitForRoll(String player) {
        JButton btn = rollBtns.get(player);
        btn.setVisible(true);
        pressed = false;
        btn.addActionListener(e ->
        {
            pressed = true;
        }); 
        while (!pressed) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        btn.setVisible(false);
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

}
