import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class GameView {

    //Constructor
    private GameView() {
        initGame();
        createAndShowGUI();
        runGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameView());
    }

    //constants and global variables
    final static int EMPTY = 0;
    final static int BSIZE = 20;     //board size
    final static int HEXSIZE = 40;   //hex size in pixels
    final static int BORDERS = 15;
    final static int SCREENSIZE_Width =  1500;
    final static int SCREENSIZE_Height = 800;

    private boolean GAMEOVER;

    private GameBoard board;
    private GameRules rules;
    private Deck deck;
    private Tile currentTile;
    HashMap<Point, Hex> currentBoard;
    private boolean isPlaced;

    private JFrame mainFrame;
    private JTextField[] textFields;
    DrawingPanel mainGamePanel;

    void initGame() {
        //construct new GameBoard, and Deck
        board = new GameBoard();
        deck = new Deck();
        rules = new GameRules(board);
        GAMEOVER = false;
        isPlaced = false;

        //set HexLayout
        HexView.setLayout(new Point2D.Double(HEXSIZE, HEXSIZE), new Point2D.Double(SCREENSIZE_Width/2, SCREENSIZE_Height/2));


        //set up board here
        Tile tile1 = deck.getTile();
        Tile tile2 = deck.getTile();
        Point[] hexCoords1 = {new Point(0,0), new Point(1,0), new Point(0,1)};
        Point[] hexCoords2 = {new Point(1, -1), new Point(0, -1), new Point(1, -2)};
        addTile(tile1, hexCoords1);
        addTile(tile2, hexCoords2);

        //get current Tile
        currentTile = deck.getTile();



        /*
        currentBoard.put(new Point(0,0), (int)'A');
        currentBoard.put(new Point(0,1), (int)'Q');
        currentBoard.put(new Point(1,0), -(int)'B');
        */
    }

    void runGame() {
        while(GAMEOVER == false) {
            isPlaced = false;
            while (!isPlaced) {
                Point[] userHexCoords = getUserInput();
                placeTile(userHexCoords);
            }
            setCurrentTile();
            mainGamePanel.repaint();
        }
    }

    void setCurrentTile() {
        currentTile = deck.getTile();
    }

    void placeTile(Point[] userHexCoords) {
        addTile(currentTile, userHexCoords);
    }

    Point[] getUserInput() {
        //setup JTextField array and array of their values
        Point[] textFieldValues = new Point[3];
        textFields = new JTextField[6];
        for(int i = 0; i < 6; i++) {
            textFields[i] = new JTextField(1);
        }

        int selection = JOptionPane.showConfirmDialog(null, getUserInputPanel(), "Enter Hex Coordinates",
                                                       JOptionPane.OK_CANCEL_OPTION,
                                                       JOptionPane.PLAIN_MESSAGE);


        if (selection == JOptionPane.OK_OPTION) {

            textFieldValues[0] = new Point(Integer.valueOf(textFields[0].getText()),
                                           Integer.valueOf(textFields[1].getText()));
            textFieldValues[1] = new Point(Integer.valueOf(textFields[2].getText()),
                                           Integer.valueOf(textFields[3].getText()));
            textFieldValues[2] = new Point(Integer.valueOf(textFields[4].getText()),
                                           Integer.valueOf(textFields[5].getText()));

        } else if (selection == JOptionPane.CANCEL_OPTION) {
            getUserInput();
        }
        return textFieldValues;
    }

    private JPanel getUserInputPanel() {
        JPanel basePanel = new JPanel();
        basePanel.setOpaque(true);
        basePanel.setBackground(Color.BLUE.darker());


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 5,5));
        centerPanel.setOpaque(true);
        centerPanel.setBackground(Color.WHITE);

        JLabel aLabel1 = new JLabel("A(x)");
        JLabel aLabel2 = new JLabel("A(y)");
        JLabel bLabel1 = new JLabel("B(x)");
        JLabel bLabel2 = new JLabel("B(y)");
        JLabel cLabel1 = new JLabel("C(x)");
        JLabel cLabel2 = new JLabel("C(y)");

        centerPanel.add(aLabel1);
        centerPanel.add(textFields[0]);
        centerPanel.add(aLabel2);
        centerPanel.add(textFields[1]);
        centerPanel.add(bLabel1);
        centerPanel.add(textFields[2]);
        centerPanel.add(bLabel2);
        centerPanel.add(textFields[3]);
        centerPanel.add(cLabel1);
        centerPanel.add(textFields[4]);
        centerPanel.add(cLabel2);
        centerPanel.add(textFields[5]);

        basePanel.add(centerPanel);

        return basePanel;
    }

    private void addTile(Tile tile, Point[] hexCoords) {
        try {
            rules.TryToAddTile(tile, hexCoords);
            board.AddTile(tile, hexCoords);
            isPlaced = true;
        } catch (GameRulesException e) {
            System.out.println(e);
        }
    }

    private void createAndShowGUI() {
        mainGamePanel = new DrawingPanel();

        mainFrame = new JFrame("TigerIsland");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = mainFrame.getContentPane();
        content.add(mainGamePanel);
        mainFrame.setSize(SCREENSIZE_Width, SCREENSIZE_Height);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    class DrawingPanel extends JPanel {
        //mouse variables here
        Point mPt = new Point(0,0);

        public DrawingPanel() {
            setBackground(Color.WHITE);

            MyMouseListener ml = new MyMouseListener();
            addMouseListener(ml);
        }

        public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            super.paintComponent(g2);

            //HexView.drawHex(0, 0, g2);
            //HexView.drawHex(0,1, g2);
            //HexView.drawHex(1,0, g2);
            //HexView.drawHex(1,1, g2);
            //HexView.drawHex(-1,0, g2);

            //draw grid
            for (int i=0; i < BSIZE; i++) {
                for (int j=0; j < BSIZE; j++) {
                    HexView.drawHex(i, j, g2);
                    HexView.drawHex(-i, -j, g2);
                    HexView.drawHex(-i, j, g2);
                    HexView.drawHex(i, -j, g2);
                }
            }

            //draw current tile box
            g2.drawRect(SCREENSIZE_Width - SCREENSIZE_Width/8,SCREENSIZE_Height - SCREENSIZE_Height/5, SCREENSIZE_Width/8, SCREENSIZE_Height/5);
            g2.setColor(Color.WHITE);
            g2.fillRect(SCREENSIZE_Width - SCREENSIZE_Width/8,SCREENSIZE_Height - SCREENSIZE_Height/5, SCREENSIZE_Width/8, SCREENSIZE_Height/5);

            //fill in hexes
            g.setFont(new Font("TimesRoman", Font.PLAIN, HEXSIZE/2));
            currentBoard = board.getMap();
            for(Map.Entry<Point, Hex> entry : currentBoard.entrySet()) {
                Point pt = entry.getKey();
                HexView.fillHex((int)pt.getX(), (int)pt.getY(), entry.getValue(), g2);
            }

            //fill in current tile
            Hex[] currentHexes = currentTile.getHexes();
            System.out.println(currentHexes[0].getTerrain());
            //int xBox = SCREENSIZE_Width - SCREENSIZE_Width/8;
            //int yBox = SCREENSIZE_Height - SCREENSIZE_Height/6;
            //HexView.drawCurrentTile(currentTile, xBox, yBox, g2);
            HexView.fillHex(7, 5, currentHexes[0], g2);
            HexView.fillHex(6, 6, currentHexes[1], g2);
            HexView.fillHex(7, 6, currentHexes[2], g2);


        }
        class MyMouseListener extends MouseAdapter {
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                Point p = HexView.pixelToHex(e.getX(), e.getY());

                System.out.println("x: " + p.x + " y:" + p.y);

                //What to do when a hexagon is clicked
                //currentBoard.put(p, (int)'X');
                //repaint();
            }
        } // end of MyMouseListener
    } //end of DrawingPanel


}