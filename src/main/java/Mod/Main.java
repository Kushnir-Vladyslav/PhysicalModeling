package Mod;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private List<MyCircle> circleList;
    private Stage mainStage;

    private final int xMax = 500;
    private final int yMax = 500;

    private final int maxNumCir = 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        circleList = new ArrayList<>();
    }

    @Override
    public void start(Stage stage) {
        mainStage = stage;
        Scene scene = new Scene(new Group(), 500, 500);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        new MyTimer().start();
    }

    private class MyTimer extends AnimationTimer {
        private long time = 0;
        private long oldLTime = 0;

//        Color col = Color.valueOf(1);

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
//                circle.getVelocity().add(new Vec2D(0, 9.8 * time / 1e9));

                circle.setCenterX(circle.getCenterX() + circle.getVelocityVector().x * circle.getVelocityScalar());
                circle.setCenterY(circle.getCenterY() + circle.getVelocityVector().y * circle.getVelocityScalar());
            }

            collisionParticles();
//            for (MyCircle circle: circleList) {
//                if (circle.getCenterY() > yMax - circle.getRadius()) {
//                    circle.getVelocity().mul(-0.75);
//                }
//            }
//
//            for (int i = circleList.size() - 1; i >= 0; i--) {
//                if (circleList.get(i).getCenterY() > yMax + circleList.get(i).getRadius()) {
//                    circleList.remove(i);
//                }
//            }

            Group group = new Group(circleList.toArray(circleList.toArray(new Circle[0])));
            Scene scene = new Scene(group, xMax, yMax);
            mainStage.setScene(scene);
        }

        void randomSpawn() {
            double maxVelocity = 10;
            double radius = Math.random() * 7 + 3;

            try {
                circleList.add(new MyCircle(Math.random() * xMax,
                        Math.random() * yMax,
                        25,
                        Color.rgb((int) (Math.random() * 255),
                                (int) (Math.random() * 255),
                                (int) (Math.random() * 255))));

                Vec2D vec = new Vec2D((Math.random() - 0.5) * maxVelocity, (Math.random() - 0.5) * maxVelocity);

                circleList.get(circleList.size() - 1).setVelocityVector(vec.normalized());
                circleList.get(circleList.size() - 1).setVelocityScalar(vec.modulus());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        void collisionBounders() {
            for (MyCircle circle : circleList) {
                if (circle.getCenterY() <= 0 + circle.getRadius()) {
                    circle.getVelocityVector().converseY();
                    circle.setCenterY(2 * circle.getRadius() - circle.getCenterY());
                }
                if (circle.getCenterY() >= yMax - circle.getRadius()) {
                    circle.getVelocityVector().converseY();
                    circle.setCenterY(2 * (yMax - circle.getRadius()) - circle.getCenterY());
                }
                if (circle.getCenterX() <= 0 + circle.getRadius()) {
                    circle.getVelocityVector().converseX();
                    circle.setCenterX(2 * circle.getRadius() - circle.getCenterX());
                }
                if (circle.getCenterX() >= xMax - circle.getRadius()) {
                    circle.getVelocityVector().converseX();
                    circle.setCenterX(2 * (xMax - circle.getRadius()) - circle.getCenterX());
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
                        first.setVelocityVector(Vec2D.newNorVec(first.getVelocityVector(), second.getVelocityVector()));
                        second.setVelocityVector(Vec2D.newNorVec(second.getVelocityVector(), first.getVelocityVector()));

                        second.setCenterX(second.getCenterX() + second.getVelocityVector().x * (distance - sumRadiuse));
                        second.setCenterY(second.getCenterY() + second.getVelocityVector().y * (distance - sumRadiuse));

                        second.setCenterX(second.getCenterX() + second.getVelocityVector().x * (distance - sumRadiuse));
                        second.setCenterY(second.getCenterY() + second.getVelocityVector().y * (distance - sumRadiuse));
                    }
                }
            }
        }

    }
}
