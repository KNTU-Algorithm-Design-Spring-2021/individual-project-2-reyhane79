import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
public class GraficOfCubes extends Application {
    private static final int ratio1 = 15;
    private static final int ratio2 = 30;

    @Override
    public void start(Stage stage) throws Exception {
        Group pane = new Group();
        Scene scene = new Scene(pane, 700, 700);
        stage.setScene(scene);
        stage.show();
        ColourCube[] tower = Tower.getHighestTower();
        int[] topColors = Tower.getHighestTowerTopColors();
        int prevX = 250, prevY = 100, topColor;
        double prevWeight, weight;
        Rectangle rectangle;
        pane.getChildren().addAll(getUpperCube(prevX, prevY,tower[tower.length - 1]));
        for (int i = tower.length - 2; i >= 0; i--) {
            prevWeight = tower[i + 1].getWeight();
            weight = tower[i].getWeight();
            topColor = topColors[topColors.length - 1 - i];
            pane.getChildren().addAll(getUpperSide(prevX, prevY, prevWeight, weight, topColor));
            rectangle = new Rectangle(
                    prevX - ratio1 * weight,
                    prevY + ratio1 * weight + ratio2 * prevWeight,
                    ratio2 * weight, ratio2 * weight
            );
            rectangle.setFill(Color.WHITE);
            rectangle.setStroke(Color.BLACK);
            pane.getChildren().add(rectangle);
            Text text = new Text(String.valueOf(weight));
            text.setX(rectangle.getX() + rectangle.getWidth() / 3);
            text.setY(rectangle.getY() + rectangle.getWidth() / 3);
            pane.getChildren().add(text);
            pane.getChildren().add(getRightSide(prevX, prevY, prevWeight, weight));
            prevY += ratio2 * prevWeight;
        }
    }

    private static ArrayList<Node> getUpperCube(double x, double y, ColourCube cube) {
        ArrayList<Node> node = new ArrayList<>();
        double weight = cube.getWeight();
        Path path = new Path();
        MoveTo moveTo = new MoveTo(x, y);
        LineTo lineTo1 = new LineTo(x + ratio2 * weight, y);
        LineTo lineTo2 = new LineTo(x + (ratio2 - ratio1) * weight, y + ratio1 * weight);
        LineTo lineTo3 = new LineTo(x - ratio1 * weight, y + ratio1 * weight);
        ClosePath closePath = new ClosePath();
        path.getElements().addAll(moveTo, lineTo1, lineTo2, lineTo3, closePath);
        node.add(path);
        Rectangle rectangle = new Rectangle(x - ratio1 * weight, y + ratio1 * weight, ratio2 * weight, ratio2 * weight);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        node.add(rectangle);
        path = new Path();
        moveTo = new MoveTo(x + (ratio2 - ratio1) * weight, y + ratio1 * weight);
        lineTo1 = new LineTo(x + ratio2 * weight, y);
        lineTo2 = new LineTo(x + ratio2 * weight, y + ratio2 * weight);
        lineTo3 = new LineTo(x + (ratio2 - ratio1) * weight, y + (ratio1 + ratio2) * weight);
        closePath = new ClosePath();
        path.getElements().addAll(moveTo, lineTo1, lineTo2, lineTo3, closePath);
        node.add(path);
        Text text = new Text(String.valueOf(weight));
        text.setX(rectangle.getX() + rectangle.getWidth() / 3);
        text.setY(rectangle.getY() + rectangle.getWidth() / 2);
        node.add(text);
        return node;
    }

    private static ArrayList<Node> getUpperSide(double prevX, double prevY, double prevWeight, double weight, int color) {
        ArrayList<Node> nodes = new ArrayList<>();
        Path path = new Path();
        MoveTo moveTo = new MoveTo(prevX - ratio1 * prevWeight, prevY + (ratio1 + ratio2) * prevWeight);
        LineTo lineTo1 = new LineTo(prevX + (ratio2 - ratio1) * prevWeight, prevY + (ratio1 + ratio2) * prevWeight);
        LineTo lineTo2 = new LineTo(prevX + ratio2 * prevWeight, prevY + ratio2 * prevWeight);
        LineTo lineTo3 = new LineTo(prevX + ratio2 * weight, prevY + ratio2 * prevWeight);
        LineTo lineTo4 = new LineTo(prevX + (ratio2 - ratio1) * weight, prevY + ratio2 * prevWeight + ratio1 * weight);
        LineTo lineTo5 = new LineTo(prevX - ratio1 * weight, prevY + ratio2 * prevWeight + ratio1 * weight);
        ClosePath closePath = new ClosePath();
        path.getElements().addAll(moveTo, lineTo1, lineTo2, lineTo3, lineTo4, lineTo5, closePath);
        nodes.add(path);
        Text text = new Text("The colour that is top= " + color);
        text.setX(prevX + ratio2 * weight);
        text.setY(prevY + ratio2 * prevWeight);
        nodes.add(text);
        return nodes;
    }

    private static Path getRightSide(double prevX, double prevY, double prevWeight, double weight) {
        Path path = new Path();
        MoveTo moveTo = new MoveTo(prevX + (ratio2 - ratio1) * weight, prevY + ratio2 * prevWeight + ratio1 * weight);
        LineTo lineTo1 = new LineTo(prevX + ratio2 * weight, prevY + ratio2 * prevWeight);
        LineTo lineTo2 = new LineTo(prevX + ratio2 * weight, prevY + ratio2 * (prevWeight + weight));
        LineTo lineTo3 = new LineTo(prevX + (ratio2 - ratio1) * weight, prevY + ratio2 * prevWeight + (ratio1 + ratio2) * weight);
        ClosePath closePath = new ClosePath();
        path.getElements().addAll(moveTo, lineTo1, lineTo2, lineTo3, closePath);
        path.setFill(Color.WHITE);
        return path;
    }

    public static void launchApplication(String[] args) {
        launch(args);
    }
}
