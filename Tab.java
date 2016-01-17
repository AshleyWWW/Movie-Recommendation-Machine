import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Menu tabs that have labels. 
 * The purpose is to reduce the number of buttons on 
 * the screen at any one time. 
 * Contents of genImage(_backClr_) are from Mr Cohen's 
 * TextButton class. 
 * 
 * @author Ashley Wang 
 * @version April 2015 -> Jan 2016
 */
public class Tab extends Actor
{
    private TextButton[] items; //the buttons that belong to this tab
    private final int ACT_TIME = 60; //the time it takes for the whole animation to run
    String buttonText; //text displayed on button
    int fontSize, count;

    /**
     * Constructor. 
     * Some code taken from Mr Cohen's TextButton class.
     *
     * @param  items    the items under this tab
     */
    public Tab(TextButton[] t)
    {
        this(t, " ", 20);
    }

    /**
     * Constructor. 
     * Some code taken from Mr Cohen's TextButton class.
     *
     * @param  items    the items under this tab
     * @param text the text displayed on this button
     */
    public Tab(TextButton[] t, String text)
    {
        this(t, text, 20);
    }

    /**
     * Constructor. 
     * Some code taken from Mr Cohen's TextButton class.
     *
     * @param  items    the items under this tab
     * @param text the text displayed on this button
     * @param textSize the text size
     */
    public Tab(TextButton[] t, String text, int textSize)
    {
        items = t;
        fontSize = textSize; 
        buttonText = text;
        setImage(genImage(Color.BLACK));
        count = 0;
    }

    // /**
    // * Animates the Tab upon addition to world
    // *
    // * @param  w   The world it is in
    // */
    // public void addedToWorld(World w)
    // {
    // super.addedToWorld(w);
    // animate(true);
    // }

    /**
     * Act - do whatever the Tab wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        // if (Greenfoot.mouseClicked(this)) {
        // if (items[0].getWorld() != null) {
        // animate(false);
        // } else {
        // animate(true);
        // }
        if (count > 30 ) {
            animate(true);
            count = -1;
        }
        else if (count != -1)
            count++;
    }    

    /**
     * Animates the menu items
     *
     * @param  add  a boolean - if true, is adding items to the screen. false if removing. 
     */
    public void animate(boolean add)
    {
        int sign = 1;
        if (!add) {
            sign = -1;
        } 
        double[] speeds = new double[items.length];
        int height = items[0].getImage().getHeight();
        //final distance over some number of act cycles
        //final distance is the image height times (i + 1)
        int i, x;
        x = getX();
        //set movement speeds
        for (i = speeds.length - 1; i >= 0; i--) {
            speeds[i] = (double) sign*height*(i+1)/ACT_TIME;
            //add the buttons
            if (add) {
                getWorld().addObject(items[i], x, getY());
            }
        }
        double dy[] = new double[items.length];
        //move the buttons
        for (int j = 0; j < ACT_TIME; j++) {
            for (i = 0; i < items.length; i++) {
                dy[i] += speeds[i];
                if (Math.abs(dy[i]) > 1.0) {
                    items[i].setLocation(x, items[i].getY() + (int) dy[i]);
                    dy[i] %= 1.0;
                }
            }
            Greenfoot.delay(1);
        }
        //remove the buttons if necessary
        if (!add) {
            for (TextButton t : items) {
                getWorld().removeObject(t);
            }
        }
    }

    /**
     * Generates an image. 
     * From Mr Cohen's TextButton class. 
     *
     * @return     the GreenfootImage that was generated
     */
    public GreenfootImage genImage(Color backClr)
    {
        GreenfootImage tempTextImage = new GreenfootImage (buttonText, fontSize, Color.GREEN, backClr);
        GreenfootImage myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (backClr);
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);
        myImage.setColor (Color.WHITE);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        return myImage;
    }


}