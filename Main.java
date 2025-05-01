import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Main {
    public static ArrayList<Projectile> proj = new ArrayList<>();
    public static int max_proj = 1000;
    public static int ind = 0;
    // public static Player player = new Player("/Sprites/O-4.png", 600, 100);
    public static final ConcurrentLinkedQueue<Projectile> queuedProjectiles = new ConcurrentLinkedQueue<>();
    public static int yourhorse = 0;
    public static boolean CARROT = true;

    public static void main(String[] args) {
        createWall2();
        padr velocity = new padr();
        velocity.set(0, 0);
        Projectile p = new Projectile(13*50+26, 9*50+24, 1, velocity);
        Main.queuedProjectiles.add(p);
        new Gameloop().start();
    }  

    public static void delay(int n) {
        try {Thread.sleep(n);} 
        catch (Exception e) {}
    }

    public static void addWall(int ind, int x1, int x2, int y1, int y2){
        walls.bounds[ind][0].set(x1, x2);
        walls.bounds[ind][1].set(y1, y2);
        walls.active[ind]=true;
    }

    public static void createWall(){
        for(int i = 0 ; i < walls.numWall; i++){
            for(int j = 0 ; j < 2; j++){
                walls.bounds[i][j] = new pair();
            }
        }


        //Wall left
        addWall(walls.numWall-1, -500, 23, -400, 3500);
        //Wall up
        addWall(walls.numWall-2, -400, 3500, -500, 23);
        addWall(walls.numWall-7, 800, 3500, -500, 28);
        //Wall right
        addWall(walls.numWall-3, 1512, 3500, -400, 3500);
        //wall down
        addWall(walls.numWall-4, -400, 3500, 765, 3500);
        addWall(walls.numWall-5, 480, 3500, 760, 3500);
        addWall(walls.numWall-6, 9*50, 14*50, 750, 3500);

        // // big rectangle
        addWall(4, 0, 13*50+25, 500, 595);
        addWall(3, 10*50+30, 13*50+15, 500, 660);
        addWall(12, 0*50, 5*50+10, 500, 600);
        addWall(13, 1*50, 5*50, 497, 610);

        // // jail
        addWall(6, 23*50, 23*50+20, 8*50+25, 900);
        addWall(16, 23*50-10, 23*50+20, 10*50+25, 680+50);

        addWall(5, 24*50+30, 1600, 300+20, 300+35);

        // // hanging fan
        addWall(7, 7*50-20, 350+5, 0, 360);
        addWall(8, 4*50-20, 500+20, 350-15, 365);

        // // floating square
        addWall(9, 14*50, 20*50-10, 150-2, 220);
        addWall(11, 16*50, 20*50-20, 150-2, 230);

        
        addWall(10, 16*50+10, 19*50+40, 455, 480);

//=====================================================================================


        // //Wall left
        // addWall(walls.numWall-1, -500, 557, -400, 3500);
        // //Wall up
        // addWall(walls.numWall-2, -400, 3500, -500, 7);
        // //Wall right
        // addWall(walls.numWall-3, 977, 3500, -400, 3500);
        // //wall down
        // addWall(walls.numWall-4, -400, 3500, 785, 3500);

        // // addWall(1, 750, 780, 140, 170);

        // addWall(17, 660, 670, 140, 150);
        // // addWall(15, 750, 760, 144, 154);
        // addWall(16, 830, 840, 143, 153);

        // addWall(11, 880, 890, 271, 281);
        // addWall(12, 800, 810, 270, 280);
        // addWall(13, 700, 710, 273, 283);
        // addWall(14, 610, 620, 270, 280);

        // // addWall(2, 800, 830, 270, 300);
        // // addWall(3, 690, 720, 270, 300);

        // addWall(4, 660, 670, 399, 409);
        // addWall(5, 750, 760, 400, 410);
        // addWall(6, 830, 840, 402, 412);

        // addWall(7, 880, 890, 530, 540);
        // addWall(8, 800, 810, 535, 545);
        // addWall(9, 690, 700, 533, 543);
        // addWall(10, 610, 620, 530, 540);

        // addWall(21, 900, 910, 650, 800);
        // addWall(18, 800, 810, 650, 800);
        // addWall(19, 710, 720, 650, 800);
        // addWall(20, 610, 620, 650, 800);



    }

    public static void createWall2(){
        for(int i = 0 ; i < walls.numWall; i++){
            for(int j = 0 ; j < 2; j++){
                walls.bounds[i][j] = new pair();
            }
        }

        addWall(walls.numWall-1, -500, (int)(7.5*50), -400, 3500);
        //Wall up
        addWall(walls.numWall-2, -400, 3500, -500, 28);
        //Wall right
        addWall(walls.numWall-3, 1500-350-10, 3500, -400, 3500);
        //wall down
        addWall(walls.numWall-4, -400, 3500, 760, 3500);


        addWall(1,550, 19*50+23, 150+11, 150+43  );
        addWall(2, 550, 580, 150+12, 12*50);
        addWall(3, 550, 1200, 353, 390);
        addWall(4, 15*50, 15*50+30, 103, 193);
        addWall(5, 19*50-2, 19*50+25,160, 244);

        addWall(7, 19*50+2, 19*50+35,7*50-40, 13*50+32);


        addWall(8, 11*50, 17*50+27, 12*50-25, 12*50+2);


        addWall(9, 19*50+20, 21*50+40, 10*50+14, 11*50-3);


        addWall(10, 7*50, 7*50+36, 0, 5*50+25);
        addWall(11, 7*50, 7*50+36, 14*50, 16*50+25);
        addWall(12, 11*50, 11*50+36, 4*50-20, 7*50+30);

    }
    

}





