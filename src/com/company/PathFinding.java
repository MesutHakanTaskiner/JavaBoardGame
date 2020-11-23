import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.util.*;
import java.util.Timer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class PathFinding{

    //Frame
    JFrame frame;
    JFrame frame2;

    //General Variables
    public static int value = 500;
    private int cells = 20;
    public int cells2 = 0;
    public final int WIDTH = 850;
    public final int HEIGHT = 650;
    public final int MSIZE = 600;
    public int CSIZE = MSIZE/ getCells();
    public  int [] gold_x = new int[value];
    public  int [] gold_y = new int[value];
    public boolean control = true, timer_control = true;
    public int golds = 200;
    public static int a_golds = 200;
    public static int b_golds = 200;
    public static int c_golds = 200;
    public static int d_golds = 200;
    public int a1 = 0, a2 = 0; // Player a current coordinate
    public int b1 = getCells() -1, b2 = 0;  // Player b current coordinate
    public int c1 = getCells() -1, c2 = getCells() -1; // Player c current coordinate
    public int d1 = 0, d2 = getCells() -1; // Player d current coordinate
    public int number_of_steps_a = 0, number_of_steps_b = 0, number_of_steps_c = 0, number_of_steps_d = 0;
    public ArrayList<Integer> random_control = new ArrayList<>();
    public int[][] visible_golds = new int[value][value];
    int a_collected = 0, b_collected = 0, c_collected = 0, d_collected = 0;
    int a_spent = 0, b_spent = 0, c_spent = 0, d_spent = 0;
    public String game_winner;
    public int total = 0;
    public int golds_a = -10, golds_b = -15, golds_c = -20, golds_d = -25;
    public Timer myTimer = new Timer();
    public TimerTask task;

    //Util
    Node[][] map;
    Random r = new Random();

    //Slider Size
    JSlider size = new JSlider(1,5,2);

    //Labels initialize
    JLabel sizeL = new JLabel("Size:");
    JLabel cellsL = new JLabel(getCells() + "x" + getCells());
    JLabel a_gold = new JLabel( "A current gold" );
    JLabel b_gold = new JLabel( "B current gold" );
    JLabel c_gold = new JLabel( "C current gold" );
    JLabel d_gold = new JLabel( "D current gold" );
    JLabel a_gold_t = new JLabel(Integer.toString(a_golds));
    JLabel b_gold_t = new JLabel(Integer.toString(b_golds));
    JLabel c_gold_t = new JLabel(Integer.toString(c_golds));
    JLabel d_gold_t = new JLabel(Integer.toString(d_golds));

    //Labels Initialize2
    JLabel a_steps = new JLabel( "A Steps Number" );
    JLabel b_steps = new JLabel( "B Steps Number" );
    JLabel c_steps = new JLabel( "C Steps Number" );
    JLabel d_steps = new JLabel( "D Steps Number" );
    JLabel a_steps_t = new JLabel(Integer.toString(number_of_steps_a));
    JLabel b_steps_t = new JLabel(Integer.toString(number_of_steps_b));
    JLabel c_steps_t = new JLabel(Integer.toString(number_of_steps_c));
    JLabel d_steps_t = new JLabel(Integer.toString(number_of_steps_d));

    JLabel a_gold_last = new JLabel( "A current gold" );
    JLabel b_gold_last = new JLabel( "B current gold" );
    JLabel c_gold_last = new JLabel( "C current gold" );
    JLabel d_gold_last = new JLabel( "D current gold" );
    JLabel a_gold_last_t = new JLabel( Integer.toString(a_golds));
    JLabel b_gold_last_t = new JLabel( Integer.toString(b_golds));
    JLabel c_gold_last_t = new JLabel( Integer.toString(c_golds));
    JLabel d_gold_last_t = new JLabel( Integer.toString(d_golds));

    JLabel a_collected2 = new JLabel( "A collected gold" );
    JLabel b_collected2 = new JLabel( "B collected gold" );
    JLabel c_collected2 = new JLabel( "C collected gold" );
    JLabel d_collected2 = new JLabel( "D collected gold" );
    JLabel a_collected1 = new JLabel( Integer.toString(a_collected));
    JLabel b_collected1 = new JLabel( Integer.toString(b_collected));
    JLabel c_collected1 = new JLabel( Integer.toString(c_collected));
    JLabel d_collected1 = new JLabel( Integer.toString(d_collected));

    JLabel a_spent2 = new JLabel( "A Spent gold" );
    JLabel b_spent2 = new JLabel( "B Spent gold" );
    JLabel c_spent2 = new JLabel( "C Spent gold" );
    JLabel d_spent2 = new JLabel( "D Spent gold" );
    JLabel a_spent1 = new JLabel( Integer.toString(a_spent));
    JLabel b_spent1 = new JLabel( Integer.toString(b_spent));
    JLabel c_spent1 = new JLabel( Integer.toString(c_spent));
    JLabel d_spent1 = new JLabel( Integer.toString(d_spent));

    JLabel Information = new JLabel("Goal Setting And Movement Cost");

    JLabel required_gold_a2 = new JLabel("A");
    JLabel required_gold_b2 = new JLabel("B");
    JLabel required_gold_c2 = new JLabel("C");
    JLabel required_gold_d2 = new JLabel("D");

    JLabel winner = new JLabel("Winner : ");
    JLabel winner2 = new JLabel();

    //Text Fields
    JTextField players_gold = new JTextField(Integer.toString(golds));

    JTextField required_gold_a = new JTextField(Integer.toString(golds_a));
    JTextField required_gold_b = new JTextField(Integer.toString(golds_b));
    JTextField required_gold_c = new JTextField(Integer.toString(golds_c));
    JTextField required_gold_d = new JTextField(Integer.toString(golds_d));

    //Buttons
    JButton start = new JButton("Start Game");
    JButton golds_players_placement = new JButton("Placement");
    JButton finish_game = new JButton("Finish Game");
    JButton get_golds = new JButton("Get Golds");

    //Panels
    JPanel toolP = new JPanel();
    JPanel toolP2 = new JPanel();

    //Canvas
    Map canvas;

    //Border
    Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);

    // Main Method
    public static void main(String[] args) {
        new PathFinding();
    }

    // Constructor
    public PathFinding() {
        clearMap();
        initialize();
    }

    public void start_game(){
        setCells(size.getValue()*10);
        cells2 = getCells();
        b1 = (cells2-1); b2 = 0;
        c1 = (cells2-1); c2 = (cells2-1);
        d1 = 0; d2 = (cells2-1);

        total = ((cells2*cells2)/5);

        File file_a = new File("Coordinates_A.txt");

        if(file_a.exists()){
            file_a.delete();
        }

        File file_b = new File("Coordinates_B.txt");

        if(file_b.exists()){
            file_b.delete();
        }

        File file_c = new File("Coordinates_C.txt");

        if(file_c.exists()){
            file_c.delete();
        }

        File file_d = new File("Coordinates_D.txt");

        if(file_d.exists()){
            file_d.delete();
        }

        task = new TimerTask(){
            @Override
            public void run() {

                if (a_golds > 0){
                    try {
                        Thread.sleep(600);
                        player_a();
                        total--;
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (b_golds > 0){
                    try {
                        Thread.sleep(600);
                        player_b();
                        total--;
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (c_golds > 0){
                    try {
                        Thread.sleep(600);
                        player_c();
                        total--;
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (d_golds > 0){
                    try {
                        Thread.sleep(600);
                        player_d();
                        total--;
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if(total < 1 || (a_golds <= 0 && b_golds <= 0 && c_golds <= 0 && d_golds <= 0)) {
                    finish_game.setEnabled(false);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    a_steps_t.setText(Integer.toString(number_of_steps_a));
                    b_steps_t.setText(Integer.toString(number_of_steps_b));
                    c_steps_t.setText(Integer.toString(number_of_steps_c));
                    d_steps_t.setText(Integer.toString(number_of_steps_d));

                    if(a_golds < 0)
                        a_gold_last_t.setText(Integer.toString(0));
                    else
                        a_gold_last_t.setText(Integer.toString(a_golds));

                    if(b_golds < 0)
                        b_gold_last_t.setText(Integer.toString(0));
                    else
                        b_gold_last_t.setText(Integer.toString(b_golds));

                    if(c_golds < 0)
                        c_gold_last_t.setText(Integer.toString(0));
                    else
                        c_gold_last_t.setText(Integer.toString(c_golds));

                    if(d_golds < 0)
                        d_gold_last_t.setText(Integer.toString(0));
                    else
                        d_gold_last_t.setText(Integer.toString(d_golds));


                    a_collected1.setText(Integer.toString(a_collected));
                    b_collected1.setText(Integer.toString(b_collected));
                    c_collected1.setText(Integer.toString(c_collected));
                    d_collected1.setText(Integer.toString(d_collected));

                    a_spent1.setText(Integer.toString(-a_spent));
                    b_spent1.setText(Integer.toString(-b_spent));
                    c_spent1.setText(Integer.toString(-c_spent));
                    d_spent1.setText(Integer.toString(-d_spent));

                    if((a_golds > b_golds) && (a_golds > c_golds) && (a_golds > d_golds))
                        game_winner = "Player A Won!";
                    else if((b_golds > a_golds) && (b_golds > c_golds) && (b_golds > d_golds))
                        game_winner = "Player B Won!";
                    else if((c_golds > b_golds) && (c_golds > a_golds) && (c_golds > d_golds))
                        game_winner = "Player C Won!";
                    else if((d_golds > b_golds) && (d_golds > c_golds) && (d_golds > a_golds))
                        game_winner = "Player D Won!";
                    else if((a_golds == b_golds) && (a_golds > c_golds) && (a_golds > d_golds))
                        game_winner = "Player A - B Won!";
                    else if((a_golds == c_golds) && (a_golds > b_golds) && (a_golds > d_golds))
                        game_winner = "Player A - C Won!";
                    else if((a_golds == d_golds) && (a_golds > b_golds) && (a_golds > c_golds))
                        game_winner = "Player A - D Won!";
                    else if((b_golds == c_golds) && (b_golds > a_golds) && (b_golds > d_golds))
                        game_winner = "Player B - C Won!";
                    else if((b_golds == d_golds) && (b_golds > a_golds) && (b_golds > c_golds))
                        game_winner = "Player B - D Won!";
                    else if((c_golds == d_golds) && (c_golds > a_golds) && (c_golds > b_golds))
                        game_winner = "Player C - D Won!";

                    winner2.setText(game_winner);

                    initialize2();
                    myTimer.cancel();
                }
            }
        };
        myTimer.schedule(task,1,1000);
    }

    public void finish_the_game(){
        myTimer.cancel();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a_steps_t.setText(Integer.toString(number_of_steps_a));
        b_steps_t.setText(Integer.toString(number_of_steps_b));
        c_steps_t.setText(Integer.toString(number_of_steps_c));
        d_steps_t.setText(Integer.toString(number_of_steps_d));

        if(a_golds < 0)
            a_gold_last_t.setText(Integer.toString(0));
        else
            a_gold_last_t.setText(Integer.toString(a_golds));

        if(b_golds < 0)
            b_gold_last_t.setText(Integer.toString(0));
        else
            b_gold_last_t.setText(Integer.toString(b_golds));

        if(c_golds < 0)
            c_gold_last_t.setText(Integer.toString(0));
        else
            c_gold_last_t.setText(Integer.toString(c_golds));

        if(d_golds < 0)
            d_gold_last_t.setText(Integer.toString(0));
        else
            d_gold_last_t.setText(Integer.toString(d_golds));


        a_collected1.setText(Integer.toString(a_collected));
        b_collected1.setText(Integer.toString(b_collected));
        c_collected1.setText(Integer.toString(c_collected));
        d_collected1.setText(Integer.toString(d_collected));

        a_spent1.setText(Integer.toString(-a_spent));
        b_spent1.setText(Integer.toString(-b_spent));
        c_spent1.setText(Integer.toString(-c_spent));
        d_spent1.setText(Integer.toString(-d_spent));

        if((a_golds > b_golds) && (a_golds > c_golds) && (a_golds > d_golds))
            game_winner = "Player A Won!";
        else if((b_golds > a_golds) && (b_golds > c_golds) && (b_golds > d_golds))
            game_winner = "Player B Won!";
        else if((c_golds > b_golds) && (c_golds > a_golds) && (c_golds > d_golds))
            game_winner = "Player C Won!";
        else if((d_golds > b_golds) && (d_golds > c_golds) && (d_golds > a_golds))
            game_winner = "Player D Won!";
        else if((a_golds == b_golds) && (a_golds > c_golds) && (a_golds > d_golds))
            game_winner = "Player A - B Won!";
        else if((a_golds == c_golds) && (a_golds > b_golds) && (a_golds > d_golds))
            game_winner = "Player A - C Won!";
        else if((a_golds == d_golds) && (a_golds > b_golds) && (a_golds > c_golds))
            game_winner = "Player A - D Won!";
        else if((b_golds == c_golds) && (b_golds > a_golds) && (b_golds > d_golds))
            game_winner = "Player B - C Won!";
        else if((b_golds == d_golds) && (b_golds > a_golds) && (b_golds > c_golds))
            game_winner = "Player B - D Won!";
        else if((c_golds == d_golds) && (c_golds > a_golds) && (c_golds > b_golds))
            game_winner = "Player C - D Won!";

        winner2.setText(game_winner);

        initialize2();

    }

    public void get_golds(){
        String text = players_gold.getText();

        String text_a = required_gold_a.getText();
        String text_b = required_gold_b.getText();
        String text_c = required_gold_c.getText();
        String text_d = required_gold_d.getText();

        golds = Integer.parseInt(text);

        a_golds = Integer.parseInt(text);
        b_golds = Integer.parseInt(text);
        c_golds = Integer.parseInt(text);
        d_golds = Integer.parseInt(text);

        a_gold_t.setText(Integer.toString(a_golds));
        b_gold_t.setText(Integer.toString(b_golds));
        c_gold_t.setText(Integer.toString(c_golds));
        d_gold_t.setText(Integer.toString(d_golds));

        golds_a = Integer.parseInt(text_a);
        golds_b = Integer.parseInt(text_b);
        golds_c = Integer.parseInt(text_c);
        golds_d = Integer.parseInt(text_d);
    }

    public int[] manhattan_a(int b, int n){
        int distance = 0;
        int temp = getCells() -1;
        int temp_x = 0, temp_y = 0;
        int[] arr = new int[4];

        for (int x = 0; x < getCells(); x++) {
            for (int y = 0; y < getCells(); y++) {
                if (map[x][y].getType() == 2 || map[x][y].getType() == 9) {
                    distance = Math.abs(map[b][n].x - map[x][y].x) + Math.abs(map[b][n].y - map[x][y].y);

                    if(distance < temp){
                        temp = distance;
                        temp_x = map[x][y].x;
                        temp_y = map[x][y].y;
                        arr[3] = distance;
                    }
                }
            }
        }

        arr[0] = map[temp_x][temp_y].x;
        arr[1] = map[temp_x][temp_y].y;
        arr[2] = visible_golds[temp_x][temp_y];

        return arr;
    }

    public int[] manhattan_b(int b, int n){
        int distance = 0;
        int cost = 2;
        int profit = 0;
        int temp = getCells() - 1;
        int temp_x = 0, temp_y = 0;
        int[] arr = new int[4];

        ArrayList<Integer> gain = new ArrayList<Integer>();
        gain.add(-10000);

        for (int x = 0; x < getCells(); x++) {
            for (int y = 0; y < getCells(); y++) {
                if (map[x][y].getType() == 2 || map[x][y].getType() == 9) {
                    distance = Math.abs(map[b][n].x - map[x][y].x) + Math.abs(map[b][n].y - map[x][y].y);
                    temp = golds_b * (distance/3);
                    profit = visible_golds[x][y] + temp;

                    if(profit > Collections.max(gain)){
                        gain.add(profit);
                        temp_x = map[x][y].x;
                        temp_y = map[x][y].y;
                        arr[3] = distance;
                    }
                }
            }
        }

        arr[0] = map[temp_x][temp_y].x;
        arr[1] = map[temp_x][temp_y].y;
        arr[2] = visible_golds[temp_x][temp_y];

        gain.clear();
        return arr;
    }

    public int[] manhattan_c(int b, int n){
        int distance = 0;
        int temp = getCells() -1;
        int temp_x = 0, temp_y = 0;
        int u_distance = 0;
        int u_temp = getCells() -1;
        int u_temp_x = 0, u_temp_y = 0;

        int[] arr = new int[4];

        for (int x = 0; x < getCells(); x++) {
            for (int y = 0; y < getCells(); y++) {
                if (map[x][y].getType() == 2 || map[x][y].getType() == 9) {
                    distance = Math.abs(map[b][n].x - map[x][y].x) + Math.abs(map[b][n].y - map[x][y].y);

                    if(distance < temp){
                        temp = distance;
                        temp_x = map[x][y].x;
                        temp_y = map[x][y].y;
                        arr[3] = distance;
                    }
                }

                if (map[x][y].getType() == 1) {
                    u_distance = Math.abs(map[b][n].x - map[x][y].x) + Math.abs(map[b][n].y - map[x][y].y);

                    if(u_distance < u_temp){
                        u_temp = u_distance;
                        u_temp_x = map[x][y].x;
                        u_temp_y = map[x][y].y;
                    }
                }
            }
        }
        if(map[u_temp_x][u_temp_y] != map[0][0]){
            map[u_temp_x][u_temp_y].setType(9);
        }
        arr[0] = map[temp_x][temp_y].x;
        arr[1] = map[temp_x][temp_y].y;
        arr[2] = visible_golds[temp_x][temp_y];

        return arr;
    }

    public void player_a() throws IOException {
        File file = new File("Coordinates_A.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        int[] arr = new int[4] ;
        arr = manhattan_a(a1, a2);

        if(arr[0] > 0 || arr[1] > 0){
            map[a1][a2].setType(3);
            map[arr[0]][arr[1]].setType(4);
        }

        if(arr[3]%3 == 0){
            a_golds += (golds_a * arr[3]/3);
            a_spent += (golds_a * arr[3]/3);
        }
        else{
            a_golds += (golds_a * arr[3]/3) + golds_a;
            a_spent += (golds_a * arr[3]/3) + golds_a;
        }

        a1 = arr[0];
        a2 = arr[1];

        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(Integer.toString(arr[0]) + " ");
        bw.write(Integer.toString(arr[1]));
        bw.newLine();
        bw.close();

        a_golds += arr[2];
        a_collected += arr[2];

        if(a_golds > 0)
            a_gold_t.setText(Integer.toString(a_golds));
        else
            a_gold_t.setText(Integer.toString(0));

        number_of_steps_a += arr[3];
        Update();
    }

    public void player_b() throws IOException {
        File file = new File("Coordinates_B.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        int[] arr;

        arr = manhattan_b(b1, b2);

        if(arr[0] > 0 || arr[1] > 0){
            map[b1][b2].setType(3);
            map[arr[0]][arr[1]].setType(5);
        }

        if(arr[3]%3 == 0){
            b_golds += (golds_b * arr[3]/3);
            b_spent += (golds_b * arr[3]/3);
        }
        else{
            b_golds += (golds_b * arr[3]/3) + golds_b;
            b_spent += (golds_b * arr[3]/3) + golds_b;
        }

        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(Integer.toString(arr[0]) + " ");
        bw.write(Integer.toString(arr[1]));
        bw.newLine();
        bw.close();

        b1 = arr[0];
        b2 = arr[1];

        b_golds += arr[2];
        b_collected += arr[2];

        if(b_golds > 0)
            b_gold_t.setText(Integer.toString(b_golds));
        else
            b_gold_t.setText(Integer.toString(0));

        number_of_steps_b += arr[3];
        Update();
    }

    public void player_c() throws IOException {
        File file = new File("Coordinates_C.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        int[] arr = new int[3];

        arr = manhattan_c(c1, c2);

        if(arr[0] > 0 || arr[1] > 0){
            map[c1][c2].setType(3);
            map[arr[0]][arr[1]].setType(6);
        }

        if(arr[3]%3 == 0){
            c_golds += (golds_c * arr[3]/3);
            c_spent += (golds_c * arr[3]/3);
        }
        else{
            c_golds += (golds_c * arr[3]/3) + golds_c;
            c_spent += (golds_c * arr[3]/3) + golds_c;
        }

        c1 = arr[0];
        c2 = arr[1];

        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(Integer.toString(arr[0]) + " ");
        bw.write(Integer.toString(arr[1]));
        bw.newLine();
        bw.close();

        c_golds += arr[2];
        c_collected += arr[2];

        if(c_golds > 0)
            c_gold_t.setText(Integer.toString(c_golds));
        else
            c_gold_t.setText(Integer.toString(0));

        number_of_steps_c += arr[3];
        Update();
    }

    public void player_d() throws IOException {
        File file = new File("Coordinates_D.txt");

        if(!file.exists()){
            file.createNewFile();
        }

        int[] arr = new int[3];
        arr = manhattan_b(d1, d2);

        if(arr[0] > 0 || arr[1] > 0){
            map[d1][d2].setType(3);
            map[arr[0]][arr[1]].setType(7);
        }

        if(arr[3]%3 == 0){
            d_golds += (golds_d * arr[3]/3);
            d_spent += (golds_d * arr[3]/3);
        }
        else{
            d_golds += (golds_d * arr[3]/3) + golds_d;
            d_spent += (golds_d * arr[3]/3) + golds_d;
        }

        d1 = arr[0] ;
        d2 = arr[1] ;

        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(Integer.toString(arr[0]) + " ");
        bw.write(Integer.toString(arr[1]));
        bw.newLine();
        bw.close();

        d_golds += arr[2];
        d_collected += arr[2];

        if(d_golds > 0)
            d_gold_t.setText(Integer.toString(d_golds));
        else
            d_gold_t.setText(Integer.toString(0));

        number_of_steps_d += arr[3];
        Update();
    }

    // Random gold placement
    public void visible_golds() {
        clearMap();	// Create Clear Map To Start

        start.setEnabled(true);

        for(int i = 0; i < (getCells() * getCells())/5; i++) {
            int x = 0;
            int y = 0;
            do {
                x = r.nextInt(getCells());
                y = r.nextInt(getCells());

            } while(map[x][y].getType() == 2 || ((x == 0 && y == 0) || (x == (getCells() - 1) && y == 0) || (x == 0 && y == (getCells() - 1)) || (x == (getCells() - 1) && y == (getCells() - 1))));

            map[x][y].setType(2);

            gold_x[i] = map[x][y].x;
            gold_y[i] = map[x][y].y;

            visible_golds[x][y] = r.nextInt(20/5)*5 + 5;
        }

        unvisible_golds();
        player_placement();
    }

    public void unvisible_golds() {
        int Random = 0;
        ArrayList<Integer> control = new ArrayList<Integer>();

        for(int i = 0; i < (getCells() * getCells())/50; i++)
        {
            while (control.contains(Random)){
                Random = r.nextInt(getCells());
            }
            if(control.contains(Random)){
            }
            else {
                map[gold_x[Random]][gold_y[Random]].setType(1);
                visible_golds[gold_x[Random]][gold_y[Random]] = r.nextInt(20/5)*5 + 5;
            }
            control.add(Random);
            Random = 0;
        }
        control.clear();
    }

    public void player_placement(){
        map[0][0].setType(4); // A
        map[getCells() -1][0].setType(5); // B
        map[getCells() -1][getCells() -1].setType(6); // C
        map[0][getCells() -1].setType(7); // D
    }

    // Clear Map
    public void clearMap() {
        control = true;
        map = new Node[getCells()][getCells()];	// Create New Map Of Nodes
        for(int x = 0; x < getCells(); x++) {
            for(int y = 0; y < getCells(); y++) {
                map[x][y] = new Node(3, x, y);	//Set All Nodes To Empty
            }
        }
    }

    // Update Elements Of The Gui
    public void Update() {
        CSIZE = MSIZE/ getCells();
        canvas.repaint();
        cellsL.setText(getCells() + "x" + getCells());
    }

    private void initialize2() {
        frame.setVisible(false);
        frame2 = new JFrame();
        frame2.setVisible(true);
        frame2.setResizable(false);
        frame2.setSize(WIDTH, HEIGHT);
        frame2.setTitle("Results");
        frame2.setLocationRelativeTo(null);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.getContentPane().setLayout(null);

        toolP2.setBorder(BorderFactory.createTitledBorder(loweredetched,"Summary Table"));
        toolP2.setLayout(null);
        toolP2.setBounds(10,10,800,600);

        a_steps.setBounds(10,10,150,75);
        toolP2.add(a_steps);
        b_steps.setBounds(10,25,150,75);
        toolP2.add(b_steps);
        c_steps.setBounds(10,40,150,75);
        toolP2.add(c_steps);
        d_steps.setBounds(10,55,150,75);
        toolP2.add(d_steps);

        a_steps_t.setBounds(150,10, 100, 70);
        toolP2.add(a_steps_t);
        b_steps_t.setBounds(150,25, 100, 70);
        toolP2.add(b_steps_t);
        c_steps_t.setBounds(150,40, 100, 70);
        toolP2.add(c_steps_t);
        d_steps_t.setBounds(150,55, 100, 75);
        toolP2.add(d_steps_t);

        a_gold_last.setBounds(10,100, 100, 70);
        toolP2.add(a_gold_last);
        b_gold_last.setBounds(10,115, 100, 70);
        toolP2.add(b_gold_last);
        c_gold_last.setBounds(10,130, 100, 70);
        toolP2.add(c_gold_last);
        d_gold_last.setBounds(10,145, 100, 70);
        toolP2.add(d_gold_last);

        a_gold_last_t.setBounds(150,100, 100, 70);
        toolP2.add(a_gold_last_t);
        b_gold_last_t.setBounds(150,115, 100, 70);
        toolP2.add(b_gold_last_t);
        c_gold_last_t.setBounds(150,130, 100, 70);
        toolP2.add(c_gold_last_t);
        d_gold_last_t.setBounds(150,145, 100, 75);
        toolP2.add(d_gold_last_t);

        a_collected2.setBounds(10,190, 100, 70);
        toolP2.add(a_collected2);
        b_collected2.setBounds(10,205, 100, 70);
        toolP2.add(b_collected2);
        c_collected2.setBounds(10,220, 100, 70);
        toolP2.add(c_collected2);
        d_collected2.setBounds(10,235, 100, 70);
        toolP2.add(d_collected2);

        a_collected1.setBounds(150,190, 100, 70);
        toolP2.add(a_collected1);
        b_collected1.setBounds(150,205, 100, 70);
        toolP2.add(b_collected1);
        c_collected1.setBounds(150,220, 100, 70);
        toolP2.add(c_collected1);
        d_collected1.setBounds(150,235, 100, 70);
        toolP2.add(d_collected1);

        a_spent2.setBounds(10,280, 100, 70);
        toolP2.add(a_spent2);
        b_spent2.setBounds(10,295, 100, 70);
        toolP2.add(b_spent2);
        c_spent2.setBounds(10,310, 100, 70);
        toolP2.add(c_spent2);
        d_spent2.setBounds(10,325, 100, 70);
        toolP2.add(d_spent2);

        a_spent1.setBounds(150,280, 100, 70);
        toolP2.add(a_spent1);
        b_spent1.setBounds(150,295, 100, 70);
        toolP2.add(b_spent1);
        c_spent1.setBounds(150,310, 100, 70);
        toolP2.add(c_spent1);
        d_spent1.setBounds(150,325, 100, 70);
        toolP2.add(d_spent1);

        winner.setBounds(400,150, 100, 200);
        toolP2.add(winner);
        winner2.setBounds(450,150, 120, 200);
        toolP2.add(winner2);

        frame2.getContentPane().add(toolP2);
    }

    // Initialize The Gui Elements
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
        toolP.setBounds(10,10,210,600);

        start.setBounds(40, space, 120, 25);
        start.setEnabled(false);
        toolP.add(start);
        space += buff;

        golds_players_placement.setBounds(40, space, 120, 25);
        toolP.add(golds_players_placement);
        space += buff;

        finish_game.setBounds(40, space, 120, 25);
        toolP.add(finish_game);
        space += 40;

        players_gold.setToolTipText("Default 200 Gold");
        players_gold.setBounds(40, space, 120, 25);
        toolP.add(players_gold);
        space += buff;

        Information.setBounds(8, 190,500,25);
        toolP.add(Information);

        required_gold_a2.setBounds(62, 210,50,25);
        toolP.add(required_gold_a2);
        required_gold_a.setBounds(45, 230,50,25);
        toolP.add(required_gold_a);

        required_gold_b2.setBounds(127, 210,50,25);
        toolP.add(required_gold_b2);
        required_gold_b.setBounds(110, 230,50,25);
        toolP.add(required_gold_b);

        required_gold_c2.setBounds(62, 260,50,25);
        toolP.add(required_gold_c2);
        required_gold_c.setBounds(45, 280,50,25);
        toolP.add(required_gold_c);

        required_gold_d2.setBounds(127, 260,50,25);
        toolP.add(required_gold_d2);
        required_gold_d.setBounds(110, 280,50,25);
        toolP.add(required_gold_d);

        get_golds.setBounds(40, 310, 120, 25);
        toolP.add(get_golds);

        sizeL.setBounds(15, 340,40,25);
        toolP.add(sizeL);
        size.setMajorTickSpacing(10);
        size.setBounds(50, 340,100,25);
        toolP.add(size);
        cellsL.setBounds(160, 340,40,25);
        toolP.add(cellsL);

        a_gold.setBounds(10,380,90,25);
        toolP.add(a_gold);
        b_gold.setBounds(10,410,90,25);
        toolP.add(b_gold);
        c_gold.setBounds(10,440,90,25);
        toolP.add(c_gold);
        d_gold.setBounds(10,470,90,25);
        toolP.add(d_gold);

        a_gold_t.setBounds(100,380, 100, 25);
        toolP.add(a_gold_t);
        b_gold_t.setBounds(100,410, 100, 25);
        toolP.add(b_gold_t);
        c_gold_t.setBounds(100,440, 100, 25);
        toolP.add(c_gold_t);
        d_gold_t.setBounds(100,470, 100, 25);
        toolP.add(d_gold_t);

        frame.getContentPane().add(toolP);

        canvas = new Map();
        canvas.setBounds(230, 10, MSIZE+1, MSIZE+1);
        frame.getContentPane().add(canvas);

        // Action listener
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start_game();
            }
        });

        golds_players_placement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                visible_golds();
                Update();
            }
        });

        finish_game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finish_the_game();
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
                setCells(size.getValue()*10);
                clearMap();
                Update();
            }
        });
    }

    public int getCells() {
        return cells;
    }

    public void setCells(int cells) {
        this.cells = cells;
    }

    // Map Class
    class Map extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);  // Repaint
            for (int x = 0; x < getCells(); x++) {    // Paint Each Node In The Grid
                for (int y = 0; y < getCells(); y++) {
                    switch(map[x][y].getType()) {
                        case 1: // unvisible Gold Placement
                            g.setColor(Color.WHITE);
                            break;
                        case 2: // Gold Placement
                            g.setColor(Color.ORANGE);
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
                        g.drawString(Integer.toString(visible_golds[x][y]), x*CSIZE, y*CSIZE + 10);
                    }

                    if (map[x][y].getType() == 2) {
                        g.setColor(Color.BLACK);
                        g.drawString(Integer.toString(visible_golds[x][y]), x * CSIZE, y * CSIZE + 10);
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
        public int cellType = 0;
        public int x;
        public int y;

        // Constructor
        public Node(int type, int x, int y) {
            cellType = type;
            this.x = x;
            this.y = y;
        }

        //Get Methods
        public int getType() {return cellType;}

        //Set Methods
        public void setType(int type) {cellType = type;}
    }
}