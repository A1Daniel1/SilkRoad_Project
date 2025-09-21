import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code Road} representa la carretera de la Ruta de la Seda.
 * 
 * Dibuja la carretera como una espiral cuadrada formada por franjas
 * rectangulares continuas. Cada tramo (derecha, abajo, izquierda, arriba)
 * se dibuja como un único rectángulo largo, de modo que la carretera
 * quede visualmente continua.
 * 
 * @author Daniel
 * @version 2025-2
 */
public class Road {
    private List<Rectangle> segments;    // rectángulos del camino
    private int canvasWidth;
    private int canvasHeight;
    private int length;

    public Road(int length) {
        this.segments = new ArrayList<>(); // Inicializar la lista
        this.canvasWidth = length;
        this.canvasHeight = 200;
        this.length = length;
        drawRoad(this.length);
    }

    /**
     * Dibuja el camino por secciones
     */
    public void drawRoad(int length) { 
        Rectangle camino = new Rectangle();
        camino.moveHorizontal(-50);
        camino.moveVertical(60);
        camino.changeColor("black");
        camino.changeSize(2, length);
        camino.makeVisible();
        
        segments.add(camino); // Agregar a la lista para que funcionen los otros métodos
    }

    /** Hace visible la carretera */
    public void makeVisible() {
        for (Rectangle seg : segments) seg.makeVisible();
    }

    /** Hace invisible la carretera */
    public void makeInvisible() {
        for (Rectangle seg : segments) seg.makeInvisible();
    }

    /** Limpia los segmentos */
    public void clear() {
        if (segments != null) { // Verificación de seguridad
            for (Rectangle seg : segments) seg.makeInvisible();
            segments.clear();
        }
    }
}