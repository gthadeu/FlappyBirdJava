public class Passaro {
    //coordenadas
    public double x,y;
    //velocidade Y
    public double vy = 0;
    //Gravidade
    public static double G = 500;
    //
    public static double FLAP = -200;

    public Hitbox box;

    public Passaro(double x, double y){
        this.x = x;
        this.y = y;
        this.box = new Hitbox(x,y,x+34,y+24);
    }

    public void atualizar(double dt){
        vy += G*dt;
        y += vy*dt;

        box.mover(0, vy*dt);

    }

    public void flap(){
        vy = FLAP;
    }

    public void desenhar(Tela tela){
        tela.imagem("flappy.png", 528,128,34,24,Math.atan(vy/200),x,y);
    }
}
