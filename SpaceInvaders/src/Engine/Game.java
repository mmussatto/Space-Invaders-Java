/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;
import java.util.*;

import ElementosDoSistema.*;
import InterfaceGrafica.*;
import javafx.scene.canvas.GraphicsContext;


/**
 * @author Murilo Mussatto Nusp: 11234245
 */
public class Game {

    /**
     * Construtor da Classe Game.
     * @param gc
     */
    public Game(GraphicsContext gc) {
        Score = 0;
        Nfase = 1;
        this.gc = gc;
    }

    /**
     * Pontuacao do Jogador.
     */
    private int Score;

    /**
     * Número da fase em que o jogador está.
     */
    private int Nfase;

    /**
     * Número total de invasores .
     */
    private final int Ninvasores = 55;
    
    /**
     * Contador do número de invasores destruídos.
     */
    private int Ninvasoresmortos = 0;
    
    /**
     * Obejto da Classe Tela que representa o display do jogo.
     */
    public Tela Display;
    
    /**
     * Vetor de Obejtos da Classe Invasor.
     */
    private ArrayList<Invasor> Ainvasores = new ArrayList();
    
    /**
     * Vetor de Objetos da Classe Tiro.
     */
    private ArrayList<Tiro> Atiro = new ArrayList();
    
    /**
     * Vetor de Objetos da Classe Barreira.
     */
    private ArrayList<Barreira> Abarreiras = new ArrayList();
    
    /**
     * Obejeto da Classe Canhao.
     *  Representa o Player
     */
    public Canhao Ocanhao;
    
    /**
     * Obejto da Classe Random.
     *  É utilizado para randomizar os tiros dos invasores
     */
    private Random random = new Random();
    
    /**
     * Graphics Context do JavaFx
     */
    GraphicsContext gc;

    
 /*---------------- Load --------------------------------------------------------------------------------------------------------*/    

    /**
     * Cria o objeto Canhao.
     * 
     */
    public void LoadCanhao() {
       Ocanhao = new Canhao(this, Display.getColunas()/2, Display.getLinhas()-100, 0);
       Ocanhao.setImage("Assets/Canhao.png");
    }

    /**
     * Cria o vetor de invasores e o preenche com os objetos.
     */
    public void LoadInvasores() {
        
        for (int i = 0; i < Ninvasores; i++){
            Invasor e = new Invasor(this, 0, 0, i/11); 
            Ainvasores.add(e);     
        }
        
        double width = Ainvasores.get(0).getWidth();
        double height = Ainvasores.get(0).getHeight();
        double x = 10;
        double y = 30;
        
        //Posicionamento
        for (int i = 0; i < Ninvasores; i++){ 
            if (i % 11 == 0 && i != 0){
                y = y + height + height/2;
                x = 10;
            }
            x = x + width + width/2;
            Ainvasores.get(i).setPosition(x, y);
            Ainvasores.get(i).setVelocity(width/2, 0);
        }
    }

    /**
     * Cria o vetor de barreiras e o preenche com os objetos.
     *  Para a implementação do jogo, considerei 4 conjuntos de 3 barreiras
     */
    public void LoadBarreiras() {
        for (int i = 1; i <= 4; i++) {
            
            Barreira b1 = new Barreira(this, i*Display.getColunas()*2/10-128, Display.getLinhas()*3/4-32);
            Barreira b2 = new Barreira(this, i*Display.getColunas()*2/10-64, Display.getLinhas()*3/4-32);
            Barreira b3 = new Barreira(this, i*Display.getColunas()*2/10, Display.getLinhas()*3/4-32);
            
            Abarreiras.add(b1);
            Abarreiras.add(b2);
            Abarreiras.add(b3);
            
        }
    }
    
    /**
     * Cria o Display do jogo.
     * 
     */
    public void LoadDisplay() {
       Display = new Tela(gc, this);
       Display.IniciaTela();
    }
    
    
 /*---------------- Desenha -----------------------------------------------------------------------------------------------------*/    

    /**
     *
     */
    
    
    public void DesenhaEntities(){
        DesenhaTiros();
        DesenhaCanhao();
        DesenhaInvasores();
        DesenhaBarreiras();
    }
    
