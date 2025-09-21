import java.awt.Color;
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code Robot} representa un robot dentro de la simulación Silk Road.
 * 
 * Cada robot tiene:
 * <ul>
 *   <li>Una posición inicial en la carretera</li>
 *   <li>Una posición actual</li>
 *   <li>Una representación visual (círculo de color)</li>
 * </ul>
 * 
 * El robot puede moverse, regresar a su posición inicial y mostrarse u ocultarse.
 */
public class Robot {
    private int initialLocation;
    private int currentLocation;
    private Circle visualRepresentation;
    private static final String[] COLORS = {"red", "blue", "green", "yellow", "magenta", "black"};
    private static int colorIndex = 0; 
    private List<Integer> profits;

    /**
     * Crea un robot en una ubicación inicial específica.
     *
     * @param location posición inicial del robot
     */
    public Robot(int location) {
        this.initialLocation = location;
        this.currentLocation = location;
        this.visualRepresentation = new Circle();
        this.visualRepresentation.changeSize(20); 
        this.visualRepresentation.moveHorizontal(location); 
        this.visualRepresentation.moveVertical(60); 
        assignColor();
        this.profits = new ArrayList<>();
    }

    /** @return ubicación inicial del robot */
    public int getInitialLocation() {
        return initialLocation;
    }

    /** @return ubicación actual del robot */
    public int getCurrentLocation() {
        return currentLocation;
    }

    /**
     * Cambia la posición actual del robot.
     *
     * @param location nueva ubicación
     */
    public void setCurrentLocation(int location) {
        this.currentLocation = location;
    }

    /**
     * Mueve el robot a una ubicación objetivo.
     *
     * @param targetLocation nueva ubicación
     */
    public void moveTo(int targetLocation) {
        visualRepresentation.moveHorizontal(targetLocation - this.currentLocation);
        this.currentLocation = targetLocation;
    }

    /** Regresa al robot a su posición inicial. */
    public void returnToInitialPosition() {
        visualRepresentation.moveHorizontal(this.initialLocation - this.currentLocation);
        this.currentLocation = this.initialLocation;
    }

    /** Hace visible la representación gráfica del robot. */
    public void makeVisible() {
        visualRepresentation.makeVisible();
    }

    /** Oculta la representación gráfica del robot. */
    public void makeInvisible() {
        visualRepresentation.makeInvisible();
    }

    /** Asigna un color al robot de forma cíclica. */
    private void assignColor() {
        visualRepresentation.changeColor(COLORS[colorIndex]);
        colorIndex = (colorIndex + 1) % COLORS.length; 
    }

    /** Registrar una ganancia de movimiento */
    public void addProfit(int profit) {
        profits.add(profit);
    }

    /** Devuelve todas las ganancias registradas */
    public List<Integer> getProfits() {
        return profits;
    }

    /** Devuelve la ganancia total acumulada */
    public int getTotalProfit() {
        return profits.stream().mapToInt(Integer::intValue).sum();
    }
}
