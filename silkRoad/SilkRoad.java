import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * La clase {@code SilkRoad} representa la carretera principal donde interactúan
 * robots y tiendas.
 * 
 * Funcionalidades principales:
 * <ul>
 *   <li>Colocar y eliminar tiendas</li>
 *   <li>Colocar y eliminar robots</li>
 *   <li>Mover robots a lo largo de la carretera</li>
 *   <li>Reabastecer tiendas</li>
 *   <li>Reiniciar la simulación (reboot)</li>
 *   <li>Mostrar/ocultar la simulación</li>
 * </ul>
 */
public class SilkRoad {
    private int length;
    private List<Store> stores;
    private List<Robot> robots;
    private long totalProfit;
    private boolean isVisible;
    private boolean lastOperationOk;
    
    
        /**
     * Crea una nueva carretera Silk Road.
     *
     * @param length longitud de la carretera
     */
    public SilkRoad(int length) {
        this.length = length;
        this.stores = new ArrayList<>();
        this.robots = new ArrayList<>();
        this.totalProfit = 0;
        this.isVisible = false;
        this.lastOperationOk = true;
    }
    
     /**
     * Coloca una tienda en una ubicación específica.
     *
     * @param location ubicación de la tienda
     * @param tenges   cantidad inicial de tenges
     */
    public void placeStore(int location, int tenges) {
        lastOperationOk = false;

        if (location < 0 || location > this.length) {
            return;
        }
        for (Store s : stores) {
            if (s.getLocation() == location) {
                return;
            }
        }

        Store newStore = new Store(location, tenges);
        stores.add(newStore);
        Collections.sort(stores, Comparator.comparingInt(Store::getLocation));
        if (isVisible) {
            newStore.makeVisible();
        }
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
            lastOperationOk = true;
        }
    }

     /**
     * Coloca un robot en una ubicación específica.
     *
     * @param location posición inicial del robot
     */
    public void placeRobot(int location) {
        lastOperationOk = false;

        if (location < 0 || location > this.length) {
            return;
        }
        
        for (Robot r : robots) {
            if (r.getInitialLocation() == location) {
                return; 
            }
        }

        Robot newRobot = new Robot(location);
        robots.add(newRobot);
        Collections.sort(robots, Comparator.comparingInt(Robot::getCurrentLocation)); 
        if (isVisible) {
            newRobot.makeVisible();
        }
        lastOperationOk = true;
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
            if (isVisible) {
                robotToRemove.makeInvisible();
            }
            robots.remove(robotToRemove);
            lastOperationOk = true;
        }
    }
    
    /**
     * Mueve un robot desde su ubicación actual un número de metros.
     *
     * @param location ubicación actual del robot
     * @param meters   distancia a mover
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
            if (targetLocation < 0 || targetLocation > this.length) {
                return;
            }
            robotToMove.moveTo(targetLocation);
            Collections.sort(robots, Comparator.comparingInt(Robot::getCurrentLocation));
            lastOperationOk = true;
        }
    }

    /** 
     * Reabastece todas las tiendas de la carretera. 
     */
    public void resupplyStores() {
        lastOperationOk = false;
        for (Store s : stores) {
            s.resupply();
        }
        lastOperationOk = true;
    }

    /** 
     * Devuelve todos los robots a su posición inicial. 
     */
    public void returnRobots() {
        lastOperationOk = false;
        for (Robot r : robots) {
            r.returnToInitialPosition();
        }
        Collections.sort(robots, Comparator.comparingInt(Robot::getCurrentLocation));
        lastOperationOk = true;
    }

    /** 
     * Reinicia la simulación (resetea tiendas, robots y ganancias). 
     */
    public void reboot() {
        lastOperationOk = false;
        resupplyStores();
        returnRobots();
        this.totalProfit = 0;
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
        for (Store s : stores) {
            s.makeVisible();
        }
        for (Robot r : robots) {
            r.makeVisible();
        }
        lastOperationOk = true;
    }

    /** 
     * Oculta la carretera y todos los elementos. 
     */
    public void makeInvisible() {
        lastOperationOk = false;
        Canvas.getCanvas().setVisible(false);
        this.isVisible = false;
        lastOperationOk = true;
    }

    /** 
     * Finaliza la simulación cerrando el programa. 
     */
    public void finish() {
        lastOperationOk = false;
        System.exit(0);

    }

    /** 
     * @return true si la última operación fue exitosa 
     */
    public boolean ok() {
        return this.lastOperationOk;
    }
}