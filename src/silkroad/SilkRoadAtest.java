package silkroad;
import javax.swing.JOptionPane;
import shapes.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Prueba de aceptación visual del ciclo 4.
 * Demuestra el comportamiento combinado de tiendas y robots.
 */
public class SilkRoadAtest {
    
    @Test
    public void acceptanceTest() throws InterruptedException {
        SilkRoad world = new SilkRoad(12);
        world.makeVisible();
        
        // Tiendas
        world.placeStore("normal", 2, 100);
        world.placeStore("fighter", 5, 200);
        world.placeStore("autonomous", 0, 150);
        world.placeStore("robinhood", 9, 120); 
        
        // Robots
        world.placeRobot("normal", 1);
        world.placeRobot("neverback", 3);
        world.placeRobot("tender", 6);
        world.placeRobot("illbeback", 10);
        
        JOptionPane.showMessageDialog(null,
            "Etapa 1: Configuración creada.\nObserva las posiciones en el tablero.",
            "SilkRoad Atest", JOptionPane.INFORMATION_MESSAGE);
        
        world.autoMoveRobots();
        
        JOptionPane.showMessageDialog(null,
            "Etapa 2: Los robots se movieron automáticamente.\n¿Se comportan como esperas?",
            "Verificación de comportamiento", JOptionPane.QUESTION_MESSAGE);
        
        world.resupplyStores();
        world.returnRobots();
        
        JOptionPane.showMessageDialog(null,
            "Etapa 3: Tiendas reabastecidas y robots regresaron.\n¿Aceptas la simulación?",
            "Verificación final", JOptionPane.QUESTION_MESSAGE);
    }
}
