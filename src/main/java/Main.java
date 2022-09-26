import java.util.Set;
import java.util.ArrayList;
import java.util.Random;

public class Main implements Jogo {

    public Passaro passaro;
    public Random random = new Random();
    public int record = 0;
    public ScoreNumber scoreNumber;
    public int game_state = 0;

    public ArrayList<Cano> canos = new ArrayList<Cano>();
    public double cenario_offset = 0;
    public double ground_offset = 0;
    public Timer timer_cano;
    public Hitbox groundbox;

    public Timer auxtimer;


    public Main() {
        passaro = new Passaro(50, getAltura() / 4);
        timer_cano = new Timer(3, true, addCano());
        scoreNumber = new ScoreNumber(0);
        groundbox = new Hitbox(0, getAltura() - 112, getLargura(), getAltura());
//        addCano().executa();
    }

    private Acao addCano() {
        return new Acao() {
            public void executa() {
                canos.add(new Cano(getLargura() + 10, random.nextInt(getAltura() - 112 - Cano.HOLESIZE)));
            }
        };
    }

    private Acao proxCena() {
        return new Acao() {
            public void executa() {
                game_state += 1;
                game_state = game_state % 4;
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

    public void gameOver() {
        canos = new ArrayList<Cano>();
        passaro = new Passaro(50, getAltura() / 4);
        proxCena().executa();
    }

    public void tecla(String tecla) {
        switch (game_state) {
            case 0:
                if (tecla.equals(" ")) {
                    auxtimer = new Timer(1.6, false, proxCena());
                    proxCena().executa();
                }
                break;
            case 1:
                break;
            case 2:
                if (tecla.equals(" ")) {
                    passaro.flap();
                }
                break;
            case 3:
                if (tecla.equals(" ")) {
                    scoreNumber.setScore(0);
                    proxCena().executa();
                }
        }
    }

    public void tique(java.util.Set<String> teclas, double dt) {
        cenario_offset += dt * 25;
        cenario_offset = cenario_offset % 288;
        ground_offset += dt * 100;
        ground_offset = ground_offset % 308;

        switch (game_state) {
            case 0: //TELA INICIAL
                break;
            case 1:
                auxtimer.tique(dt);
                passaro.atualizarSprite(dt);
                break;
            case 2:
                timer_cano.tique(dt);
                passaro.atualizar(dt);
                passaro.atualizarSprite(dt);
                if (groundbox.intersecao(passaro.box) != 0) {
                    gameOver();
                    return;
                }
                if (passaro.y < -5) {
                    gameOver();
                    return;
                }
                for (Cano cano : canos) {
                    cano.tique(dt);
                    if (cano.canocima.intersecao(passaro.box) != 0 || cano.canobaixo.intersecao(passaro.box) != 0) {
                        if (scoreNumber.getScore() > ScoreNumber.record) {
                            scoreNumber.record = scoreNumber.getScore();
                        }
                        gameOver();
                        return;
                    }
                    if (!cano.counted && cano.x < passaro.x) {
                        cano.counted = true;
                        scoreNumber.modifyScore(1);
                    }
                }
                if (canos.size() > 0 && canos.get(0).x < -70) {
                    canos.remove(0);
                }
                break;
            case 3: //Tela de Game Over
                break;

        }


    }


    public void desenhar(Tela tela) {
        //Background
        tela.imagem("flappy.png", 0, 0, 288, 512, 0, (int) -cenario_offset, 0);
        tela.imagem("flappy.png", 0, 0, 288, 512, 0, (int) (288 - cenario_offset), 0);
        tela.imagem("flappy.png", 0, 0, 288, 512, 0, (int) ((288 * 2) - cenario_offset), 0);

        //Canos
        for (Cano cano : canos) {
            cano.desenharCano(tela);
        }

        //Ground
        tela.imagem("flappy.png", 292, 0, 308, 112, 0, -ground_offset, getAltura() - 112);
        tela.imagem("flappy.png", 292, 0, 308, 112, 0, 308 - ground_offset, getAltura() - 112);
        tela.imagem("flappy.png", 292, 0, 308, 112, 0, (308 * 2) - ground_offset, getAltura() - 112);

        switch (game_state){
            case 0:
                tela.imagem("flappy.png",292,346,192,44,0,getLargura()/2 -192/2,100);
                tela.imagem("flappy.png",352,306,70,36,0,getLargura()/2-70/2,175);
                tela.texto("Pressione Espaço", 60,getAltura()/2-16,32, Cor.BRANCO);
                break;
            case 1:
                passaro.desenharPassaro(tela);
                tela.imagem("flappy.png",292,442,174,44,0,getLargura()/2 -174/2,getAltura()/3);
                scoreNumber.drawScore(tela,5,5);
                break;
            case 2:
                scoreNumber.drawScore(tela,5,5);
                passaro.desenharPassaro(tela);
                break;
            case 3:
                tela.imagem("flappy.png", 292, 398, 188, 38, 0, getLargura()/2 - 188/2, 100);
                tela.imagem("flappy.png", 292, 116, 226, 116, 0, getLargura()/2 - 226/2, getAltura()/2 - 116/2);
                scoreNumber.drawScore(tela, getLargura()/2 + 50, getAltura()/2-25);
                scoreNumber.drawRecord(tela, getLargura()/2 + 55, getAltura()/2 + 16);
                break;
        }

    }

    public static void main(String[] args) {
        roda();
    }

    private static void roda() {
        new Motor(new Main());
    }
}
