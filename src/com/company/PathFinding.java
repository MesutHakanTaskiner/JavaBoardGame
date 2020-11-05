package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class PathFinding {

    //FRAME
    JFrame frame;

    //GENERAL VARIABLES
    public static int value = 500;
    private int cells = 20;
    private int startx = -1;
    private int starty = -1;
    private int finishx = -1;
    private int finishy = -1;
    private final int WIDTH = 850;
    private final int HEIGHT = 650;
    private final int MSIZE = 600;
    private int CSIZE = MSIZE/cells;
    public  int [] gold_x = new int[value];
    public  int [] gold_y = new int[value];
    public ArrayList<Integer> random_control = new ArrayList<>();

    //BOOLEANS
    private boolean solving = false;

    //UTIL
    Node[][] map;
    Random r = new Random();

    //Slider Size
    JSlider size = new JSlider(1,5,2);

    //LABELS
    JLabel sizeL = new JLabel("Size:");
    JLabel cellsL = new JLabel(cells + "x" + cells);

    //BUTTONS
    JButton start = new JButton("Start Game");
    JButton golds_players_placement = new JButton("Placement");
    JButton clearMap = new JButton("Clear Map");

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
        map[cells-1][0].setType(4); // B
        map[cells-1][cells-1].setType(4); // C
        map[0][cells-1].setType(4); // D
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
        finishx = 0;	// RESET THE START AND FINISH
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
        toolP.setBounds(10,10,210,200);

        start.setBounds(40,space, 120, 25);
        toolP.add(start);
        space+=buff;

        golds_players_placement.setBounds(40,space, 120, 25);
        toolP.add(golds_players_placement);
        space+=buff;

        clearMap.setBounds(40,space, 120, 25);
        toolP.add(clearMap);
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
            for(int x = 0; x < cells; x++) {	// PAINT EACH NODE IN THE GRID
                for(int y = 0; y < cells; y++) {
                    //System.out.println(map[x][y].getType()); // Manuel Debug
                    switch(map[x][y].getType()) {
                        case 1: // unvisible Gold Placement
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
                    }

                    g.fillRect(x*CSIZE,y*CSIZE, CSIZE, CSIZE);
                    g.setColor(Color.BLUE);
                    g.drawRect(x*CSIZE,y*CSIZE, CSIZE, CSIZE);

                    if (map[x][y].getType() == 2)
                    {
                        g.setColor(Color.BLACK);
                        g.drawString(Integer.toString(random), x*CSIZE, y*CSIZE + 10);
                    }

                    else if(map[x][y].getType() == 4)
                    {
                        g.setColor(Color.GREEN);
                        g.drawString("A", x*CSIZE + 12, y*CSIZE + 20);
                    }

                    /*if (map[x][y].getType() == 1)
                    {
                        g.setColor(Color.RED);
                        g.drawString(Integer.toString(random), x*CSIZE, y*CSIZE + 10);
                    }*/
                }
            }
            /*for (int i = 0; i < random_control.size(); i++)
                System.out.println(random_control.get(i));*/
        }
}

    class Node {

        // 0 = start, 1 = finish, 2 = wall, 3 = empty, 4 = checked, 5 = finalpath
        private int cellType = 0;
        private int hops;
        private int x;
        private int y;
        private int lastX;
        private int lastY;
        private double dToEnd = 0;

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