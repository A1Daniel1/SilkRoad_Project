import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

/**
 * La clase Robot representa un robot dentro de la simulación Silk Road.
 * El robot con mayor ganancia parpadea para destacarse visualmente.
 *
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class Robot {
    protected int initialLocation;
    protected int currentLocation;
    protected Circle visualRepresentation;
    protected Road road;
    protected List<Integer> profits;
    protected String originalColor;
    protected boolean isBlinking;
    protected Timer blinkTimer;
    protected boolean isVisible;
    protected int blinkCount;
    protected String type; // "normal", "neverback", "tender"

    /**
     * Constructor que crea un robot en una ubicación inicial específica.
     *
     * @param road la carretera
     * @param location posición inicial del robot
     */
    public Robot(Road road, int location) {
        this.road = road;
        this.initialLocation = location;
        this.currentLocation = location;
        this.visualRepresentation = new Circle();
        this.visualRepresentation.changeSize(20);
        
        // Validar que la ubicación existe en el camino
        int x = road.getX(location);
        int y = road.getY(location);
        
        this.visualRepresentation.setPosition(x + 20, y + 20);
        this.profits = new ArrayList<>();
        this.isBlinking = false;
        this.isVisible = false;
        this.blinkCount = 0;
        this.type = "normal";
        assignColor();
        setupBlinkTimer();
    }

    public int getInitialLocation() {
        return initialLocation;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(int location) {
        this.currentLocation = location;
    }

    public String getType() {
        return type;
    }

    /**
     * Verifica si el robot puede moverse a una ubicación.
     * Puede ser sobreescrito por subclases.
     *
     * @param targetLocation ubicación objetivo
     * @return true si el movimiento es válido
     */
    public boolean canMoveTo(int targetLocation) {
        return true; // Los robots normales pueden moverse a cualquier lado
    }

    /**
     * Mueve el robot a una ubicación objetivo.
     *
     * @param targetLocation nueva ubicación
     */
    public void moveTo(int targetLocation) {
        if (!canMoveTo(targetLocation)) {
            return;
        }
        int targetX = road.getX(targetLocation) + 20;
        int targetY = road.getY(targetLocation) + 20;
        visualRepresentation.setPosition(targetX, targetY);
        this.currentLocation = targetLocation;
    }

    /**
     * Regresa al robot a su posición inicial.
     */
    public void returnToInitialPosition() {
        int initialX = road.getX(this.initialLocation) + 20;
        int initialY = road.getY(this.initialLocation) + 20;
        visualRepresentation.setPosition(initialX, initialY);
        this.currentLocation = this.initialLocation;
    }

    public void makeVisible() {
        visualRepresentation.makeVisible();
        this.isVisible = true;
    }

    public void makeInvisible() {
        this.isVisible = false;
        stopBlinking();
        visualRepresentation.makeInvisible();
    }

    /**
     * Registra una ganancia de movimiento.
     *
     * @param profit ganancia obtenida en el movimiento
     */
    public void addProfit(int profit) {
        profits.add(profit);
    }

    public List<Integer> getProfits() {
        return new ArrayList<>(profits);
    }

    /**
     * @return la ganancia total acumulada
     */
    public int getTotalProfit() {
        return profits.stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Recoge tenges de una tienda.
     * Puede ser sobreescrito por subclases para comportamiento diferente.
     *
     * @param store la tienda de la que recoger
     * @return cantidad de tenges recolectados
     */
    public int collectFrom(Store store) {
        return store.empty(this);
    }

    /**
     * Inicia el efecto de parpadeo para el robot con mayor ganancia.
     * El robot parpadeará 6 veces y luego se detendrá.
     */
    public void startBlinking() {
        if (!isBlinking && isVisible) {
            isBlinking = true;
            blinkCount = 0;
            blinkTimer.start();
        }
    }

    /**
     * Detiene el efecto de parpadeo.
     */
    public void stopBlinking() {
        if (isBlinking) {
            isBlinking = false;
            blinkCount = 0;
            if (blinkTimer != null) {
                blinkTimer.stop();
            }
            visualRepresentation.changeColor(originalColor);
        }
    }

    /**
     * Limpia todas las ganancias registradas.
     */
    public void clearProfits() {
        profits.clear();
    }

    /**
     * Destruye el robot y libera recursos.
     */
    public void destroy() {
        stopBlinking();
        if (blinkTimer != null) {
            blinkTimer.stop();
            blinkTimer = null;
        }
        makeInvisible();
    }

    /**
     * Asigna un color al robot dependiendo de su tipo.
     */
    protected void assignColor() {
        String newColor;

        switch (type) {
            case "neverback":
                newColor = "red";
                break;
            case "tender":
                newColor = "yellow";
                break;
            case "illbeback":
                newColor = "magenta";
                break;
            default:
                newColor = "blue";
                break;
        }

        originalColor = newColor;
        visualRepresentation.changeColor(newColor);
    }


    /**
     * Configura el timer para el efecto de parpadeo.
     * El robot parpadea 6 veces (12 cambios de color) y luego se detiene.
     */
    protected void setupBlinkTimer() {
        blinkTimer = new Timer(400, new ActionListener() {
            private boolean showOriginal = true;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBlinking && isVisible && blinkCount < 12) {
                    if (showOriginal) {
                        visualRepresentation.changeColor("white");
                    } else {
                        visualRepresentation.changeColor(originalColor);
                    }
                    showOriginal = !showOriginal;
                    blinkCount++;
                    
                    if (blinkCount >= 12) {
                        stopBlinking();
                    }
                }
            }
        });
    }
}