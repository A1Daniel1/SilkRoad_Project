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
 */
public class Store {
    private int location;
    private int initialTenges;
    private int currentTenges;
    private Rectangle visualRepresentation;

    private static final String[] COLORS = {"red", "blue", "green", "yellow", "magenta", "black"};
    private static int colorIndex = 0; 

    private int timesEmptied;

    /**
     * Crea una nueva tienda en una ubicación específica con un capital inicial.
     *
     * @param location posición horizontal de la tienda en la carretera
     * @param tenges   cantidad inicial de tenges
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

    /** @return ubicación de la tienda */
    public int getLocation() {
        return location;
    }

    /** @return cantidad inicial de tenges */
    public int getInitialTenges() {
        return initialTenges;
    }

    /** @return cantidad actual de tenges */
    public int getCurrentTenges() {
        return currentTenges;
    }

    /** @return cantidad de veces que fue desocupada */
    public int getTimesEmptied() { 
        return timesEmptied; 
    }

    /** Vacia la tienda y retorna la cantidad de tenges */
    public int empty() {
        int collected = this.currentTenges;
        if (collected > 0) {
            this.currentTenges = 0;
            timesEmptied++;
        }
        return collected;
    }

    /**
     * Modifica la cantidad actual de tenges.
     * 
     * @param amount nuevo valor de tenges
     */
    public void setCurrentTenges(int amount) {
        this.currentTenges = amount;
    }

    /** Restablece la cantidad de tenges al valor inicial. */
    public void resupply() {
        this.currentTenges = this.initialTenges;
    }

    /** Hace visible la representación gráfica de la tienda. */
    public void makeVisible() {
        visualRepresentation.makeVisible();
    }

    /** Oculta la representación gráfica de la tienda. */
    public void makeInvisible() {
        visualRepresentation.makeInvisible();
    }

    /** Asigna un color a la tienda de forma cíclica. */
    private void assignColor() {
        visualRepresentation.changeColor(COLORS[colorIndex]);
        colorIndex = (colorIndex + 1) % COLORS.length;
    }
}

