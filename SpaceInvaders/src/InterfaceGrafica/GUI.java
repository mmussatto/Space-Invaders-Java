/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGrafica;

import Engine.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *  Interface grafica
 
 * 
 * @author Murilo
 */
public class GUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
              
        stage.setTitle("Space Invaders");
        
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        Canvas canvas = new Canvas( 1500, 1000 );
        root.getChildren().add( canvas );
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setFill( Color.GREEN );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);
        
        /*Criacao do Jogo*/
        Game game = new Game(gc);
        game.IniciaGame();

        
        /*Entradas do Player*/
        ArrayList<String> input = new ArrayList<>();
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            String code = event.getCode().toString();
            
            if(!input.contains(code)) input.add(code);
        });
        
        scene.setOnKeyReleased((KeyEvent event) -> {
            String code = event.getCode().toString();
            input.remove( code );
        });
        
        
        LongValue Tempo_Antigo = new LongValue(System.nanoTime());
        
        
        new AnimationTimer()
        {
        @Override
        public void handle(long Tempo_Atual)
        {
            
                double timer = 0;
                
                // calculate time since last update.
                double Tempo_Decorrido = (Tempo_Atual - Tempo_Antigo.value) / 1000000000.0;
                Tempo_Antigo.value = Tempo_Atual;
                
                
                //gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                game.Display.Clear();
                
                game.Ocanhao.setVelocity(0, 0);
                if(input.contains("LEFT")){
                    if(!(game.Ocanhao.getPosX()-20 <= 0))
                        game.Ocanhao.setVelocity(-290, 0);
                }
                    
                if(input.contains("RIGHT")){
                    if(!(game.Ocanhao.getPosX()+game.Ocanhao.getWidth()+20 >= canvas.getWidth()))
                    game.Ocanhao.setVelocity(290, 0);
                }
                    
                
                if(input.contains("SPACE"))
                    game.AtiraCanhao();
                
                game.AtiraInvasor();
                game.MoveEntities(Tempo_Decorrido);
                if(game.Colisao() || timer > 0) {
                    if(timer == 0){
                        timer ++;
                        game.Ocanhao.Atingido(false);
                    }
                    else if (timer >= 999999999*999999999){
                        timer = 0;
                    }
                    else{
                        timer ++;
                    }
                    
                }
                else{
                    
                    System.out.println(" ");

                    game.DesenhaEntities();

                    game.MostraInfo(gc);
                }
                
                if(game.VerficaFim()){
                    game.GameOver();
                    this.stop();
                }
                


        }
    }.start();
      
        
    stage.show();
        
        
    }
    
}
