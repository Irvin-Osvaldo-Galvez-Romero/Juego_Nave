package Juegos;

import java.awt.*;

public class bala {
    private int x, y;
    private int dy;
    private int width, height;
    private boolean visible; // Indica si la bala es visible o no

    public bala(int x, int y) {
        this.x = x;
        this.y = y;
        this.dy = -2; // Velocidad de la bala hacia arriba

        // Ajusta el tamaño de la bala según tu diseño
        this.width = 5;
        this.height = 10;
        this.visible = true; // La bala es visible al crearse
    }

    public void actualizar() {
        y += dy;
    }

    public void draw(Graphics g) {
        if (visible) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, width, height); // Dibuja un rectángulo como ejemplo, ajusta según tu diseño
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void visible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getY() {
        return y;
    }
}
