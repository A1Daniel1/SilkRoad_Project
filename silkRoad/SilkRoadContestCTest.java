/**
 * Casos de prueba comunes para SilkRoadContest.
 *
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SilkRoadContestCTest {

    private SilkRoadContest contest;

    @BeforeEach
    public void setUp() {
        int[][] input = {
            {2, 10, 100},
            {1, 5}
        };
        contest = new SilkRoadContest(50, input);
    }


    @Test
    public void testSolveFewDays() {
        int profit = contest.solve(3);
        assertTrue(profit >= 0);
    }

    @Test
    public void testSolveManyDays() {
        int profit = contest.solve(10);
        assertTrue(profit >= 0);
    }
}