/**
 * Robot que solo puede moverse en reversa (hacia atrás).
 * 
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class IllBeBackRobot extends Robot {

    /**
     * Constructor que crea un robot illbeback.
     *
     * @param road la carretera
     * @param location posición inicial del robot
     */
    public IllBeBackRobot(Road road, int location) {
        super(road, location);
        this.type = "illbeback";
        assignColor();
    }

    /**
     * Verifica si el robot puede moverse a una ubicación.
     * Solo permite movimientos hacia atrás (posiciones menores).
     *
     * @param targetLocation ubicación objetivo
     * @return true si el movimiento es hacia atrás
     */
    @Override
    public boolean canMoveTo(int targetLocation) {
        return targetLocation <= this.currentLocation;
    }
}
