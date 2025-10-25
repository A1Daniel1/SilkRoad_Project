import java.util.Random;

/**
 * Tienda autónoma que escoge su propia ubicación en lugar de que se la indiquen.
 * 
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class AutonomousStore extends Store {
    private static Random random = new Random();
    
    /**
     * Constructor que crea una tienda autónoma.
     * La ubicación proporcionada es ignorada y la tienda escoge su propia posición.
     *
     * @param road la carretera
     * @param ignoredLocation ubicación sugerida (será ignorada)
     * @param tenges cantidad inicial de tenges
     * @param roadLength longitud de la carretera para escoger posición válida
     */
    public AutonomousStore(Road road, int ignoredLocation, int tenges, int roadLength) {
        super(road, chooseLocation(roadLength), tenges);
        this.type = "autonomous";
        updateAppearance();
    }
    
    /**
     * Escoge una ubicación aleatoria para la tienda.
     *
     * @param roadLength longitud máxima de la carretera
     * @return ubicación escogida
     */
    private static int chooseLocation(int roadLength) {
        return random.nextInt(roadLength + 1);
    }
}