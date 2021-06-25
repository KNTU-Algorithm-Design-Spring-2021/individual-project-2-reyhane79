import java.io.*;
import java.util.ArrayList;
import java.util.*;
public class Main {
    static List<String> output ;
    static HashMap<String , Boolean>  DICTIONARY ;
    public static HashMap<String , Boolean> readFile (){
        HashMap<String , Boolean> DICTIONARY = new HashMap<>();
        File file = new File("C:\\Users\\ASUS\\Desktop\\words_alpha.txt");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String fileString;
        try {
            while ((fileString = bufferedReader.readLine()) != null) {
                if(fileString.length() == 1 && !fileString.equals("a")){
                    DICTIONARY.put(fileString, false);
                }else {
                    DICTIONARY.put(fileString, true);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return DICTIONARY;
    }
    public static void main(String []args) {
        DICTIONARY = readFile();
        Scanner input = new Scanner(System.in);
        String in = input.next();
        spilteSentence(in);
        printOutput();
    }
    public static boolean checkValidation(String word){
        if(DICTIONARY.get(word) == null) {
            return false;
        } else if(DICTIONARY.get(word)) {
            return true;
        }
        return false;
    }
    public static boolean spilteSentence(String sentence) {
        output  = new ArrayList<>();
        sentence = sentence.toLowerCase();
        int length = sentence.length();

        if (length == 0)
            return true;

        if (checkValidation(sentence)) {
            output.add(sentence);
            return true;
        }
        for (int i = length; i >0 ; i--) {
            if ( checkValidation ( sentence.substring(0, i) )
                    && spilteSentence (sentence.substring(i, length) )) {
                output.add(sentence.substring(0,i));
                return true;
            }
        }

        return false;
    }
    public static void printOutput(){
        Collections.reverse(output);
        for(String string : output){
            System.out.print(string + " ");
        }
        System.out.println();
        output = new ArrayList<>();
    }
}
