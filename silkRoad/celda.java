

/**
 * A cell that can be manipulated and that draws itself on a canvas with black borders and white background.
 *
 * @author Daniel Ahumada y Juan Neira
 * @version ciclo 3
 */
public class celda extends Figura
{
    private int width;
    private int height;

    /**
     * Constructor for objects of class celda
     */
    public celda()
    {
        super();
        width = 40;
        height = 40;
        xPosition = 0;
        yPosition = 0;
    }

    /**
     * Change the size to the new size
     * @param newWidth the new width in pixels. newWidth must be >=0.
     * @param newHeight the new height in pixels. newHeight must be >=0.
     */
    public void changeSize(int newWidth, int newHeight) {
        erase();
        width = newWidth;
        height = newHeight;
        draw();
    }

    /**
     * Draw the cell with current specifications on screen.
     */
    protected void draw(){
        if(isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, "white", new java.awt.Rectangle(xPosition, yPosition, width, height));
            canvas.wait(10);
        }
    }
}