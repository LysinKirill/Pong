import Vectors.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Main extends JComponent implements KeyListener, ActionListener, ComponentListener, MouseListener {
    static boolean[] pressedKeys = new boolean[1024];
    Timer timer = new Timer(0, this); //    delay = ?
    static Toolkit toolkit = Toolkit.getDefaultToolkit();
    static Dimension dimension = toolkit.getScreenSize();
    static int width = (int)(dimension.width);
    static int height = (int)(dimension.height*0.98);
    static double wUnit = width/1000f;
    static double hUnit = wUnit * (height/((double)width));
    static double unit = width/1000f;  //   preserves shape
    static int pullX1, pullY1, pullX2, pullY2;
    static double vSpeed = 3;
    public static Vec2d hit = new Vec2d();
    public static Vec2d dir = new Vec2d();
    public static double timeRate = 1;
    //static Vec2d point = new Vec2d();
    static JFrame frame = new JFrame("Pong");

    //public static Ball ball = new Ball(new Vec2d(width/2f, height/2f), new Vec2d(Math.random()*5-2.5, Math.random()*5-2.5), 20);
    public static Ball ball = new Ball(new Vec2d(width/2f, height/2f), new Vec2d(0, 0), 20);

    public static ArrayList<Block> blocks = new ArrayList<>();


    public static void main(String[] args){
        //System.out.println(Vec2d.getDist(new Vec2d(1, 1), new Vec2d(3, 3), new Vec2d(2, 0)));


        /*blocks.add(new Box(new Vec2d(30*wUnit, 50*hUnit), new Vec2d(1.5*wUnit, 1.5*hUnit), 30*unit, 30*unit, new Vec2d[]{
                new Vec2d(30*wUnit, 50*hUnit),
                new Vec2d(970*wUnit, 50*hUnit),
                new Vec2d(970*wUnit, 900*hUnit),
                new Vec2d(30*wUnit, 900*hUnit),
        }));*/
        blocks.add(new Wall(500, 150, 2, new Vec2d[]{
                new Vec2d(100*wUnit, 500*hUnit),

                new Vec2d(500*wUnit, 850*hUnit),
                new Vec2d(900*wUnit, 500*hUnit),
                new Vec2d(500*wUnit, 150*hUnit),
        }));
        blocks.get(0).scale(3);


        for(int i = 0; i < 20; i ++){ //верхний ряд
            blocks.add(new Wall(25 + 50*i, 30, 2, new Vec2d[]{
                    new Vec2d(975*wUnit, 30*hUnit),
                    new Vec2d(975*wUnit, 975*hUnit),
                    new Vec2d(25*wUnit, 975*hUnit),
                    new Vec2d(25*wUnit, 30*hUnit)
            }));
        }

        for(int i = 0; i < 10; i++){ // правый столбец
            blocks.add(new Wall(975, 120 + 85 * i, 2, new Vec2d[]{
                    new Vec2d(975*wUnit, 975*hUnit),
                    new Vec2d(25*wUnit, 975*hUnit),
                    new Vec2d(25*wUnit, 30*hUnit),
                    new Vec2d(975*wUnit, 30*hUnit),
            }));
        }

        for(int i = 0; i < 20; i ++){ //нижний ряд
            blocks.add(new Wall(975 - 50*i, 970, 2, new Vec2d[]{
                    new Vec2d(25*wUnit, 975*hUnit),
                    new Vec2d(25*wUnit, 30*hUnit),
                    new Vec2d(975*wUnit, 30*hUnit),
                    new Vec2d(975*wUnit, 975*hUnit)
            }));
        }

        for(int i = 0; i < 10; i++){ // правый столбец
            blocks.add(new Wall(25, 880 - 85 * i, 2, new Vec2d[]{
                    new Vec2d(25*wUnit, 30*hUnit),
                    new Vec2d(975*wUnit, 30*hUnit),
                    new Vec2d(975*wUnit, 975*hUnit),
                    new Vec2d(25*wUnit, 975*hUnit)
            }));
        }

        //blocks.add(new Box(new Vec2d(800*wUnit, 800*hUnit), new Vec2d(), new Polygon(new int[]{(int)(800*wUnit), (int)(950*wUnit), (int)(650*wUnit)}, new int[]{(int)(650*hUnit), (int)(950*hUnit), (int)(700*hUnit)}, 3), new Vec2d[]{}));
        //blocks.add(new Box(new Vec2d(), new Vec2d(), 40, 40, new Vec2d[]{}));


        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main simulation = new Main();


                //frame.pack();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setUndecorated(true);
                //frame.setAlwaysOnTop(true);

                frame.setSize(width, height);
                frame.setVisible(true);
                frame.setLocation(0, 0);
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.add(simulation);
                frame.addMouseListener(simulation);
                frame.addKeyListener(simulation);
                frame.addComponentListener(simulation);

                //options.setSize(700, 700);
                //options.setVisible(false);
                //frame.add(options);
                /*JPanel menu = new JPanel(new BorderLayout());
                menu.setSize(50, 50);
                menu.setLocation(50, 50);
                menu.setBackground(Color.black);
                menu.setVisible(true);
                frame.getContentPane().add(menu, BorderLayout.CENTER);

                */
                //frame.setBackground(Color.EH);
                //frame.add(menu);*/
            }
        });

        /// GAME CYCLE
        while(true){

            Main.controls();

            //System.out.println(timeRate);
            // Intersection of the ball with blocks (boxes) and movement
            for(int i = 0; i < blocks.size(); i++){

                if(ball.shape.getBounds().intersects(blocks.get(i).shape.getBounds())){
                    blocks.get(i).bounce(ball);
                }
                blocks.get(i).move();
            }

            if(ball.speed.lengthSqr() > vSpeed*vSpeed){
                ball.speed = Vec2d.mult(ball.speed, 0.995);
            }
            ball.move();






            try{
                //System.out.println(timeRate);
                //TimeUnit.MICROSECONDS.sleep(7812);}catch(InterruptedException e){//128 исполнений цикла за секнду
                TimeUnit.MICROSECONDS.sleep((int)(7812*timeRate));}catch(InterruptedException e){//128 исполнений цикла за секнду
                e.getStackTrace();
            }
        }

    }
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        g2d.setStroke(new BasicStroke(3));


        for(int i = 0; i < blocks.size(); i++) {
            g2d.draw(blocks.get(i).shape);
            //g2d.draw(blocks.get(i).shape.getBounds2D());
            g2d.setColor(new Color(108, 255, 213, 134));
            g2d.fill(blocks.get(i).shape);
            g2d.setColor(Color.black);

            g2d.draw(Main.getCircle(blocks.get(i).pos, 10));
        }
        g2d.draw(ball.shape);
        g2d.setColor(ball.color);
        g2d.fill(ball.shape);
        g2d.setColor(Color.BLACK);
        ball.speed.draw(g2d, ball.pos.getX(), ball.pos.getY(), 50);

        //g2d.drawLine((int)hit.getX(), (int)hit.getY(), (int)(hit.getX() + dir.getX()), (int)(hit.getY() + dir.getY()));
        //g2d.draw(Main.getCircle(point, 10));
        timer.start();
    }


    public static Shape getCircle(Vec2d pos, double r){
        return new Ellipse2D.Double(pos.getX() - r, pos.getY() - r, 2*r, 2*r);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }

    @Override
    public void componentResized(ComponentEvent componentEvent) {
        width = this.getWidth();
        height = this.getHeight();
    }

    @Override
    public void componentMoved(ComponentEvent componentEvent) {

    }

    @Override
    public void componentShown(ComponentEvent componentEvent) {

    }

    @Override
    public void componentHidden(ComponentEvent componentEvent) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        //if(e.getKeyCode() == 0){
        //    frame.setVisible(!frame.isVisible());
        //}
        //System.out.println(e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = false;
        //System.out.println(e.getKeyCode());
    }


    static void controls(){
        //double vSpeed = 3;
        //Vec2d nSpeed = new Vec2d(0, 0);
        Vec2d nSpeed = ball.speed;
        if(pressedKeys[37]){   //LEFT_ARROW

            timeRate *= 1.01;
        }
        if(pressedKeys[39]){   //RIGHT_ARROW
            if(timeRate > 0.05){
                timeRate *= 0.99;
            }
        }
        if(pressedKeys[27]){
            frame.setState(1);
            pressedKeys[27] = false;
        }

        if(pressedKeys[82]){
            ball = new Ball(new Vec2d(width/2f, height/2f), new Vec2d(0, 0), 20);
            ball.speed = new Vec2d();
        }
        if(pressedKeys[87]) { // w pressed
            nSpeed.add(new Vec2d(0, -1));
            //if(ball.speed.getY() - ball.acceleration < -ball.maxSpeed){
            //    ball.speed.setY(-ball.maxSpeed);
            //}else{ball.speed.setY(ball.speed.getY() - ball.acceleration);}
            //}else{ball.decelerate(1, ball.decelerationRate, 1);}
        }
        if(pressedKeys[83]) { // s pressed
            nSpeed.add(new Vec2d(0, 1));
            //if(ball.speed.getY() + ball.acceleration > ball.maxSpeed){
            //    ball.speed.setY(ball.maxSpeed);
            //}else{ball.speed.setY(ball.speed.getY() + ball.acceleration);}
            //}else{ball.decelerate(1, ball.decelerationRate, 1);}
        }
        if(pressedKeys[65]) { // a pressed
            nSpeed.add(new Vec2d(-1, 0));
            //if(ball.speed.getX() - ball.acceleration < -ball.maxSpeed){
            //    ball.speed.setX(-ball.maxSpeed);
            //}else{ball.speed.setX(ball.speed.getX() - ball.acceleration);}
            //}else{ball.decelerate(ball.decelerationRate, 1, 1);}
        }
        if(pressedKeys[68]) { // d pressed
            nSpeed.add(new Vec2d(1, 0));
            //if(ball.speed.getX() + ball.acceleration > ball.maxSpeed){
            //    ball.speed.setX(ball.maxSpeed);
            //}else{ball.speed.setX(ball.speed.getX() + ball.acceleration);}
            //}else{ball.decelerate(ball.decelerationRate, 1, 1);}
        }
        /*if(pressedKeys[81]){ // q pressed
            if(ball.angular_velocity - ball.angularAcceleration < -ball.maxAngularVelocity){
                ball.angular_velocity = -ball.maxAngularVelocity;
            }else{ball.angular_velocity -= ball.angularAcceleration;}
        }else{ball.decelerate(1, 1, ball.decelerationRate);}

        if(pressedKeys[69]){ // e pressed
            if(ball.angular_velocity + ball.angularAcceleration > ball.maxAngularVelocity){
                ball.angular_velocity = ball.maxAngularVelocity;
            }else{ball.angular_velocity += ball.angularAcceleration;}
        }else{ball.decelerate(1, 1, ball.decelerationRate);}

        if(pressedKeys[32]){ // space pressed
            if(ball.shooting_timer <= 0){
                for(int i = 0; i < ball.shooting_vertexes.size(); i++){
                    bullets.add(ball.shoot(i));
                }
            }
        }*/

        /*if(!((nSpeed.getX() == 0)&&(nSpeed.getY() == 0))){

            ball.speed = Vec2d.mult(nSpeed.toUnitVector(), vSpeed);
            //if(ball.speed.lengthSqr() > vSpeed*vSpeed){
            //    ball.speed = Vec2d.mult(ball.speed, 0.99);
            //}

        }else{
            ball.speed = nSpeed;
        }*/

        //System.out.println(ball.pos);
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        pullX1 = e.getX();
        pullY1 = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //System.out.println(e.getButton());
        if(e.getButton() == 3){blocks.get(0).moveTo(e.getX(), e.getY());}
        if(e.getButton() == 1){
            pullX2 = e.getX();
            pullY2 = e.getY();
            ball.speed = Vec2d.mult(new Vec2d(pullX2 - pullX1, pullY2 - pullY1).toUnitVector(), vSpeed);
        }

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
