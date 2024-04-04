//Game Example
//Lockwood Version 2023-24
// Learning goals:
// make an object class to go with this main class
// the object class should have a printInfo()
//input picture for background
//input picture for object and paint the object on the screen at a given point
//create move method for the object, and use it
// create a wrapping move method and a bouncing move method
//create a second object class
//give objects rectangles
//start interactions/collisions

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class GameLand implements Runnable {

    //Variable Declaration Section
    //Declare the variables used in the program
    //You can set their initial values here if you want

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    //Declare the objects used in the program below
    /***Step 0 declare object ***/
    public Hero astro;
    public Hero dino;
    public Hero butterfly;
    public Hero rabbit;
    public Hero dog;

    /*** Step 1 declare image for object ***/
    public Image astroPic;
    public Image dinoPic;
    public Image butterflyPic;
    public Image rabbitPic;
    public Image dogPic;
public boolean changepic;
    //declare background image
    public Image backgroundPic;

    public boolean astroIsIntersectingButterfly = false;
    public boolean dogIsIntersectingButterfly = false;

    //three steps to add an image
    //1. declare, 2. construct, 3. use (you fo this for the object and do it again for the image) ~ only adding image; like background then you only used first

    // Main method definition: PSVM
    // This is the code that runs first and automatically
    public static void main(String[] args) {
       GameLand ex = new GameLand();   //creates a new instance of the game and tells GameLand() method to run
        new Thread(ex).start();       //creates a thread & starts up the code in the run( ) method
    }

    // Constructor Method
    // This has no return type and has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public GameLand() {
        setUpGraphics();

        //create (construct) the objects needed for the game below
        /*** Step 2 construct object ***/
        astro = new Hero(200,300,2,0,60,80);
        dino = new Hero(800,600,0,-1,100,100);
        butterfly = new Hero(400,400,1,1/2,40,40);
        rabbit = new Hero (350,200,5,2,100,75);
        dog = new Hero (200,350,2,1,100,90);

        /***Step 3 add image to object ***/
        astroPic=Toolkit.getDefaultToolkit().getImage("astronaut.png");
        dinoPic=Toolkit.getDefaultToolkit().getImage("Dino Pc.png");
        backgroundPic=Toolkit.getDefaultToolkit().getImage("RealbackgroundPic.png");
        butterflyPic=Toolkit.getDefaultToolkit().getImage("Butterfly.png");
        rabbitPic=Toolkit.getDefaultToolkit().getImage("Rabbit.png");
        dogPic=Toolkit.getDefaultToolkit().getImage("Dog.png");
changepic = false;


        astro.printInfo();
        dino.printInfo();
        butterfly.printInfo();
        rabbit.printInfo();
        dog.printInfo();
        //for each object that has a picture, load in images as well


    }// GameLand()

//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        //for the moment we will loop things forever using a while loop
        while (true) {
            moveThings();  //move all the game objects
            collisions();
            render();  // paint the graphics
            pause(20); // sleep for 20 ms
        }
    }

    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        //draw our background Image below:
        g.drawImage(backgroundPic, 0,0,WIDTH, HEIGHT, null);
        //draw the image of your objects below:
        /** Step 4 draw  object images ***/

     //   if(astro.isAlive == true) {
            g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);
    //    }else {
            g.drawImage(dinoPic,dino.xpos,dino.ypos,dino.width,dino.height,null);
    //    }
        //else {}
        g.drawImage(butterflyPic,butterfly.xpos, butterfly.ypos,butterfly.width,butterfly.height,null);
        if(changepic){
            //something else
            g.drawImage(rabbitPic,rabbit.xpos,rabbit.ypos,rabbit.width,rabbit.height,null);

        }
        else{
            g.drawImage(dogPic,dog.xpos,dog.ypos,dog.width,dog.height,null);

        }

        //dispose the images each time(this allows for the illusion of movement).
        g.dispose();

        bufferStrategy.show();
    }

    public void moveThings() {
        //call the move() method code from your object class
        astro.bouncingMove();
        dino.wrappingMove();
        butterfly.wrappingMove();
        butterfly.bouncingMove();
        rabbit.bouncingMove();
        dog.bouncingMove();


    }

    public void collisions() {
        //** The Astro and Star collision **/
        if (astro.rec.intersects(butterfly.rec)) {
            if (astro.rec.intersects(butterfly.rec) && astroIsIntersectingButterfly) {
               butterfly.dx = 6;
            }
        }
        if(astro.rec.intersects(dino.rec)){
            System.out.println("dino and astro crash");
            dino.width = 200;
        }
        if(butterfly.rec.intersects(dino.rec)){
            System.out.println("butterfly and dino crash");
            butterfly.dy = butterfly.dy+1;
        }
        if(dog.rec.intersects(butterfly.rec)){
            System.out.println("dog and butterfly crash ");
            changepic=true;
        }
        if(rabbit.rec.intersects(dino.rec)){
            System.out.println("watch out!");
            rabbit.dx=rabbit.dx*-1;
            rabbit.dy=rabbit.dy*-1;
        }
    }
    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Game Land");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

}