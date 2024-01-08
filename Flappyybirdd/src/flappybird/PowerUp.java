package flappybird;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PowerUp {
    private Bird bird;
    private ArrayList<Rectangle> rects;
    private static final int RAD = 25;
    private Image bigPotion;
    private int x; // X-coordinate of the potion
    private int y; // Y-coordinate of the potion
    private int width; // Width of the potion
    private int height; // Height of the potion
    private boolean activated; // Flag to check if the power-up has been activated
    private long activationTime; // Time when the power-up was activated

    // Constructor
    public PowerUp(int x, int y, int width, int height, Bird bird, ArrayList<Rectangle> rects) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bird = bird;
        this.rects = rects;

        try {
            bigPotion = ImageIO.read(new File("bigpotion.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Graphics g) {
        if (activated) {
            // Check if 5 seconds have passed since activation, deactivate the power-up
            long currentTime = System.currentTimeMillis();
            if (currentTime - activationTime >= 5000) {
                deactivate();
            }
        }

        g.setColor(Color.BLACK);
        g.drawImage(bigPotion, Math.round(x - RAD), Math.round(y - RAD), 2 * RAD, 2 * RAD, null);
    }

    public void activate() {
        if (!activated) {
            bird.x=bird.x * 3;
            bird.y=bird.y * 3;// Increase bird size 3 times
            removeCollidingPipes(); // Remove pipes that collide with the bird

            activated = true;
            activationTime = System.currentTimeMillis();
        }
    }

    public void deactivate() {
        if (activated) {
            bird.setSize(bird.getSize() / 3); // Restore bird size to normal
            activated = false;
        }
    }

    private void removeCollidingPipes() {
        ArrayList<Rectangle> pipesToRemove = new ArrayList<>();

        for (Rectangle r : rects) {
            if (r.contains(bird.x, bird.y)) {
                pipesToRemove.add(r);
            }
        }

        rects.removeAll(pipesToRemove);
    }
}