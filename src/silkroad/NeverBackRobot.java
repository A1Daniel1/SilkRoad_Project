package silkroad;
import shapes.*;
/**
 * Robot que nunca se devuelve. Solo puede moverse hacia adelante.
 * 
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class NeverBackRobot extends Robot {
    
    /**
     * Constructor que crea un robot neverback.
     *
     * @param road la carretera
     * @param location posición inicial del robot
     */
    public NeverBackRobot(Road road, int location) {
        super(road, location);
        this.type = "neverback";
        assignColor();
    }
    
    /**
     * Verifica si el robot puede moverse a una ubicación.
     * Solo permite movimientos hacia adelante (posiciones mayores).
     *
     * @param targetLocation ubicación objetivo
     * @return true si el movimiento es hacia adelante
     */
    @Override
    public boolean canMoveTo(int targetLocation) {
        return targetLocation >= this.currentLocation;
    }
}