    /**
     * Adiciona os invasores na matriz de char do Display.
     */
    public void DesenhaInvasores() {
        for (int i = 0; i < Ninvasores; i++) {
            if( Ainvasores.get(i).getMorto() == false)
                Ainvasores.get(i).render(gc);
        }
    }
    
    /**
     * Adiciona o canhão na matriz de char do Display.
     */
    public void DesenhaCanhao() {
        Ocanhao.render(gc);
    }
    
    /**
     * Adiciona os tiros na matriz de char do Display.
     */
    public void DesenhaTiros(){
        for (int i = 0; i < Atiro.size(); i++) {
            Atiro.get(i).render(gc);        
        }
    }
    
    /**
     * Adiciona as barreiras na matriz de char do Display.
     */
    public void DesenhaBarreiras(){
        for (int i = 0; i < Abarreiras.size(); i++) {
            if(!Abarreiras.get(i).getDestruido()){
                Abarreiras.get(i).render(gc);
            }
        }
    }
  
 /*-------------- Tiro e COlisao ----------------------------------------------------------------------------------------------------*/   
    
    /**
     * Controla o disparo do canhão.
     */
    public void AtiraCanhao(){
        
        if(!GetTiroCanhao()){
            Tiro tiro = Ocanhao.Atrirar();
        
            if(tiro != null){
                tiro.setVelocity(0, -tiro.getHeight()*20);
                Atiro.add(tiro);
            }
        }
                
    }
    
    /**
     * Controla o disparo dos invasores.
     * 
     * O número de tiros randomicos depende da fase em que o jogador está.
     * Como existe um espaçamento entre os invasore, é preciso achar um 
     *  que esteja em uma coluna suficientemente próxima da do canhão para o 
     *  tiro calculado. Além disso, é preciso se certificar de que que existe
     *  algum invasor vivo nas 5 fileiras e que esteja nessa coluna aproximada.
     */
    public void AtiraInvasor(){
        
        if(GetTiroInvasor()){
            /*Tiros Calculados*/
            double colunaCanhao = Ocanhao.getPosX(); //guarda a coluna do canhao 
            double colunaInvasor;                    //guarda a coluna do invasor
            int invasor = -1;                           //indice do invasor no vetor de invasores

            /*Procura um invasor que esteja em uma coluna proxima a do canhao*/
            for (int i = 0; i < Ninvasores; i++) {
                colunaInvasor = Ainvasores.get(i).getPosX();
                if (colunaInvasor >= colunaCanhao && colunaInvasor <= colunaCanhao+4){
                    invasor = i;
                    break;
                }
            }

            /*Percorre as 5 linhas de invasores até achar um apto a atirar*/
            for (int i = 0; i < 5; i++) {

                if(invasor == -1) break; //nao achou um invaor

                Tiro tiro = Ainvasores.get(invasor+11*i).Atrirar();

                if(tiro != null){
                    tiro.setVelocity(0, +tiro.getHeight()*20);
                    Atiro.add(tiro);
                    break;
                }
            }
        }
              
        
        
        //Tiros aleatórios
        for (int i = 0; i < Nfase; i++) {
            if(GetTiroInvasor()){
                int j = random.nextInt(55);
                Tiro tiro = Ainvasores.get(j).Atrirar();

                if(tiro != null){
                    tiro.setVelocity(0, +tiro.getHeight()*20);
                    Atiro.add(tiro);
                }
            }
        }
             
    }
    
