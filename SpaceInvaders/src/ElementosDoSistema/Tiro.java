package ElementosDoSistema;
import Engine.*;

/**
 * @author Murilo Mussatto Nusp: 11234245
 */
public class Tiro extends Entity {

    /**
     * Construtor da classe Tiro
     * 
     * @param g         jogo a qual a classe pertence
     * @param x         posicao no eixo X
     * @param y         posicao no eixo Y
     * @param patirador difere quem criou o tiro
     */
    public Tiro(Game g, double x, double y, boolean patirador) {
        
        super(g, x, y);
        
        Atirador = patirador;
                
        setImage("Assets/Tiro1.png");
        
        
    }

    /**
     * se true:     player
     * se false:    invasor
     */
    private boolean Atirador;

    /**
     * Nessa funcao, eu nao precisei utilizar o parametro d que veio da classe
     * abstrata devido a variáveis de controle da própria classe tiro, no
     * caso, a váriavel Atirador
     * 
     * @param d     nao é utilizado para a classe tiro
     */
    @Override
    public void move(boolean d){
        
        if(Atirador == true){ //tiro do player
            PosX-=VelX;
        }
        else{ //tiro do invasor
            PosX+=VelX;
        } 
    }
    
    /**
     * 
     * @return quem produziu o tiro
     */
    public boolean getAtirador(){
        return Atirador;
    }
}