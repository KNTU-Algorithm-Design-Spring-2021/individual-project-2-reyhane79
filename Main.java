import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
public class Main
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Number: ");
        int n = scanner.nextInt();
        double weight;
        int colour1, colour2, colour3, colour4, colour5, colour6;
        for (int i = 0; i < n; i++) {
            System.out.println("Cube" + (i + 1));
            System.out.print("weight of cube: ");
            weight = scanner.nextDouble();
            System.out.print("colors of cube: ");
            colour1 = scanner.nextInt();
            colour2 = scanner.nextInt();
            colour3 = scanner.nextInt();
            colour4 = scanner.nextInt();
            colour5 = scanner.nextInt();
            colour6 = scanner.nextInt();
            new ColourCube(weight, colour1, colour2, colour3, colour4, colour5, colour6);
        }
        Tower.buildHighestTower();
        GraficOfCubes.launchApplication(args);
    }
}


 class ColourCube
 {
     Double weight;
     ArrayList<Integer> colours;
     static Map<Double, ColourCube> cubes = new HashMap<>();
     static ArrayList<Double> weights = new ArrayList<>();
     static int[][] graphMatrix;
     static ArrayList<Tower>[][] towers;

    public ColourCube(double weight, int colour1, int colour2, int colour3, int colour4, int colour5, int colour6) {
        this.weight = weight;
        colours = new ArrayList<>();
        colours.add(colour1);
        colours.add(colour2);
        colours.add(colour3);
        colours.add(colour4);
        colours.add(colour5);
        colours.add(colour6);
        weights.add(weight);
        cubes.put(weight, this);
    }
    public static ColourCube getCube(int index) {
        return cubes.get(weights.get(index));
    }
     public ArrayList<Integer> getColors() {
         return colours;
     }
     public Double getWeight() {
         return weight;
     }
     public static ArrayList<Double> getWeights() {
         return weights;
     }
     
    private static boolean PossibleMakeTower(int cube1Index, int cube2Index) {
        if (cube1Index >= cube2Index) {
            return false;
        }
        ColourCube cube1 = getCube(cube1Index);
        ColourCube cube2 = getCube(cube2Index);
        ArrayList<Integer> colors = cube2.getColors();
        Tower tower;
        boolean result = false;
        for (Integer color : cube1.getColors()) {
            if (colors.contains(color)) {
                tower = new Tower(
                        2,
                        5 - cube1.getColors().indexOf(color),
                        5 - colors.indexOf(color));
                tower.addToTopColors(cube1.getColors().get(tower.getTopColorIndex()));
                tower.addToTopColors(color);
                towers[cube1Index][cube2Index].add(tower);
                result = true;
            }
        }
        return result;
    }
    public static void setHighestTower() {
        int n = cubes.size(), maximumH = 0, maxHeightRow = 0, maxHeightColumn = 0,maxCombinedHeight;
        for (int k = 1; k < n; k++) {
            for (int i = 0, j = i + k; i < n && j < n; i++) {
                if (graphMatrix[i][j] == 1 && maximumH < 2) {
                    maximumH = 2;
                    maxHeightRow = i;
                    maxHeightColumn = j;
                }
                for (int p = i + 1; p < j; p++) {
                    if (Tower.getMaxHeight(towers[i][p]) == 0 || Tower.getMaxHeight(towers[p][j]) == 0)
                        continue;
                    maxCombinedHeight = getMaxCombinedHeight(i, j, p);
                    graphMatrix[j][i] = p;
                    if (maximumH < maxCombinedHeight) {
                        maximumH = maxCombinedHeight;
                        maxHeightRow = i;
                        maxHeightColumn = j;
                    }
                }
            }
        }
        Tower.setHighestTowerTopColors(towers[maxHeightRow][maxHeightColumn]);
        Tower.setHighestTower(maxHeightRow, maxHeightColumn);
    }
    public static void makeGraph() {
     int n = weights.size();
     graphMatrix = new int[n][n];
     towers = new ArrayList[n][n];
     for (int i = 0; i < n; i++) {
         for (int j = 0; j < n; j++) {
             graphMatrix[i][j] = 0;
             towers[i][j] = new ArrayList<>();
             if (PossibleMakeTower(i, j)) {
                 graphMatrix[i][j] = 1;
             }
         }
     }
 }
    public static void setTower(int from, int to) {
        if (graphMatrix[from][to] != 0) {
            setTower(from, graphMatrix[from][to]);
            Tower.CubeAddToTower(getCube(graphMatrix[from][to]));
            setTower(graphMatrix[from][to], to);
        }
    }
     private static int getMaxCombinedHeight(int row, int column, int cubeIndex) {
         Tower tower;
         int maximmumH = 0;
         for (Tower tower1 : towers[row][cubeIndex]) {
             for (Tower tower2 : towers[cubeIndex][column]) {
                 if (tower1.getBottomColorIndex() == 5 - tower2.getTopColorIndex()) {
                     tower = new Tower(tower1.getHeight() + tower2.getHeight() - 1,
                             tower1.getTopColorIndex(), tower2.getBottomColorIndex());
                     tower.addToTopColors(tower1.getTopColors(), tower2.getTopColors());
                     towers[row][column].add(tower);
                     if (maximmumH < tower.getHeight()) {
                         maximmumH = tower.getHeight();
                     }
                 }
             }
         }
         return maximmumH;
     }
}


