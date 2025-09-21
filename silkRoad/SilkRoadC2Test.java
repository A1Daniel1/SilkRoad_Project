 

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SilkRoadC2Test {
    
    private SilkRoad silkRoad;
    private int[][] input;
    
    @BeforeEach
    public void setUp() {
        silkRoad = new SilkRoad(100);
        input = new int[][]{
            {2, 10, 50},  // tienda en posición 10 con 50 tenges
            {2, 30, 75},  // tienda en posición 30 con 75 tenges
            {2, 60, 40},  // tienda en posición 60 con 40 tenges
            {1, 0},       // robot en posición 0
            {1, 20},      // robot en posición 20
            {1, 80}       // robot en posición 80
        };
    }
    
    @Test
    public void createFromInputShouldCreateSilkRoadWithValidInput() {
        silkRoad.createFromInput(input);
        
        int[][] stores = silkRoad.stores();
        int[][] robots = silkRoad.robots();
        
        assertEquals(3, stores.length);
        assertEquals(3, robots.length);
        assertTrue(silkRoad.ok());
    }
    
    @Test
    public void createFromInputShouldHandleEmptyInput() {
        int[][] emptyInput = {};
        
        silkRoad.createFromInput(emptyInput);
        
        assertEquals(0, silkRoad.stores().length);
        assertEquals(0, silkRoad.robots().length);
        assertTrue(silkRoad.ok());
    }
    
    @Test
    public void createFromInputShouldValidateStoreData() {
        int[][] validInput = {{2, 50, 100}};
        
        silkRoad.createFromInput(validInput);
        
        int[][] stores = silkRoad.stores();
        assertEquals(1, stores.length);
        assertEquals(50, stores[0][0]); // location
        assertEquals(100, stores[0][1]); // tenges
    }
    
    @Test
    public void createFromInputShouldValidateRobotData() {
        int[][] validInput = {{1, 25}};
        
        silkRoad.createFromInput(validInput);
        
        int[][] robots = silkRoad.robots();
        assertEquals(1, robots.length);
        assertEquals(25, robots[0][0]); // current location
        assertEquals(25, robots[0][1]); // initial location
    }
    
    @Test
    public void createFromInputShouldIgnoreInvalidEntries() {
        int[][] invalidInput = {
            {3, 10, 50},  // tipo inválido
            {2, 50, 100}, // tienda válida
            {1, 30}       // robot válido
        };
        
        silkRoad.createFromInput(invalidInput);
        
        assertEquals(1, silkRoad.stores().length);
        assertEquals(1, silkRoad.robots().length);
    }
    
    @Test
    public void autoMoveRobotsShouldMaximizeProfit() {
        silkRoad.createFromInput(input);
        int initialProfit = silkRoad.profit();
        
        silkRoad.autoMoveRobots();
        
        int finalProfit = silkRoad.profit();
        assertTrue(finalProfit >= initialProfit);
    }
    
    @Test
    public void autoMoveRobotsShouldMoveRobotsToStores() {
        silkRoad.createFromInput(input);
        int[][] initialRobots = silkRoad.robots();
        
        silkRoad.autoMoveRobots();
        
        int[][] finalRobots = silkRoad.robots();
        assertEquals(initialRobots.length, finalRobots.length);
    }
    
    @Test
    public void autoMoveRobotsShouldHandleNoStores() {
        int[][] robotOnlyInput = {{1, 50}};
        silkRoad.createFromInput(robotOnlyInput);
        
        silkRoad.autoMoveRobots();
        
        assertEquals(0, silkRoad.profit());
        assertTrue(silkRoad.ok());
    }
    
    @Test
    public void autoMoveRobotsShouldHandleNoRobots() {
        int[][] storeOnlyInput = {{2, 50, 100}};
        silkRoad.createFromInput(storeOnlyInput);
        
        silkRoad.autoMoveRobots();
        
        assertEquals(0, silkRoad.profit());
        assertTrue(silkRoad.ok());
    }
    
    @Test
    public void consultStoreStatsShouldReturnEmptyTimes() {
        silkRoad.createFromInput(input);
        silkRoad.autoMoveRobots();
        
        Map<Integer, Integer> storeStats = silkRoad.consultStoreStats();
        
        assertNotNull(storeStats);
        assertEquals(3, storeStats.size());
        for (Integer times : storeStats.values()) {
            assertTrue(times >= 0);
        }
    }
    
    @Test
    public void consultStoreStatsShouldTrackEmptyings() {
        silkRoad.createFromInput(input);
        Map<Integer, Integer> initialStats = silkRoad.consultStoreStats();
        
        silkRoad.autoMoveRobots();
        
        Map<Integer, Integer> finalStats = silkRoad.consultStoreStats();
        assertEquals(initialStats.size(), finalStats.size());
    }
    
    @Test
    public void consultStoreStatsShouldHandleNoStores() {
        int[][] robotOnlyInput = {{1, 50}};
        silkRoad.createFromInput(robotOnlyInput);
        
        Map<Integer, Integer> storeStats = silkRoad.consultStoreStats();
        
        assertNotNull(storeStats);
        assertTrue(storeStats.isEmpty());
    }
    
    @Test
    public void consultRobotStatsShouldReturnProfits() {
        silkRoad.createFromInput(input);
        silkRoad.autoMoveRobots();
        
        Map<Integer, List<Integer>> robotStats = silkRoad.consultRobotStats();
        
        assertNotNull(robotStats);
        assertEquals(3, robotStats.size());
        for (List<Integer> profits : robotStats.values()) {
            assertNotNull(profits);
        }
    }
    
    @Test
    public void consultRobotStatsShouldTrackProfitHistory() {
        silkRoad.createFromInput(input);
        Map<Integer, List<Integer>> initialStats = silkRoad.consultRobotStats();
        
        silkRoad.autoMoveRobots();
        
        Map<Integer, List<Integer>> finalStats = silkRoad.consultRobotStats();
        assertEquals(initialStats.size(), finalStats.size());
    }
    
    @Test
    public void consultRobotStatsShouldHandleNoRobots() {
        int[][] storeOnlyInput = {{2, 50, 100}};
        silkRoad.createFromInput(storeOnlyInput);
        
        Map<Integer, List<Integer>> robotStats = silkRoad.consultRobotStats();
        
        assertNotNull(robotStats);
        assertTrue(robotStats.isEmpty());
    }
    
    @Test
    public void rebootShouldResetSimulation() {
        silkRoad.createFromInput(input);
        silkRoad.autoMoveRobots();
        int profitBeforeReboot = silkRoad.profit();
        
        silkRoad.reboot();
        
        assertEquals(0, silkRoad.profit());
        assertTrue(silkRoad.ok());
    }
    
    @Test
    public void rebootShouldRestoreStores() {
        silkRoad.createFromInput(input);
        int[][] initialStores = silkRoad.stores();
        silkRoad.autoMoveRobots();
        
        silkRoad.reboot();
        
        int[][] finalStores = silkRoad.stores();
        assertEquals(initialStores.length, finalStores.length);
        for (int i = 0; i < initialStores.length; i++) {
            assertEquals(initialStores[i][0], finalStores[i][0]); // location
        }
    }
    
    @Test
    public void rebootShouldReturnRobots() {
        silkRoad.createFromInput(input);
        int[][] initialRobots = silkRoad.robots();
        silkRoad.autoMoveRobots();
        
        silkRoad.reboot();
        
        int[][] finalRobots = silkRoad.robots();
        assertEquals(initialRobots.length, finalRobots.length);
        for (int i = 0; i < initialRobots.length; i++) {
            assertEquals(finalRobots[i][0], finalRobots[i][1]); // current == initial
        }
    }
    
    @Test
    public void shouldHandleMultipleAutoMoves() {
        silkRoad.createFromInput(input);
        
        for (int i = 0; i < 3; i++) {
            silkRoad.autoMoveRobots();
            silkRoad.resupplyStores();
        }
        
        assertTrue(silkRoad.profit() >= 0);
        assertTrue(silkRoad.ok());
    }
    
    @Test
    public void shouldMaintainConsistentState() {
        silkRoad.createFromInput(input);
        int initialStoreCount = silkRoad.stores().length;
        int initialRobotCount = silkRoad.robots().length;
        
        silkRoad.autoMoveRobots();
        silkRoad.reboot();
        
        assertEquals(initialStoreCount, silkRoad.stores().length);
        assertEquals(initialRobotCount, silkRoad.robots().length);
        assertTrue(silkRoad.ok());
    }
    
    @Test
    public void shouldHandleEmptyStores() {
        int[][] smallInput = {
            {2, 10, 0},  // tienda vacía
            {1, 5}       // robot
        };
        silkRoad.createFromInput(smallInput);
        
        silkRoad.autoMoveRobots();
        
        assertEquals(0, silkRoad.profit());
        assertTrue(silkRoad.ok());
    }
    
    @Test
    public void shouldCalculateProfitCorrectly() {
        int[][] profitTestInput = {
            {2, 10, 100}, // tienda con muchos tenges
            {1, 5}        // robot cerca
        };
        silkRoad.createFromInput(profitTestInput);
        
        silkRoad.autoMoveRobots();
        
        assertTrue(silkRoad.profit() > 0);
    }
    
    @Test
    public void shouldHandleRobotAtStoreLocation() {
        int[][] sameLocationInput = {
            {2, 50, 100},
            {1, 50}  // robot en la misma posición que la tienda
        };
        silkRoad.createFromInput(sameLocationInput);
        
        silkRoad.autoMoveRobots();
        
        assertTrue(silkRoad.ok());
    }
    
    @Test
    public void visibilityOperationsShouldWork() {
        silkRoad.createFromInput(input);
        
        silkRoad.makeVisible();
        assertTrue(silkRoad.ok());
        
        silkRoad.makeInvisible();
        assertTrue(silkRoad.ok());
    }
    
    @Test
    public void placeAndRemoveOperationsShouldWork() {
        silkRoad.placeStore(25, 150);
        assertTrue(silkRoad.ok());
        
        silkRoad.placeRobot(25);
        assertTrue(silkRoad.ok());
        
        silkRoad.removeRobot(25);
        assertTrue(silkRoad.ok());
        
        silkRoad.removeStore(25);
        assertTrue(silkRoad.ok());
    }
}
