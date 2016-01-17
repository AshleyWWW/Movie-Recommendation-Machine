import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.io.*;

/**
 * Interface is what holds the things that ask the user about 
 * what they want and also holds their answers.
 * 
 * @author Ashley Wang 
 * @version 0.1
 */
public class Interface extends World
{
    Tab question;
    TextButton[] answers; 
    // boolean[] ansClicked;
    ArrayList<String> qBank;
    TextButton next;
    int currentQ;
    int[][] likes;
    // Analysis analysis;

    /**
     * Constructor for objects of class Interface.
     * 
     */
    public Interface()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
        Greenfoot.setSpeed(57); //to allow the animation to move fast enough
        setPaintOrder(Tab.class); //tabs go on top of everything else

        // answers = new TextButton[9];
        // answers[0] = new TextButton(" [ MEH ] ");
        // answers[1] = new TextButton(" [ ENERGETIC ] ");
        // answers[2] = new TextButton(" [ SAD ] ");
        // answers[3] = new TextButton(" [ HAPPY ] ");
        // answers[4] = new TextButton(" [ ANGRY ] ");
        // answers[5] = new TextButton(" [ DISGUSTED ] ");
        // answers[6] = new TextButton(" [ AFRAID ] ");
        // answers[7] = new TextButton(" [ ENGSCI ] ");
        // answers[8] = new TextButton(" [ ARTSCI ] ");
        // question = new Tab(answers, " [ HOW ARE YOU FEELING TODAY? ] ");

        // addObject(question, getWidth()/2, getHeight()/4);
        qBank = loadQBank("Questions.txt");
        likes = new int[qBank.size()][];
        newQ(0);

