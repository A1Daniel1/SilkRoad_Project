package silkroad;
import shapes.*;
/**
 * Clase base Store: representa una tienda en la carretera.
 * 
 * Cada tienda tiene:
 * - Una posición.
 * - Cantidad inicial y actual de tenges.
 * - Un tipo que determina su comportamiento.
 * 
 * Las subclases pueden sobreescribir el método canBeEmptiedBy() para personalizar
 * las condiciones bajo las cuales pueden ser vaciadas.
 * 
 * @author 
 * Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class Store {
    protected int location;
    protected int initialTenges;
    protected int currentTenges;
    protected Rectangle visualRepresentation;
    protected int timesEmptied;
    protected int cellX;
    protected int cellY;
    protected String type; // normal, fighter, autonomous, robinhood

    /**
     * Constructor base para todas las tiendas.
     *
     * @param road      carretera en la que se ubica la tienda
     * @param location  posición en la carretera
     * @param tenges    cantidad inicial de tenges
     */
    public Store(Road road, int location, int tenges) {
        this.location = location;
        this.initialTenges = tenges;
        this.currentTenges = tenges;
        this.visualRepresentation = new Rectangle();
        this.visualRepresentation.changeSize(30, 30);

        this.cellX = road.getX(location);
        this.cellY = road.getY(location);

        int x = cellX + 5;
        int y = cellY + 5;
        this.visualRepresentation.setPosition(x, y);
        this.timesEmptied = 0;
        this.type = "normal";

        updateAppearance();
    }

    public int getLocation() { return location; }
    public int getInitialTenges() { return initialTenges; }
    public int getCurrentTenges() { return currentTenges; }
    public int getTimesEmptied() { return timesEmptied; }
    public String getType() { return type; }

    public boolean canBeEmptiedBy(Robot robot) {
        return true;
    }

    public int empty(Robot robot) {
        if (!canBeEmptiedBy(robot)) return 0;

        int collected = this.currentTenges;
        if (collected > 0) {
            this.currentTenges = 0;
            timesEmptied++;
            updateAppearance();
        }
        return collected;
    }

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

    public void resupply() {
        this.currentTenges = this.initialTenges;
        updateAppearance();
    }

    public void makeVisible() { visualRepresentation.makeVisible(); }
    public void makeInvisible() { visualRepresentation.makeInvisible(); }

    /**
     * Actualiza la apariencia según el tipo de tienda y su estado.
     */
    public void updateAppearance() {
        int size;
        if (currentTenges == 0) {
            // Tienda vacía
            switch (type) {
                case "fighter":
                    visualRepresentation.changeColor("gray");
                    break;
                case "autonomous":
                    visualRepresentation.changeColor("lightGray");
                    break;
                case "robinhood":
                    visualRepresentation.changeColor("cyan");
                    break;
                default:
                    visualRepresentation.changeColor("black");
            }
            size = 20;
        } else {
            // Tienda con dinero
            switch (type) {
                case "fighter":
                    visualRepresentation.changeColor("red");
                    break;
                case "autonomous":
                    visualRepresentation.changeColor("green");
                    break;
                case "robinhood":
                    visualRepresentation.changeColor("orange");
                    break;
                default:
                    visualRepresentation.changeColor("blue");
            }
            size = 20 + (currentTenges * 10 / initialTenges);
            if (size > 30) size = 30;
        }

        visualRepresentation.changeSize(size, size);
        int offset = (40 - size) / 2;
        visualRepresentation.setPosition(cellX + offset, cellY + offset);
    }

    public void resetTimesEmptied() { this.timesEmptied = 0; }
}

