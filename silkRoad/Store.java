import java.awt.Color;
import java.util.Random;
import java.util.Arrays;

/**
 * La clase {@code Store} representa una tienda dentro de la simulación de Silk Road.
 * 
 * Cada tienda tiene:
 * <ul>
 *   <li>Una ubicación en la carretera</li>
 *   <li>Una cantidad inicial de tenges (moneda)</li>
 *   <li>Una cantidad actual de tenges</li>
 *   <li>Una representación visual (rectángulo de color)</li>
 * </ul>
 * 
 * El color de la tienda se asigna de forma cíclica a partir de una lista predefinida.
 * Las tiendas desocupadas lucen diferentes a las que tienen mercancía.
 */
public class Store {
    private int location;
    private int initialTenges;
    private int currentTenges;
    private Rectangle visualRepresentation;
    private static final String[] COLORS = {"red", "blue", "green", "yellow", "magenta", "orange"};
    private static int colorIndex = 0; 
    private int timesEmptied;
    private String originalColor;

    /**
     * Constructor que crea una nueva tienda.
     *
     * @param location posición horizontal de la tienda en la carretera
     * @param tenges cantidad inicial de tenges
     */
    public Store(int location, int tenges) {
        this.location = location;
        this.initialTenges = tenges;
        this.currentTenges = tenges;
        this.visualRepresentation = new Rectangle();
        this.visualRepresentation.changeSize(30, 30);
        this.visualRepresentation.moveHorizontal(location); 
        this.visualRepresentation.moveVertical(50);
        this.timesEmptied = 0;
        assignColor();
    }

    public int getLocation() {
        return location;
    }

    public int getInitialTenges() {
        return initialTenges;
    }

    public int getCurrentTenges() {
        return currentTenges;
    }

    public int getTimesEmptied() { 
        return timesEmptied; 
    }

    /**
     * Vacía la tienda y retorna la cantidad de tenges.
     *
     * @return cantidad de tenges recolectados
     */
    public int empty() {
        int collected = this.currentTenges;
        if (collected > 0) {
            this.currentTenges = 0;
            timesEmptied++;
            updateAppearance();
        }
        return collected;
    }

    public void setCurrentTenges(int amount) {
        this.currentTenges = amount;
        updateAppearance();
    }

    /**
     * Restablece la cantidad de tenges al valor inicial.
     */
    public void resupply() {
        this.currentTenges = this.initialTenges;
        updateAppearance();
    }

    public void makeVisible() {
        visualRepresentation.makeVisible();
    }

    public void makeInvisible() {
        visualRepresentation.makeInvisible();
    }

    /**
     * Actualiza la apariencia de la tienda según su estado.
     * Las tiendas vacías se ven grises y más pequeñas.
     */
    public void updateAppearance() {
        if (currentTenges == 0) {
            visualRepresentation.changeColor("grey");
            visualRepresentation.changeSize(20, 20);
        } else {
            visualRepresentation.changeColor(originalColor);
            visualRepresentation.changeSize(30, 30);
        }
    }

    /**
     * Resetea el contador de veces vaciada.
     */
    public void resetTimesEmptied() {
        this.timesEmptied = 0;
    }

    /**
     * Asigna un color a la tienda de forma cíclica.
     */
    private void assignColor() {
        originalColor = COLORS[colorIndex];
        visualRepresentation.changeColor(originalColor);
        colorIndex = (colorIndex + 1) % COLORS.length;
    }
}