import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class Main
{
    public static void main(String[] args) {
        int countText = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter no. of elements you want in array:");
        countText = scanner.nextInt();
        String[] theText = new String[countText];
        for(int i = 0; i < countText; i++) {
            theText[i] = scanner.next();
        }
        int L = 18;
        System.out.println(Main.fullJustify(theText, L));
    }
    public static List<String> fullJustify(String[] words, int L) {
        List<String> list = new ArrayList<String>();
        List<String> wordsLeft = new ArrayList<String>(Arrays.asList(words));
        List<String> toUse = new ArrayList<String>();
        int wordsUsed = 0;
        boolean lastLine = false;
        if (wordsLeft.get(0).length() == 0) {
            StringBuffer noBuffer = new StringBuffer(L);
            for (int addGhostSpaces = 0; addGhostSpaces < L; addGhostSpaces++)
                noBuffer.append(" ");
            list.add(noBuffer.toString());
            return list;
        } else{
            while (wordsLeft.size() > 0) {
                int lengthTotalChars = 0;
                int spacesLeft = L;
                int charsTotal = 0;
                int i = 0;
                while (spacesLeft > 0 && wordsUsed < wordsLeft.size()) {
                    int getWordLength = wordsLeft.get(i).length();
                    if (spacesLeft < getWordLength) spacesLeft = 0;
                    if (spacesLeft >= getWordLength) {
                        spacesLeft -= getWordLength;
                        charsTotal += wordsLeft.get(i).length();
                        toUse.add(wordsLeft.get(i));
                        wordsUsed++;
                        if (lengthTotalChars < L) {
                            lengthTotalChars++;
                            spacesLeft--;
                        }
                    }
                    i++;
                }
                wordsLeft.subList(0, toUse.size()).clear();
                if (wordsLeft.size() == 0) lastLine = true;
                list.add(createLine(toUse, L, charsTotal, lastLine).toString());
                toUse.clear();
                wordsUsed = 0;
            }
            return list;
        }
    }

    public static StringBuffer createLine(List<String> s, int lineLength, int charsTotal, boolean lastLine) {
        StringBuffer sb = new StringBuffer(lineLength);
        int spaceInAll = lineLength;
        int wordsTotal = s.size();
        if (lastLine) {
            for (int spaceLoop = 0; spaceLoop < s.size(); spaceLoop++) {
                sb.append(s.get(spaceLoop) + " ");
                int newWordLength = s.get(spaceLoop).length() + 1;
                spaceInAll -= newWordLength;
            }

            for (int remainingLastSpace = 0; remainingLastSpace < spaceInAll; remainingLastSpace++)
                sb.append(" ");
        }else{

            if (s.get(0).length() == lineLength) {
                sb.append(s.get(0));
            }else{
                int spaces = lineLength - charsTotal;
                int lastToAdd;
                if (wordsTotal == 1) {
                    lastToAdd = wordsTotal;
                } else {
                    lastToAdd = wordsTotal-1;
                }
                int number = spaces/lastToAdd;
                int toAdd_remain = spaces%lastToAdd;
                for (int remainder = 0; remainder < wordsTotal; remainder++) {
                    sb.append(s.get(remainder));
                    for (int sP = 0; sP < number; sP++)
                        sb.append(" ");
                    if (remainder < toAdd_remain) sb.append(" ");
                }
            }
        }
        sb.setLength(lineLength);
        System.out.println(sb.toString() + "| (" + sb.toString().length() + ")");
        return sb;
    }
}