class Tower
{
    int h;
    int TCI;
    private int bottomColorIndex;
    ArrayList<Integer> topColors;
    static int[] highestTowerTopColors;
    static ColourCube[] highestTower;
    static int highestTowerIndex = 0;
    public Tower(int height, int topColorIndex, int bottomColorIndex) {
        this.h = height;
        this.TCI = topColorIndex;
        this.bottomColorIndex = bottomColorIndex;
        topColors = new ArrayList<>();
    }
    public ArrayList<Integer> getTopColors() {
        return topColors;
    }
    public int getHeight() {
        return h;
    }
    public int getTopColorIndex() {
        return TCI;
    }
    public int getBottomColorIndex() {
        return bottomColorIndex;
    }
    public static ColourCube[] getHighestTower() {
        return highestTower;
    }
    public static int[] getHighestTowerTopColors() {
        return highestTowerTopColors;
    }
    public static void buildHighestTower() {
        Collections.sort(ColourCube.getWeights());
        ColourCube.makeGraph();
        ColourCube.setHighestTower();
    }
    public void addToTopColors(ArrayList<Integer> colors1, ArrayList<Integer> colors2) {
        for (Integer color : colors1) {
            topColors.add(topColors.size(), color);
        }
        topColors.remove(topColors.size() - 1);
        for (Integer color : colors2) {
            topColors.add(topColors.size(), color);
        }
    }
    public void addToTopColors(int color) {
        if (!topColors.contains(color)) {
            topColors.add(topColors.size(), color);
        }
    }
    public static int getMaxHeight(ArrayList<Tower> towers) {
        int maximum = 0;
        for (Tower tower : towers) {
            if (maximum < tower.h) {
                maximum = tower.h;
            }
        }
        return maximum;
    }
    public static void setHighestTowerTopColors(ArrayList<Tower> towers) {
        if (towers.isEmpty()) {
            highestTower = new ColourCube[1];
            highestTower[0] = ColourCube.getCube(0);
            highestTowerTopColors = new int[1];
            highestTowerTopColors[0] = ColourCube.getCube(0).getColors().get(0);
            return;
        }
        int maxHeight = 0;
        Tower highestTower = towers.get(0);
        for (Tower tower : towers) {
            if (maxHeight < tower.h) {
                maxHeight = tower.h;
                highestTower = tower;
            }
        }
        highestTowerTopColors = new int[highestTower.getTopColors().size()];
        int i = 0;
        for (Integer color : highestTower.getTopColors()) {
            highestTowerTopColors[i] = color;
            i++;
        }
    }
    public static void CubeAddToTower(ColourCube cube) {
        highestTower[highestTowerIndex] = cube;
        highestTowerIndex++;
    }
    public static void setHighestTower(int row, int column) {
        if (highestTowerTopColors.length == 1) {
            return;
        }
        highestTower = new ColourCube[highestTowerTopColors.length];
        highestTower[0] = ColourCube.getCube(column);
        highestTowerIndex++;
        ColourCube.setTower(column, row);
        highestTower[highestTowerIndex] = ColourCube.getCube(row);
        highestTowerIndex++;
    }
}
