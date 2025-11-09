package silkroad;
import shapes.*;
/**
 * La clase SilkRoadContest simula y resuelve el problema de la maratón de robots en la ruta de la seda.
 * Basada en SilkRoad, proporciona métodos para calcular resultados óptimos y simular visualmente.
 *
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class SilkRoadContest {
    private SilkRoad silkRoad;
    private int[][] initialInput;
    private int length;

    /**
     * Constructor que crea una nueva simulación Silk Road Contest.
     *
     * @param length longitud de la carretera
     * @param input matriz con las instrucciones iniciales
     */
    public SilkRoadContest(int length, int[][] input) {
        this.length = length;
        this.silkRoad = new SilkRoad(length);
        this.initialInput = input.clone();
        silkRoad.createFromInput(input);
    }

    /**
     * Calcula y devuelve el resultado óptimo de la simulación sin mostrarla visualmente.
     *
     * @param days número de días a simular
     * @return ganancia total óptima
     */
    public int solve(int days) {
        // Reiniciar la simulación
        resetSimulation();

        int totalProfit = 0;

        for (int day = 0; day < days; day++) {
            silkRoad.autoMoveRobots();
            totalProfit = silkRoad.profit();
            silkRoad.resupplyStores();
        }

        return totalProfit;
    }
    
    /**
     * Realiza una pausa segura en milisegundos sin interrumpir el estado del hilo.
     *
     * @param millis tiempo en milisegundos a esperar
     */
    private void sleepSafely(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Mantiene el estado de interrupción
            System.err.println("Simulación interrumpida: " + e.getMessage());
        }
    }
    /**
     * Ejecuta la simulación visualmente, mostrando día a día y paso a paso.
     *
     * @param days número de días a simular
     * @param slow true para animación lenta y detallada, false para rápida
     */
    public void simulate(int days, boolean slow) {
        // Reiniciar y hacer visible
        resetSimulation();
        silkRoad.makeVisible();
        
        final int INITIAL_DELAY_MS = 1000;
        final int SLOW_DELAY_MS = 2000;
        final int FAST_DELAY_MS = 500;

        final int delay = slow ? SLOW_DELAY_MS : FAST_DELAY_MS;

        // Pausa inicial para que la interfaz se muestre correctamente
        sleepSafely(INITIAL_DELAY_MS);


        for (int day = 0; day < days; day++) {
            sleepSafely(delay);
            silkRoad.autoMoveRobots();

            sleepSafely(delay);
            silkRoad.resupplyStores();

            // Actualizar barra de progreso (ya se actualiza internamente)
            int progress = ((day + 1) * 100) / days;
            // ProgressBar se actualiza dentro de autoMoveRobots()
        }

        // Pausa final antes de terminar la simulación
        sleepSafely(delay * 2);

    }

    /**
     * Reinicia la simulación al estado inicial.
     */
    private void resetSimulation() {
        silkRoad = new SilkRoad(this.length);
        silkRoad.createFromInput(initialInput);
        silkRoad.reboot();
    }

    // Para acceder a length, modificar SilkRoad o guardar aquí
    // Por simplicidad, asumir length conocido o modificar SilkRoad para exponerlo
}