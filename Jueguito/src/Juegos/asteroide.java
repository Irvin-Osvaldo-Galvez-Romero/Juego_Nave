package Juegos;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

public class asteroide {
    private int x, y;
    private int dy;
    private int width, height;
    private BufferedImage image; // Imagen del asteroide

    public asteroide() {
        Random rand = new Random();
        this.x = rand.nextInt(600); // Ajusta el valor máximo según tu diseño de pantalla
        this.y = 0;
        this.dy = 1; // Velocidad de caída del asteroide

        try {
            // Cargar la imagen del asteroide desde un archivo
            image = ImageIO.read(getClass().getResource("/images/asteroid.png"));
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        y += dy;
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, null);
        } else {
            // Dibujar un rectángulo blanco como alternativa si la imagen no se carga correctamente
            g.setColor(Color.WHITE);
            g.fillRect(x, y, width, height);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getY() {
        return y;
    }
}
