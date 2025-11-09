package test;
import shapes.*;
import silkroad.SilkRoad;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

public class SilkRoadC2Test {
    
    private SilkRoad silkRoad;
    
    @BeforeEach
    public void setUp() {
        silkRoad = new SilkRoad(100);
    }
    
    @Test
    public void accordingANShouldCreateRoadAndMoveRobots() {
        int[][] input = {
            {2, 10, 150},
            {2, 30, 200},
            {1, 5},
            {1, 40}
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
            {2, 8, 100},
            {2, 25, 150},
            {1, 5},
            {1, 30}
        };
        
        silkRoad.createFromInput(input);
        silkRoad.autoMoveRobots();
        silkRoad.resupplyStores();
        silkRoad.autoMoveRobots();
        
        int emptyTimes = silkRoad.consultStoreEmptyTimes(8);
        List<Integer> profits = silkRoad.consultRobotProfits(50);
        int totalProfit = silkRoad.consultRobotTotalProfit(50);
        
        assertTrue(emptyTimes >= 0);
        assertNotNull(profits);
        assertTrue(totalProfit >= 0);
        assertEquals(-1, silkRoad.consultStoreEmptyTimes(999));
        assertNull(silkRoad.consultRobotProfits(999));
    }
}