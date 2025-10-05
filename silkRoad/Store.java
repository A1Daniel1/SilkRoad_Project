
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
 *
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class Store {
    private int location;
    private int initialTenges;
    private int currentTenges;
    private Rectangle visualRepresentation;
    private static final String[] COLORS = {"purple", "cyan", "pink", "brown", "navy", "lime"};
    private static int colorIndex = 0;
    private int timesEmptied;
    private String originalColor;
    private int cellX;
    private int cellY;

    /**
     * Constructor que crea una nueva tienda.
     *
     * @param road la carretera
     * @param location posición de la tienda en la carretera
     * @param tenges cantidad inicial de tenges
     */
    public Store(Road road, int location, int tenges) {
        this.location = location;
        this.initialTenges = tenges;
        this.currentTenges = tenges;
        this.visualRepresentation = new Rectangle();
        this.visualRepresentation.changeSize(30, 30);
        this.cellX = road.getX(location);
        this.cellY = road.getY(location);
        int x = cellX + 5; // center 30x30 in 40x40 cell, so +5 for left
        int y = cellY + 5;
        this.visualRepresentation.setPosition(x, y);
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
     * Las tiendas con diferentes cantidades de tenges tienen diferentes tamaños.
     */
    public void updateAppearance() {
        int size;
        if (currentTenges == 0) {
            visualRepresentation.changeColor("grey");
            size = 20;
        } else {
            visualRepresentation.changeColor(originalColor);
            // Tamaño proporcional a los tenges actuales, entre 20 y 30
            size = 20 + (currentTenges * 10 / initialTenges);
            if (size > 30) size = 30;
        }
        visualRepresentation.changeSize(size, size);
        // Ajustar posición para centrar en la celda 40x40
        int offset = (40 - size) / 2; // offset desde esquina de celda
        visualRepresentation.setPosition(cellX + offset, cellY + offset);
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