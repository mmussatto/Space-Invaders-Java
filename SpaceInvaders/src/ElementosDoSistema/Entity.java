package ElementosDoSistema;
import Engine.*;
import javafx.geometry.Rectangle2D;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * @author Murilo Mussatto Nusp: 11234245
 */
public abstract class Entity {

    /**
     * Construtor da classe abstrata
     * 
     * @param g     jogo a qual a classe pertence
     * @param x     posicao no eixo X
     * @param y     posicao no eixo Y
     */
    public Entity(Game g, double x, double y) {
        game = g;
        PosX = x;
        PosY = y;
        VelX = 0;
        VelY = 0;
    }


    /**
     * Posição no eixo X.
     */
    protected double PosX;

    /**
     * Posição no eixo Y.
     */
    protected double PosY;

    /**
     * Velocidade no eixo X.
     */
    protected double VelX;
    
    /**
     * Velocidade no eixo Y.
     */
    protected double VelY;
    
    /**
     * Jogo a qual a entity pertence.
     */
    protected Game game;
    
    /**
     * Obejto da classe image que representa a entidade.
     */
    protected Image img;
    
    /**
     * Comprimento da imagem.
     */
    protected double width;
    
    /**
     * Altura da imagem.
     */
    protected double height;
    
    /**
     * true - esquerda
     * false - direita
     */
    protected boolean Direcao;

    /**
     * Método de movimento das Entidades.
     * 
     * @param d utilizacao depende da subclasse
     */
    public abstract void move(boolean d);
    

    /**
     * 
     * @return  posicao no eixo X
     */    
    public double getPosX(){
        return PosX;
    }

    /**
     * 
     * @return  posicao no eixo Y
     */    
    public double getPosY(){
        return PosY;
    }
    
    /**
     * 
     * @return  velocidade no eixo X
     */    
    public double getVelX(){
        return VelX;
    }
    
    /**
     * 
     * @return  velocidade no eixo Y
     */    
    public double getVelY(){
        return VelY;
    }
    
    /**
     * 
     * @return direcao do movimento
     */    
    public boolean getDirecao(){
        return Direcao;
    }
    
    /**
     *
     * @return
     */
    public double getWidth(){
        return width;
    }
    
    /**
     *
     * @return
     */
    public double getHeight(){
        return height;
    }
    
    
    /**
     * 
     * @param arq - Arquivo da Imagem da Entidade
     */ 
    public void setImage(String arq){
        Image i = new Image(arq);
        this.img = i;
        this.width = i.getWidth();
        this.height = i.getHeight();
    }
    
    /**
     *
     * @param x
     * @param y
     */
    public void setPosition(double x, double y)
    {
        PosX = x;
        PosY = y;
    }
    
    /**
     *
     * @param x
     * @param y
     */
    public void setVelocity(double x, double y)
    {
        VelX = x;
        VelY = y;
    }
    
    /**
     *
     * @param x
     * @param y
     */
    public void addVelocity(double x, double y)
    {
        VelX += x;
        VelY += y;
    }
    
    /**
     *
     * @param time
     */
    public void update(double time)
    {
        PosX += VelX * time;
        PosY += VelY * time;
    }
    
    /**
     *
     * @param gc
     */
    public void render(GraphicsContext gc)
    {
        gc.drawImage( img, PosX, PosY);
    }
    
    /**
     *
     * @return
     */
    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(PosX,PosY,width,height);
    }
    
    /**
     *
     * @param s
     * @return
     */
    public boolean intersects(Entity s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
    }
    
    @Override
    public String toString()
    {
        return " Position: [" + PosX + "," + PosY + "]" 
        + " Velocity: [" + VelX + "," + VelY + "]";
    }
    
}