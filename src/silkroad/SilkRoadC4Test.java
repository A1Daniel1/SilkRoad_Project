package silkroad;
import shapes.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas de unidad del ciclo 4: tipos nuevos de robots y tiendas.
 * Compatible con BlueJ y JUnit 5.
 */
public class SilkRoadC4Test {
    
    private SilkRoad road;
    
    @BeforeEach
    public void setUp() {
        road = new SilkRoad(20);
    }

    @Test
    public void shouldCreateDifferentStores() {
        road.placeStore("normal", 3, 100);
        road.placeStore("fighter", 8, 200);
        road.placeStore("autonomous", 0, 150);
        assertTrue(road.ok());
        assertEquals(3, road.stores().length, "Debe haber tres tiendas creadas");
    }

    @Test
    public void shouldCreateDifferentRobots() {
        road.placeRobot("normal", 2);
        road.placeRobot("neverback", 5);
        road.placeRobot("tender", 10);
        road.placeRobot("illbeback", 15);
        assertTrue(road.ok());
        assertEquals(4, road.robots().length, "Debe haber cuatro robots creados");
    }

    @Test
    public void shouldCollectProfitWhenMoving() {
        road.placeStore(6, 100);
        road.placeRobot(5);
        int before = road.profit();
        road.moveRobot(5, 1);
        assertTrue(road.ok());
        assertTrue(road.profit() > before, "Debe haber recolectado ganancias");
    }

    @Test
    public void neverBackRobotCannotGoBackwards() {
        road.placeRobot("neverback", 4);
        road.moveRobot(4, 2); // avance válido
        road.moveRobot(6, -1); // no debería poder retroceder
        assertFalse(road.ok(), "El robot neverback no puede devolverse");
    }

    @Test
    public void tenderRobotShouldTakeHalf() {
        road.placeStore(7, 100);
        road.placeRobot("tender", 6);
        road.moveRobot(6, 1);
        int profit = road.profit();
        assertTrue(profit > 0 && profit < 100, "Debe haber tomado solo la mitad del dinero");
    }

    @Test
    public void autonomousStoreShouldFindFreeLocation() {
        road.placeStore("autonomous", 0, 50);
        assertTrue(road.ok(), "La tienda autónoma debería ubicarse correctamente");
    }

    @Test
    public void fighterStoreShouldRejectPoorRobot() {
        road.placeStore("fighter", 9, 300);
        road.placeRobot(7);
        road.moveRobot(7, 2);
        assertFalse(road.ok(), "El robot sin dinero no puede vaciar una tienda fighter");
    }

    @Test
    public void rebootShouldResetProfits() {
        road.placeStore(5, 80);
        road.placeRobot(4);
        road.moveRobot(4, 1);
        assertTrue(road.profit() > 0);
        road.reboot();
        assertEquals(0, road.profit(), "Después del reinicio la ganancia debe ser 0");
    }
}