    /**
     * Esse método controla as colisões entre os tiros tanto dos invasores 
     *  quanto do canhão.
     * <p>
     * Como o tiro possui uma velocidade maior que 1, foi preciso colocar 
     *  condicionais com operadores maior-igual e menor-igual, utilizando a 
     *  posição atual do tiro e sua posição antiga.
     * <p>
     * Funcionamento: o primeiro "for" percorre o vetor de tiros, checando as 
     *  condicões para cada um dos objetos.
     * Cada tiro é comparado com todos os objetos do vetor de invasores, todos 
     *  os objetos do vetor de barreiras, todos os objetos do vetor de tiros 
     *  e o canhão.
     * 
     * @return true caso o canhão tenha sido atingido por um tiro inimigo
     */
    public boolean Colisao(){
        
        boolean remove; /*condicao para remoção do tiro do vetor de tiros*/
        boolean CanhaoAtingido = false;
        boolean Atirador;       /*true->player, false->invasor*/
        boolean remove2tiros;   /*condicao para quando um tiro acerta outro*/
        int segundotiro = 0;    /*indice do segundo tiro*/
        

        
        /*percorre todo o vetor de tiros*/
        for (int i = 0; i < Atiro.size(); i++){
            
            Atirador = Atiro.get(i).getAtirador();
            /*
            //TPosXAtual = Atiro.get(i).PosX; 
            TPosYAtual = Atiro.get(i).getPosY(); 
            
            
            //define posicao antiga do tiro na coordenada X
            if(Atirador){ //tiro do player
                TPosYAntiga = TPosYAtual + Atiro.get(i).getVelX();                 
            }
            else{ //tiro de um invasor
                TPosYAntiga = TPosYAtual + Atiro.get(i).getVelX(); 
            }
            */
            remove = false;         /*reset*/
            remove2tiros = false;   /*reset*/
            
            
            /*Colisao com invasores*/
            if(Atirador){ //se o tiro foi de um invasor, nao tem colisao com outros invasores
                
                /*percorre vetor de invasores*/
                for (int j = 0; j < Ninvasores; j++){ 
                    if(!Ainvasores.get(j).getMorto()){ //se invasor já estiver morto, pula 
                        if(Atiro.get(i).intersects(Ainvasores.get(j))) {
                            
                            Ainvasores.get(j).setMorto();  
                            AumentaScore(Ainvasores.get(j).getTipo());
                            Ninvasoresmortos++;
                            remove = true;
                            break;
                            
                        }
                    }  
                }
            }/*end colição com invasores*/
            
            /*Colisao com barreiras*/
            for (int j = 0; j < Abarreiras.size(); j++) {
                
                if(!Abarreiras.get(j).getDestruido()){                  //se a barreira já estiver destruída, pula
                    
                    if(Atiro.get(i).intersects(Abarreiras.get(j))){
                        Abarreiras.get(j).setDestruido();
                        remove = true;
                        break;
                    }
                }
            } /*end colisão com barreira*/
            
            
            //Colisao com outros tiros
            for (int j = i+1; j < Atiro.size(); j++) {                //i+1 -> para não comparar tiros iguais
                
                if(Atiro.get(i).intersects(Atiro.get(j))){
                    remove2tiros = true;
                    segundotiro = j;
                    remove = true;
                    break;
                }
            }//end colisão com outros tiros
            
            
            /*Colisao com o canhao*/
            if(Atiro.get(i).intersects(Ocanhao)){
                remove = true;
                CanhaoAtingido = true;
            }/*end colisão com canhão*/
            
            
            if(remove2tiros == true) Atiro.remove(segundotiro);
            if(remove == true) Atiro.remove(i);
            
        }/*end vetor de tiros*/
        
        return CanhaoAtingido;   
    }
    
    /**
     * Esse método remove todos os tiros do vetor de tiros.
     *  Ele é chamado quando o canhão é atingido e o jogo recomeça.
     */
    public void LimpaTiro(){
        Atiro.clear();
    }
    
    /**
     * Verifica o numero de tiros do canhao no vetor
     * @return
     */
    public boolean GetTiroCanhao()
    {
        for (Tiro t : Atiro) {
            if(t.getAtirador())
                return true;
        }
        return false;
    }
    
    /**
     *Verifica o numero de tiros do invasor no vetor
     * @return
     */
    public boolean GetTiroInvasor(){
        int count = 0;
        for (Tiro t : Atiro) {
            if(!t.getAtirador())
                count++;
        }
        if(count <= 1){
            return true;
        }
        else{
            return false;
        }
    }
    
 /*---------------- Move ----------------------------------------------------------------------------------------------------------*/

