package InterfaceGrafica;
import Engine.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author  Murilo Mussatto Nusp: 11234245
 *          
 */
public class Tela {
    
    /**s
     * Construtor da classe Tela
     *
     * 
     * @param gc        GraphicContext do JavaFx
     * @param g         jogo a qual a classe pertence
     */
    public Tela(GraphicsContext gc, Game g){      
        this.gc = gc;
        this.game = g;
        this.cv = gc.getCanvas();
        
        this.Background = new Image("Assets/Space.png");
    }
    
    private GraphicsContext gc;
    
    private Canvas cv; 
    
    private Image Background;
    
    
    /**
     * Jogo a qual a Tela pertence.
     */
    private final Game game;
    
    /**
     * Essa fução é responsável por Imprimir no console a matriz 
     * de char do dispay.
     */
    public void ImprimeTela(){
        
       
    }
    
    /**
     * Essa função é responsável por limpar a tela, preparando o inicio do jogo
     */
    public void IniciaTela(){
        gc.clearRect(0, 0, cv.getWidth(), cv.getHeight());
        
    }
    
    /**
     * Essa função adiciona um char qualquer a uma dada posicao da matriz 
     * do display
     * 
     * @param linha     linha onde o char será incluído
     * @param coluna    coluna onde o char será incluído
     * @param c         char que será incluído na matriz
     * 
     */
    public void AdicionaTela(int linha, int coluna, char c){
        //display[linha][coluna] = c;
    }
    
    /**
     * 
     * @return altura do display
     */
    public double getLinhas(){
        return cv.getHeight();
    }
    
    /**
     * 
     * @return comprimento do display
     */
    public double getColunas(){
        return cv.getWidth();
    }
    
    /**
     * Essa função é responsável por "limpar" a janela do console. Por enquanto
     * ela só printa várias quebras de linha para separar as impressões antigas
     * das novas
     */
    public void Clear(){
        gc.drawImage(Background, 0, 0, cv.getWidth(), cv.getHeight()); 
    }
    
    /**
     * Essa função é responsável por mostrar uma mensagem quando o jogador é 
     *  atingido por um tiro inimigo.
     * <p>
     * Para isso foram criadas duas strings e os caracteres dessas strings
     *  foram adicionadads na matriz do display.
     */
    public void MsgAtingido(){
        
        
        String msg1 = "Jogador Atingido!";
        String msg2 = "Vidas Restantes: " + String.valueOf(game.getVidas());
               
        gc.fillText( msg1, cv.getWidth()*2/5, cv.getHeight()*2/5 );
        gc.strokeText( msg1,cv.getWidth()*2/5, cv.getHeight()*2/5);
        
        gc.fillText( msg2, cv.getWidth()*2/5, cv.getHeight()*2/5+50 );
        gc.strokeText( msg2,cv.getWidth()*2/5, cv.getHeight()*2/5+50);
        
      
    }
    
    /**
     * Essa função é responsável por mostrar uma mensagem quando o jogador 
     *  derrota todos os inimigos e passa de fase.
     * <p>
     * Para isso foram criadas tres strings e os caracteres dessas strings
     *  foram adicionadads na matriz do display.
     */
    public void MsgProxFase(){
        
        String msg1 = "Todos os inmigos foram derrotados!";
        String msg2 = "Vidas Restantes: " + String.valueOf(game.getVidas());
        String msg3 = "Proxima Fase: " + String.valueOf(game.getFase());

        
                
    }
    
    /**
     * Essa função é responsável por mostrar uma mensagem quando os invasores
     *  se aproximam do canhão e a fase recomeça.
     * <p>
     * Para isso foram criadas quatro strings e os caracteres dessas strings
     *  foram adicionadads na matriz do display.
     */
    public void MsgInvasoresWin(){
        
        String msg1 = "Voce falhou!";
        String msg2 = "Os inimigos invadiram a Terra!";
        String msg3 = "Recomecando Fase!";
        String msg4 = "Vidas Restantes: " + String.valueOf(game.getVidas());
               

        
    }
    
    /**
     * Essa função é responsável por mostrar uma mensagem quando o jogador perde 
     *  o jogo.
     * <p>
     * Para isso foram criadas duas strings e os caracteres dessas strings
     *  foram adicionadads na matriz do display.
     */
    public void MsgGameOver(){
        
        String msg1 = "Game Over!";
        String msg2 = "Score: " + String.valueOf(game.getScore());
            
        gc.fillText( msg1, cv.getWidth()*2/5, cv.getHeight()*2/5 );
        gc.strokeText( msg1,cv.getWidth()*2/5, cv.getHeight()*2/5);
        
        gc.fillText( msg2, cv.getWidth()*2/5, cv.getHeight()*2/5+50 );
        gc.strokeText( msg2,cv.getWidth()*2/5, cv.getHeight()*2/5+50);
       
    }
    
    /**
     * Método interno da classe Tela, responsável por 
     *  imprimir mensgens (Strings) no display.
     * 
     * @param i     linha onde a mensgagem será mostrada
     * @param j     coluna onde a mensgaem será mostrada
     * @param msg   mensagem que será mostrada
     * 
     */
    private void ImprimeMsg(double i, double j, String msg){
        for (int k = 0; k < msg.length(); k++) {
            //display[i][j] = msg.charAt(k);
            j++;
        }
    }
}
