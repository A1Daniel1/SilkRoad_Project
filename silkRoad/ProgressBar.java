/**
 * La clase {@code ProgressBar} representa una barra de progreso visual
 * que muestra el estado actual de la simulación Silk Road.
 * 
 * @author Daniel
 * @version 2025-2
 */
public class ProgressBar {
    private Rectangle background;
    private Rectangle progress;
    private Rectangle border;
    private int maxValue;
    private int currentValue;
    private int width;
    private int height;
    private int xPosition;
    private int yPosition;
    private boolean isVisible;

    /**
     * Constructor que crea una nueva barra de progreso visible automáticamente.
     *
     * @param maxValue valor máximo de la barra
     * @param xPosition posición horizontal
     * @param yPosition posición vertical
     * @param width ancho de la barra
     * @param height alto de la barra
     */
    public ProgressBar(int maxValue, int xPosition, int yPosition, int width, int height) {
        this.maxValue = maxValue;
        this.currentValue = 0;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.isVisible = false;
        
        this.border = new Rectangle();
        this.border.changeSize(height + 4, width + 4);
        this.border.moveHorizontal(xPosition - 2);
        this.border.moveVertical(yPosition - 2);
        this.border.changeColor("black");
  
        this.background = new Rectangle();
        this.background.changeSize(height, width);
        this.background.moveHorizontal(xPosition);
        this.background.moveVertical(yPosition);
        this.background.changeColor("lightGray");
   
        this.progress = new Rectangle();
        this.progress.changeSize(height, 0); 
        this.progress.moveHorizontal(xPosition);
        this.progress.moveVertical(yPosition);
        this.progress.changeColor("blue");

        makeVisible();
    }

    /**
     * Establece el valor actual de la barra de progreso.
     *
     * @param value nuevo valor (debe estar entre 0 y maxValue)
     */
    public void setValue(int value) {
        if (value < 0) value = 0;
        if (value > maxValue) value = maxValue;
        
        this.currentValue = value;
        updateProgress();
    }

    /**
     * Incrementa el valor actual de la barra de progreso.
     *
     * @param increment cantidad a incrementar
     */
    public void increment(int increment) {
        setValue(currentValue + increment);
    }

    /**
     * Establece el valor máximo de la barra de progreso.
     *
     * @param maxValue nuevo valor máximo
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        if (currentValue > maxValue) {
            currentValue = maxValue;
        }
        updateProgress();
    }

    /**
     * Reinicia la barra de progreso a cero.
     */
    public void reset() {
        this.currentValue = 0;
        updateProgress();
    }

    /**
     * Hace visible la barra de progreso.
     */
    public void makeVisible() {
        if (!isVisible) {
            border.makeVisible();
            background.makeVisible();
            progress.makeVisible();
            isVisible = true;
        }
    }

    /**
     * Hace invisible la barra de progreso.
     */
    public void makeInvisible() {
        if (isVisible) {
            border.makeInvisible();
            background.makeInvisible();
            progress.makeInvisible();
            isVisible = false;
        }
    }

    /**
     * Cambia el color de la barra de progreso.
     *
     * @param color nuevo color
     */
    public void changeColor(String color) {
        progress.changeColor(color);
    }

    /**
     * Obtiene el valor actual de la barra de progreso.
     *
     * @return valor actual
     */
    public int getValue() {
        return currentValue;
    }

    /**
     * Obtiene el valor máximo de la barra de progreso.
     *
     * @return valor máximo
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Obtiene el porcentaje de progreso actual.
     *
     * @return porcentaje (0-100)
     */
    public int getPercentage() {
        if (maxValue == 0) return 0;
        return (currentValue * 100) / maxValue;
    }

    /**
     * Actualiza la apariencia visual de la barra de progreso.
     */
    private void updateProgress() {
        if (maxValue > 0) {
            int progressWidth = (currentValue * width) / maxValue;
            progress.changeSize(height, progressWidth);

            if (getPercentage() >= 100) {
                progress.changeColor("green");
            } else if (getPercentage() >= 75) {
                progress.changeColor("blue");
            } else if (getPercentage() >= 50) {
                progress.changeColor("yellow");
            } else if (getPercentage() >= 25) {
                progress.changeColor("orange");
            } else {
                progress.changeColor("red");
            }
        }
    }
}