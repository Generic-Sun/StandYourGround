import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {
    private Timer timer;
    private int points;
    private Player player;
    private List<Enemy> enemies;
    private boolean inGame;
    private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private final int width = (int) size.getWidth();
    private final int height = (int) size.getHeight();
    private final int delay = 15;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        addMouseListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        inGame = true;

        setPreferredSize(new Dimension(width, height));

        player = new Player(width / 2, (height - 48) / 2);

        initEnemies();

        timer = new Timer(delay, this);
        timer.start();
    }

    public void initEnemies() {
        enemies = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            int j = (int) (Math.random() * 4 + 1);
            if (j == 1) {
                enemies.add(new Enemy((int) (Math.random() * width), (int) (Math.random() * -500)));
            } else if (j == 2) {
                enemies.add(new Enemy((int) (Math.random() * width), (int) (Math.random() * (height + 500)) + height));
            } else if (j == 3) {
                enemies.add(new Enemy((int) (Math.random() * (width + 500)) + width, (int) (Math.random() * height)));
            } else if (j == 4) {
                enemies.add(new Enemy((int) (Math.random() * -500), (int) (Math.random() * height)));
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (inGame) {
            drawObjects(g);
        } else {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics g) {
        if (player.isVisible()) {
            g.drawImage(player.getImage(), (int) player.getX(), (int) player.getY(), this);
        }

        List<Projectile> p = player.getProjectiles();

        for (Projectile pr : p) {
            if (pr.isVisible()) {
                g.drawImage(pr.getImage(), (int) pr.getX(), (int) pr.getY(), this);
            }
        }

        for (Enemy e : enemies) {
            if (e.isVisible()) {
                g.drawImage(e.getImage(), (int) e.getX(), (int) e.getY(), this);
            }
        }

        String str = "Score: " + points;
        Font f = new Font("Helvetica", Font.BOLD, 24);

        g.setColor(Color.WHITE);
        g.setFont(f);
        g.drawString(str, 5, 25);
    }

    private void drawGameOver(Graphics g) {
        String str = "Score: " + points;
        Font f = new Font("Helvetica", Font.BOLD, 24);
        FontMetrics fm = getFontMetrics(f);

        g.setColor(Color.WHITE);
        g.setFont(f);
        g.drawString(str, (width - fm.stringWidth(str)) / 2, height / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        inGame();

        checkCollision();

        updateProjectiles();
        updateEnemies();

        checkCollision();

        repaint();
    }

    private void inGame() {
        if (!inGame) {
            timer.stop();
        }
    }

    private void updateProjectiles() {
        List<Projectile> p = player.getProjectiles();

        for (int i = 0; i < p.size(); i++) {
            Projectile pr = p.get(i);

            if (pr.isVisible()) {
                pr.move();
            } else {
                p.remove(i);
            }
        }
    }

    private void updateEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);

            if (e.isVisible()) {
                e.move(player.getY(), player.getX());
            } else {
                enemies.remove(i);
            }
        }
    }

    private void checkCollision() {
        Rectangle r3 = player.getBounds();

        for (Enemy e : enemies) {

            Rectangle r2 = e.getBounds();

            if (r2.intersects(r3)) {

                player.setVisible(false);
                e.setVisible(false);
                inGame = false;
            }
        }

        List<Projectile> p = player.getProjectiles();

        for (Projectile pr : p) {

            Rectangle r1 = pr.getBounds();

            for (Enemy e : enemies) {

                Rectangle r2 = e.getBounds();

                if (r1.intersects(r2)) {
                    points += e.getPoints();
                    pr.setVisible(false);
                    for (int i = 0; i < 20; i++) {
                        int j = (int) (Math.random() * 4 + 1);
                        if (j == 1) {
                            e.setPosition((int) (Math.random() * width), (int) (Math.random() * -500));
                        } else if (j == 2) {
                            e.setPosition((int) (Math.random() * width),
                                    (int) (Math.random() * (height + 500)) + height);
                        } else if (j == 3) {
                            e.setPosition((int) (Math.random() * (width + 500)) + width,
                                    (int) (Math.random() * height));
                        } else if (j == 4) {
                            e.setPosition((int) (Math.random() * -500), (int) (Math.random() * height));
                        }
                    }
                }
            }
        }
    }

    private class TAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            player.mousePressed(e);
        }
    }
}
