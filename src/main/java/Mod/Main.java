package Mod;

import com.almasb.fxgl.entity.level.tiled.Layer;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main extends Application {
    private List<MyCircle> circleList;
    private Stage mainStage;

    private final int xMax = 500;
    private final int yMax = 500;

    private final int maxNumCir = 1;

    private final double maxRadiusCircle = 5;

    private List<HashSet<MyCircle>> grid;

    private final int sizeGrid = (int)Math.floor(maxRadiusCircle * 2.1);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        circleList = new ArrayList<>();

        grid = new ArrayList<>();

        int numCell = (int)(Math.floor((double)xMax / sizeGrid) * Math.floor((double)yMax / sizeGrid));

        for (int i = 0; i < numCell; i++) {
            grid.add(new HashSet<>());
        }
    }

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        Scene scene = new Scene(new Group(), 500, 500);
        stage.setScene(scene);
        stage.sizeToScene();
//        stage.setX(0);
//        stage.setY(0);
        stage.show();
        new MyTimer().start();
    }

    private class MyTimer extends AnimationTimer {
        private long time = 0;
        private long oldLTime = 0;

        private Label label = new Label("0");

        @Override
        public void handle(long lTime) {
            if (oldLTime == 0) {
                oldLTime = lTime;
                return;
            }

            time = lTime - oldLTime;
            oldLTime = lTime;

            if (circleList.size() < maxNumCir) {
                randomSpawn();
            }

            collisionBounders();

            for (MyCircle circle : circleList) {
//                circle.setVelocityScalar(circle.getVelocityScalar() -
//                        Math.abs(circle.getVelocityVector().y * circle.getVelocityScalar()) +
//                        9.8 * time / 1e9 + circle.getVelocityVector().y * circle.getVelocityScalar());
//
//                circle.getVelocityVector().y += (9.8 * time / 1e9) /
//                        Math.abs(circle.getVelocityVector().y * circle.getVelocityScalar());
//
//                circle.normalizationVelocity();

                circle.setCenterX(circle.getCenterX() + circle.getVelocityVector().x * circle.getVelocityScalar());
                circle.setCenterY(circle.getCenterY() + circle.getVelocityVector().y * circle.getVelocityScalar());
            }

            collisionParticles();

            label.setText((int)( 1e9 / time) + "\t" + circleList.size());
            Group group = new Group(circleList.toArray(circleList.toArray(new Circle[0])));
            Group group1 = new Group(group, label);
            Scene scene = new Scene(group1, xMax, yMax);
            mainStage.setScene(scene);
        }

        void randomSpawn() {
            double maxVelocity = 10;
            double radius = Math.random() * 7 + 3;

            try {
                circleList.add(new MyCircle(Math.random() * xMax,
                        radius,
                        5,
                        Color.rgb((int) (Math.random() * 255),
                                (int) (Math.random() * 255),
                                (int) (Math.random() * 255))));

                Vec2D vec = new Vec2D((Math.random() - 0.5) * maxVelocity, (Math.random() - 0.5) * maxVelocity);

                circleList.get(circleList.size() - 1).setVelocityScalar(vec.modulus());
                circleList.get(circleList.size() - 1).setVelocityVector(vec);
                circleList.get(circleList.size() - 1).normalizationVelocity();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        void collisionBounders() {
            for (MyCircle circle : circleList) {
                if (circle.getCenterY() <= 0 + circle.getRadius()) {
                    circle.getVelocityVector().converseY();
                    circle.setCenterY(2 * circle.getRadius() - circle.getCenterY());

                    circle.getVelocityVector().y /= 2;
                    circle.setVelocityScalar(circle.getVelocityScalar() *
                            (1 - Math.abs(circle.getVelocityVector().y)));
                    circle.normalizationVelocity();
                }
                if (circle.getCenterY() >= yMax - circle.getRadius()) {
                    circle.getVelocityVector().converseY();
                    circle.setCenterY(2 * (yMax - circle.getRadius()) - circle.getCenterY());

                    circle.getVelocityVector().y /= 2;
                    circle.setVelocityScalar(circle.getVelocityScalar() *
                            (1 - Math.abs(circle.getVelocityVector().y)));
                    circle.normalizationVelocity();
                }
                if (circle.getCenterX() <= 0 + circle.getRadius()) {
                    circle.getVelocityVector().converseX();
                    circle.setCenterX(2 * circle.getRadius() - circle.getCenterX());

                    circle.getVelocityVector().x /= 2;
                    circle.setVelocityScalar(circle.getVelocityScalar() *
                            (1 - Math.abs(circle.getVelocityVector().x)));
                    circle.normalizationVelocity();
                }
                if (circle.getCenterX() >= xMax - circle.getRadius()) {
                    circle.getVelocityVector().converseX();
                    circle.setCenterX(2 * (xMax - circle.getRadius()) - circle.getCenterX());

                    circle.getVelocityVector().x /= 2;
                    circle.setVelocityScalar(circle.getVelocityScalar() *
                            (1 - Math.abs(circle.getVelocityVector().x)));
                    circle.normalizationVelocity();
                }
            }
        }

        void collisionParticles() {
            MyCircle first;
            MyCircle second;

            double distance;
            double sumRadiuse;

            for (int i = 0; i < circleList.size() - 1; i++) {
                for (int j = i + 1; j < circleList.size(); j++) {
                    first = circleList.get(i);
                    second = circleList.get(j);

                    distance = Vec2D.distance(first.getCenter(), second.getCenter());
                    sumRadiuse = (first.getRadius() + second.getRadius());

                    if (distance <= sumRadiuse) {
                        first.setVelocityVector(Vec2D.newNorVec(first.getCenter(), second.getCenter()));
                        second.setVelocityVector(Vec2D.newNorVec(second.getCenter(), first.getCenter()));

                        double sumVelocity = (first.getVelocityScalar() + second.getVelocityScalar()) / 2.1;

                        first.setVelocityScalar(sumVelocity);
                        second.setVelocityScalar(sumVelocity);

                        first.normalizationVelocity();
                        second.normalizationVelocity();

                        first.setCenterX(first.getCenterX() + first.getVelocityVector().x * (sumRadiuse - distance));
                        first.setCenterY(first.getCenterY() + first.getVelocityVector().y * (sumRadiuse - distance));

                        second.setCenterX(second.getCenterX() + second.getVelocityVector().x * (sumRadiuse - distance));
                        second.setCenterY(second.getCenterY() + second.getVelocityVector().y * (sumRadiuse - distance));
                    }
                }
            }
        }

    }
}
