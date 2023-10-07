import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Arrays;
import java.util.concurrent.*;

public class SnakePanel extends JPanel implements ActionListener {
    private static final int S_HEIGHT = 600;
    private static final int S_WIDTH = 1200;
    private static final int GAME_UNIT_SIZE = 50;
    public Timer timer;
    private boolean game_flag = false;
    private static int DELAY = 200;
    private int foodX, foodY;
    private int snakeLength = 2;
    private int foodEaten;
    private static final int G_SIZE = (S_HEIGHT * S_WIDTH) / (GAME_UNIT_SIZE * GAME_UNIT_SIZE);
    private static int[] snakeX = new int[G_SIZE];
    private static int[] snakeY = new int[G_SIZE];
    private char dir = 'R';
    private static Random rand = new Random();
    private boolean isPaused = false;
    private Theme theme;

    public SnakePanel() {
        setPreferredSize(new Dimension(S_WIDTH, S_HEIGHT));
        setTheme(new HighContrastTheme());
        setBackground(theme.background);
        setFocusable(true);
        addKeyListener(new MyKeys());
        startGame();
    }

    public void startGame() {
        newFoodPosition();
        game_flag = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (game_flag) {
            drawFood(g);
            drawHead(g);
            // draw the snake body
            for (int i = 1; i < snakeLength; i++) {
                g.setColor(theme.snakeBody);
                g.fillRect(snakeX[i], snakeY[i], GAME_UNIT_SIZE, GAME_UNIT_SIZE);
                g.setColor(theme.borderColor);
                g.drawRect(snakeX[i], snakeY[i], GAME_UNIT_SIZE, GAME_UNIT_SIZE);
            }
            g.setColor(Color.BLUE);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics myFont = getFontMetrics(g.getFont());
            g.drawString("Score : " + foodEaten, (S_WIDTH - myFont.stringWidth("Score : " + foodEaten)) / 2,
                    g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    private void drawHead(Graphics g) {
        g.setColor(theme.snakeHead);
        g.fillRect(snakeX[0], snakeY[0], GAME_UNIT_SIZE, GAME_UNIT_SIZE);
        g.setColor(theme.borderColor);
        g.drawRect(snakeX[0], snakeY[0], GAME_UNIT_SIZE, GAME_UNIT_SIZE);
        drawEyes(g);
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics myFont = getFontMetrics(g.getFont());
        g.drawString("Score : " + foodEaten, (S_WIDTH - myFont.stringWidth("Score : " + foodEaten)) / 2,
                g.getFont().getSize());
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        myFont = getFontMetrics(g.getFont());
        g.drawString("Game Over", (S_WIDTH - myFont.stringWidth("Game Over")) / 2, S_HEIGHT / 2);
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        myFont = getFontMetrics(g.getFont());
        g.drawString("Press R to Replay", (S_WIDTH - myFont.stringWidth("Press R to Replay")) / 2,
                S_HEIGHT / 2 - 150);
    }

    public void move() {
        for (int i = snakeLength; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }
        switch (dir) {
            case 'U':
                snakeY[0] = snakeY[0] - GAME_UNIT_SIZE;
                break;
            case 'L':
                snakeX[0] = snakeX[0] - GAME_UNIT_SIZE;
                break;
            case 'D':
                snakeY[0] = snakeY[0] + GAME_UNIT_SIZE;
                break;
            case 'R':
                snakeX[0] = snakeX[0] + GAME_UNIT_SIZE;
                break;
        }
    }

    public void newFoodPosition() {
        foodX = rand.nextInt((int) (S_WIDTH / GAME_UNIT_SIZE)) * GAME_UNIT_SIZE;
        foodY = rand.nextInt((int) (S_HEIGHT / GAME_UNIT_SIZE)) * GAME_UNIT_SIZE;
    }

    public void foodEatenOrnot() {
        if (snakeX[0] == foodX && snakeY[0] == foodY) {
            snakeLength++;
            foodEaten++;
            newFoodPosition();
        }
    }

    public void checkHit() {
        for (int i = snakeLength; i > 0; i--) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                game_flag = false;
            }
        }
        if (snakeX[0] < 0 || snakeX[0] > S_WIDTH || snakeY[0] < 0 || snakeY[0] > S_HEIGHT) {
            game_flag = false;
        }
        if (!game_flag) {
            timer.stop();
        }
    }

    public void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            timer.stop();
        } else {
            timer.start();
        }
    }

    public static void setDelay(int newDelay) {
        DELAY = newDelay;
    }

    public static int getDelay() {
        return DELAY;
    }

    class MyKeys extends KeyAdapter {
        public void keyPressed(KeyEvent event) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if (dir != 'R')
                        dir = 'L';
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if (dir != 'U')
                        dir = 'U';
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if (dir != 'L')
                        dir = 'R';
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if (dir != 'U')
                        dir = 'D';
                    break;
                case KeyEvent.VK_R:
                    if (!game_flag) {
                        foodEaten = 0;
                        snakeLength = 2;
                        dir = 'R';
                        Arrays.fill(snakeX, 0);
                        Arrays.fill(snakeY, 0);
                        startGame();
                    }
                    break;
                case KeyEvent.VK_SPACE:
                    togglePause();
                    break;

            }
        }
    }

    public void drawEyes(Graphics g) {
        g.setColor(theme.eyeColor);
        int eye1X = 0, eye1Y = 0, eye2X = 0, eye2Y = 0;
        switch (dir) {
            case 'R':
                eye1X = 32;
                eye1Y = 7;
                eye2X = 32;
                eye2Y = 32;
                break;
            case 'L':
                eye1X = 7;
                eye1Y = 7;
                eye2X = 7;
                eye2Y = 32;
                break;
            case 'U':
                eye1X = 7;
                eye1Y = 7;
                eye2X = 32;
                eye2Y = 7;
                break;
            case 'D':
                eye1X = 7;
                eye1Y = 32;
                eye2X = 32;
                eye2Y = 32;
                break;
        }
        g.fillOval(snakeX[0] + eye1X, snakeY[0] + eye1Y, 11, 11);
        g.setColor(theme.eyeColor);
        g.fillOval(snakeX[0] + eye2X, snakeY[0] + eye2Y, 11, 11);
    }

    public void drawFood(Graphics g) {
        int size = GAME_UNIT_SIZE - 15;
        int offset = (GAME_UNIT_SIZE - size) / 2;

        g.setColor(theme.foodBody);
        g.fillOval(foodX + offset, foodY + offset, size, size);

        int earSize = size / 3;
        g.setColor(Color.PINK);
        g.fillOval(foodX + offset - earSize / 2, foodY + offset - earSize / 2, earSize, earSize);
        g.fillOval(foodX + offset + size - earSize / 2, foodY + offset - earSize / 2, earSize, earSize);

        int eyeSize = size / 8 + 2;
        int eyeOffset = size / 4;
        g.setColor(theme.foodEyes);
        g.fillOval(foodX + offset + eyeOffset, foodY + offset + eyeOffset, eyeSize, eyeSize);
        g.fillOval(foodX + offset + size - eyeOffset - eyeSize, foodY + offset + eyeOffset, eyeSize, eyeSize);

        int noseSize = size / 6 + 2;
        g.setColor(theme.foodNose);
        g.fillOval(foodX + offset + size / 2 - noseSize / 2, foodY + offset + size / 2 - noseSize / 2, noseSize,
                noseSize);
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    private ExecutorService exe = Executors.newSingleThreadExecutor();

    public void actionPerformed(ActionEvent ae) {
        if (game_flag && !isPaused) {
            move();
            exe.execute(() -> checkHit());
            foodEatenOrnot();
        }
        repaint();
    }

    public void reset() {
        timer.stop();
        Arrays.fill(snakeX, 0);
        Arrays.fill(snakeY, 0);
        dir = 'R';
        foodEaten = 0;
        snakeLength = 2;
        game_flag = false;
        setBackground(theme.background);
        startGame();
    }
}