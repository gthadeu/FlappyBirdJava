public class Cano {

    //velocidade do cano na tela
    public double x,y;
    public double vxcano;
    public static int HOLESIZE = 120;
    public Hitbox boxup;
    public Hitbox boxdown;


    public Cano(double x, double y, double vx){
        this.x = x;
        this.y = y;
        this.vxcano = vx;
        boxup = new Hitbox(x,y-270 ,x+52,y);
        boxdown = new Hitbox(x,y+HOLESIZE,x+52,y+HOLESIZE+242);
    }

    public void atualizar(double dt){

        x += vxcano*dt;
        boxup.mover(vxcano*dt,0);
        boxdown.mover(vxcano*dt,0);

    }

    public void desenha(Tela tela){
        tela.imagem("flappy.png", 604,0,52,270,0, x,y-270); //cano de cima
        tela.imagem("flappy.png", 660,0,52,242,0, x,y+HOLESIZE); //cano debaixo

    }
}
