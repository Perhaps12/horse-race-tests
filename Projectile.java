import java.awt.*;
import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Projectile {
    public boolean active = true;
    private BufferedImage image;
    private int ID;
    private final padr pos = new padr();
    private final pair hitbox;
    private final padr wallBox;
    private final padr vel;
    private padr acc;
    private long last;
    private long now;
    private long totalTime = 0;
    public pair box[];

    private String[] hrt = {"/Sprites/horse race test/jovial merryment.png",
                            "/Sprites/horse race test/resolute mind afternoon.png",
                            "/Sprites/horse race test/bullet n board.png",
                            "/Sprites/horse race test/comely material morning.png",
                            "/Sprites/horse race test/door knob.png",
                            "/Sprites/horse race test/superstitional realism.png",
                            "/Sprites/horse race test/yellow.png",};

    public Projectile(double centerX, double centerY, int projID, padr vel) {
        
        this.hitbox = new pair();
        this.wallBox = new padr();
        this.pos.first = centerX;
        this.pos.second = centerY;
        this.ID = projID;
        this.vel = vel;
        this.box = new pair[2];
        box[0] = new pair();
        box[1] = new pair();
        acc = new padr();
        last = System.nanoTime();

        switch (projID) {
            case 3 -> {
                try{image = ImageIO.read(getClass().getResource(hrt[Main.yourhorse]));}
                catch(IOException | IllegalArgumentException e){}
                // hitbox.set(60, 50);
                wallBox.set(16, 18);
                Main.yourhorse=(Main.yourhorse+1)%7;

                this.box[0].set((int)pos.first-(int)wallBox.first, (int)pos.first+(int)wallBox.first);
                this.box[1].set((int)pos.second-(int)wallBox.second, (int)pos.second+(int)wallBox.second);
            }
            case 4-> {
                try{image = ImageIO.read(getClass().getResource(hrt[(int)(Math.random()*7)]));}
                catch(IOException | IllegalArgumentException e){}
                // hitbox.set(32, 32);
                wallBox.set(15, 17);
                acc.set(0, -0.6);
            }
            case 1-> {
                try{image = ImageIO.read(getClass().getResource("/Sprites/carrot (1).png"));}
                catch(IOException | IllegalArgumentException e){}
                // hitbox.set(32, 32);
                wallBox.set(20, 11);
            }
            default -> {
                try{image = ImageIO.read(getClass().getResource("/Sprites/friendlinessPellet.png"));}
                catch(IOException | IllegalArgumentException e){}
                hitbox.set(32, 32);
                wallBox.set(15, 15);
                acc.set(0, -0.9);
            }
            
        }
    }

    //display ===========================================================================================================
    
    public void draw(Graphics g) {
        if (active && image != null) {
            if (ID==1) {
                g.drawImage(image, (int)pos.first-(int)wallBox.first-12, (int)pos.second-(int)wallBox.second-13, null);
                return;
            }
            g.drawImage(image, (int)pos.first-(int)wallBox.first, (int)pos.second-(int)wallBox.second, null);
        }
    }

    //movement ===========================================================================================================

    private int insideWall(pair[] box){
        //1 left wall  2 right wall  3 top wall  4 bottom wall

        if(box[1].first < pos.second-wallBox.second && pos.second-wallBox.second < box[1].second
            &&!(pos.first-wallBox.first > box[0].second || pos.first+wallBox.first < box[0].first))return 3;

        if(box[1].first < pos.second+wallBox.second && pos.second+wallBox.second < box[1].second
                && !(pos.first-wallBox.first > box[0].second || pos.first+wallBox.first < box[0].first))return 4;

        if(box[0].first < pos.first-wallBox.first && pos.first-wallBox.first < box[0].second
            &&!(pos.second-wallBox.second > box[1].second || pos.second+wallBox.second < box[1].first))return 1;

        if(box[0].first < pos.first+wallBox.first && pos.first+wallBox.first < box[0].second
            &&!(pos.second-wallBox.second > box[1].second || pos.second+wallBox.second < box[1].first))return 2;
        return 0;
    }

    private void collideWall(pair[] box){
        
        if(insideWall(box) == 0)return;
        
        double leftDist = Math.abs(pos.first-wallBox.first - box[0].second);
        double rightDist = Math.abs(pos.first+wallBox.first - box[0].first);
        double upDist = Math.abs(pos.second-wallBox.second - box[1].second);
        double downDist = Math.abs(pos.second+wallBox.second - box[1].first);
        double minDist = Math.min(Math.min(leftDist, rightDist), Math.min(upDist, downDist));

        if(minDist==leftDist){
            pos.first = box[0].second+wallBox.first;
        }
        if(minDist==rightDist){
            pos.first = box[0].first - wallBox.first;
        }
        if(minDist==upDist){
            pos.second = box[1].second+wallBox.second;
        }
        if(minDist==downDist){
            pos.second = box[1].first - wallBox.second;
        }

    }

    public boolean touchingU(){
        for(pair[] box : walls.bounds){
            if(  (pos.second+wallBox.second <= box[1].first && pos.second+wallBox.second >= box[1].first )&& !(pos.first-wallBox.first > box[0].second || pos.first+wallBox.first < box[0].first)   ){
                return true;
            }
        }

        for(Projectile box : Main.proj){
            if(this==box)continue;
            if(  (pos.second+wallBox.second <= box.box[1].first && pos.second+wallBox.second >= box.box[1].first )&& !(pos.first-wallBox.first > box.box[0].second || pos.first+wallBox.first < box.box[0].first)   ){
                return true;
            }
        }
        return false;
    }

    public boolean touchingR(){
        for(pair[] box : walls.bounds){
            if(box[0].second <= pos.first-wallBox.first && pos.first-wallBox.first<=box[0].second
            &&!(pos.second-wallBox.second > box[1].second || pos.second+wallBox.second < box[1].first)){
                return true;
            }
        }

        for(Projectile box : Main.proj){
            if(this == box)continue;
            if(box.box[0].second <= pos.first-wallBox.first && pos.first-wallBox.first<=box.box[0].second
            &&!(pos.second-wallBox.second > box.box[1].second || pos.second+wallBox.second < box.box[1].first)){
                return true;
            }
        }
        return false;
    }

    public boolean touchingL(){
        for(pair[] box : walls.bounds){
            if(box[0].first >= pos.first+wallBox.first && pos.first+wallBox.first>=box[0].first
            &&!(pos.second-wallBox.second > box[1].second || pos.second+wallBox.second < box[1].first)){
                return true;
            }
        }

        for(Projectile box : Main.proj){
            if(this == box)continue;
            if(box.box[0].first >= pos.first+wallBox.first && pos.first+wallBox.first>=box.box[0].first
            &&!(pos.second-wallBox.second > box.box[1].second || pos.second+wallBox.second < box.box[1].first)){
                return true;
            }
        }
        return false;
    }

    public boolean touchingD(){
        for(pair[] box : walls.bounds){
            if(  (pos.second-wallBox.second >= box[1].second && pos.second-wallBox.second <= box[1].second )&& !(pos.first-wallBox.first > box[0].second || pos.first+wallBox.first < box[0].first)   ){
                return true;
            }
        }
        for(Projectile box : Main.proj){
            if(this==box)continue;
            if(  (pos.second-wallBox.second >= box.box[1].second && pos.second-wallBox.second <= box.box[1].second )&& !(pos.first-wallBox.first > box.box[0].second || pos.first+wallBox.first < box.box[0].first)   ){
                return true;
            }
        }
        return false;
    }

    public int direction = (Math.random()>0.4)? 1 : -1;
    public int Direction = (Math.random()>0.4)? 1 : -1;
    public double speed = 4;

    public void update() {
        now = System.nanoTime();
        totalTime+=(now-last);
        

        switch(ID){

            

            //jovial merryment
            case 3 -> {
                if(!Main.CARROT){
                    return;
                }
                speed = Math.random()+4.5;
                double angle = Math.random() * 2*Math.PI;
                vel.first = Math.abs(vel.first);
                vel.second = Math.abs(vel.second);
                pos.first+=direction*vel.first;
                pos.second-=Direction*vel.second;
                
                for(int i = 0 ; i < walls.bounds.length; i++){
                    if(walls.active[i]){
                        collideWall(walls.bounds[i]);
                    }
                }
                for(Projectile p : Main.proj){
                    if(this!=p)collideWall(p.box);
                }

                if(touchingR()){
                    direction=1;
                    vel.first=speed*Math.cos(angle);
                    vel.second=speed*Math.sin(angle);
                    SoundPlayer.playSound("Sounds//hit_sound.wav", 0.5);
                }
                if(touchingL()){
                    direction=-1;
                    vel.first=speed*Math.cos(angle);
                    vel.second=speed*Math.sin(angle);
                    SoundPlayer.playSound("Sounds//hit_sound.wav", 0.5);
                }

                

                if(touchingD()){
                    Direction = -1;
                    vel.first=speed*Math.cos(angle);
                    vel.second=speed*Math.sin(angle);
                    SoundPlayer.playSound("Sounds//hit_sound.wav", 0.5);
                }

                if(touchingU()){
                    Direction = 1;
                    vel.first=speed*Math.cos(angle);
                    vel.second=speed*Math.sin(angle);
                    SoundPlayer.playSound("Sounds//hit_sound.wav", 0.5);
                }

            }

            //bouncing 
            case 4 -> {
                pos.first+=vel.first;
                pos.second-=vel.second;

                vel.second+=acc.second;

                for(int i = 0 ; i < walls.bounds.length; i++){
                    if(walls.active[i]){
                        collideWall(walls.bounds[i]);
                    }
                }

                if(totalTime/1000000 > 5000){
                    active = false;
                    return;
                }

                if( touchingU()){
                    vel.second*=-0.75;
                    if(vel.second<4)vel.second=0;
                    vel.first*=0.97;
                }
                else if(touchingL() || touchingR()){
                    vel.first*=-0.9;
                }
                
                else if(touchingD()){
                    vel.second = -1;
                }
                


            }
            case 1->{
                // for(Projectile p : Main.proj){
                //     if(this!=p)collideWall(p.box);
                // }
                if((touchingD() || touchingU() || touchingR() || touchingL())&&Main.CARROT){
                    Main.CARROT = false;
                    
                    new Thread(() -> {
                        try {
                            Thread.sleep(400); // small delay to spread out load
                        } catch (InterruptedException ignored) {}
                        for (int i = 0; i < 400; i++) {

                            if(Main.proj.size()>Main.max_proj)break;
                            padr velocity = new padr();
                            velocity.set(Math.random() * 18 - 9, Math.random() * 18 - 9);
                            Projectile p = new Projectile(pos.first, pos.second, 4, velocity);
                            Main.queuedProjectiles.add(p);
                            try {
                                Thread.sleep(1); // small delay to spread out load
                            } catch (InterruptedException ignored) {}
                        }
                    }).start();
                    return;
                }


            }

            default -> {
                pos.first+=vel.first;
                pos.second-=vel.second;

                vel.second+=acc.second;

                for(int i = 0 ; i < walls.bounds.length; i++){
                    if(walls.active[i] && insideWall(walls.bounds[i])>0){
                        active = false;
                        return;   
                    }
                }
            }
        }

        last=now;
        box[0].set((int)pos.first-(int)wallBox.first, (int)pos.first+(int)wallBox.first);
        box[1].set((int)pos.second-(int)wallBox.second, (int)pos.second+(int)wallBox.second);
        
    }
}