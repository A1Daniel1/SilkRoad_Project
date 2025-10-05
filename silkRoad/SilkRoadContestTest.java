/**
 * Pruebas unitarias para SilkRoadContest.
 *
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SilkRoadContestTest {

    private SilkRoadContest contest;

    @BeforeEach
    public void setUp() {
        int[][] input = {
            {2, 10, 150},
            {1, 5}
        };
        contest = new SilkRoadContest(50, input);
    }

    @Test
    public void testSolvePositiveDays() {
        int profit = contest.solve(2);
        assertTrue(profit >= 0);
    }

    @Test
    public void testSolveZeroDays() {
        int profit = contest.solve(0);
        assertEquals(0, profit);
    }
}