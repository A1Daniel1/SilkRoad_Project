package silkroad;
import shapes.*;
/**
 * Tienda RobinHood que solo permite ser vaciada por robots con menos dinero que ella.
 * 
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class RobinHoodStore extends Store {

    /**
     * Constructor que crea una tienda RobinHood.
     *
     * @param road la carretera
     * @param location posición de la tienda en la carretera
     * @param tenges cantidad inicial de tenges
     */
    public RobinHoodStore(Road road, int location, int tenges) {
        super(road, location, tenges);
        this.type = "robinhood";
        updateAppearance();
    }

    /**
     * Verifica si un robot puede vaciar esta tienda.
     * Solo robots con menos dinero (ganancia total) que los tenges actuales pueden vaciarla.
     *
     * @param robot el robot que intenta vaciar la tienda
     * @return true si el robot tiene menos dinero que la tienda
     */
    @Override
    public boolean canBeEmptiedBy(Robot robot) {
        return robot.getTotalProfit() < this.currentTenges;
    }
}
