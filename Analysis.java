// import java.util.ArrayList;

/**
 * Analyzes movie data. 
 * At the moment, only ID, title, year, director/actor people, soon genre
 * 
 * @author Ashley Wang 
 * @version 0.1
 */
public class Analysis
{
    // trait names and corresponding likeability
    private String[][] traits; //genres array, people array, etc
    private int[][] like; 
    private float rLow, rHigh, yLow, yHigh; //the rating and year bounds
    // private int top;

    /**
     * Constructor for objects of class Analysis
     */
    public Analysis()
    {
        // top = -1;
    }

    // /**
    // * Constructor for objects of class Analysis
    // */
    // public Analysis(int x)
    // {
    // top = x; //the top-scoring number of movies
    // }

    /**
     * @param  strs String array to look in
     * @param s the String to look for
     * @return     the index number
     */
    public int indexOf(String[] strs, String s)
    {
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].equalsIgnoreCase(s)) {
                return i;
            }
        }
        return -1; //else
    }

    /**
     * Set the list of traits. 
     *
     * @param  t   array of trait Strings
     */
    public void setTraitList(String[][] t)
    {
        traits = t;
    }

    /**
     * Receives a array of traits likeability (1, 0, -1). 
     * The index no. is the trait ID (e.g. genre), 
     * the values are the likeability of the trait. 
     *
     * @param  like the likeability of the trait
     * @return     the sum of x and y
     */
    public void setLike(int[][] li)
    {
        like = li;
    }

    /**
     * Computes how "liked" a list of traits corresponding to
     * a movie or whatever is. 
     *
     * @param   tr  list of movie's traits
     * @return     a numerical score of how liked it is. 
     */
    public int likeLevel(String[] tr)
    {
        return likeLevel(tr, 0);
    }

    /**
     * Computes how "liked" a list of traits corresponding to
     * a movie or whatever is. 
     *
     * @param   tr  list of movie's traits
     * @param pos the position of this category of adjectives in the preference table
     * @return     a numerical score of how liked it is. 
     */
    public int likeLevel(String[] tr, int pos)
    {
        //find the Strings in tr in traits, get their index number
        //if they exist, add the same value that is found at the index
        int n, sum = 0;
        if (pos < traits.length) { //there is preference info at this position
            for (int i = 0; i < tr.length; i++) {
                n = indexOf(traits[pos], tr[i]);
                if (n != -1) {
                    sum += like[pos][n]; 
                }
            }
        }
        return sum;
    }

    /**
     * Set the rating acceptability range. 
     *
     * @param  low   the lower range of acceptability
     * @param  high the high end of accetability
     * @param  type the type (r for rating, y for year)
     */
    public void setRatingRange(float low, float high, char type)
    {
        if (type == 'r') {
            rLow = low;
            rHigh = high;
        } else if (type == 'y') {
            yLow = low;
            yHigh = high;
        }
    }

    /**
     * Checks to see if the rating is in the acceptable range
     *
     * @param  r the rating to check
     * @param type 'r' if you want to check rating, 'y' for year
     * @return     true if it is within range
     */
    public boolean isWithinRange(float n, char type)
    {
        if ((type == 'r' && n >= rLow && n <= rHigh) || 
        (type == 'y' && n >= yLow && n <= yHigh))
            return true;
        return false;
    }

    /**
     * Combines numbers in the first array
     * with the second array's (as a multiplier).
     *
     * @param  likes   the array of likeability
     * @param  wts the weightings
     * @return an int array of each item multiplied together
     */
    public int[] multiplyItems(int[] likes, int[] wts)
    {
        for (int i = 0; i < like.length && i < wts.length; i++) {
            likes[i] *= wts[i];
        }
        return likes;
    }

    // /**
    // * Takes in various lists of adjectives.
    // * Then returns them as a 1D array. 
    // * Example: 
    // *
    // * @param  str the string 2D array of words 
    // * @return 
    // */
    // public void sampleMethod()
    // {
    // // put your code here
    // }

    /**
     * Accepts a 1D array of all the movie's information, 
     * parses, and otherwise deals with it. 
     *
     * @param  info the String array of info
     * @return     the overall likeability 
     */
    public int dealWithInfo(String[] info)
    {
        int[] pos = {2, 5, 6};
        return dealWithInfo(info, pos);
    }

    /**
     * A version that calculates the likeability of 
     * all the adjective fields based on a legend that 
     * says where they are. 
     * Accepts a 1D array of all the movie's information, 
     * parses, and otherwise deals with it. 
     * Position 0 is ID
     * 1 is the normal name. 
     * 2 is a comma-separated list of genres/adjectives/tags/actors/etc
     * 5 is the rating. 
     * 4 is the year. 
     * 3 is the array of actors/directors/other real things
     * 6 is the array of keywords
     * Not all things have been implemented fully. 
     *
     * @param  info the String array of info
     * @param the positions of the adjectives
     * @return     the overall likeability 
     */
    public int dealWithInfo(String[] info, int[] adjPos)
    {
        int iLike = 0;
        //0th position is the unique ID
        //1st position is the list of genres
        String[] adj;
        for (Integer p : adjPos) { //each position that uses adjectives
            if (p < info.length && info[p] != null) { //but the info table needs to actually have info there
                // for (String st : info) System.out.println(st);
                adj = info[p].split(","); 
                //also trim the words - unless it's known that it'll always be ", "
                for (int i = 0; i < adj.length; i++) {
                    adj[i] = adj[i].trim();
                }
                iLike += likeLevel(adj, p); //likeability due to this set of adjectives
            }
        }

        // //check rating and year at the end
        // float num; 
        // try {
        // num = Float.parseFloat(info[3].trim());
        // if (!isWithinRange(num, 'r')) iLike = 0;
        // } catch (NumberFormatException e) {
        // System.out.println("No rating available for " + info[1]);
        // }
        // try {
        // num = Float.parseFloat(info[4].trim());
        // if (!isWithinRange(num, 'y')) iLike = 0;
        // } catch (NumberFormatException e) {
        // System.out.println("No year available for " + info[1]);
        // }
        return iLike;
    }

    /**
     * Make the number for tt___
     *
     * @param  n   a sample parameter for a method
     * @return     the string version
     */
    public static String genNum(int n)
    {
        String str = "000000";
        int dig = (int) Math.log10((double) n) + 1;
        if (dig == 7) return Integer.toString(n);
        return str.substring(0,str.length()) + Integer.toString(n);
    }

}
