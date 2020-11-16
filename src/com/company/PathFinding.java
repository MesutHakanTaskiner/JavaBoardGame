package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class PathFinding {

    //FRAME
    JFrame frame;
    JFrame frame2;

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
    public static int a_golds = 200;
    public static int b_golds = 200;
    public static int c_golds = 200;
    public static int d_golds = 200;
    public int first_a = 0, second_a = 0;
    public int b = 0, n = 0; // Player a current coordinate
    public int first_b = cells-1, second_b = 0;  // Player b current coordinate
    public int c1 = cells-1, c2 = cells-1;
    public int d1 = 0, d2 = cells-1;
    public int number_of_steps_a = 0, number_of_steps_b = 0, number_of_steps_c = 0, number_of_steps_d= 0;
    public ArrayList<Integer> random_control = new ArrayList<>();
    public int[][] visible_golds = new int[cells][cells];

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
    JLabel a_gold = new JLabel(  "A current gold" );
    JLabel b_gold = new JLabel( "B current gold" );
    JLabel c_gold = new JLabel( "C current gold" );
    JLabel d_gold = new JLabel( "D current gold" );
    JLabel a_gold_t = new JLabel(Integer.toString(a_golds));
    JLabel b_gold_t = new JLabel(Integer.toString(b_golds));
    JLabel c_gold_t = new JLabel(Integer.toString(c_golds));
    JLabel d_gold_t = new JLabel(Integer.toString(d_golds));

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

        Timer myTimer = new Timer();
        TimerTask gorev = new TimerTask(){
            @Override
            public void run() {
                if (a_golds > -1){
                    a_golds -= 5;
                }

                if (b_golds > -1){
                    b_golds -= 5;
                }
                //Update();
            }
        };
        myTimer.schedule(gorev,1,10000);

        frame2 = new JFrame();
        //frame2.setVisible(true);
        frame2.setResizable(false);
        frame2.setSize(WIDTH, HEIGHT);
        frame2.setTitle("Results");
        frame2.setLocationRelativeTo(null);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.getContentPane().setLayout(null);
    }

    public void get_golds(){
        String text = players_gold.getText();
        golds = Integer.parseInt(text);
        a_golds = Integer.parseInt(text);
        b_golds = Integer.parseInt(text);
        c_golds = Integer.parseInt(text);
        d_golds = Integer.parseInt(text);
        a_gold_t.setText(Integer.toString(a_golds));
        b_gold_t.setText(Integer.toString(b_golds));
        c_gold_t.setText(Integer.toString(c_golds));
        d_gold_t.setText(Integer.toString(d_golds));
    }

    public int[] manhattan(int b, int n){
        int distance = 0;
        int temp = cells-1;
        int temp_x=0, temp_y=0;
        int[] arr = new int[4];
        //ArrayList<Integer> near_d = new ArrayList<Integer>();

        // = Math.abs(x1-x0) + Math.abs(y1-y0);
        for (int x = 0; x < cells; x++) {
            for (int y = 0; y < cells; y++) {
                if (map[x][y].getType()==2) {
                    distance = Math.abs(map[b][n].x - map[x][y].x) + Math.abs(map[b][n].y - map[x][y].y);
                    //near_d.add(distance);
                    //System.out.println(map[x][y].x + "," + map[x][y].y + "DİSTANCE  " + distance);

                    if(distance<temp){
                        temp = distance;
                        temp_x = map[x][y].x;
                        temp_y = map[x][y].y;
                    }
                }
            }
        }
        /*map[b][n].setType(3);
        map[temp_x][temp_y].setType(typ);*/
        arr[0] = map[temp_x][temp_y].x;
        arr[1] = map[temp_x][temp_y].y;
        arr[2] = visible_golds[temp_x][temp_y];
        arr[3] = temp;

        return arr;
    }

    public int[] manhattan_b(int b, int n){
        int distance = 0;
        int cost = 2;
        int profit = 0;
        int temp = cells-1;
        int temp_x = 0, temp_y = 0;
        int[] arr = new int[4];
        ArrayList<Integer> near_d = new ArrayList<Integer>();
        ArrayList<Integer> gain = new ArrayList<Integer>();
        gain.add(0);

        for (int x = 0; x < cells; x++) {
            for (int y = 0; y < cells; y++) {
                if (map[x][y].getType() == 2) {
                    distance = Math.abs(map[b][n].x - map[x][y].x) + Math.abs(map[b][n].y - map[x][y].y);
                    temp = cost * distance;
                    profit = visible_golds[x][y] - temp;


                    if(profit > Collections.max(gain)){
                        gain.add(profit);
                        temp_x = map[x][y].x;
                        temp_y = map[x][y].y;
                    }
                }
            }
        }
        /*map[b][n].setType(3);
        map[temp_x][temp_y].setType(typ);*/

        arr[0] = map[temp_x][temp_y].x;
        arr[1] = map[temp_x][temp_y].y;
        arr[2] = visible_golds[temp_x][temp_y];
        arr[3] = temp;

        gain.clear();
        return arr;
    }

    public int[] manhattan_c(int b, int n){
        //public void manhattan_c(int b, int n){
        int distance = 0;
        int temp = cells-1;
        int temp_x = 0, temp_y = 0;
        int u_distance = 0;
        int u_temp = cells-1;
        int u_temp_x = 0, u_temp_y = 0;
        int[] arr = new int[4];
        ArrayList<Integer> near_d = new ArrayList<Integer>();

        // = Math.abs(x1-x0) + Math.abs(y1-y0);
        for (int x = 0; x < cells; x++) {
            for (int y = 0; y < cells; y++) {
                if (map[x][y].getType() == 2) {
                    distance = Math.abs(map[b][n].x - map[x][y].x) + Math.abs(map[b][n].y - map[x][y].y);
                    //near_d.add(distance);
                    //System.out.println(map[x][y].x + "," + map[x][y].y + "DİSTANCE  " + distance);

                    if(distance < temp){
                        temp = distance;
                        temp_x = map[x][y].x;
                        temp_y = map[x][y].y;
                    }
                }

                if (map[x][y].getType() == 1) {
                    u_distance = Math.abs(map[b][n].x - map[x][y].x) + Math.abs(map[b][n].y - map[x][y].y);
                    //near_d.add(distance);
                    //System.out.println(map[x][y].x + "," + map[x][y].y + "DİSTANCE  " + distance);

                    if(u_distance < u_temp){
                        u_temp = u_distance;
                        u_temp_x = map[x][y].x;
                        u_temp_y = map[x][y].y;

                    }
                }
            }
        }

        if(map[u_temp_x][u_temp_y]!=map[0][0]){
            map[u_temp_x][u_temp_y].setType(9);
        }
        /*map[b][n].setType(3);
        map[temp_x][temp_y].setType(typ);*/
        arr[0] = map[temp_x][temp_y].x;
        arr[1] = map[temp_x][temp_y].y;
        arr[2] = visible_golds[temp_x][temp_y];
        arr[3] = temp;

        return arr;
    }

    public void player_a(){
        a_golds -= 5;
        int[] arr = new int[4] ;
        //manhattan(b,n,4);
        arr = manhattan(b,n);
        map[b][n].setType(3);
        map[arr[0]][arr[1]].setType(4);
        b = arr[0];
        n = arr[1];

        a_golds += arr[2];
        a_gold_t.setText(Integer.toString(a_golds));
        Update();
    }

    public void player_b(){
        b_golds -= 10;
        // b_golds += manhattan(first_b,second_b,5);
        //manhattan(b,n,4);
        //System.out.println(arr[2]);
        int d1 = 0, d2 = 0;
        int gold1 = 0, gold2 = 0;

        int[] arr;

        arr = manhattan_b(first_b,second_b);

        map[first_b][second_b].setType(3);
        map[arr[0]][arr[1]].setType(5);

        first_b = arr[0] ;
        second_b = arr[1] ;

        b_golds += arr[2];
        System.out.println(arr[2]);
        b_gold_t.setText(Integer.toString(b_golds));
        Update();
    }

    public void player_c(){
        c_golds -= 15;
        // b_golds += manhattan(first_b,second_b,5);
        //manhattan_c(c1,c2);
        int[] arr = new int[3];
        //manhattan(b,n,4);
        //System.out.println(arr[2]);
        arr = manhattan_c(c1, c2);
        map[c1][c2].setType(3);
        map[arr[0]][arr[1]].setType(6);
        c1 = arr[0];
        c2 = arr[1];

        c_golds += arr[2];
        c_gold_t.setText(Integer.toString(c_golds));
        Update();
    }

    public void player_d(){
        d_golds -= 20;
        // d_golds += manhattan(first_b,second_b,5);
        int[] arr = new int[3];
        //manhattan(b,n,4);
        //System.out.println(arr[2]);
        arr = manhattan(d1, d2);
        map[d1][d2].setType(3);
        map[arr[0]][arr[1]].setType(7);
        d1 = arr[0] ;
        d2 = arr[1] ;

        d_golds += arr[2];
        d_gold_t.setText(Integer.toString(d_golds));
        Update();
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

        for(int i=0; i<cells; i++) {
            for (int j=0; j<cells; j++) {
                if(map[i][j].getType()==2){
                    visible_golds[i][j] = r.nextInt(20/5)*5 + 5;
                }
                //else if(map[i][j].getType()==1)

                //visible_golds[i][j] = r.nextInt(20/5)*5 + 5;
                //System.out.println(visible_golds[i][j]);
                else
                    visible_golds[i][j] =0;
            }
            //System.out.println();
        }
        unvisible_golds();
        player_placement();
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

        for(int i=0; i < cells; i++) {
            for (int j=0; j < cells; j++) {
                if(map[i][j].getType() == 1){
                    visible_golds[i][j] = r.nextInt(20/5)*5 + 5;
                }
            }
        }
        control.clear();
    }

    public void player_placement(){
        map[0][0].setType(4); // A
        map[cells-1][0].setType(5); // B
        map[cells-1][cells-1].setType(6); // C
        map[0][cells-1].setType(7); // D
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

        a_gold_t.setBounds(100,270, 100, 25);
        toolP.add(a_gold_t);

        b_gold_t.setBounds(100,300, 100, 25);
        toolP.add(b_gold_t);

        c_gold_t.setBounds(100,330, 100, 25);
        toolP.add(c_gold_t);

        d_gold_t.setBounds(100,360, 100, 25);
        toolP.add(d_gold_t);
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

        a_gold.setBounds(10,270,90,25);
        toolP.add(a_gold);
        b_gold.setBounds(10,300,90,25);
        toolP.add(b_gold);
        c_gold.setBounds(10,330,90,25);
        toolP.add(c_gold);
        d_gold.setBounds(10,360,90,25);
        toolP.add(d_gold);
        space+=buff;

        frame.getContentPane().add(toolP);

        canvas = new Map();
        canvas.setBounds(230, 10, MSIZE+1, MSIZE+1);
        frame.getContentPane().add(canvas);

        // ACTION LISTENERS
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(a_golds > -1 || b_golds > -1 || c_golds > -1 || d_golds > -1){
                    player_a();
                    player_b();
                    player_c();
                    player_d();
                }
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

        get_golds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                get_golds();
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
                            //random = r.nextInt(20/5)*5 + 5;
                            random = visible_golds[x][y] ;
                            //random_control.add(random);
                            break;
                        case 2: // Gold Placement
                            g.setColor(Color.ORANGE);
                            //random = r.nextInt(20/5)*5 + 5;
                            random = visible_golds[x][y] ;
                            //random_control.add(random);
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
                        case 8:
                            if(map[x][y].getType()==1){
                                g.setColor(Color.RED);
                                g.drawString(Integer.toString(random), x*CSIZE, y*CSIZE + 10);}

                            break;
                        case 9:
                            g.setColor(Color.WHITE);
                            break;
                    }
                    g.fillRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
                    g.setColor(Color.BLUE);
                    g.drawRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);

                    if (map[x][y].getType() == 9)
                    {
                        g.setColor(Color.RED);
                        g.drawString(Integer.toString(random), x*CSIZE, y*CSIZE + 10);
                    }

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