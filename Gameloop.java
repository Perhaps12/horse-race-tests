import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import javax.imageio.*;
import javax.swing.*;

public class Gameloop extends Canvas implements Runnable, KeyListener {
    private final JFrame frame;
    public static boolean running = false;
    public static final Set<Integer> keys = new HashSet<>();
    public static final int WIDTH = 1920, HEIGHT = 1080;
    private int frames = 0;
    private int fps = 0;
    private long fpsTimer = System.currentTimeMillis();

    private BufferedImage back;
    private BufferedImage gate;
    

    public static void main(String[] args) {
        new Gameloop().start();
        
    }

    public Gameloop() {
        frame = new JFrame("Horse race test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIgnoreRepaint(true); 
        // frame.setResizable(false);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        addKeyListener(this);

        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(frame.MAXIMIZED_BOTH);

        

        try {
            back = ImageIO.read(getClass().getResourceAsStream("/Sprites/The chamber (2).png"));
        } catch (Exception e) {
        }

        try {
            gate = ImageIO.read(getClass().getResourceAsStream("/Sprites/gatekeeping.png"));
        } catch (Exception e) {
        }

        

        createBufferStrategy(2); 
    }

    public void start() {
        running = true;
        new Thread(this).start();
    }

    public static int arr[][] = { {805, 320}, {850, 270}, {700, 300}, {750, 314},{690, 250}, {750, 230}, {800, 240}  };
    
    public long time = 0;
    public int timemins = 0;
    public int timesecs = 0;
    public int timemill = 0;
    public String mill = "";
    public String mins = "";
    public String secs = "";
    public int left = 10;
    public String Left = "place your bets in... " + left;
    
    private padr placebets = new padr();
    @Override
    public void run() {
        final long frameTime = 1_000_000_000 / 60;
        long lastTime = System.nanoTime();

        
        for(int i = 0 ; i < 7; i++){
            padr velocity = new padr();
            double angle = Math.random()*(Math.PI/2-0.2)+0.1;
            velocity.set(3*Math.cos(angle), 3*Math.sin(angle));
            
            Projectile p = new Projectile(arr[i][0], arr[i][1], 3, velocity);
            // Projectile p = new Projectile(Math.random()*60+120, Math.random()*60+120, 3, velocity);

            Main.queuedProjectiles.add(p);
        }
        while(mins.length()<2)mins = "0" + mins;
        while(secs.length()<2)secs = "0" + secs;
        while(mill.length()<3)mill = "0" + mill;
        placebets.set(650,450);
        update();
        render();

        try {
            Thread.sleep(1000);
            
        } catch (Exception e) {
        }
        // while(left>0){
        //     try {
        //         Thread.sleep(1000);
                
        //     } catch (Exception e) {
        //     }
            
        //     left--;
        //     render();
        // }

        time=0;
        lastTime=System.nanoTime();
        // SoundPlayer.playSound("Sounds//omori_audio.wav");
        
        while (left>-1) {
            long now = System.nanoTime();
            if (now - lastTime >= frameTime) {
                time+=now-lastTime;
                timesecs = (int)(time/1000000000);
                left = 10-timesecs;
                double angle = Math.random()*2*Math.PI;
                placebets.first+=Math.cos(angle);
                placebets.second+=Math.sin(angle);
                Left = "place your bets in... " + left;
                render();
                lastTime=now;
            }

            try {
                Thread.sleep(1);
            } catch (Exception e) {}
        }
        Left = "GO!";
        blender = true;




        time=0;
        lastTime=System.nanoTime();


        render();
        update();



        while (running) {
            long now = System.nanoTime();
            if (now - lastTime >= frameTime) {
                if(Main.CARROT)time+=now-lastTime;
                render();
                update();
                // System.out.println(Main.proj.size());
                frames++;

                if (System.currentTimeMillis() - fpsTimer >= 1000) {
                    fps = frames;
                    frames = 0;
                    fpsTimer += 1000;
                }
                timemill = (int)(time/1000000);
                timesecs = (int)(time/1000000000);
                timemins = timesecs/60;
                timesecs%=60;
                timemill%=1000;
                mins = timemins + "";
                secs = timesecs+"";
                mill = timemill + "";
                while(mins.length()<2)mins = "0" + mins;
                while(secs.length()<2)secs = "0" + secs;
                while(mill.length()<3)mill = "0" + mill;
                lastTime = now;

            }

            try {
                Thread.sleep(1);
            } catch (Exception e) {}
        }
    }

    public void update() {
        while (!Main.queuedProjectiles.isEmpty()) {
            if (Main.proj.size() < Main.max_proj) {
                Main.proj.add(Main.queuedProjectiles.poll());
            } else {
                // break; // Avoid going over the limit
                Main.queuedProjectiles.poll();
            }
        }
        
        // Main.player.update();
        for(int i = 0 ; i < Main.proj.size(); i++){
            Main.proj.get(i).update();
            if(!Main.proj.get(i).active){
                Main.proj.remove(i);
                i--;
            }
        }
    }

    public boolean blender = false;
    private void render() {
        
        BufferStrategy bs = getBufferStrategy();
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        // Clear screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        
        g.setStroke(new BasicStroke(1));
        // Grid
        g.setColor(Color.GRAY);
        for (int x = 0; x < WIDTH; x += 50)
            g.drawLine(x, 0, x, HEIGHT);
        for (int y = 0; y < HEIGHT; y += 50)
            g.drawLine(0, y, WIDTH, y);

        if(gate!=null && !blender){
            g.drawImage(gate, 22, 335, null);
        }
        
        if (back != null) {
            g.drawImage(back, 0, 0, null);
        }
        
        g.setStroke(new BasicStroke(3));

        // wall
        // g.setColor(Color.RED);
        // int I = -1;
        // for(pair[] i : walls.bounds){
        //     I++;
        //     if(!walls.active[I])continue;
        //     g.drawRect(i[0].first, i[1].first, i[0].second-i[0].first, i[1].second-i[1].first);
        // }
        
        // if (back != null) {
            //     g.drawImage(back, 0, 0, null);
            // }
            
            // // Grid
            // g.setColor(Color.GRAY);
            // for (int x = 0; x < WIDTH; x += 50)
            //     g.drawLine(x, 0, x, HEIGHT);
            // for (int y = 0; y < HEIGHT; y += 50)
            //     g.drawLine(0, y, WIDTH, y);
            
        g.setColor(Color.RED);   
        for(Projectile p : Main.proj){
            p.draw(g);
            // g.drawRect(p.box[0].first, p.box[1].first, p.box[0].second-p.box[0].first, p.box[1].second-p.box[1].first);
        }
        // Main.player.draw(g);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.BOLD, 24));
        // g.drawString("FPS: " + fps, 570, 30);
        g.drawString( mins + ":" + secs + ":" + mill, 620, 430);

        if(!blender||time/1000000000 < 1){
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.RED);
            g.fillRect((int)placebets.first-5, (int)placebets.second-20, 220, 36);
            g.setColor(Color.WHITE);
            g.drawString(Left, (int)placebets.first , (int)placebets.second);
        }


        g.dispose(); // release Graphics
        bs.show();   // draw to screen
        Toolkit.getDefaultToolkit().sync(); // force render
    }

    @Override public void keyPressed(KeyEvent e) { keys.add(e.getKeyCode()); }
    @Override public void keyReleased(KeyEvent e) {keys.remove(e.getKeyCode()); }
    @Override public void keyTyped(KeyEvent e) {}
}
