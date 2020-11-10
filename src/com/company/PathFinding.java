package com.company;

import com.sun.javafx.collections.MappingChange;
import oracle.jrockit.jfr.JFR;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.soap.Node;

/*class Player_A < T extends PathFinding & Node>{

    public int gold = 200;
    public int a = 0;

    public Player_A(){
        PathFinding pathfinding = new PathFinding();

        int x = 0, y = 0;

        a = pathfinding.map[x][y].x + pathfinding.map[x][y].y;
        //System.out.println(a);

        for (x = 0; x < pathfinding.cells; x++){
            for (y = 0; y < pathfinding.cells; y++){
                pathfinding.map.
                int equal = 0;

                if(a != pathfinding.map[x][y].x + pathfinding.map[x][y].y){
                    equal = pathfinding.map[x][y].x + pathfinding.map[x][y].y;
                    System.out.println(equal);

                }

            }
        }
    }
}*/

class PathFinding {

    //FRAME
    JFrame frame;

    //GENERAL VARIABLES
    public static int value = 500;
    public int cells = 20;
    public int startx = -1;
    public int starty = -1;
    public int finishx = -1;
    public int finishy = -1;
    public final int WIDTH = 850;
    public final int HEIGHT = 650;
    public final int MSIZE = 600;
    public int CSIZE = MSIZE/cells;
    public  int [] gold_x = new int[value];
    public  int [] gold_y = new int[value];
    public boolean control = true;
    public int golds = 200;
    public int first_a = 0, second_a = 0;
    public int first_b = cells-1, second_b = 0;
    public int number_of_steps_a = 0, number_of_steps_b = 0, number_of_steps_c = 0, number_of_steps_d= 0;
    public ArrayList<Integer> random_control = new ArrayList<>();

    //BOOLEANS
    private boolean solving = false;

    //UTIL
    Node[][] map;
    Random r = new Random();

    //Slider Size
    JSlider size = new JSlider(1,5,2);
    JSlider starting_gold = new JSlider(1,5,1);

    //LABELS
    JLabel sizeL = new JLabel("Size:");
    JLabel cellsL = new JLabel(cells + "x" + cells);

    //TEXT FIELD
    JTextField players_gold = new JTextField(Integer.toString(golds));

    //BUTTONS
    JButton start = new JButton("Start Game");
    JButton golds_players_placement = new JButton("Placement");
    JButton clearMap = new JButton("Clear Map");
    JButton get_golds = new JButton("Get Golds");

    //PANELS
    JPanel toolP = new JPanel();

    //CANVAS
    Map canvas;

    //BORDER
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    // MAIN METHOD
    public static void main(String[] args) {
        new PathFinding();
    }

    // CONSTRUCTOR
    public PathFinding() {
        clearMap();
        initialize();
    }

    public void start_game(){
       do{
           //Player_A();
           Player_B();
           golds -= 5;
       }while (golds != 0);
    }

    public void get_golds(){
        String text = players_gold.getText();
        golds = Integer.parseInt(text);
    }

    /*public void Player_A(){
        int x = 0, y = 0;

        int equal = (cells-1)+(cells-1);
        int[] eq = new int[2];

        for (x = 0; x < cells; x++) {
            for (y = 0; y < cells; y++) {
                if (map[x][y].getType() == 2) {
                    if ((map[x][y].x + map[x][y].y) < equal) {
                        equal = map[x][y].x + map[x][y].y;
                        eq[0] = map[x][y].x;
                        eq[1] = map[x][y].y;
                    }
                }
            }
        }
            map[eq[0]][eq[1]].setType(4);
            number_of_steps_a++;
            map[first_a][second_a].setType(3);

            first_a = eq[0];
            second_a = eq[1];
    }*/

