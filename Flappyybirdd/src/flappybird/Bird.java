package flappybird;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Bird {
    public float x, y, vx, vy;
    public static final int RAD = 25;
    private Image img;
    private Clip jumpSound;
    private Clip hitSound;

    public Bird() {
        x = FlappyBird.WIDTH / 4;
        y = FlappyBird.HEIGHT / 3;
        try {
            img = ImageIO.read(new File("flappybird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("jump.wav"));
            jumpSound = AudioSystem.getClip();
            jumpSound.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("hit.wav"));
            hitSound = AudioSystem.getClip();
            hitSound.open(audioInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void physics() {
        x += vx;
        y += vy;
        vy += 0.5f;
    }

    public void update(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawImage(img, Math.round(x - RAD), Math.round(y - RAD), 2 * RAD, 2 * RAD, null);
    }

    public void jump() {
        vy = -8;
        playJumpSound();
    }

    public void reset() {
        x = 640 / 4;
        y = 640 / 4;
        vx = vy = 0;
    }
    private void playJumpSound() {
        if (jumpSound.isRunning()) {
            jumpSound.stop();
        }
        jumpSound.setFramePosition(0);
        jumpSound.start();
    }
    public void playHitSound() {
        if (hitSound.isRunning()) {
            hitSound.stop();
        }
        hitSound.setFramePosition(0);
        hitSound.start();
    }
    @Override
    protected void finalize() throws Throwable {
        if (jumpSound != null) {
            jumpSound.close();
        }
        if (hitSound != null) {
            hitSound.close();
        }

    }
}