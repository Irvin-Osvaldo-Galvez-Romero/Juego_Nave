package Juegos;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import java.net.URL; 

public class Juego extends JPanel implements ActionListener, KeyListener {
    private boolean inGame = false;
    private Timer timer;
    private nave spaceship;
    private ArrayList<asteroide> asteroids;
    private ArrayList<bala> bullets;
    private Image background; 

    public Juego() {
        setFocusable(true);
        setBackground(Color.BLACK); // Fondo negro para el espacio
        addKeyListener(this);

        spaceship = new nave();
        asteroids = new ArrayList<>();
        bullets = new ArrayList<>();

        timer = new Timer(15, this); // Actualiza el juego cada 15 ms

        // Cargar el fondo GIF
        URL backgroundUrl = getClass().getResource("/images/background.gif");
        if (backgroundUrl != null) {
            ImageIcon ii = new ImageIcon(backgroundUrl);
            background = ii.getImage();
        } else {
            System.err.println("Error: No se encontró el fondo GIF en la ruta /images/background.gif.");
        }
    }

    public void iniciar() {
        inGame = true;
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }
        if (inGame) {
            vidas(g);
        } else {
            inicio(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void inicio(Graphics g) {
        // Texto de inicio
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("Presiona Enter para iniciar", 200, 300);
    }

    private void vidas(Graphics g) {
        spaceship.draw(g);
        for (asteroide asteroid : asteroids) {
            asteroid.draw(g);
        }
        for (bala bullet : bullets) {
            bullet.draw(g);
        }

        // Dibujar vidas restantes
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Vidas: " + spaceship.vidas(), 20, 20);
    }

    public void actionPerformed(ActionEvent e) {
        actualizacion();
        repaint();
    }

    private void actualizacion() {
        spaceship.update();
        Generarasteroides(); // Generar nuevos asteroides
        Moverasteroides(); // Mover asteroides existentes
        Posbalas(); // Actualizar balas
        coalicion(); // Verificar colisiones
    }

    private void Generarasteroides() {
        // Generar asteroides nuevos aleatoriamente
        if (Math.random() < 0.02) { // Ajusta el valor para controlar la frecuencia
            asteroids.add(new asteroide());
        }
    }

    private void Moverasteroides() {
        // Mover asteroides y eliminarlos si salen de la pantalla
        for (int i = 0; i < asteroids.size(); i++) {
            asteroide asteroid = asteroids.get(i);
            asteroid.actualizar();
            if (asteroid.getY() > getHeight()) {
                asteroids.remove(i);
                i--; // Retroceder índice después de eliminar
            }
        }
    }

    private void Posbalas() {
        // Actualizar posición de las balas y eliminar las que salgan de la pantalla
        for (int i = 0; i < bullets.size(); i++) {
            bala bullet = bullets.get(i);
            bullet.actualizar();
            if (bullet.getY() < 0) { // Si la bala sale de la pantalla hacia arriba
                bullets.remove(i);
                i--; // Retroceder índice después de eliminar
            }
        }
    }

private void coalicion() {
    // Verificar colisiones entre balas y asteroides
    for (int i = 0; i < bullets.size(); i++) {
        bala bullet = bullets.get(i);
        Rectangle bulletRect = bullet.getBounds();
        for (int j = 0; j < asteroids.size(); j++) {
            asteroide asteroid = asteroids.get(j);
            Rectangle asteroidRect = asteroid.getBounds();
            if (bulletRect.intersects(asteroidRect)) {
                // Si hay colisión entre bala y asteroide, eliminar ambos
                bullets.remove(i);
                asteroids.remove(j);
                i--; // Retroceder índice después de eliminar bala
                break; // Salir del bucle interno
            }
        }
    }

    // Verificar colisiones entre nave y asteroides
    Rectangle shipRect = spaceship.bomus();
    int i = 0;
    while (i < asteroids.size()) {
        asteroide asteroid = asteroids.get(i);
        Rectangle asteroidRect = asteroid.getBounds();
        if (shipRect.intersects(asteroidRect)) {
            // Colisión detectada con la nave, restar vida
            asteroids.remove(i);
            spaceship.perder();
            if (spaceship.vidas() <= 0) {
                // Si se quedan sin vidas, finalizar juego
                inGame = false;
                timer.stop();
                spaceship.reiniciarvidas(); // Reiniciar vidas al perder el juego
                break; // Salir del bucle si se acaban las vidas
            }
        } else {
            i++;
        }
    }
}

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!inGame && key == KeyEvent.VK_ENTER) {
            iniciar();
        }
        if (inGame) {
            spaceship.keyPressed(e);
            if (key == KeyEvent.VK_SPACE) {
                // Disparar bala
                bullets.add(new bala(spaceship.getX(), spaceship.getY()));
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        spaceship.keyReleased(e);
    }
}