    public void Player_B(){

        int x = cells-1, y = 0;

        int close_number = 0, number = cells-1, close_number2 = 0, proximity = 0, proximity2 = -1, random_x = 10, random_y = 0;

        int[] eq = new int[2];

        close_number = random_x;
        proximity = Math.abs(random_x - number);

        for (x = cells-1; x > 0; x--) {
            for (y = 0; y < cells; y++) {
                if (map[x][y].getType() == 2) {
                    random_x = map[x][y].x + map[x][y].y;
                    if (Math.abs(random_x - number) < proximity){
                        proximity = Math.abs(random_x - number);
                        close_number = random_x;
                        eq[0] = map[x][y].x;
                        eq[1] = map[x][y].y;
                        //System.out.println(eq[0] + " " + eq[1]);
                    }
                    else if (Math.abs(random_x - number) == proximity){
                        if(random_x != close_number){
                            proximity2 = proximity;
                            close_number2 = random_x;
                        }
                    }
                }
            }
        }
        map[eq[0]][eq[1]].setType(5);
        number_of_steps_b++;
        map[first_b][second_b].setType(3);

        first_b = eq[0];
        second_b = eq[1];
    }

    // Random gold placement
    public void visible_golds() {
        clearMap();	// CREATE CLEAR MAP TO START

        for(int i = 0; i < (cells*cells)/5; i++) {
            int x = 0;
            int y = 0;
            do {
                x = r.nextInt(cells);
                y = r.nextInt(cells);

                //System.out.println(x + " " + y); // Manuel Debug

            } while(map[x][y].getType() == 2 || ((x == 0 && y == 0) || (x == (cells - 1) && y == 0) || (x == 0 && y == (cells - 1)) || (x == (cells - 1) && y == (cells - 1))));

            map[x][y].setType(2);

            gold_x[i] = map[x][y].x;
            gold_y[i] = map[x][y].y;
        }
        unvisible_golds();
        player_placement();
    }

    public void player_placement(){
        map[0][0].setType(4); // A
        map[cells-1][0].setType(5); // B
        map[cells-1][cells-1].setType(6); // C
        map[0][cells-1].setType(7); // D
    }

    public void unvisible_golds() {
        int Random = 0, Old_random = 0;
        ArrayList<Integer> control = new ArrayList<Integer>();

        for(int i = 0; i < (cells*cells)/50; i++)
        {
            while (control.contains(Random)){
                Random = r.nextInt(cells);
            }
                if(control.contains(Random)){
                    //System.out.println("if" + " " + Random); // Manuel Debug
                }
                else {
                    //System.out.println("else" + " " + Random); // Manuel Debug
                    map[gold_x[Random]][gold_y[Random]].setType(1);
                }

                //System.out.println(gold_x[Random] + " " + gold_y[Random]); // Manuel Debug
                control.add(Random);
                Random = 0;
        }
        control.clear();
    }

    // Clear Map
    public void clearMap() {
        control = true;
        finishx = 0;  // RESET THE START AND FINISH
        finishy = -1;
        startx = -1;
        starty = -1;
        map = new Node[cells][cells];	// CREATE NEW MAP OF NODES
        for(int x = 0; x < cells; x++) {
            for(int y = 0; y < cells; y++) {
                map[x][y] = new Node(3, x, y);	//SET ALL NODES TO EMPTY
            }
        }
    }

    // UPDATE ELEMENTS OF THE GUI
    public void Update() {
        CSIZE = MSIZE/cells;
        canvas.repaint();
        cellsL.setText(cells + "x" + cells);
    }

    // INITIALIZE THE GUI ELEMENTS
    private void initialize() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Gold Collecting Game");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        toolP.setBorder(BorderFactory.createTitledBorder(loweredetched,"Controls"));
        int space = 25;
        int buff = 45;

        toolP.setLayout(null);
        toolP.setBounds(10,10,210,400);

        start.setBounds(40,space, 120, 25);
        toolP.add(start);
        space+=buff;

        golds_players_placement.setBounds(40,space, 120, 25);
        toolP.add(golds_players_placement);
        space+=buff;

        clearMap.setBounds(40,space, 120, 25);
        toolP.add(clearMap);
        space+=40;

        players_gold.setToolTipText("Varsayılan 200 Altın");
        players_gold.setBounds(40,space, 120, 25);
        toolP.add(players_gold);
        space+=40;

        get_golds.setBounds(40,space, 120, 25);
        toolP.add(get_golds);
        space+=40;

