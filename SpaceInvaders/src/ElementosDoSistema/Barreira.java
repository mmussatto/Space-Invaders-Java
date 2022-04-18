package ElementosDoSistema;
import Engine.*;

/**
 *
 * @author Murilo Mussatto Nusp: 11234245
 */
public class Barreira extends Entity{
    
    /**
     * Construtor da classe Barreira
     * 
     * @param g             jogo a qual a classe pertence
     * @param x             posicao no eixo X
     * @param y             posicao no eixo Y
     */
    public Barreira (Game g, double x, double y){
        
        /*Construtor da Classe Mãe*/
        super(g, x, y);
        
        Destruido = false;
        setImage("Assets/Barreira.png");
    }
    
    /**
     * Estado da Barreira
     */
    private boolean Destruido;
      
    /**
     * @return estado da barreira
     */
    public boolean getDestruido(){
        return Destruido;
    }
    
    /**
     * Esse método é chamado quando a barreira é atingida
     */
    public void setDestruido(){
        Destruido = true;
    }
    
    /**
     * Esse método não é utilizado por essa classe uma vez que a barreira
     *  não se move.
     */
    @Override
    public void move(boolean d) {
    }
     
}