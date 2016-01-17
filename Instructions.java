import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 *These are the on-screen instructions for the user. It will explain what the user 
 *should do and what the user should not do. 
 *
 *The code for box randomization may be modified so that this is visible
 * 
 * @author (Sroshti) 
 * @version (a version number or a date)
 */
public class Instructions extends Actor
{
    private GreenfootImage instructions;
    /**
     * Act - do whatever the Instructions wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Instructions(String instr) 
    {
        instructions = new GreenfootImage (instr, 25, Color.BLACK, null);
        setImage(instructions);
    }    
}
