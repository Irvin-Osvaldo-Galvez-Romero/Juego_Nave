package Juegos;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class nave {
    private int x, y;
    private int dx, dy;
    private Image image;
    private int lives; // Atributo para las vidas
    private int width, height; // Variables para el tamaño de la imagen

    public nave() {
        // Constructor
        imagennave();
        x = 400; // Posición inicial
        y = 500;
        lives = 3; // Inicializar vidas
    }

    // Métodos para obtener y restar vidas
    public int vidas() {
        return lives;
    }
     public void reiniciarvidas() {
        lives = 3; // Reiniciar vidas a 3
    }

    public void perder() {
        lives--;
    }

    private void imagennave() {
        ImageIcon ii = new ImageIcon(getClass().getResource("/images/spaceship.png"));
        image = ii.getImage();
        width = image.getWidth(null); // Obtener ancho de la imagen
        height = image.getHeight(null); // Obtener alto de la imagen
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, null);
    }

    public void update() {
        x += dx;
        y += dy;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            dx = -2;
        }
        if (key == KeyEvent.VK_RIGHT) {
            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

    public Rectangle bomus() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}