package shapes;
import silkroad.*;

/**
 * Clase abstracta base para las figuras.
 * Centraliza comportamiento común y evita repetición de código.
 *
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 4
 */
public abstract class Figura {

    // 🔒 Encapsulamiento correcto: accesible en subclases, no fuera del paquete
    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    /**
     * Constructor por defecto
     */
    public Figura() {
        xPosition = 0;
        yPosition = 0;
        color = "blue";
        isVisible = false;
    }

    /** Hace visible la figura. */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /** Hace invisible la figura. */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /** Mueve la figura hacia la derecha. */
    public void moveRight() {
        moveHorizontal(20);
    }

    /** Mueve la figura hacia la izquierda. */
    public void moveLeft() {
        moveHorizontal(-20);
    }

    /** Mueve la figura hacia arriba. */
    public void moveUp() {
        moveVertical(-20);
    }

    /** Mueve la figura hacia abajo. */
    public void moveDown() {
        moveVertical(20);
    }

    /** Mueve la figura horizontalmente. */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /** Mueve la figura verticalmente. */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    /** Mueve la figura lentamente en dirección horizontal. */
    public void slowMoveHorizontal(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        distance = Math.abs(distance);

        for (int i = 0; i < distance; i++) {
            xPosition += delta;
            draw();
        }
    }

    /** Mueve la figura lentamente en dirección vertical. */
    public void slowMoveVertical(int distance) {
        int delta = (distance < 0) ? -1 : 1;
        distance = Math.abs(distance);

        for (int i = 0; i < distance; i++) {
            yPosition += delta;
            draw();
        }
    }

    /** Cambia el color de la figura. */
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    /** Establece la posición de la figura. */
    public void setPosition(int x, int y) {
        erase();
        xPosition = x;
        yPosition = y;
        draw();
    }

    /** Dibuja la figura (debe implementarse en cada subclase). */
    public abstract void draw();

    /** Borra la figura del canvas. */
    public void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    public int getXPosition() { return xPosition; }
    public int getYPosition() { return yPosition; }
    public String getColor() { return color; }
    public boolean isVisible() { return isVisible; }
}
