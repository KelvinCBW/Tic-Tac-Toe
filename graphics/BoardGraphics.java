package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;
import java.awt.BasicStroke;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import data.AI;
import data.Board;

@SuppressWarnings("serial")
public class BoardGraphics extends JFrame {

    public final static int HEIGHT = 600;
    public final static int WIDTH = 600;
    public final static int GRIDWIDTH = 500;
    public final static int GRIDHEIGHT = 500;

    
    protected boolean isSinglePlayer;

    public BoardGraphics() {
        setTitle("Tic Tac Toe");
        setBackground(Color.BLACK);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initMenu("Tic Tac Toe");
    }
    
    private void destroyGrahpics(){
        getContentPane().removeAll();
        repaint();
    }

    public void initGame(boolean isSP){
        destroyGrahpics();
        isSinglePlayer = isSP;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (x != 1)
                    c.weightx = (double) (WIDTH - GRIDWIDTH) / (2 * WIDTH);
                else
                    c.weightx = (double) GRIDWIDTH / WIDTH;
                if (y != 1)
                    c.weighty = (double) (HEIGHT - GRIDHEIGHT) / (2 * HEIGHT);
                else
                    c.weighty = (double) GRIDHEIGHT / HEIGHT;
                c.gridx = x;
                c.gridy = y;
                if (x != 1 || y != 1)
                    add(new EmptyPanel(WIDTH, HEIGHT, GRIDWIDTH, GRIDHEIGHT), c);
                else 
                    add(new Grid(GRIDWIDTH, GRIDHEIGHT), c);
                
            }
        }
        pack();
        setVisible(true);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    protected void initMenu(String text){
        destroyGrahpics();
        setLayout(new GridLayout(1,1));
        add(new MenuGraphics(WIDTH, HEIGHT, text, this));
        pack();
        setVisible(true);
    }

    public class Grid extends JPanel {

        private static final int GAP = 10;
        
        private JButton[][] arr;
        private Board board;
        protected AI ai;

        public Grid(int gridWidth, int gridHeight) {
            super();
            board = new Board();
            arr = new JButton[3][3];
            if (isSinglePlayer)
                ai = new AI(board, arr, 1, false);
            setPreferredSize(new Dimension(gridWidth, gridHeight));
            setBackground(Color.BLACK);
            GridLayout gl = new GridLayout(3, 3, GAP, GAP);
            setLayout(gl);
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++){
                    arr[x][y] = new Tile(x, y);
                    add(arr[x][y]);
                }
            }
            pack();
        }

        private class Tile extends JButton {

            private boolean blank;
            private int turn;

            public Tile(int x, int y) {
                super();
                blank = true;
                setBackground(Color.WHITE);
                setBorder(null);
                setLayout(new GridLayout(1,1));
                addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        turn = board.getTurn();
                        board.insert(x, y);
                        blank = false;
                        repaint();
                        int state = board.getState();
                        if (0 < state && state < 4){
                            String s;
                            switch (state) {
                                case 1:
                                    s = "Circle has won!";
                                    break;
                                case 2:
                                    s = "Cross has won!";
                                    break;
                                case 3:
                                    s = "Draw!";
                                    break;
                                default:
                                    s = "Error";
                                    break;
                            }
                            initMenu(s);
                        }
                        setEnabled(false);
                        if (isSinglePlayer && board.getTurn() == 1 && board.getState() == 0)
                            ai.move();
                    }
                });
                pack();
            }

            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(15));
                if (!blank){
                    if (turn == 0) {
                        g.setColor(Color.BLACK);
                        int radius = (int) (getWidth() * 0.35);
                        Point center = new Point(getWidth() / 2, getHeight() / 2);
                        int centerX = (int) center.getX() - radius;
                        int centerY = (int) center.getY() - radius;
                        g2d.drawOval(centerX, centerY, radius * 2, radius * 2);
                    }
                    else{
                        g.setColor(Color.RED);
                        int x1 = (int) (getWidth() * 0.15);
                        int y1 = (int) (getHeight() * 0.15);
                        int x2 = (int) (getWidth() - x1);
                        int y2 = (int) (getHeight() - y1);
                        g.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    }
                }
            }
        }
    }

    private class EmptyPanel extends JPanel{
        
        public EmptyPanel(int width, int height, int gridWidth, int gridHeight){
            super();
            int dimx = (width - gridWidth) / 2;
            int dimy = (height - gridHeight) / 2;
            setPreferredSize(new Dimension(dimx,dimy));
            setBackground(Color.BLACK);
            pack();
            setVisible(true);
        }
    }

}