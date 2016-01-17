import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * A Generic Button to display text that is clickable. 
 * 
 * @author Jordan Cohen, Ashley Wang
 * @version v0.1.5
 */
public class TextButton extends Actor
{
    // Declare private variables
    private GreenfootImage myImage;
    private String buttonText;
    private int textSize;
    private boolean isClicked; 
    private char type;

    /**
     * Construct a TextButton with a given String at the default size
     * 
     * @param text  String value to display
     * 
     */
    public TextButton (String text)
    {
        this(text, 20);
    }

    /**
     * Construct a TextButton with a given String and a specified size
     * 
     * @param text  String value to display
     * @param textSize  size of text, as an integer
     * 
     */
    public TextButton (String text, int textSize)
    {
        buttonText = text;
        if (buttonText.indexOf("**TEXT BOX**") != -1) type = 't';
        click(false);
        isClicked = false;
    }

    /**
     * Change the text displayed on this Button
     * 
     * @param   text    String to display
     */
    public void update (String text)
    {
        buttonText = text;
        click(false);
        isClicked = false;
    }
    
    /**
     * Act. Get clicked. 
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) click(!isClicked);
        if (type == 't')  //t for text box
            textBoxBehaviour();
    }
    
    /**
     * Change colour
     * 
     * @param is true if it is getting clicked
     */
    public void click(boolean is)
    {
        Color back, front;
        if (is) {
            back = Color.GRAY;
            front = Color.BLUE;
        } else {
            back = Color.WHITE;
            front = Color.RED;
        }
        
        GreenfootImage tempTextImage = new GreenfootImage (buttonText, 20, front, back);
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (back);
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.BLACK);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
        
        isClicked = is;
    }
    
    /**
     * @return   isClicked
     */
    public boolean isClicked()
    {
        return isClicked;
    }

    /**
     * Text box function
     */
    public void textBoxBehaviour()
    {
        String k = Greenfoot.getKey();
        if (k != null && !k.equals("shift") && !k.equals("enter") && !k.equals("control")) {
            if (k != "backspace" && k != "space") {
                update(buttonText + k);
            } else if (buttonText.length() == 1 && k == "backspace") {
                update("");
            } else if ( !(buttonText.isEmpty()) && k == "backspace") {
                update(buttonText.substring(0, buttonText.length() - 1));
            } else if (k.equals("space")) {
                update(buttonText + " ");
            }
        }
    }
    
    /**
     * @return     the button's type
     */
    public char getType()
    {
        return type;
    }
    
    /**
     * @return     the button's text
     */
    public String getText()
    {
        return buttonText;
    }
}
