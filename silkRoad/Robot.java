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
    private int initialLocation;
    private int currentLocation;
    private Circle visualRepresentation;
    private Road road;
    private static final String[] COLORS = {"red", "blue", "green", "yellow", "magenta", "black", "orange", "pink", "cyan", "purple"};
    private static int colorIndex = 0;
    private List<Integer> profits;
    private String originalColor;
    private boolean isBlinking;
    private Timer blinkTimer;
    private boolean isVisible;
    private int blinkCount;

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
        int x = road.getX(location) + 20;
        int y = road.getY(location) + 20;
        this.visualRepresentation.setPosition(x, y);
        this.profits = new ArrayList<>();
        this.isBlinking = false;
        this.isVisible = false;
        this.blinkCount = 0;
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

    /**
     * Mueve el robot a una ubicación objetivo.
     *
     * @param targetLocation nueva ubicación
     */
    public void moveTo(int targetLocation) {
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
     * Asigna un color al robot de forma cíclica.
     */
    private void assignColor() {
        originalColor = COLORS[colorIndex];
        visualRepresentation.changeColor(originalColor);
        colorIndex = (colorIndex + 1) % COLORS.length; 
    }

    /**
     * Configura el timer para el efecto de parpadeo.
     * El robot parpadea 6 veces (12 cambios de color) y luego se detiene.
     */
    private void setupBlinkTimer() {
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