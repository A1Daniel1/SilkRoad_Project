package silkroad;
import shapes.*;
/**
 * Tienda luchadora que solo permite ser vaciada por robots con más dinero que ella.
 * 
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class FighterStore extends Store {
    
    /**
     * Constructor que crea una tienda fighter.
     *
     * @param road la carretera
     * @param location posición de la tienda en la carretera
     * @param tenges cantidad inicial de tenges
     */
    public FighterStore(Road road, int location, int tenges) {
        super(road, location, tenges);
        this.type = "fighter";
        updateAppearance();
    }
    
    /**
     * Verifica si un robot puede vaciar esta tienda.
     * Solo robots con más dinero (ganancia total) que los tenges actuales pueden vaciarla.
     *
     * @param robot el robot que intenta vaciar la tienda
     * @return true si el robot tiene más dinero que la tienda
     */
    @Override
    public boolean canBeEmptiedBy(Robot robot) {
        return robot.getTotalProfit() > this.currentTenges;
    }
}