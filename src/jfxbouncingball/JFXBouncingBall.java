package jfxbouncingball;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.util.Duration;


public class JFXBouncingBall extends Application {

   // public static Circle ball;
    public static Sphere ball;
    public static Pane canvas;
    double deltaX=3;
    double deltaY=3;

    @Override
    public void start(final Stage primaryStage) {

        try {
            //**********************************************************
            Binding sharedVar=new Binding();
            String pathScript=System.getProperty("user.dir");
            GroovyScriptEngine engine=new GroovyScriptEngine(pathScript);
            sharedVar.setVariable("imgNumber", "1");
            sharedVar.setVariable("vitesse", 10);
            sharedVar.setVariable("ballRayon", 10);
            engine.run("monScript.groovy", sharedVar);
            int numimg=Integer.parseInt(sharedVar.getVariable("imgNumber").toString(),10);
            int vitesse=Integer.parseInt(sharedVar.getVariable("vitesse").toString(),10);
            int ballRayon=Integer.parseInt(sharedVar.getVariable("ballRayon").toString(),10);

            //***********************************************************

            canvas = new Pane();
            //Image img=new Image(getClass().getResourceAsStream("bball"+((int)(Math.random()*2)+1)+".jpg"));
            Image img=new Image(getClass().getResourceAsStream("bball"+numimg+".jpg"));
            ImageView imgv=new ImageView(img);

            final Scene scene = new Scene(canvas, 800, 450);

            primaryStage.setResizable(false);

            primaryStage.setTitle("Boucing Ball JavaFX");
            primaryStage.setScene(scene);
            primaryStage.show();

            //ball = new Circle(10, Color.BLUEVIOLET);
            ball=new Sphere(ballRayon);
            PhongMaterial phong=new PhongMaterial();
            phong.setSpecularMap(new Image(getClass().getResourceAsStream("imp32.jpg")));
            phong.setDiffuseMap(new Image(getClass().getResourceAsStream("imp32.jpg")));
            //phong.setSelfIlluminationMap(new Image(getClass().getResourceAsStream("imp32.jpg")));

            phong.setSpecularPower(1.0);
            ball.setMaterial(phong);

            ball.relocate((int)(Math.random()*700), (int)(Math.random()*400));

            canvas.getChildren().addAll(imgv,ball);

            final Timeline boucle = new Timeline(new KeyFrame(Duration.millis(vitesse), (final ActionEvent t) -> {
                ball.setLayoutX(ball.getLayoutX() + deltaX);
                ball.setLayoutY(ball.getLayoutY() + deltaY);

                final Bounds limites = canvas.getBoundsInLocal();
                final boolean atRightBorder = ball.getLayoutX() >= (limites.getMaxX() - ball.getRadius());
                final boolean atLeftBorder = ball.getLayoutX() <= (limites.getMinX() + ball.getRadius());
                final boolean atBottomBorder = ball.getLayoutY() >= (limites.getMaxY() - ball.getRadius());
                final boolean atTopBorder = ball.getLayoutY() <= (limites.getMinY() + ball.getRadius());

                if (atRightBorder || atLeftBorder) {
                    deltaX *= -1;
                }
                if (atBottomBorder || atTopBorder) {
                    deltaY *= -1;
                }
            }));

            boucle.setCycleCount(Timeline.INDEFINITE);
            boucle.play();
        } catch (IOException | ResourceException | ScriptException ex) {
            Logger.getLogger(JFXBouncingBall.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(final String[] args) {
        launch(args);
    }
}