        next = new TextButton(" [ NEXT ] ");
        addObject(next, getWidth() - next.getImage().getWidth()/2 -10, getHeight() - next.getImage().getHeight()/2-10);

    }

    // /**
    // * Use Analysis
    // */
    // public void analyzePrep()
    // {
    // analysis = new Analysis();
    // analysis.setTraitList(qBankToTraits());
    // analysis.setLike(likes);
    // // String[][] ts = {{"Comedy", "Fantasy", "Action"},{"Bob","Joe","Mustachio"}};
    // // a.setTraitList(ts); //the user likes these things
    // // int[][] ls = {{-1, 0, 1}, {2,2,2}};
    // // a.setLike(ls);
    // // String[] table = {"dsfja", "Horror, Comedy, DARG,AFSAFDS, Chilly, Act", "Jack, R2D2, Mustachio"};

    // // // System.out.println(a.likeLevel(ts2));
    // // int[] adjPos = {1};
    // // System.out.println(a.dealWithInfo(table, adjPos));
    // }

    /**
     * Main analysis method
     *
     * @param  movies   a 2D array of movies and their info
     * @return     a 2D string array, sorted from most likeable to least
     */
    public String[][] analyzeAll(String[][] movies)
    {
        Analysis analysis = new Analysis();
        analysis.setTraitList(qBankToTraits());
        analysis.setLike(likes);
        //so, there's a big table. 
        //take each movie and analyze it
        int[] worth = new int[movies.length];
        int[] adjPos = {1,2}; //update the adjective positions later
        for (int i = 0; i < movies.length; i++) {
            worth[i] = analysis.dealWithInfo(movies[i], adjPos);
        }
        //now, their worth is known. 
        //so sort them and the movies array at the same time. 
        return quickSortLS(movies, worth, 0, worth.length-1);
    }

    /**
     * Tests  analyzeAll(_movies_)
     */
    public void aATester()
    {
        String[][] table = {{"tt10000", "bhel, fkralegnrlkan, nrja, Romance, nfekla", "Bob John", "1990", "C,D"},
                {"tt0000", "Comedy, Horror, Action, Sci-fi", "DARG", "1700", "A, B"}};
        table = analyzeAll(table);
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }

    // /**
    // * Call this method with individual lists of info. 
    // * Currently out of use. 
    // * 
    // * @param  t   the table of movie info
    // */
    // public void analyzeOne(String[] t)
    // {
    // int[] adjPos = {1};
    // int worth = analysis.dealWithInfo(t, adjPos);

    // }

    /**
     * Reads the question text file. 
     * Loads the questions and their answers. 
     *
     * @param  str  the entire file name of the question file
     */
    public static ArrayList<String> loadQBank(String str)
    {
        ArrayList<String> bank = new ArrayList<String>();
        String qA = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(str));
            str = br.readLine();
            while (str != null) { //still lines in the file
                while (str != null && !str.trim().equals("")) {
                    qA += str + ", ";
                    str = br.readLine(); //NEXT LINE 
                    //if this reads a "" (a lineskip), then break
                } //each line then separate with a comma
                // if (str.endsWith(", ")) { 
                //remove the last comma
                if (qA.length() > 2)
                    qA = qA.substring(0, qA.length() - 2);
                // }
                str = br.readLine();
                // if (str != null) 
                bank.add(qA);
                qA = "";
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Sorry, error.");
        }
        // while (bank.get(bank.size()-1).equals(null)) {
        // bank.remove(bank.size()-1);
        // }
        // System.out.println(bank.size());
        return bank;
    }

    /**
     * Changes the question on the screen. 
     *
     * @param  n   the question number in the question bank
     */
    public void newQ(int n)
    {
        String[] qA = splitString(qBank.get(n), ",");
        answers = new TextButton[qA.length-1]; //because the first in qA is the question. 
        //construct new set of text boxes
        for (int i = 0; i < answers.length; i++) {
            answers[i] = new TextButton(" [ " + qA[i+1] + " ] ");
        }
        question = new Tab(answers," [ " + qA[0] + " ] ");
        //now I've made a new set of tabs
        addObject(question, getWidth()/2, getHeight()/4);
        likes[currentQ] = new int[answers.length];
        // ansClicked = new boolean[answers.length];
    }

    /**
     * Removes the question and answer boxes. 
     */
    public void removeQA()
    {
        for (TextButton t : answers)
            removeObject(t);
        removeObject(question);
    }

    /**
     * Click behaviour
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(null)) {
            if (Greenfoot.mouseClicked(next)) {
                next.click(false);
                if (answers[0].getType() == 't') {
                    qBank.set(currentQ, splitString(qBank.get(currentQ), ",")[0] + ", " + answers[0].getText());
                    likes[currentQ] = new int[splitString(qBank.get(currentQ), ",").length-1];
                    for (int j = 0; likes[currentQ] != null && j < likes[currentQ].length; j++) {
                        if (splitString(qBank.get(currentQ), ",")[j+1].indexOf("**TEXT BOX**") == -1)     
                            likes[currentQ][j] = 1;
                        else 
                            likes[currentQ][j] = 0;
                    }
                }
                if (currentQ < qBank.size() - 1) { //next question is the last
                    currentQ++;
                    removeQA();
                    newQ(currentQ);
                } else if (currentQ >= qBank.size() - 1) {
                    removeQA();
                    //replace with Srishti's thing
                    try {
                        String[][] table = analyzeAll(ITried.uh());
                        System.out.println("ID no.\tGenre(s)\tYear\tPeople\tName");
                        for (int i = 0; i < table.length; i++) {
                            if (table[i][0] != null) {
                                for (int j = 0; j < table[i].length; j++) {
                                    while (table[i][j].length() > 1 && table[i][j].charAt(0) == ',') {
                                        // if (table[i][j].charAt(0) == ',' && table[i][j].length() > 1) {
                                        table[i][j] = table[i][j].substring(1);
                                        table[i][j] = table[i][j].trim();                                        
                                    } 
                                    System.out.print(table[i][j] + "\t");
                                }
                                System.out.println();
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Hadarg you, Srishti! \n ...And you too, IMDb!");
                    }
                }
            }
            //check the answer boxes for clicks
            for (int i = 0; i < answers.length; i++) {
                if (Greenfoot.mouseClicked(answers[i])) {
                    if (answers[i].isClicked()) likes[currentQ][i] = 0;
                    else likes[currentQ][i] = 1; 
                }
            }
        }
    }

    /**
     * Converts the question and answer bank to a trait list
     *
     * @return     the 2D string array of answers
     */
    public String[][] qBankToTraits()
    {
        String[] qA;
        String[][] traits = new String[qBank.size()][];
        for (int i = 0; i < traits.length; i++) { //for each question
            //split it into the question and the answers
            qA = qBank.get(i).split(","); 
            traits[i] = new String[qA.length-1];
            for (int j = 0; j < qA.length-1; j++) 
                traits[i][j] = qA[j+1].trim();
        }
        return traits;
    }

    private static int partition(String[][] ids, int arr[], int left, int right)
    {
        int i = left, j = right;
        int tmp;
        String[] tmps;
        int pivot = arr[(left + right) / 2];

        while (i <= j) {
            while (arr[i] > pivot)
                i++;
            while (arr[j] < pivot)
                j--;
            if (i <= j) {
                tmp = arr[i];
                tmps = ids[i];
                arr[i] = arr[j];
                ids[i] = ids[j];
                arr[j] = tmp;
                ids[j] = tmps;
                i++;
                j--;
            }
        }
        return i;
    }

    /**
     * This quicksort sorts from largest to smallest. 
     */
    public static String[][] quickSortLS(String[][] ids, int arr[], int left, int right) {
        int index = partition(ids, arr, left, right);
        if (left < index - 1)
            quickSortLS(ids, arr, left, index - 1);
        if (index < right)
            quickSortLS(ids, arr, index, right);
        return ids;
    }

    /**
     * Reverses an array
     *
     * @param  a   an array
     * @return     the reversed version
     */
    public static int[] reverse(int[] a)
    {
        int[] b = a;
        for (int i = 0; i < a.length; i++) {
            a[i] = b[b.length - 1- i];
        }
        return a;
    }

    // /**
    // * Sorts the array of value scores. 
    // * Incomplete. 
    // * 
    // * @param ids the unique IDs of each movie
    // * @param  v   the likeability scores (how much it matches what the person wanted)
    // * @return     the sorted array, with position 0 as the most liked
    // */
    // public static int[] sortValues(String[] ids, int[] v)
    // {
    // quickSortLS(ids, v, 0, v.length - 1);
    // return v;
    // }

    /**
     * A replacement split method. 
     * This one uses whatever divider,
     * but ignores whether there's any whitespace
     * to either side. 
     *
     * @param  str the comma-separated String
     * @param  sp  the string to divide it with
     * @return     the separated string array
     */
    public String[] splitString(String str, String sp)
    {
        String[] div = str.split(sp);
        for (int i = 0; i < div.length; i++) div[i] = div[i].trim();
        return div;
    }
}