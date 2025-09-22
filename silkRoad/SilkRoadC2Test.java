import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SilkRoadC2Test {
    
    private SilkRoad silkRoad;
    
    @BeforeEach
    public void setUp() {
        silkRoad = new SilkRoad(500);
    }
    
    @Test
    public void accordingANShouldCreateRoadAndMoveRobots() {
        int[][] input = {
            {2, 100, 150},
            {2, 300, 200},
            {1, 50},
            {1, 400}
        };
        
        silkRoad.createFromInput(input);
        int beforeProfit = silkRoad.profit();
        silkRoad.autoMoveRobots();
        
        assertEquals(2, silkRoad.stores().length);
        assertEquals(2, silkRoad.robots().length);
        assertTrue(silkRoad.profit() > beforeProfit);
    }
    
    @Test
    public void accordingANShouldConsultStoreAndRobotData() {
        int[][] input = {
            {2, 80, 100},
            {2, 250, 150},
            {1, 50},
            {1, 300}
        };
        
        silkRoad.createFromInput(input);
        silkRoad.autoMoveRobots();
        silkRoad.resupplyStores();
        silkRoad.autoMoveRobots();
        
        int emptyTimes = silkRoad.consultStoreEmptyTimes(80);
        List<Integer> profits = silkRoad.consultRobotProfits(50);
        int totalProfit = silkRoad.consultRobotTotalProfit(50);
        
        assertTrue(emptyTimes >= 0);
        assertNotNull(profits);
        assertTrue(totalProfit >= 0);
        assertEquals(-1, silkRoad.consultStoreEmptyTimes(999));
        assertNull(silkRoad.consultRobotProfits(999));
    }
}