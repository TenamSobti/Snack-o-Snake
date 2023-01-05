import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Random;

public class Panel extends JPanel implements ActionListener {
    // ? Dimensions Of Panel.
    static int width = 1200, height = 600;
    // ? Size Of Each Unit.
    static int unit = 50;
    // ? Total Units
    static int size = (width * height) / (unit * unit);
    // ? For Food Spawning.
    Random random;
    int fx, fy; // * Coordinates Of Food. (Multiple of unit).
    // ? To Check State Of Game At Regular Interval.
    boolean flag = false; // * True = Game On, False = Game Off.
    Timer timer;
    static int delay = 160;
    // ? Making Snake's Body.
    int body = 3; // * One Head And Two Body Parts.
    char dir = 'R'; // * Initial Direction Of Movement.
    int[] xsnake = new int[size];
    int[] ysnake = new int[size];
    // ? Score Of Game.
    int score = 0;

    Panel() {
        // ? Panel Setup.
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        // ? Make Sure Panel In Focus i.e. Reads Keyboard Input.
        this.setFocusable(true);
        random = new Random();
        // ? Adding Key Listener To Panel.
        this.addKeyListener(new Key());
        // ? Start The Game.
        game_start();
    }

    public void game_start() {
        flag = true;
        // ? Spawning Food.
        spawnFood();
        // ? Starting Timer To Check Game (Every 160 Milliseconds).
        timer = new Timer(delay, this);
        timer.start();
    }

    public void spawnFood() {
        // ? Setting Random Coordinates For Food. (Multiple Of Unit).
        fx = random.nextInt((int) (width / unit)) * unit;
        fy = random.nextInt((int) (height / unit)) * unit;

    }

    public void checkHit() {
        // ? Checking If Body is Hit.
        for (int i = body; i > 0; i--) {
            if ((xsnake[0] == xsnake[i]) && (ysnake[0] == ysnake[i])) {
                // ? If Hit Own Body, Game Over.
                flag = false;
            }
        }
        // ? Checking If Wall is Hit.
        if (xsnake[0] < 0) {
            flag = false;
        }
        if (xsnake[0] > width) {
            flag = false;
        }
        if (ysnake[0] < 0) {
            flag = false;
        }
        if (ysnake[0] > height) {
            flag = false;
        }
        if (flag == false) {
            timer.stop();
        }
    }

    // ? Helper To Call Draw Method.
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic); // * Allows Painting Component.
        draw(graphic);
    }

    public void draw(Graphics graphic) {
        if (flag) {
            // ? Setting Parameters For Food.
            graphic.setColor(Color.RED);
            graphic.fillOval(fx, fy, unit, unit);
            // ? Setting Parameters For Snake.
            for (int i = 0; i < body; i++) {
                if (i == 0) {
                    // * For Head
                    graphic.setColor(Color.GREEN);
                    graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
                } else {
                    // * For Body
                    graphic.setColor(Color.ORANGE);
                    graphic.fillRect(xsnake[i], ysnake[i], unit, unit);
                }
            }
            // ? Drawing Score.
            graphic.setColor(Color.CYAN);
            graphic.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics fm = getFontMetrics(graphic.getFont());
            // ? It Takes The String To Draw, Starting Position In X and The Stating
            // Position In Y.
            graphic.drawString("Score: " + score, (width - fm.stringWidth("Score: " + score)) / 2,
                    graphic.getFont().getSize());
        } else {
            gameOver(graphic);
        }
    }

    public void gameOver(Graphics graphic) {
        // * Below Displays Final Score On Game Over.
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics fm = getFontMetrics(graphic.getFont());
        // ? It Takes The String To Draw, Starting Position In X and The Stating
        // Position In Y.
        graphic.drawString("Score: " + score, (width - fm.stringWidth("Score: " + score)) / 2,
                graphic.getFont().getSize());
        // * Below Displays Big GAME OVER.
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Arial", Font.BOLD, 80));
        FontMetrics fm2 = getFontMetrics(graphic.getFont());
        // ? It Takes The String To Draw, Starting Position In X and The Stating
        // Position In Y.
        graphic.drawString("GAME OVER", (width - fm2.stringWidth("GAME OVER!")) / 2, height / 2);
        // * Below Displays Replay Option On Game Over.
        graphic.setColor(Color.RED);
        graphic.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics fm3 = getFontMetrics(graphic.getFont());
        // ? It Takes The String To Draw, Starting Position In X and The Stating
        // Position In Y.
        graphic.drawString("Press R To Replay", (width - fm3.stringWidth("Score: " + score)) / 2, height / 2 - 180);
    }

    public void move() {
        // ? Loop For Updating The Body Parts.
        for (int i = body; i > 0; i--) {
            xsnake[i] = xsnake[i - 1];
            ysnake[i] = ysnake[i - 1];
        }
        // ? Updating The Head Parts.
        switch (dir) {
            case 'U':
                ysnake[0] = ysnake[0] - unit;
                break;
            case 'D':
                ysnake[0] = ysnake[0] + unit;
                break;
            case 'L':
                xsnake[0] = xsnake[0] - unit;
                break;
            case 'R':
                xsnake[0] = xsnake[0] + unit;
                break;
        }
    }

    public void checkScore() {
        // ? Checking If The Head And The Body Coincides.
        if (fx == xsnake[0] && fy == ysnake[0]) {
            body++;
            score++;
            spawnFood();
        }
    }

    public class Key extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    if (dir != 'D')
                        dir = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (dir != 'U')
                        dir = 'D';
                    break;
                case KeyEvent.VK_LEFT:
                    if (dir != 'R')
                        dir = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (dir != 'L')
                        dir = 'R';
                    break;
                case KeyEvent.VK_R:
                    if (!flag) {
                        // ? Restarting The Game On Pressing R.
                        score = 0;
                        body = 3;
                        dir = 'R';
                        Arrays.fill(xsnake, 0);
                        Arrays.fill(ysnake, 0);
                        game_start();
                    }
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(flag){
            move();
            checkScore();
            checkHit();
        }
        repaint();
    }
}