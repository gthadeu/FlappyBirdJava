public class Passaro {
    //coordenadas
    public double x, y;
    //velocidade Y
    public double vy = 0;
    //Gravidade
    public static double G = 1200;
    //Jump
    public static double FLAP = -250;

    public Hitbox box;
    public Timer sprite_timer;
    public int sprite_state = 0;
    public int[] sprite_states = {0, 1, 2, 1};
    public int[] sprites_x = {528, 528, 446};
    public int[] sprites_y = {128, 180, 248};

    public Acao mudarSprite() {
        return new Acao() {
            public void executa() {
                sprite_state += 1;
                sprite_state = sprite_state % sprite_states.length;
            }
        };
    }

    public Passaro(double x, double y) {
        this.x = x;
        this.y = y;
        this.vy = 0;
        this.box = new Hitbox(x, y, x + 34, y + 24);
        sprite_timer = new Timer(0.1, true, mudarSprite());
    }

    public void atualizar(double dt) {
        vy += G * dt;
        y += vy * dt;

        box.mover(0, vy * dt);

    }

    public void atualizarSprite(double dt){
        sprite_timer.tique(dt);
    }

    public void flap() {
        vy = FLAP;
    }

    public void desenharPassaro(Tela tela) {
        tela.imagem("flappy.png", sprites_x[sprite_states[sprite_state]], sprites_y[sprite_states[sprite_state]], 34, 24, Math.atan(vy/300), x, y);
    }
}
