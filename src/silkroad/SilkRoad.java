package silkroad;
import shapes.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * La clase SilkRoad representa la carretera principal donde interactúan
 * robots y tiendas en la simulación de la Ruta de la Seda.
 *
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class SilkRoad {
    private int length;
    private List<Store> stores;
    private List<Robot> robots;
    private long totalProfit;
    private boolean isVisible;
    private boolean lastOperationOk;
    private Road road;
    private ProgressBar progressBar;

    /**
     * Constructor que crea una nueva carretera Silk Road.
     *
     * @param n dimensión del tablero (crea un camino de n celdas en espiral)
     */
    public SilkRoad(int n) {
        Canvas canvas = Canvas.getCanvas();
        canvas.eraseAll();

        this.road = new Road(n);
        this.length = n; // El camino tiene n celdas (0 a n-1)
        this.stores = new ArrayList<>();
        this.robots = new ArrayList<>();
        this.totalProfit = 0;
        this.isVisible = true;
        this.lastOperationOk = true;
     
        this.progressBar = new ProgressBar(100, 50, 50, 400, 20);
        this.progressBar.makeVisible();
    }
    
    /**
     * Obtiene el tamaño real del camino (número de celdas).
     *
     * @return número de celdas en el camino
     */
    public int getLength() {
        return this.length;
    }
    
    /**
     * Coloca una tienda en una ubicación específica.
     * Por defecto crea una tienda normal.
     *
     * @param location ubicación de la tienda
     * @param tenges cantidad inicial de tenges
     */
    public void placeStore(int location, int tenges) {
        placeStore("normal", location, tenges);
    }
    
    /**
     * Coloca una tienda de un tipo específico.
     *
     * @param type tipo de tienda: "normal", "autonomous", "fighter","robinhood"
     * @param location ubicación de la tienda (ignorada si es autonomous)
     * @param tenges cantidad inicial de tenges
     */
    public void placeStore(String type, int location, int tenges) {
        lastOperationOk = false;

        Store newStore = null;
        int finalLocation = location;
        
        if (type.equals("autonomous")) {
            // Para autonomous, primero creamos la tienda para que escoja su ubicación
            // Luego verificamos si esa ubicación está disponible
            int attempts = 0;
            boolean locationFound = false;
            
            while (attempts < 100 && !locationFound) {
                newStore = new AutonomousStore(road, location, tenges, this.length);
                finalLocation = newStore.getLocation();
                
                // Verificar que no haya otra tienda en esa ubicación
                boolean occupied = false;
                for (Store s : stores) {
                    if (s.getLocation() == finalLocation) {
                        occupied = true;
                        break;
                    }
                }
                
                if (!occupied) {
                    locationFound = true;
                } else {
                    newStore.makeInvisible();
                    attempts++;
                }
            }
            
            if (!locationFound) {
                showError("No se pudo encontrar una ubicación libre para la tienda autónoma después de 100 intentos");
                return;
            }
        } else if (type.equals("fighter")) {
            if (location < 0 || location >= this.length) {
                showError("Ubicación inválida: " + location + ". Debe estar entre 0 y " + (length - 1));
                return;
            }
            
            // Verificar que no haya otra tienda en esa ubicación
            for (Store s : stores) {
                if (s.getLocation() == location) {
                    showError("Ya existe una tienda en la ubicación " + location);
                    return;
                }
            }
            
            newStore = new FighterStore(road, location, tenges);
            finalLocation = location;
        } else { // "normal"
            if (location < 0 || location >= this.length) {
                showError("Ubicación inválida: " + location + ". Debe estar entre 0 y " + (length - 1));
                return;
            }
            
            // Verificar que no haya otra tienda en esa ubicación
            for (Store s : stores) {
                if (s.getLocation() == location) {
                    showError("Ya existe una tienda en la ubicación " + location);
                    return;
                }
            }
            
            newStore = new Store(road, location, tenges);
            finalLocation = location;
        }

        stores.add(newStore);
        Collections.sort(stores, Comparator.comparingInt(Store::getLocation));
        
        if (isVisible) {
            newStore.makeVisible();
        }
        
        updateProgressBar();
        lastOperationOk = true;
    }

    /**
     * Elimina una tienda ubicada en una posición.
     *
     * @param location ubicación de la tienda a eliminar
     */
    public void removeStore(int location) {
        lastOperationOk = false;
        Store storeToRemove = null;
        
        for (Store s : stores) {
            if (s.getLocation() == location) {
                storeToRemove = s;
                break;
            }
        }

        if (storeToRemove != null) {
            if (isVisible) {
                storeToRemove.makeInvisible();
            }
            stores.remove(storeToRemove);
            updateProgressBar();
            lastOperationOk = true;
        } else {
            showError("No se encontró tienda en la ubicación " + location);
        }
    }

    /**
     * Coloca un robot en una ubicación específica.
     * Por defecto crea un robot normal.
     *
     * @param location posición inicial del robot
     */
    public void placeRobot(int location) {
        placeRobot("normal", location);
    }
    
    /**
     * Coloca un robot de un tipo específico.
     *
     * @param type tipo de robot: "normal", "neverback", "tender","illbeback"
     * @param location posición inicial del robot
     */
    public void placeRobot(String type, int location) {
        lastOperationOk = false;

        if (location < 0 || location >= this.length) {
            showError("Ubicación inválida: " + location + ". Debe estar entre 0 y " + (length - 1));
            return;
        }
        
        // Verificar que no haya otro robot en esa ubicación (tanto inicial como actual)
        for (Robot r : robots) {
            if (r.getCurrentLocation() == location) {
                showError("Ya existe un robot en la ubicación " + location);
                return; 
            }
        }

        try {
            Robot newRobot;
            if (type.equals("neverback")) {
                newRobot = new NeverBackRobot(road, location);
            } else if (type.equals("tender")) {
                newRobot = new TenderRobot(road, location);
            } else if (type.equals("illbeback")) {
                newRobot = new IllBeBackRobot(road, location);    
            } else { // "normal"
                newRobot = new Robot(road, location);
            }
            
            robots.add(newRobot);
            Collections.sort(robots, Comparator.comparingInt(Robot::getCurrentLocation)); 
            
            if (isVisible) {
                newRobot.makeVisible();
            }
            
            updateProgressBar();
            lastOperationOk = true;
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        }
    }
    
    /**
     * Elimina un robot ubicado en una posición.
     *
     * @param location ubicación actual del robot a eliminar
     */
    public void removeRobot(int location) {
        lastOperationOk = false;
        Robot robotToRemove = null;
        
        for (Robot r : robots) {
            if (r.getCurrentLocation() == location) {
                robotToRemove = r;
                break;
            }
        }

        if (robotToRemove != null) {
            robotToRemove.destroy();
            robots.remove(robotToRemove);
            updateProgressBar();
            lastOperationOk = true;
        } else {
            showError("No se encontró robot en la ubicación " + location);
        }
    }
    
    /**
     * Mueve un robot desde su ubicación actual un número de metros.
     * Si el robot llega a una tienda con tenges, automáticamente los recolecta.
     *
     * @param location ubicación actual del robot
     * @param meters distancia a mover
     */
    public void moveRobot(int location, int meters) {
        lastOperationOk = false;
        Robot robotToMove = null;
        
        for (Robot r : robots) {
            if (r.getCurrentLocation() == location) {
                robotToMove = r;
                break;
            }
        }

        if (robotToMove != null) {
            int targetLocation = robotToMove.getCurrentLocation() + meters;
            if (targetLocation < 0 || targetLocation >= this.length) {
                showError("Movimiento inválido. La posición " + targetLocation + " está fuera de los límites (0-" + (length - 1) + ")");
                return;
            }
            
            // Verificar si el robot puede moverse a esa ubicación (para illbeback)
            if (!robotToMove.canMoveTo(targetLocation) && robotToMove.getType()== "illbeback") {
                showError("El robot " + robotToMove.getType() + " no puede moverse hacia adelante");
                return;
            }
            // Verificar si el robot puede moverse a esa ubicación (para neverback)
            if (!robotToMove.canMoveTo(targetLocation)) {
                showError("El robot " + robotToMove.getType() + " no puede moverse hacia atrás");
                return;
            }
            
            // Verificar que no haya otro robot en la posición objetivo
            for (Robot r : robots) {
                if (r != robotToMove && r.getCurrentLocation() == targetLocation) {
                    showError("Ya hay un robot en la ubicación " + targetLocation);
                    return;
                }
            }
            
            int oldLocation = robotToMove.getCurrentLocation();
            robotToMove.moveTo(targetLocation);
            
            // Verificar si hay una tienda en la nueva posición
            Store storeAtTarget = getStoreAtLocation(targetLocation);
            if (storeAtTarget != null && storeAtTarget.getCurrentTenges() > 0) {
                int collected = robotToMove.collectFrom(storeAtTarget);
                if (collected > 0) {
                    int distance = Math.abs(targetLocation - oldLocation);
                    int profit = collected - distance;
                    robotToMove.addProfit(profit);
                    totalProfit += profit;
                } else if (storeAtTarget.getType().equals("fighter")) {
                    showError("El robot no tiene suficiente dinero para vaciar esta tienda fighter");
                }
            }
            
            Collections.sort(robots, Comparator.comparingInt(Robot::getCurrentLocation));
            updateProgressBar();
            updateVisualEffects();
            lastOperationOk = true;
        } else {
            showError("No se encontró robot en la ubicación " + location);
        }
    }

    /**
     * Reabastece todas las tiendas de la carretera.
     * Automáticamente hace que los robots recolecten de tiendas en su posición actual.
     */
    public void resupplyStores() {
        lastOperationOk = false;
        for (Store s : stores) {
            s.resupply();
        }
        
        // Automáticamente recolectar de tiendas en posición actual
        for (Robot robot : robots) {
            Store storeAtPosition = getStoreAtLocation(robot.getCurrentLocation());
            if (storeAtPosition != null && storeAtPosition.getCurrentTenges() > 0) {
                int collected = robot.collectFrom(storeAtPosition);
                if (collected > 0) {
                    robot.addProfit(collected); // Sin costo de movimiento
                    totalProfit += collected;
                }
            }
        }
        
        updateStoreAppearances();
        updateProgressBar();
        updateVisualEffects();
        lastOperationOk = true;
    }

    /**
     * Devuelve todos los robots a su posición inicial.
     * Los robots neverback no pueden regresar.
     */
    public void returnRobots() {
        lastOperationOk = false;
        for (Robot r : robots) {
            // Solo regresar si no es neverback
            if (!r.getType().equals("neverback")) {
                r.returnToInitialPosition();
            }
        }
        Collections.sort(robots, Comparator.comparingInt(Robot::getCurrentLocation));
        updateProgressBar();
        lastOperationOk = true;
    }

    /**
     * Reinicia la simulación.
     */
    public void reboot() {
        lastOperationOk = false;
        resupplyStores();
        returnRobots();
        this.totalProfit = 0;
        
        for (Robot r : robots) {
            r.clearProfits();
        }
        
        for (Store s : stores) {
            s.resetTimesEmptied();
        }
        
        progressBar.reset();
        updateVisualEffects();
        lastOperationOk = true;
    }

    /**
     * @return el beneficio total acumulado
     */
    public int profit() {
        return (int) this.totalProfit; 
    }
    
    /**
     * @return matriz con las tiendas (ubicación, tenges actuales)
     */
    public int[][] stores() {
        int[][] result = new int[stores.size()][2];
        for (int i = 0; i < stores.size(); i++) {
            Store s = stores.get(i);
            result[i][0] = s.getLocation();
            result[i][1] = s.getCurrentTenges();
        }
        return result;
    }

    /**
     * @return matriz con los robots (ubicación actual, ubicación inicial)
     */
    public int[][] robots() {
        int[][] result = new int[robots.size()][2];
        for (int i = 0; i < robots.size(); i++) {
            Robot r = robots.get(i);
            result[i][0] = r.getCurrentLocation();
            result[i][1] = r.getInitialLocation();
        }
        return result;
    }

    /**
     * Hace visible la carretera y todos los elementos.
     */
    public void makeVisible() {
        lastOperationOk = false;
        Canvas.getCanvas().setVisible(true);
        this.isVisible = true;

        road.makeVisible();

        for (Store s : stores) {
            s.makeVisible();
        }

        for (Robot r : robots) {
            r.makeVisible();
        }

        updateVisualEffects();
        lastOperationOk = true;
    }

    /**
     * Oculta la carretera y todos los elementos.
     */
    public void makeInvisible() {
        lastOperationOk = false;
        Canvas.getCanvas().setVisible(false);
        this.isVisible = false;
        road.makeInvisible();
        lastOperationOk = true;
    }

    /**
     * Finaliza la simulación.
     */
    public void finish() {
        lastOperationOk = false;
        cleanupAll();
        System.exit(0);
    }

    /**
     * Limpia todos los recursos de la simulación.
     */
    private void cleanupAll() {
        for (Robot robot : robots) {
            robot.destroy();
        }
        robots.clear();
        stores.clear();
        Canvas.getCanvas().eraseAll();
    }

    /**
     * @return true si la última operación fue exitosa
     */
    public boolean ok() {
        return this.lastOperationOk;
    }

    /**
     * Crea la ruta de seda a partir de entrada de datos del problema de maratón.
     * Formato: {1, x} -> robot en x, {2, x, c} -> tienda en x con c tenges
     *
     * @param lines matriz con las instrucciones de creación
     */
    public void createFromInput(int[][] lines) {
        for (int[] parts : lines) {
            if (parts[0] == 1) {
                int x = parts[1];
                placeRobot(x);
            } else if (parts[0] == 2) {
                int x = parts[1];
                int c = parts[2];
                placeStore(x, c);
            }
        }
        updateVisualEffects();
    }

    /**
     * Movimiento automático de robots buscando maximizar ganancias.
     * Verifica que no haya colisiones entre robots.
     */
    public void autoMoveRobots() {
        progressBar.setValue(0);
        
        for (int i = 0; i < robots.size(); i++) {
            Robot robot = robots.get(i);
            
            // Primero verificar si hay una tienda con tenges en la posición actual
            Store currentStore = getStoreAtLocation(robot.getCurrentLocation());
            if (currentStore != null && currentStore.getCurrentTenges() > 0) {
                int collected = robot.collectFrom(currentStore);
                if (collected > 0) {
                    robot.addProfit(collected); // Sin costo de movimiento
                    totalProfit += collected;
                }
            } else {
                // Buscar la mejor tienda disponible
                Store bestStore = null;
                int bestProfit = Integer.MIN_VALUE;
                
                for (Store store : stores) {
                    if (store.getCurrentTenges() > 0) {
                        int distance = Math.abs(robot.getCurrentLocation() - store.getLocation());
                        int profit = store.getCurrentTenges() - distance;
                        
                        // Verificar si el robot puede moverse a esa ubicación
                        if (robot.canMoveTo(store.getLocation()) && profit > bestProfit) {
                            // Verificar que no haya otro robot en esa ubicación
                            boolean occupied = false;
                            for (Robot r : robots) {
                                if (r != robot && r.getCurrentLocation() == store.getLocation()) {
                                    occupied = true;
                                    break;
                                }
                            }
                            
                            if (!occupied) {
                                bestProfit = profit;
                                bestStore = store;
                            }
                        }
                    }
                }
                
                if (bestStore != null && bestProfit > 0) {
                    int oldLocation = robot.getCurrentLocation();
                    robot.moveTo(bestStore.getLocation());
                    int collected = robot.collectFrom(bestStore);
                    if (collected > 0) {
                        int distance = Math.abs(bestStore.getLocation() - oldLocation);
                        robot.addProfit(collected - distance);
                        totalProfit += bestProfit;
                    }
                }
            }
            
            int progress = ((i + 1) * 100) / robots.size();
            progressBar.setValue(progress);
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                // Ignorar
            }
        }
        
        progressBar.setValue(100);
        updateVisualEffects();
    }

    /**
     * Consulta cuántas veces ha sido desocupada una tienda específica.
     *
     * @param location ubicación de la tienda
     * @return número de veces que la tienda ha sido vaciada, -1 si no existe
     */
    public int consultStoreEmptyTimes(int location) {
        for (Store store : stores) {
            if (store.getLocation() == location) {
                return store.getTimesEmptied();
            }
        }
        showError("No existe tienda en la ubicación " + location);
        return -1;
    }

    /**
     * Consulta las ganancias de un robot específico.
     *
     * @param initialLocation ubicación inicial del robot
     * @return lista de ganancias del robot, null si no existe
     */
    public List<Integer> consultRobotProfits(int initialLocation) {
        for (Robot robot : robots) {
            if (robot.getInitialLocation() == initialLocation) {
                return robot.getProfits();
            }
        }
        showError("No existe robot con ubicación inicial " + initialLocation);
        return null;
    }

    /**
     * Consulta la ganancia total de un robot específico.
     *
     * @param initialLocation ubicación inicial del robot
     * @return ganancia total del robot, -1 si no existe
     */
    public int consultRobotTotalProfit(int initialLocation) {
        for (Robot robot : robots) {
            if (robot.getInitialLocation() == initialLocation) {
                return robot.getTotalProfit();
            }
        }
        showError("No existe robot con ubicación inicial " + initialLocation);
        return -1;
    }

    /**
     * Actualiza la apariencia de las tiendas según su estado.
     */
    private void updateStoreAppearances() {
        for (Store store : stores) {
            store.updateAppearance();
        }
    }

    /**
     * Actualiza todos los efectos visuales (tiendas desocupadas y robot parpadeante).
     */
    private void updateVisualEffects() {
        updateStoreAppearances();
        updateRobotBlinking();
    }

    /**
     * Actualiza el parpadeo del robot con mayor ganancia.
     */
    private void updateRobotBlinking() {
        if (robots.isEmpty()) return;

        Robot topRobot = null;
        int maxProfit = Integer.MIN_VALUE;

        for (Robot robot : robots) {
            int profit = robot.getTotalProfit();
            if (profit > maxProfit) {
                maxProfit = profit;
                topRobot = robot;
            }
        }

        for (Robot robot : robots) {
            if (robot == topRobot && maxProfit > 0) {
                robot.startBlinking();
            } else {
                robot.stopBlinking();
            }
        }
    }

    /**
     * Actualiza la barra de progreso basada en el estado de la simulación.
     */
    private void updateProgressBar() {
        if (stores.isEmpty() && robots.isEmpty()) {
            progressBar.setValue(0);
            return;
        }
        
        int storeProgress = Math.min(stores.size() * 10, 25);
        int robotProgress = Math.min(robots.size() * 10, 25);
        int profitProgress = (int) Math.min(totalProfit / 10, 25);
        int activityProgress = calculateActivityProgress();
        
        int totalProgress = storeProgress + robotProgress + profitProgress + activityProgress;
        progressBar.setValue(totalProgress);
    }

    /**
     * Calcula el progreso basado en la actividad de la simulación.
     */
    private int calculateActivityProgress() {
        int activity = 0;

        for (Store store : stores) {
            if (store.getTimesEmptied() > 0) {
                activity += 5;
            }
        }
        
        for (Robot robot : robots) {
            if (robot.getTotalProfit() > 0) {
                activity += 5;
            }
        }
        
        return Math.min(activity, 25);
    }

    /**
     * Busca una tienda en una ubicación específica.
     *
     * @param location ubicación a buscar
     * @return la tienda en esa ubicación, o null si no existe
     */
    private Store getStoreAtLocation(int location) {
        for (Store store : stores) {
            if (store.getLocation() == location) {
                return store;
            }
        }
        return null;
    }

    /**
     * Muestra un mensaje de error usando JOptionPane.
     *
     * @param message mensaje de error a mostrar
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}