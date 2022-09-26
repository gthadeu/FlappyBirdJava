public class Cano {

    public double x, y;

    public static int xspeed = -100;
    public static int HOLESIZE = 94; //50 pixels
    public boolean counted = false;

    public Hitbox canocima;
    public Hitbox canobaixo;


    public Cano(double x, double y) {
        this.x = x;
        this.y = y;
        this.canocima = new Hitbox(x, y - 270 - 220, x + 52, y);
        this.canobaixo = new Hitbox(x, y + HOLESIZE, x + 52, y + HOLESIZE + 442);
    }

    public void tique(double dt) {
        x += xspeed * dt;
        canocima.mover(xspeed * dt, 0);
        canobaixo.mover(xspeed * dt, 0);
    }

    public void desenharCano(Tela tela) {
        tela.imagem("flappy.png", 660, 0, 52, 242, 0, x, y + HOLESIZE); //cano virado pra cima
        tela.imagem("flappy.png", 660, 42, 52, 200, 0, x, y + HOLESIZE + 242); //resto do cano virado pra cima

        tela.imagem("flappy.png", 604, 0, 52, 270, 0, x, y - 270); //cano virado pra baixo
        tela.imagem("flappy.png", 604, 0, 52, 220, 0, x, y - 270 - 220); //resto do cano virado pra baixo
    }
}
