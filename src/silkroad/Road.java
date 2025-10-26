package silkroad;
import shapes.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code Road} representa la carretera de la Ruta de la Seda.
 *
 * Dibuja la carretera como un conjunto de celdas de 40x40 en forma de espiral.
 *
 * @author Daniel
 * @version 2025-2
 */
public class Road {
    private List<celda> cells;    // celdas del camino
    private int canvasWidth;
    private int canvasHeight;
    private int n;

    public Road(int n) {
        this.cells = new ArrayList<>(); // Inicializar la lista
        this.canvasWidth = 1000;
        this.canvasHeight = 600;
        this.n = n;
        drawRoad(this.n);
    }

    /**
     * Dibuja el camino con n celdas en espiral
     */
    public void drawRoad(int n) {
        int s = (int) Math.ceil(Math.sqrt(n));
        boolean[][] visited = new boolean[s][s];
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // right, down, left, up
        int dir = 0;
        int row = 0, col = 0;
        List<int[]> positions = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            positions.add(new int[]{row, col});
            visited[row][col] = true;

            int nr = row + directions[dir][0];
            int nc = col + directions[dir][1];

            if (nr < 0 || nr >= s || nc < 0 || nc >= s || visited[nr][nc]) {
                dir = (dir + 1) % 4;
                nr = row + directions[dir][0];
                nc = col + directions[dir][1];
            }

            row = nr;
            col = nc;
        }

        for (int i = 0; i < n; i++) {
            int[] pos = positions.get(i);
            int x = 100 + pos[1] * 40; // col
            int y = 100 + pos[0] * 40; // row
            celda cell = new celda();
            cell.setPosition(x, y);
            cell.makeVisible();
            cells.add(cell);
        }
    }

    /** Hace visible la carretera */
    public void makeVisible() {
        for (celda cell : cells) cell.makeVisible();
    }

    /** Hace invisible la carretera */
    public void makeInvisible() {
        for (celda cell : cells) cell.makeInvisible();
    }

    /** Limpia las celdas */
    public void clear() {
        if (cells != null) { // Verificación de seguridad
            for (celda cell : cells) cell.makeInvisible();
            cells.clear();
        }
    }

    /**
     * Obtiene la posición x de la celda en la ubicación dada.
     */
    public int getX(int location) {
        if (location >= 0 && location < cells.size()) {
            return cells.get(location).xPosition;
        }
        return 0;
    }

    /**
     * Obtiene la posición y de la celda en la ubicación dada.
     */
    public int getY(int location) {
        if (location >= 0 && location < cells.size()) {
            return cells.get(location)   .yPosition;
        }
        return 0;
    }
}