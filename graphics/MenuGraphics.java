package graphics;

import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

@SuppressWarnings("serial")
public class MenuGraphics extends JPanel {

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 50;
    private static final int BUTTON_GAP = 50;

    private JButton sp;
    private JButton mp;
    private JButton exit;
    private JLabel title;
    private BoardGraphics bg;

    public MenuGraphics(int width, int height, String text, BoardGraphics bg) {
        super();
        sp = new SPButton("Single Player");
        mp = new MPButton("Multiplayer");
        exit = new ExitButton("Exit");
        title = new Title(text, JLabel.CENTER);
        this.bg = bg;

        initMenu(width, height);
        
    }

    private void initMenu(int width, int height){
        setBackground(Color.BLACK);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 3; x++) {
                if (x != 1)
                    c.weightx = (double) (width - BUTTON_WIDTH) / (2 * width);
                else
                    c.weightx = (double) BUTTON_WIDTH / width;
                if (y % 2 == 1)
                    c.weighty = (double) BUTTON_HEIGHT / height;
                else if (y == 0)
                    c.weighty = (double) (height - ((BUTTON_HEIGHT * 3) + (BUTTON_GAP * 2))) / (((double) 100 / 65) * height);
                else if (y == 6)
                    c.weighty = (double) (height - ((BUTTON_HEIGHT * 3) + (BUTTON_GAP * 2))) / (((double) 100 / 35) * height);
                else
                    c.weighty = (double) BUTTON_GAP / height;
                
                c.gridx = x;
                c.gridy = y;

                EmptyPanel p = new EmptyPanel();
                
                if (x == 1) {
                    p.setLayout(new GridLayout(1,1));
                    switch (y) {
                        case 0:
                            p.add(title);
                            break;
                        case 1:
                            p.add(sp);
                            break;
                        
                        case 3:
                            p.add(mp);
                            break;

                        case 5:
                            p.add(exit);
                            break;
                    
                        default:
                            break;
                    }
                }
                add(p, c);
            }
        }

    }

    private class SPButton extends JButton{

        public SPButton(String s){
            super(s);
            addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    bg.initGame(true);
                }
            });
        }
    }

    private class MPButton extends JButton{
        
        public MPButton(String s){
            super(s);
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    bg.initGame(false);
                }
            });
        }
    }

    private class ExitButton extends JButton{

        public ExitButton(String s){
            super(s);
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    bg.dispose();
                }
            });
        }
    }

    private class EmptyPanel extends JPanel{

        public EmptyPanel() {
            super();
            setBackground(Color.BLACK);
        }
    }

    private class Title extends JLabel{

        public Title(String s, int alignment) {
            super(s, alignment);
            setFont(new Font("Comic Sans MS", Font.BOLD, 50));
            setForeground(Color.WHITE);
        }
    }
}