        sizeL.setBounds(15,space,40,25);
        toolP.add(sizeL);
        size.setMajorTickSpacing(10);
        size.setBounds(50,space,100,25);
        toolP.add(size);
        cellsL.setBounds(160,space,40,25);
        toolP.add(cellsL);
        space+=buff;

        frame.getContentPane().add(toolP);

        canvas = new Map();
        canvas.setBounds(230, 10, MSIZE+1, MSIZE+1);
        frame.getContentPane().add(canvas);

        // ACTION LISTENERS
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start_game();
                Update();
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                get_golds();
            }
        });

        golds_players_placement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visible_golds();
                Update();
            }
        });

        clearMap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMap();
                Update();
            }
        });

        size.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                cells = size.getValue()*10;
                clearMap();
                Update();
            }
        });
    }

    // MAP CLASS
    class Map extends JPanel {

        public void paintComponent(Graphics g) {
            int random = 0;

            super.paintComponent(g);  // REPAINT
            for (int x = 0; x < cells; x++) {    // PAINT EACH NODE IN THE GRID
                for (int y = 0; y < cells; y++) {
                    //System.out.println(map[x][y].getType()); // Manuel Debug
                    switch(map[x][y].getType()) {
                        case 1: // unvisible Gold Placement
                            g.setColor(Color.WHITE);
                            g.setColor(Color.WHITE);
                            random = r.nextInt(20/5)*5 + 5;
                            random_control.add(random);
                            break;
                        case 2: // Gold Placement
                            g.setColor(Color.ORANGE);
                            random = r.nextInt(20/5)*5 + 5;
                            random_control.add(random);
                            break;
                        case 3: // Clear Map
                            g.setColor(Color.WHITE);
                            break;
                        case 4: // PLAYER A
                            g.setColor(Color.GRAY);
                            break;
                        case 5: // PLAYER B
                            g.setColor(Color.PINK);
                            break;
                        case 6: // PLAYER C
                            g.setColor(Color.MAGENTA);
                            break;
                        case 7: // PLAYER D
                            g.setColor(Color.CYAN);
                            break;
                    }
                        g.fillRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                        g.setColor(Color.BLUE);
                        g.drawRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);

                    /*if (map[x][y].getType() == 1)
                    {
                        g.setColor(Color.RED);
                        g.drawString(Integer.toString(random), x*CSIZE, y*CSIZE + 10);
                    }*/

                    if (map[x][y].getType() == 2) {
                        g.setColor(Color.BLACK);
                        g.drawString(Integer.toString(random), x * CSIZE, y * CSIZE + 10);
                    }

                    if (map[x][y].getType() == 4) {
                        g.setColor(Color.BLACK);
                        g.drawString("A", x * CSIZE + 12, y * CSIZE + 18);
                    }

                    if (map[x][y].getType() == 5) {
                        g.setColor(Color.BLACK);
                        g.drawString("B", x * CSIZE + 12, y * CSIZE + 18);
                    }

                    if (map[x][y].getType() == 6) {
                        g.setColor(Color.BLACK);
                        g.drawString("C", x * CSIZE + 12, y * CSIZE + 18);
                    }

                    if (map[x][y].getType() == 7) {
                        g.setColor(Color.BLACK);
                        g.drawString("D", x * CSIZE + 12, y * CSIZE + 18);
                    }
                }
            }
        }
    }


    public static class Node {

        // 0 = start, 1 = finish, 2 = wall, 3 = empty, 4 = checked, 5 = finalpath
        public int cellType = 0;
        public int hops;
        public int x;
        public int y;
        public int lastX;
        public int lastY;
        public double dToEnd = 0;

        public Node(int type, int x, int y) {	//CONSTRUCTOR
            cellType = type;
            this.x = x;
            this.y = y;
            hops = -1;
        }

        //GET METHODS
        public int getX() {return x;}
        public int getY() {return y;}
        public int getLastX() {return lastX;}
        public int getLastY() {return lastY;}
        public int getType() {return cellType;}
        public int getHops() {return hops;}

        //SET METHODS
        public void setType(int type) {cellType = type;}
        public void setLastNode(int x, int y) {lastX = x; lastY = y;}
        public void setHops(int hops) {this.hops = hops;}
    }
}