    /**
     *
     * @param Time
     */

    
    public void MoveEntities(double Time){
        Ocanhao.update(Time);
        MoveInvasores(Time);
        MoveTiros(Time);
    }
    
    
    /**
     * Altera as posições dos invasores.Nesse método é verficado se o primeiro e o último invasor da primeira
  fila vão ultrapassar os limites do display.
     * 
     * Caso isso aconteca, a 
  flag "trocaDirecao" se torna true e é mandada para a rotina de movimento
  dos invasores, indicando que o movimento deve ser na vertical.
 Caso contrário, a flag continua como false, indicando que o movimento 
  deve ser na horizontal.
 <p>
     * Como, mesmo mortos, as posições dos invasores ainda mudam, basta
     *  verificar as posições desses dois invasores para saber se eles sairão 
     *  dos limites do display
     * @param Time
     */
    public void MoveInvasores(double Time){
        
        double width = Ainvasores.get(0).getWidth();
        
        boolean trocaDirecao = false;
        
        double PosXFirstInvasor = Ainvasores.get(0).getPosX();
        double VelXFirstInvasor = Ainvasores.get(0).getVelX();
        boolean DirecaoFirstInvasor = Ainvasores.get(0).getDirecao();
        
        double PosXLastInvasor = Ainvasores.get(10).getPosX();
        double VelXLastInvasor = Ainvasores.get(10).getVelX();
        boolean DirecaoLastInvasor = Ainvasores.get(10).getDirecao();
        
        //Verifica se os invasores ultrapassarão o limite da esquerda
        if(PosXFirstInvasor-VelXFirstInvasor <= 2*width && DirecaoFirstInvasor)
            trocaDirecao = true;
        
        //Verifica se os invasores ultrapassarão o limite da direita
        if (PosXLastInvasor+VelXLastInvasor >= Display.getColunas()-width-10 
                && !DirecaoLastInvasor )
            trocaDirecao = true;
        
        //Percorre o vetor de invasores, chamando o método de mover
        for (int i = 0; i < Ninvasores; i++){
            Ainvasores.get(i).move(trocaDirecao);
        }
        
        //Update da posicao
        for (int i = 0; i < Ninvasores; i++) {
            Ainvasores.get(i).update(Time);
            
        }    
    }
    
    /**
     * Altera as posições do canhão.
     * 
     * Esse método verifica para que lado o canhão deve se mover. Idealmente,
     *  aqui é o local onde seria implementado a leitura de teclas do teclado.
     *  Porém, essa parte será desenvolvida nas próximas etapas do trabalho. 
     * <p>
     * Por enquanto, o canhão se move de um lado para outro, percorrendo o
     *  display inteiro. 
     */
    public void MoveCanhao(){
        
        /*Move para a Esquerda*/
        if((Ocanhao.getPosY()+Ocanhao.getVelY() >= Display.getColunas()-1 
                && !(Ocanhao.getDirecao())) || Ocanhao.getDirecao() )
            Ocanhao.move(true);
        
        /*Move para a Direita*/
        if((Ocanhao.getPosY()-Ocanhao.getVelY() <= 0 && (Ocanhao.getDirecao())) 
                || !Ocanhao.getDirecao())
            Ocanhao.move(false);
    }
    
    /**
     * Altera as posições dos tiros.Esse método simples percorre o vetor de tiros, chamando o método de
  mover e verifica se algum tiro está ultrapassando os limites do
  display.
     * 
     * Caso o tiro esteja saindo do display, ele é retirado do vetor de tiros.
     * @param Time
     */
    public void MoveTiros(double Time){
        /*Percorre o vetor de tiros*/
        for (int i = 0; i < Atiro.size(); i++) {
            
            //Atiro.get(i).move(true);
            
            
            /*Tiro saindo por cima*/
            if(Atiro.get(i).getPosY() <= 0){ 
                Atiro.remove(i);
                break;
            } 
            
            /*Tiro saindo por baixo*/
            if(Atiro.get(i).getPosY() >= Display.getLinhas()-32){ 
                Atiro.remove(i);
                break;
            } 
            
            Atiro.get(i).update(Time);
        }
    }
    
    /**
     * Aumenta a velocidade de movimento dos invasores.
     */
    public void AceleraInvasores(){
        for (int i = 0; i < Ninvasores; i++){
            Ainvasores.get(i).AumentaVel();
        }
    }
   
    
 /*----------------- Game Settings ---------------------------------------------------------------------------------------------------*/    
    
