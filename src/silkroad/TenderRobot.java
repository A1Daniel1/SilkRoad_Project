package silkroad;
import shapes.*;
/**
 * Robot tierno que solo toma la mitad del dinero de las tiendas que visita.
 * 
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class TenderRobot extends Robot {
    
    /**
     * Constructor que crea un robot tender.
     *
     * @param road la carretera
     * @param location posición inicial del robot
     */
    public TenderRobot(Road road, int location) {
        super(road, location);
        this.type = "tender";
        assignColor();
    }
    
    /**
     * Recoge tenges de una tienda.
     * Solo toma la mitad de los tenges disponibles.
     *
     * @param store la tienda de la que recoger
     * @return cantidad de tenges recolectados (la mitad)
     */
    @Override
    public int collectFrom(Store store) {
        if (!store.canBeEmptiedBy(this)) {
            return 0;
        }
        
        int currentTenges = store.getCurrentTenges();
        if (currentTenges <= 0) {
            return 0;
        }
        
        int halfAmount = currentTenges / 2;
        store.setCurrentTenges(currentTenges - halfAmount);
        
        if (halfAmount > 0) {
            // Solo incrementar el contador si realmente tomó algo
            store.timesEmptied++;
        }
        
        return halfAmount;
    }
}