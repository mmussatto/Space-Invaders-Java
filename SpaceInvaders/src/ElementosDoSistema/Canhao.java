package ElementosDoSistema;
import Engine.*;

/**
 * @author Murilo Mussatto Nusp: 11234245
 */
public class Canhao extends Entity {

    /**
     * Construtor da classe Canhao.
     * 
     * @param g             jogo a qual a classe pertence
     * @param x             posicao no eixo X
     * @param y             posicao no eixo Y
     * @param ptotaldelay   delay para atirar
     */
    public Canhao(Game g, double x, double y, int ptotaldelay) {
        
        super(g, x, y);
        
        Vidas = 3;
        Direcao = false;    /*começa movendo pra direita*/
        DelayTiro = 0;      
        TotalDelay = ptotaldelay;
    }

    /**
     *  Guarda no número de Vidas do Canhão
     */
    private int Vidas;
    
    /**
     * Variável para controlar a quantidade de tiros e 
     *  impedir varios tiros consecutivos
     */
    private int DelayTiro;
    
    /**
     * Controla a quantidade de maxima de delay para dar o tiro
     */
    private final int TotalDelay;
    


    /**
     * Esse método possui um contador formado pelas variáveis TotalDelay 
     *  e DelayTiro. A finalidade é imperdir que o jogador atire várias vezes
     *  consecutivas.
     * 
     * @return  null caso nao tenha atirado 
     *          obejto da classe Tiro caso tenha atirado
     */
    public Tiro Atrirar() {
        
            Tiro tiro = new Tiro(this.game, PosX+width*1/4, PosY-38, true);
            return tiro;  
    }

    /**
     * Esse método é chamado quando o jogador é atingido por um tiro inimigo.
     *  Ela chama vário métodos da classe jogo a qual o canhão pertence.
     * 
     * @param x true caso os inimigos tenham chegado perto do canhao e
     *          false se foi atingido por um tiro
     */
    public void Atingido(boolean x) {
        
        Vidas--;
        
        if(x) return;
        
        game.Display.Clear();
        game.Display.MsgAtingido();
        
        game.LimpaTiro();
        
    }

    /**
     * @return número de vidas restantes
     */
    public int getVidas() {
        return Vidas;
    }
    
    /**
     * se true: move p/ esquerda
     * se false: move p/ direita
     */
    @Override
    public void move(boolean d){
        
        /*Move para a Direita*/
        if(d == true){
            PosY -= VelY;
            Direcao = d;
        }
        /*Move para a Esquerda*/
        if(d == false){
            PosY += VelY;
            Direcao = d;
        }
    }
}