import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public class OOOOOZE extends JPanel implements MouseListener, MouseMotionListener, ActionListener {

    /*
            squares beside chosen square have a percentage probability to ooze, probability gets lower the farther away
            array list so squares can only ooze once
            every action perform makes an array list : next ooze
            boolean = has been oozed, no ooze
            make each ooze their own separate random color. can overlap. make so random color doesn't change other oozes

            in constructor for grid, square initializer has randomized variable to become blocking square. boolean for blocking square ignores color.

            for timer make an if statement that guards if a random value is %5 or %10 to ooze too

     */

    private int width;
    private int height;
    // Spread Array
    private boolean[][] ooooozin;
    // Coordinate Array
    private Point[][] start;
    // Color Array
    private Color[][] multicolor;
    private ArrayList<Rectangle> arrayRectangles = new ArrayList<Rectangle>();
    private ArrayList<Point> getPoints = new ArrayList<Point>();
    Timer timer;

    /**
     *
     * @param width
     * @param height
     */
    // Constructor
    public OOOOOZE(int width, int height) {
        this.width = width;
        this.height = height;
        multicolor = new Color[width][height];
        ooooozin = new boolean[width][height];
        start = new Point[width][height];
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        timer = new Timer(30, this);
        timer.start();

        // Determines which random tiles to make block
        for (int widthIndex = 0; widthIndex < width; widthIndex++) {
            for (int heightIndex = 0; heightIndex < height; heightIndex++) {
                    Random randomBlock = new Random();
                    Float block = randomBlock.nextFloat();
                    Color blockColor = new Color(0, 0, 0);
                    if(block<0.25) {
                        multicolor[widthIndex][heightIndex] = blockColor;
                    }
                }
            }
        }

    /**
     *
     * @param widthIndex is the x coordinates
     * @param heightIndex is the y coordinates
     * @returns the size of rectangles
     */
    private static Rectangle rectangle(int widthIndex,int heightIndex){
        int x = widthIndex*10;
        int y = heightIndex*10;
        return new Rectangle(x,y,10,10);
    }

    /**
     *
     * @param graphic allows the tiles to change color
     */
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        Color white = new Color(255, 255, 255);

        // When a square is clicked, this changes the color from white
        int counter = 0;
        for (int widthIndex=0;widthIndex<width;widthIndex++){
            for(int heightIndex=0;heightIndex<height;heightIndex++){

                if(multicolor[widthIndex][heightIndex]!=null){
                    graphic.setColor(multicolor[widthIndex][heightIndex]);
                } else {
                    graphic.setColor(white);}
                graphic.fillRect(widthIndex*10,heightIndex*10,10,10);
                counter++;
            }
        }
    }

    /**
     *
     */
    // This is the spread mechanic
    private void gotOoze() {
        for (int widthIndex = 0; widthIndex < width; widthIndex++) {
            for (int heightIndex = 0; heightIndex < height; heightIndex++) {
                if (ooooozin[widthIndex][heightIndex] == true) {

                    Random maybeOoze = new Random();
                    Float probablyOoze = maybeOoze.nextFloat();
                    Point pointStart = start[widthIndex][heightIndex];
                    // This subtracts the start distance to the current spread
                    float distance = Math.abs(heightIndex-pointStart.y) + Math.abs(widthIndex-pointStart.x);
                    double oozeProbability = 1.0/(distance*4+1);

                    //This determines the spread potential
                    if (probablyOoze<oozeProbability && oozeProbability>0.05){
                    getPoints.add(new Point(widthIndex,heightIndex));
                }
                }
            }
        }
                for(int x = 0;x < getPoints.size(); x++){
                    Point xCord = getPoints.get(x);

                    int width = xCord.x;
                    int height = xCord.y;

                    Color colorSpread = multicolor[width][height];
                    Point pointSpread = start[width][height];

                    // All points that can spread

                    if(height>0 && multicolor[width][height-1] == null){
                        // height point - 1
                        multicolor[width][height-1] = colorSpread;
                        start[width][height-1] = pointSpread;
                        ooooozin[width][height-1] = true;
                    }

                    if(height<49 && multicolor[width][height+1] == null){
                        // height point + 1
                        start[width][height+1] = pointSpread;
                        multicolor[width][height+1] = colorSpread;
                        ooooozin[width][height+1] = true;
                    }

                    if(width>0 && multicolor[width-1][height] == null) {
                        // width point - 1
                        start[width - 1][height] = pointSpread;
                        multicolor[width - 1][height] = colorSpread;
                        ooooozin[width - 1][height] = true;
                    }

                    if(width<49 && multicolor[width+1][height] == null) {
                        // width point + 1
                        multicolor[width + 1][height] = colorSpread;
                        start[width + 1][height] = pointSpread;
                        ooooozin[width + 1][height] = true;
                    }
                    // starting point no longer oozing
                    ooooozin[width][height] = false;
                }
            }

    /**
     * e = event
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int widthIndex = e.getX()/10;
        int heightIndex = e.getY()/10;

        // Allows clickability and determines which random color will start
        if (widthIndex<width && heightIndex<height && multicolor[widthIndex][heightIndex] == null){
            gotOoze();
            Random ohFun = new Random();
            // r = red , g = green , b = blue
            float r = ohFun.nextFloat();
            float g = ohFun.nextFloat();
            float b = ohFun.nextFloat();
            Color randomColor = new Color(r, g, b);
            multicolor[widthIndex][heightIndex] = randomColor;
            ooooozin[widthIndex][heightIndex] = true;
            start[widthIndex][heightIndex] = new Point(widthIndex,heightIndex);
            arrayRectangles.add(new Rectangle(widthIndex*10,heightIndex*10,10,10));
            revalidate();
            repaint(); }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePressed(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Allows ooze to ooze
        gotOoze();
        repaint();
    }
}
