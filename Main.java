import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class main {

    public static void main(String[] args) {
		
		int countText = 0;
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter no. of elements you want in array:");
		countText = scanner.nextInt();
		String[] theText = new String[countText];
		//String[] theText = {"Ali","With","Friends","go to","calss", "in", "5AM"};
		//String[] theText = {"This", "is", "an", "example", "of", "text", "justification."}; // 18
        //String[] theText = {"What","must","be","shall","be."}; // 12
        //String[] theText = {"Listen","to","many,","speak","to","a","few."}; //  6
		for(int i = 0; i < countText; i++) {
			theText[i] = scanner.next();
		}


        int L = 18;
        System.out.println(main.fullJustify(theText, L));

    }

    public static List<String> fullJustify(String[] words, int L) {

        // First we create the list to send back
        List<String> list = new ArrayList<String>(); // make a list every row

        // Then we create two more. wordsLeft = words left to use
        List<String> wordsLeft = new ArrayList<String>(Arrays.asList(words)); // words left to recover from

        // and the words to use in a current line
        List<String> toUse = new ArrayList<String>();

        // words used so loop knows how many words are left.
        int wordsUsed = 0;

        // used to detect if on last line
        boolean lastLine = false;

        // if we receive a list with no words... we output the number of spaces
        // as defined by L so L = 3 would output [   ]
        if (wordsLeft.get(0).length() == 0) {

            // new string buffer to add the word
            StringBuffer noBuffer = new StringBuffer(L);

            // add spaces as dictated by L
            for (int addGhostSpaces = 0; addGhostSpaces < L; addGhostSpaces++)
                noBuffer.append(" ");
            list.add(noBuffer.toString());
            return list;
        } else{
            // Normally, we do not so we do this instead.
            // Go through words left list while
            // there is still words in it
            while (wordsLeft.size() > 0) {

                // These variables help set the spaces, word index
                // and character values left to be analyzed
                int lengthTotalChars = 0;
                int spacesLeft = L;
                int charsTotal = 0;
                int i = 0;

                // while we have spaces left, and
                // words used is MORE than the size of wordsLeft
                while (spacesLeft > 0 && wordsUsed < wordsLeft.size()) {
                    // words length to detect if there's space
                    // to fit into the L of the paragraph
                    int getWordLength = wordsLeft.get(i).length();

                    // To avoid negative numbers, if spaces left
                    // is more than word length, that means no
                    // room left so we sit it to 0
                    if (spacesLeft < getWordLength) spacesLeft = 0;

                    // if space left is EQUAL to word length (smooth fit)
                    // OR space left is MORE than word left (above it)
                    // there is still room so we advance.
                    if (spacesLeft >= getWordLength) {

                        // take space away from length of word
                        // for every character, add to characters total
                        // add to the ListArray called toUse for parsing
                        // lastly, take away wordsToGo and add one to wordsUsed
                        spacesLeft -= getWordLength;
                        charsTotal += wordsLeft.get(i).length();
                        toUse.add(wordsLeft.get(i));
                        wordsUsed++;

                        // if length of total chars (word length & space)
                        // is LESS than length, we can continue adding
                        // to the variables
                        if (lengthTotalChars < L) {
                            lengthTotalChars++;
                            spacesLeft--;
                        }
                    }

                    // we move onto the next
                    // word so we +1 (i) the number
                    i++;
                }

                // we take the number of words left
                // and clear it from 0 tp that.
                wordsLeft.subList(0, toUse.size()).clear();

                // if we have no more words left, obviously
                // that means we are on the last line
                if (wordsLeft.size() == 0) lastLine = true;

                // we now parse the line accordingly
                // and return the new justified string to add to List
                // toUse = words fitted in one line, L = length of paragraph
                // charsTotal = number of characters (excluding spaces)
                // lastLine == detect if on last line)
                list.add(createLine(toUse, L, charsTotal, lastLine).toString());

                // we clear the toUse list after
                // every line is finished
                // and set the wordsUsed to 0.
                toUse.clear();
                wordsUsed = 0;
            }

            // after every word is finished parsing
            // we return the new list
            return list;
        }
    }

    public static StringBuffer createLine(List<String> s, int lineLength, int charsTotal, boolean lastLine) {

        // create a new StringBuffer for performance
        // during string concatenation
        StringBuffer sb = new StringBuffer(lineLength);

        // Set the length of paragraph
        int spaceInAll = lineLength;

        // set words total to use
        int wordsTotal = s.size();

        // if we're on the last line,
        // we are going to left-justify instead
        if (lastLine) {
            for (int spaceLoop = 0; spaceLoop < s.size(); spaceLoop++) {
                // add append the string with the word and extra space
                sb.append(s.get(spaceLoop) + " ");
                int newWordLength = s.get(spaceLoop).length() + 1;
                spaceInAll -= newWordLength;
            }
            // what remains gets appended with more spaces
            // until we run out of spaces
            for (int remainingLastSpace = 0; remainingLastSpace < spaceInAll; remainingLastSpace++)
                sb.append(" ");
        }else{
            // If not last line, we'll check if the whole
            // word takes up the whole line
            if (s.get(0).length() == lineLength) {
                // so we just append the word
                // without adding more spaces
                sb.append(s.get(0));
            }else{
                // here's where the MAGIC happens
                // spaces is the paragraph divided by characters total
                int spaces = lineLength - charsTotal;

                // lastToAdd = will say how many
                // words to use in the algorithm
                int lastToAdd;
                if (wordsTotal == 1) {
                    // if we have 1 word
                    // lastToAdd will obviously be 1
                    lastToAdd = wordsTotal;
                } else {
                    // if not, it will be word total MINUS 1
                    lastToAdd = wordsTotal-1;
                }

                // number = get spaces divide by words
                // This is how many space breaks are in line
                int number = spaces/lastToAdd;

                // get the remaining (if odd space breaks)
                // to add to the left-most for that "justification"
                // example "Hello   World!"
                //  L=14    -----XXY------ [X = number, Y =toAdd_remain]
                int toAdd_remain = spaces%lastToAdd;

                // every word, we iterate this loop
                for (int remainder = 0; remainder < wordsTotal; remainder++) {
                    // first we add the word to the StringBuffer
                    sb.append(s.get(remainder));
                    // each word gets this mount of spaces
                    for (int sP = 0; sP < number; sP++)
                        sb.append(" ");

                    // if the loop is less than toAdd_remainder
                    // that means we still have one more space
                    // to give to this word on the line
                    if (remainder < toAdd_remain) sb.append(" ");
                }
            }
        }

        // Then we trim the sentence to the length
        // by trimming the trailing added space
        // so it fits exactly into the paragraph line
        sb.setLength(lineLength);

        // Readability for the console
        System.out.println(sb.toString() + "| (" + sb.toString().length() + ")");

        // we return the newly-justified
        // StringBuffer and we are finished
        // with the space parsing
        return sb;
    }
}