import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.FileReader;
import java.net.*;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.io.FileNotFoundException;
import java.lang.StringIndexOutOfBoundsException;
import java.util.Random;
/**
 * Write a description of class ITried here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ITried extends Actor
{ 
    public static String[][] uh() throws IOException {
        // Make a URL to the web page
        String address, str= "http://www.imdb.com/title/tt";
        String[][] movies = new String[10][5];
        int numSet[] = randGen();
        for (int i = 0; i < 10; i++){
            address = str + numSet[i] + "/";
            try{ 
                URL url = new URL(address);//"http://www.imdb.com/title/tt9999899/"
                // Get the input stream through URL Connection

                URLConnection con = url.openConnection();

                InputStream is = con.getInputStream();

                // Once you have the Input Stream, it's just plain old Java IO stuff.

                // For this case, since you are interested in getting plain-text web page
                // I'll use a reader and output the text content to System.out.

                // For binary content, it's better to directly read the bytes from stream and write
                // to the target file.

                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line = null;
                String file = "";
                // read each line and write to string
                while (br.readLine() != null){
                    file += br.readLine();
                }
                //0 id 1 name 2 genres 5 rating 4 year 3 ppl

                movies[i][4] = title(file);
                movies[i][2] = year(title(file));
                movies[i][0] = iD(file);
                movies[i][3] = people(file);
                movies[i][1] = genre(file);

            }
            catch(FileNotFoundException e){continue;}
            catch(IOException f){continue;}
        }
        return movies;
    }

    public static String title(String html){
        String str = html.substring(html.indexOf("<title>"),html.indexOf("</title>")); 
        str = str.replaceAll("&quot;", "");
        str = str.replaceAll("<title>", "");
        str = str.replaceAll("</title>", "");
        str = str.substring(0, str.indexOf("- IMDb"));
        return str;
    }

    public static String iD(String html){
        String str = html.substring(html.indexOf("<meta property=\"pageId\" content=\"tt"), html.length());
        str = str.replaceAll("<meta property=\"pageId\" content=\"tt", "");
        str = str.substring(0, str.indexOf("/>")-2);
        return str;
    }

    public static String year(String title){
        try{
            String str = title.substring(title.indexOf("("),title.indexOf(")")); 
            str = str.substring(str.length()-4,str.length());
            int year = 0;
            try {
                year = Integer.parseInt(str);
            }catch(NumberFormatException e){
                return "N/A";
            }
            if ((Integer.parseInt(str) >= 1750) && (Integer.parseInt(str) < 3000)){
                return str;
            }
            else {return "N/A";}
        }catch(StringIndexOutOfBoundsException e){ return "N/A";}
    }

    public static String genre(String html){
        String gen = ""; 
        String str, stri;
        boolean more = true; 
        //System.out.print("before do");
        try{
            str = html.substring(html.indexOf("<span class=\"itemprop\" itemprop=\"genre\">"),html.length());
        }catch(StringIndexOutOfBoundsException e){return "None";}
        do{
            try {
                //System.out.print("after try");
                str = str.substring(str.indexOf("<span class=\"itemprop\" itemprop=\"genre\">"),str.length());
                //System.out.println(str);
                //str = str.replaceAll("<span class=\"itemprop\" itemprop=\"genre\">", "");
                str = str.substring(40, str.length());
                // System.out.println(str);
                stri = str.substring(0,str.indexOf("<"));
                //System.out.print("after resize");
                gen = gen + ", " + stri;
                //System.out.print("after record");
            } catch(StringIndexOutOfBoundsException f){more = false;}
        }while(more);
        return gen;
    }

    public static String people(String html){
        //System.out.print(html.substring(html.indexOf("<meta name=\"description\" content=\"Directed by")));
        try{
            String str = html.substring(html.indexOf("<meta name=\"description\" content=\"Directed by"), html.length());

            str = str.replaceAll("<meta name=\"description\" content=\"Directed by", "");
            str = str.substring(0, str.indexOf("/>"));
            str = str.replaceAll("jr.,", ",");
            str = str.replaceAll("Jr.,", ",");
            str = str.replaceAll("With", "");
            str = str.replaceAll("with", "");

            String str2 = str.substring(0, str.indexOf("."));

            str = str.substring(str.indexOf(".")+1, str.length());
            //System.out.println("P" + str);
            //System.out.println(str2);
            try{
                String str3 = str.substring(0,str.indexOf("."));
                str = str.substring(str.indexOf(".")+1, str.length());
                return str2 + ", "+str3;
            } catch(StringIndexOutOfBoundsException e){return str2;}
            //str3 = str.substring(0,str.indexOf("/>"));//System.out.print(str+"\n"+str2+"\n"+str3);
        }
        catch(StringIndexOutOfBoundsException e){ return "";}
    }

    public void uhh() throws IOException {
        // Make a URL to the web page
        URL url = new URL("http://www.imdb.com/title/tt0000001/");

        // Get the input stream through URL Connection
        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();

        // Once you have the Input Stream, it's just plain old Java IO stuff.

        // For this case, since you are interested in getting plain-text web page
        // I'll use a reader and output the text content to System.out.

        // For binary content, it's better to directly read the bytes from stream and write
        // to the target file.

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        String file = "";
        // read each line and write to string
        while (br.readLine() != null){
            file += br.readLine();
        }

        System.out.println("Title:" + title(file));
        System.out.println("Year:" + year(title(file)));
        System.out.println("iD:" + iD(file));
        System.out.println("People:" + people(file));
        System.out.println("Genres:" + genre(file));
        //System.out.println(file);
    } 

    /**private String convert(double num){
    String stri,man,ans="", str = Double.toString(num);
    int exp, number;
    stri = str.substring(str.length()-1, str.length());
    exp = Integer.parseInt(stri);

    for (int i = str.length() - 1; i > -1; i--){
    try{
    if(Integer.parseInt(str.charAt(i)) !=0){
    //make answer = to the sequence of consecutive nonzero charactersans.substring(ans.length()-1, ans.length()) += str.charAt(i);
    }
    } catch(Exception e){}
    }
    man = str.substring(0,2);// i need to look for the first nonzero number
    for (int i = 1; i<exp; i++){
    ans += "0";
    }
    ans += Integer.parseInt(man);
    return ans;
    }
     */
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

    private static int[] randGen(){
        Random r = new Random();
        int[] set = new int[10];
        for (int i = 0; i < 10; i++){
            set[i] = r.nextInt(3284102-1248103) + 1248103;
        }
        return set;
    }
}