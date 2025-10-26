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
     * Ejecuta la simulación visualmente, mostrando día a día y paso a paso.
     *
     * @param days número de días a simular
     * @param slow true para animación lenta y detallada, false para rápida
     */
    public void simulate(int days, boolean slow) {
        // Reiniciar y hacer visible
        resetSimulation();
        silkRoad.makeVisible();

        // Asegurar que la ventana esté visible y en primer plano
        try {
            Thread.sleep(1000); // Pausa inicial para que se muestre
        } catch (InterruptedException e) {
            // ignorar
        }

        int delay = slow ? 2000 : 500; // milisegundos

        for (int day = 0; day < days; day++) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                // ignorar
            }

            silkRoad.autoMoveRobots();

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                // ignorar
            }

            silkRoad.resupplyStores();

            // Actualizar barra de progreso
            int progress = ((day + 1) * 100) / days;
            // Nota: la barra se actualiza automáticamente en autoMoveRobots
        }

        // Finalizar
        try {
            Thread.sleep(delay * 2);
        } catch (InterruptedException e) {
            // ignorar
        }
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