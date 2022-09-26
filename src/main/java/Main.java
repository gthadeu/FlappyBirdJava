import java.util.Set;
import java.util.ArrayList;
import java.util.Random;

public class Main implements Jogo {

    public double ground_offset = 0;
    //velocidade x ground
    public double gvx = 50;

    public Passaro passaro;
    public ArrayList<Cano> canos = new ArrayList<Cano>();
    public Random gerador = new Random();
    public Timer timer_cano;


    public Main(){
        passaro = new Passaro(35,(getLargura()-112)/2 + 24/2);
        timer_cano = new Timer(2.5, true, addCano());
        addCano().executa();
    }

    private Acao addCano(){
        return new Acao(){
            public void executa(){
                canos.add(new Cano(getLargura()+10, gerador.nextInt(getAltura()-112- Cano.HOLESIZE), -gvx));
            }
        };
    }

    public String getTitulo() {
        return "Flappy Bird";
    }

    public int getLargura() {
        return 384;
    }

    public int getAltura() {
        return 512;
    }
    public void tecla(String tecla) {
        if(tecla.equals(" ")){
            passaro.flap();
        }
    }

    public void tique(java.util.Set<String> teclas, double dt) {
        ground_offset += dt * gvx;
        ground_offset = ground_offset % 308;

        timer_cano.tique(dt);

        passaro.atualizar(dt);
        if(passaro.y+24>=getAltura()-112){
            System.out.println("LOSE");
        }else if(passaro.y <=0){
            System.out.println("LOSE");
        }

        for(Cano cano: canos){
            cano.atualizar(dt);
            if(passaro.box.intersecao(cano.boxup)!=0 || passaro.box.intersecao(cano.boxdown)!=0){
                //gameover
                System.out.println("LOSE");
            }
        }
        if(canos.size()>0 && canos.get(0).x < -60){
            canos.remove(0);
        }

    }


    public void desenhar(Tela tela) {
        //Background
        tela.imagem("flappy.png", 0, 0, 288, 512, 0, 0, 0);
        tela.imagem("flappy.png", 0, 0, 288, 512, 0, 288, 0);
//        tela.imagem("flappy.png", 0,0,288,512,0,288*2,0);

        //Canos
        for(Cano cano: canos){
            cano.desenha(tela);
        }

        //Ground
        tela.imagem("flappy.png", 292, 0, 308, 112, 0, -ground_offset, getAltura() - 112);
        tela.imagem("flappy.png", 292, 0, 308, 112, 0, 308 - ground_offset, getAltura() - 112);
        tela.imagem("flappy.png", 292, 0, 308, 112, 0, 308 * 2 - ground_offset, getAltura() - 112);



        //Passaro
        passaro.desenhar(tela);




    }

    public static void main(String[] args) {
        roda();
    }

    private static void roda() {
        new Motor(new Main());
    }
}