    /**
     * Mostra informacoes sobre o jogo no console.
     * 
     * @param gc
     */
    public void MostraInfo(GraphicsContext gc){
        
        String score = "Score: " + (Score);
        gc.fillText( score, 20, 20 );
        gc.strokeText( score, 20, 20 );
        
        String fase = "Fase: " + (Nfase);
        gc.fillText( fase, 200, 20 );
        gc.strokeText( fase, 200, 20 );
        
        String vidas = "Vidas: " + (Ocanhao.getVidas());
        gc.fillText( vidas, 400, 20 );
        gc.strokeText( vidas, 400, 20 );
        

        
    }    
    
    /**
     * Incrementa o Score de acordo com o tipo de inimigo atingido.
     * 
     * Como existem três tipos de inimigos, foi escolhido um número arbitrário
     *  de pontos para cada um, levando em conta sua posição no jogo. Assim,
     *  inimigos que ficam mais longe do jogador (tipo 0), valem mais pontos
     *  ao serem atingidos.
     * 
     * @param tipo tipo de invasor que foi atingido
     */
    public void AumentaScore(int tipo){
        
        switch (tipo) {
            case 0:
                Score += 250;
                break;
            case 1:
                Score += 150;
                break;
            default:
                Score += 100;
                break;
        }
    }  
    
    /**
     * Criacao dos Objetos e controle de Fases.
     * <p>
     * Esse método cria todo os objetos necessários para o funcionamento do
     *  game e inicia o loop principal.
     * O método gameloop pode retornar três diferentes valores e cada um
     *  tem seu próprio significado. Esses significados podem ser encontrados
     *  ma decrição do próprio método.
     * 
     */
    public void IniciaGame() {
        
        LoadDisplay();
        LoadInvasores();
        LoadCanhao();
        LoadBarreiras();
        
 
    }

    /**
     * Fim do jogo.
     */
    public void GameOver() {
        
        Display.Clear();
        Display.MsgGameOver();

    }
    
    /**
     * Esse método verifica se os invasores chegaram na linha das barreiras.
     *  Ele retorna true caso isso tenha acontecido e acionará o gameover
     * 
     * @return true caso os invasores tenham chagado na linha das barreiras
     */
    public boolean VerficaFim(){
        
        for (int i = 0; i < Ninvasores; i++) {
            if(!Ainvasores.get(i).getMorto()){
                if(Ainvasores.get(i).getPosY()+Ainvasores.get(i).getHeight() == Abarreiras.get(0).getPosY())
                    return true;
            }
        } 
        
        if(Ninvasoresmortos == Ninvasores)
            return true;
        
        if(Ocanhao.getVidas() <= 0)
            return true;
        
        return false;
    }
    
    /**
     * Funcao para congelar o game por um tempo.
     * 
     * Esse método precisou ser implementado para deixar os prints do jogo no
     *  console mais estáveis.
     * Além disso, quando o jogador é atingido, esse método é chamado para 
     *  congelar o tempo por alguns segundos.
     * 
     * @param ms tempo em milisegundos que o jogo fica congelado
     */
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }   

    
 /*-----------------Outside Functions ---------------------------------------------------------------------------------------------------*/
    
    /**
     * Retorna o número de vidas restantes do canhão.
     * 
     * Como o objeto do canhão é private e pertence a classe jogo, outras
     *  classes não podem acessar o método getVidas() do canhão. Assim,
     *  foi necessário criar esse método apenas para retornar as vidas restantes.
     * <p>
     * Esse método é utilizado pela classe Tela, no método MsgAtingido()
     *
     * @return vidas do canhão
     */
    public int getVidas(){
        return Ocanhao.getVidas();
    }
    
    /**
     * Retorna a pontuação do jogador.
     * 
     * Esse método é utilizado na hora de escrever a 
     *  mensagem de game over.
     * 
     * @return pontuação do jogador 
     */
    public int getScore(){
        return Score;
    }
    
    /**
     * Retorna a fase atual do jogador.
     * 
     * Esse método pe utilizado na hora de escrever a 
     *  mensagem de próxima fase
     * 
     * @return fase atual do jogador
     */
    public int getFase(){
        return Nfase;
    }
}