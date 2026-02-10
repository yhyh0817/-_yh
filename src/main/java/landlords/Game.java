package landlords;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.*;
import java.util.Timer;

public class Game extends JFrame implements MouseListener {
    public static Container container = null;
    static ArrayList<poke> all = new ArrayList<>();
    static final String[] color = {"♠", "♥", "♣", "♦"};
    static final String[] num = {"2", "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3"};
    static ArrayList<poke> player1 = new ArrayList<>();
    static ArrayList<poke> player2 = new ArrayList<>();
    static ArrayList<poke> player3 = new ArrayList<>();
    static ArrayList<poke> put1 = new ArrayList<>();
    static ArrayList<poke> put2 = new ArrayList<>();
    static ArrayList<poke> put3 = new ArrayList<>();
    static ArrayList<poke> hole = new ArrayList<>();
    static ArrayList<poke> last = new ArrayList<>();
    static int lastMan = 3;
    static JButton jb1 = new JButton("出牌");
    static JButton jb2 = new JButton("不要");
    static JButton jb3 = new JButton("提示");
    static JButton qiangButton = new JButton("抢地主");
    static JButton buqiangButton = new JButton("不抢");
    static int landlord = 0; // 1表示玩家是地主，2表示电脑2是地主，3表示电脑3是地主
    static JLabel p2 = new JLabel("Pass");
    static JLabel p3 = new JLabel("Pass");
    static JLabel p1 = new JLabel("Pass");
    static JLabel win = new JLabel("Victory");
    static JLabel lose = new JLabel("Defeat");
    
    // 分数相关变量
    static int playerScore = 0;
    static int computer1Score = 0; // 电脑2的分数
    static int computer2Score = 0; // 电脑3的分数
    static int scoreMultiplier = 1; // 分数倍数，初始值为1
    static final String SCORE_FILE = "score.json"; // 分数文件路径
    
    // 分数显示标签
    static JLabel playerScoreLabel = new JLabel("玩家分数: 0");
    static JLabel computer1ScoreLabel = new JLabel("电脑1分数: 0");
    static JLabel computer2ScoreLabel = new JLabel("电脑2分数: 0");
    static JLabel multiplierLabel = new JLabel("倍数: 1");
    static {
        for (String c : color) {
            for (String n : num) {
                poke po = new poke(c, n);
                all.add(po);
            }
        }
        all.add(new poke("大王"));
        all.add(new poke("小王"));
    }
    public Game() {
        loadScores(); // 加载分数
        InitGame();
        InitButton();
        InitJFrame();
        this.setVisible(true);
    }
    public void InitGame() {
        this.setTitle("人机斗地主");
        setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon();
        this.setIconImage(icon.getImage());
        container = this.getContentPane();
        container.setLayout(null);
        container.setBackground(Color.LIGHT_GRAY);
        Collections.shuffle(all);
        for(int i = 0; i < all.size() - 3; i++) {
            all.get(i).v.addMouseListener(this);
            if((i + 3) % 3 == 0) {
                player1.add(all.get(i));
            } else if ((i + 3) % 3 == 1) {
                player2.add(all.get(i));
            } else {
                player3.add(all.get(i));
            }
        }
        hole.add(all.get(all.size() - 3));
        hole.add(all.get(all.size() - 2));
        hole.add(all.getLast());
        System.out.println(player1);
        pokerSort(player1);
        pokerSort(player2);
        pokerSort(player3);
        System.out.println(player1);
        System.out.println(player2);
        System.out.println(player3);
        
        // 显示抢地主按钮，隐藏其他按钮
        qiangButton.setVisible(true);
        buqiangButton.setVisible(true);
        jb1.setVisible(false);
        jb2.setVisible(false);
        jb3.setVisible(false);
        
        System.out.println(player1);
    }

    public void pokerSort(ArrayList<poke> player) {
        player.sort(new Comparator<poke>() {
            @Override
            public int compare(poke o1, poke o2) {
                if (o1.num.equals("大王")) {
                    return -1;
                } else if (o2.num.equals("大王")) {
                    return 1;
                } else if (o1.num.equals("小王")) {
                    return -1;
                } else if (o2.num.equals("小王")) {
                    return 1;
                }
                if(o1.index == o2.index) {
                    if(o1.color.charAt(0) > o2.color.charAt(0)) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else {
                    return o1.index - o2.index;
                }
            }
        });
    }

    public void pokerAddHole(ArrayList<poke> player) {
        player.addAll(hole);
        pokerSort(player);
    }
    // 初始化按钮
    public void InitButton() {
        jb1.setBackground(Color.black);
        jb1.setForeground(Color.white);
        container.add(jb1);
        jb1.setBounds(490, 440, 60, 25);
        jb1.addMouseListener(this);
        jb2.setBackground(Color.black);
        jb2.setForeground(Color.white);
        jb2.addMouseListener(this);
        container.add(jb2);
        jb2.setBounds(350, 440, 60, 25);
        jb3.setBackground(Color.black);
        jb3.setForeground(Color.white);
        container.add(jb3);
        jb3.setBounds(420, 440, 60, 25);
        jb3.addMouseListener(this);
        
        // 初始化抢地主按钮
        qiangButton.setBackground(Color.black);
        qiangButton.setForeground(Color.white);
        container.add(qiangButton);
        qiangButton.setBounds(380, 400, 80, 25);
        qiangButton.addMouseListener(this);
        
        // 初始化不抢按钮
        buqiangButton.setBackground(Color.black);
        buqiangButton.setForeground(Color.white);
        container.add(buqiangButton);
        buqiangButton.setBounds(480, 400, 80, 25);
        buqiangButton.addMouseListener(this);
        
        container.repaint();
    }
    // 初始化游戏
    public void InitJFrame() {
        InitHead();
        InitScoreLabels(); // 初始化分数显示标签
        InitPoker();
    }
    
    // 初始化分数显示标签
    public void InitScoreLabels() {
        // 设置分数标签的样式
        playerScoreLabel.setFont(new Font("宋体", Font.BOLD, 14));
        computer1ScoreLabel.setFont(new Font("宋体", Font.BOLD, 14));
        computer2ScoreLabel.setFont(new Font("宋体", Font.BOLD, 14));
        multiplierLabel.setFont(new Font("宋体", Font.BOLD, 14));
        
        // 设置分数标签的位置
        playerScoreLabel.setBounds(400, 20, 120, 20);
        computer1ScoreLabel.setBounds(700, 20, 120, 20);
        computer2ScoreLabel.setBounds(100, 20, 120, 20);
        multiplierLabel.setBounds(450, 60, 100, 20);
        
        // 添加分数标签到容器
        container.add(playerScoreLabel);
        container.add(computer1ScoreLabel);
        container.add(computer2ScoreLabel);
        container.add(multiplierLabel);
        
        // 更新分数显示
        updateScoreLabels();
    }
    // 出牌操作
    public void play() {
        p1.setVisible(false);
        if(lastMan == 1) {
            System.out.println("1");
            last.clear();
        }
        if(last.isEmpty()) {
            for (int i = 0; i < put1.size(); i++) {
                put1.get(i).v.setVisible(false);
            }
            put1.clear();
            for (int i = 0; i < player1.size(); ) {
                if(player1.get(i).IsChoose) {
                    put1.add(player1.get(i));
                    player1.remove(i);

                } else {
                    i++;
                }
            }
            if(tool.findMay(put1) == tool.may.bug) {
                System.out.println("bug");
                player1.addAll(put1);
                for (int i = 0; i < player1.size(); i++) {
                    player1.get(i).IsChoose = false;
                }
                put1.clear();
                pokerSort(player1);
                InitPoker();
            } else {
                // 检测是否是炸弹
                if(tool.findMay(put1) == tool.may.aaaa) {
                    scoreMultiplier *= 2;
                    updateScoreLabels();
                }
                lastMan = 1;
                last.clear();
                last.addAll(put1);
                InitPoker();
                if(player1.isEmpty()) {
                    calculateScores(true); // 玩家胜利
                    saveScores(); // 保存分数
                    win.setVisible(true);
                    return;
                }
                play2();
            }

        } else {
            for (int i = 0; i < put1.size(); i++) {
                put1.get(i).v.setVisible(false);
            }
            put1.clear();
            for (int i = 0; i < player1.size(); ) {
                if(player1.get(i).IsChoose) {
                    put1.add(player1.get(i));
                    player1.remove(i);
                } else {
                    i++;
                }
            }
            if(tool.findMay(put1) == tool.may.bug || !tool.compare(put1, last)) {
                System.out.println("bug");
                player1.addAll(put1);
                for (int i = 0; i < player1.size(); i++) {
                    player1.get(i).IsChoose = false;
                }
                put1.clear();
                pokerSort(player1);
                InitPoker();
            } else {
                // 检测是否是炸弹
                if(tool.findMay(put1) == tool.may.aaaa) {
                    scoreMultiplier *= 2;
                    updateScoreLabels();
                }
                lastMan = 1;
                last.clear();
                last.addAll(put1);
                InitPoker();
                if(player1.isEmpty()) {
                    calculateScores(true); // 玩家胜利
                    saveScores(); // 保存分数
                    win.setVisible(true);
                    return;
                }
                play2();
            }
        }
    }
    public void play2() {
        p2.setVisible(false);
        if(lastMan == 2) {
            System.out.println("2");
            last.clear();
        }
        System.out.println("代码运行");

        if(last.isEmpty()) {
            int ppp = 1;
            for (int i = player2.size() - 1; i >= 0; i--) {

                player2.get(i).IsChoose = true;
                if(ppp == 3) {
                    System.out.println("三带一");
                    i--;
                }
                player2.get(i).IsChoose = true;
                if(i <= 0 || player2.get(i - 1).big != player2.get(i).big) {
                    break;
                }
                ppp++;
            }
            for (int i = 0; i < put2.size(); i++) {
                put2.get(i).v.setVisible(false);
            }
            put2.clear();
            for (int i = 0; i < player2.size(); ) {
                if(player2.get(i).IsChoose) {
                    put2.add(player2.get(i));
                    player2.remove(i);
                } else {
                    i++;
                }
            }
            if(tool.findMay(put2) == tool.may.bug) {
                System.out.println("出问题了");
                System.out.println("bug");
                player2.addAll(put2);
                for (int i = 0; i < player2.size(); i++) {
                    player2.get(i).IsChoose = false;
                }
                put2.clear();
                pokerSort(player2);
                InitPoker();
                play3();
            } else {
                // 检测是否是炸弹
                if(tool.findMay(put2) == tool.may.aaaa) {
                    scoreMultiplier *= 2;
                    updateScoreLabels();
                }
                lastMan = 2;
                last.clear();
                last.addAll(put2);
                InitPoker();
                if(player2.isEmpty()) {
                    // 检查是否是农民胜利
                    if(landlord != 2 && landlord != 1) {
                        // 电脑2是农民，玩家也是农民，玩家胜利
                        calculateScores(true); // 玩家胜利
                        saveScores(); // 保存分数
                        win.setVisible(true);
                        return;
                    } else {
                        calculateScores(false); // 玩家失败
                        saveScores(); // 保存分数
                        lose.setVisible(true);
                        return;
                    }
                }
                play3();
            }
        } else {
            if(!tool.findPoke(last, player2)) {
                p2.setVisible(true);
                for (int i = 0; i < put2.size(); i++) {
                    put2.get(i).v.setVisible(false);
                }
                put2.clear();
                InitPoker();
                play3();
                return;
            }
            for (int i = 0; i < put2.size(); i++) {
                put2.get(i).v.setVisible(false);
            }
            put2.clear();
            for (int i = 0; i < player2.size(); ) {
                if(player2.get(i).IsChoose) {
                    put2.add(player2.get(i));
                    player2.remove(i);
                } else {
                    i++;
                }
            }
            if(tool.findMay(put2) == tool.may.bug || !tool.compare(put2, last)) {
                System.out.println("bug");
                player2.addAll(put2);
                for (int i = 0; i < player2.size(); i++) {
                    player2.get(i).IsChoose = false;
                }
                put2.clear();
                pokerSort(player2);
                InitPoker();
                play3();
            } else {
                // 检测是否是炸弹
                if(tool.findMay(put2) == tool.may.aaaa) {
                    scoreMultiplier *= 2;
                    updateScoreLabels();
                }
                lastMan = 2;
                last.clear();
                last.addAll(put2);
                pokerSort(player2);
                InitPoker();
                if(player2.isEmpty()) {
                    // 检查是否是农民胜利
                    if(landlord != 2 && landlord != 1) {
                        // 电脑2是农民，玩家也是农民，玩家胜利
                        calculateScores(true); // 玩家胜利
                        saveScores(); // 保存分数
                        win.setVisible(true);
                        return;
                    } else {
                        calculateScores(false); // 玩家失败
                        saveScores(); // 保存分数
                        lose.setVisible(true);
                        return;
                    }
                }
                play3();
            }

        }
    }
    public void play3() {
        p3.setVisible(false);
        if(lastMan == 3) {
            System.out.println("3");
            last.clear();
        }
        System.out.println("代码运行");

        if(last.isEmpty()) {
            int ppp = 1;
            for (int i = player3.size() - 1; i >= 0; i--) {

                player3.get(i).IsChoose = true;
                if(ppp == 3) {
                    i--;
                }
                player3.get(i).IsChoose = true;
                if(i == 0 || player3.get(i - 1).big != player3.get(i).big) {
                    break;
                }
                ppp++;
            }
            for (int i = 0; i < put3.size(); i++) {
                put3.get(i).v.setVisible(false);
            }
            put3.clear();
            for (int i = 0; i < player3.size(); ) {
                if(player3.get(i).IsChoose) {
                    put3.add(player3.get(i));
                    player3.remove(i);
                } else {
                    i++;
                }
            }
            if(tool.findMay(put3) == tool.may.bug) {
                System.out.println("bug");
                player3.addAll(put3);
                for (int i = 0; i < player3.size(); i++) {
                    player3.get(i).IsChoose = false;
                }
                put3.clear();
                pokerSort(player3);
                InitPoker();
            } else {
                // 检测是否是炸弹
                if(tool.findMay(put3) == tool.may.aaaa) {
                    scoreMultiplier *= 2;
                    updateScoreLabels();
                }
                lastMan = 3;
                last.clear();
                last.addAll(put3);
                InitPoker();
                if(player3.isEmpty()) {
                    // 检查是否是农民胜利
                    if(landlord != 3 && landlord != 1) {
                        // 电脑3是农民，玩家也是农民，玩家胜利
                        calculateScores(true); // 玩家胜利
                        saveScores(); // 保存分数
                        win.setVisible(true);
                        return;
                    } else {
                        calculateScores(false); // 玩家失败
                        saveScores(); // 保存分数
                        lose.setVisible(true);
                        return;
                    }
                }
            }

        } else {
            if(lastMan == 2 && last.getFirst().big > 10 && last.getLast().big > 10) {
                System.out.println("队友");
                p3.setVisible(true);
                for (int i = 0; i < put3.size(); i++) {
                    put3.get(i).v.setVisible(false);
                }
                put3.clear();
                InitPoker();
                return;
            }
            if(!tool.findPoke(last, player3)) {
                p3.setVisible(true);
                for (int i = 0; i < put3.size(); i++) {
                    put3.get(i).v.setVisible(false);
                }
                put3.clear();
                InitPoker();
                return;
            }
            for (int i = 0; i < put3.size(); i++) {
                put3.get(i).v.setVisible(false);
            }
            put3.clear();
            for (int i = 0; i < player3.size(); ) {
                if(player3.get(i).IsChoose) {
                    put3.add(player3.get(i));
                    player3.remove(i);
                } else {
                    i++;
                }
            }
            if(tool.findMay(put3) == tool.may.bug || !tool.compare(put3, last)) {
                System.out.println("bug");
                player3.addAll(put3);
                for (int i = 0; i < player3.size(); i++) {
                    player3.get(i).IsChoose = false;
                }
                put3.clear();
                pokerSort(player3);
                InitPoker();
            } else {
                // 检测是否是炸弹
                if(tool.findMay(put3) == tool.may.aaaa) {
                    scoreMultiplier *= 2;
                    updateScoreLabels();
                }
                lastMan = 3;
                last.clear();
                last.addAll(put3);
                pokerSort(player3);
                InitPoker();
                if(player3.isEmpty()) {
                    // 检查是否是农民胜利
                    if(landlord != 3 && landlord != 1) {
                        // 电脑3是农民，玩家也是农民，玩家胜利
                        calculateScores(true); // 玩家胜利
                        saveScores(); // 保存分数
                        win.setVisible(true);
                        return;
                    } else {
                        calculateScores(false); // 玩家失败
                        saveScores(); // 保存分数
                        lose.setVisible(true);
                        return;
                    }
                }
            }
        }
    }
    // 初始化头像
    public void InitHead() {
        container.add(p2);
        container.add(p1);
        container.add(p3);
        container.add(win);
        container.add(lose);
        p2.setLayout(null);
        p3.setLayout(null);
        p1.setLayout(null);
        win.setLayout(null);
        lose.setLayout(null);
        p2.setFont(new Font(p2.getFont().getName(), p2.getFont().getStyle(), 40));
        p1.setFont(new Font(p1.getFont().getName(), p1.getFont().getStyle(), 40));
        p3.setFont(new Font(p3.getFont().getName(), p3.getFont().getStyle(), 40));
        win.setFont(new Font(win.getFont().getName(), win.getFont().getStyle(), 200));
        lose.setFont(new Font(lose.getFont().getName(), lose.getFont().getStyle(), 200));
        p2.setBounds(600, 120, 100, 100);
        p1.setBounds(405, 360, 100, 100);
        p3.setBounds(250, 120, 100, 100);
        win.setBounds(140, 0, 700, 700);
        lose.setBounds(140, 0, 700, 700);
        p2.setVisible(false);
        p1.setVisible(false);
        p3.setVisible(false);
        win.setVisible(false);
        lose.setVisible(false);
        container.repaint();
    }
    // 初始化扑克牌
    public void InitPoker() {
        // 清除现有的地主图标
        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component.getName() != null && component.getName().equals("dizhuIcon")) {
                container.remove(component);
            }
        }
        
        for (int i = player1.size() - 1; i >= 0; i--) {
//            280 + 15 * i   500
            player1.get(i).x = 280 + 17 * i;
            player1.get(i).y = 500;
            player1.get(i).front = true;
            player1.get(i).CanChoose = true;
            player1.get(i).refresh();
            Game.container.add(player1.get(i).v);
        }
        for (int i = player2.size() - 1; i >= 0; i--) {
            player2.get(i).x = 750;
            player2.get(i).y = 100 + 15 * i;
            player2.get(i).front = false;
            player2.get(i).refresh();
            Game.container.add(player2.get(i).v);
        }
        for (int i = player3.size() - 1; i >= 0; i--) {
            player3.get(i).x = 150;
            player3.get(i).y = 100 + 15 * i;
            player3.get(i).front = false;
            player3.get(i).refresh();
            Game.container.add(player3.get(i).v);
        }
        for (int i = put1.size() - 1; i >= 0; i--) {
            put1.get(i).x = 400 + 15 * i;
            put1.get(i).y = 300;
            put1.get(i).front = true;
            put1.get(i).refresh();
            Game.container.add(put1.get(i).v);
        }
        for (int i = put2.size() - 1; i >= 0; i--) {
            put2.get(i).x = 580 + 15 * i;
            put2.get(i).y = 100;
            put2.get(i).front = true;
            put2.get(i).refresh();
            Game.container.add(put2.get(i).v);
        }
        for (int i = put3.size() - 1; i >= 0; i--) {
            put3.get(i).x = 250 + 15 * i;
            put3.get(i).y = 100;
            put3.get(i).front = true;
            put3.get(i).refresh();
            Game.container.add(put3.get(i).v);
        }
        
        // 根据地主身份显示地主图标
        if (landlord > 0) {
            ImageIcon dizhuIcon = new ImageIcon(getClass().getResource("/poker/dizhu.png"));
            JLabel dizhuLabel = new JLabel(dizhuIcon);
            dizhuLabel.setName("dizhuIcon");
            dizhuLabel.setBounds(0, 0, 40, 40);
            
            if (landlord == 1) {
                // 玩家是地主，显示在玩家牌的左侧下方
                dizhuLabel.setBounds(450, 600, 40, 40);
            } else if (landlord == 2) {
                // 电脑2是地主，显示在电脑2牌的上方
                dizhuLabel.setBounds(770, 50, 40, 40);
            } else if (landlord == 3) {
                // 电脑3是地主，显示在电脑3牌的上方
                dizhuLabel.setBounds(170, 50, 40, 40);
            }
            
            container.add(dizhuLabel);
        }
        
        container.repaint();
    }

    @Override
    // 鼠标点击
    public void mouseClicked(MouseEvent e) {}

    @Override
    // 鼠标按下
    public void mousePressed(MouseEvent e) {}

    @Override
    // 鼠标抬起
    public void mouseReleased(MouseEvent e) {
        Object obj = e.getSource();
        for (int i = 0; i < player1.size(); i++) {
            if(obj == player1.get(i).v) {
                if(player1.get(i).CanChoose) {
                    if(player1.get(i).IsChoose) {
                        player1.get(i).IsChoose = false;
//                        System.out.println("点击");
                    } else {
                        player1.get(i).IsChoose = true;
//                        System.out.println("点击");
                    }
                    InitPoker();
                }
                break;
            }
        }
        // jb1:出牌，jb2:不要，jb3:提示，qiangButton:抢地主，buqiangButton:不抢
        if(obj == jb1) {
            play();
//            System.out.println("出牌");
        } else if (obj == jb2) {
            p1.setVisible(true);
            for (int i = 0; i < put1.size(); i++) {
                put1.get(i).v.setVisible(false);
            }
            put1.clear();
            InitPoker();
            play2();
        } else if (obj == jb3) {
            System.out.println(lastMan);
            if(lastMan == 1) {
                last.clear();
            }
            if(last.isEmpty()) {
                int ppp = 1;
                for (int i = player1.size() - 1; i >= 0; i--) {
                    player1.get(i).IsChoose = true;
                    if(ppp == 3) {
                        System.out.println("三带一");
                        i--;
                    }
                    player1.get(i).IsChoose = true;
                    if(i <= 0 || player1.get(i - 1).big != player1.get(i).big) {
                        break;
                    }
                    ppp++;
                }
            } else {
                tool.findPoke(last, player1);
            }
            InitPoker();
        } else if (obj == qiangButton) {
            // 玩家抢地主，获得三张底牌
            for (int i = 0; i < 3; i++) {
                hole.get(i).CanChoose = true;
                hole.get(i).v.addMouseListener(this);
            }
            pokerAddHole(player1);
            System.out.println("你成为了地主！");
            landlord = 1;
            
            // 隐藏抢地主按钮，显示其他按钮
            qiangButton.setVisible(false);
            buqiangButton.setVisible(false);
            jb1.setVisible(true);
            jb2.setVisible(true);
            jb3.setVisible(true);
            
            // 地主先出牌（玩家是地主）
            InitPoker();
        } else if (obj == buqiangButton) {
            // 玩家不抢，随机选择另外两个电脑玩家中的一个当地主
            Random random = new Random();
            int chooseLandlord = random.nextInt(2); // 0表示player2，1表示player3
            if(chooseLandlord == 0) {
                pokerAddHole(player2);
                System.out.println("电脑玩家2成为了地主！");
                landlord = 2;
                
                // 隐藏抢地主按钮，显示其他按钮
                qiangButton.setVisible(false);
                buqiangButton.setVisible(false);
                jb1.setVisible(true);
                jb2.setVisible(true);
                jb3.setVisible(true);
                
                // 地主先出牌（电脑2是地主）
                InitPoker();
                play2();
            } else {
                pokerAddHole(player3);
                System.out.println("电脑玩家3成为了地主！");
                landlord = 3;
                
                // 隐藏抢地主按钮，显示其他按钮
                qiangButton.setVisible(false);
                buqiangButton.setVisible(false);
                jb1.setVisible(true);
                jb2.setVisible(true);
                jb3.setVisible(true);
                
                // 地主先出牌（电脑3是地主）
                InitPoker();
                play3();
            }
        }
    }

    @Override
    // 鼠标滑入
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    // 鼠标滑出
    public void mouseExited(MouseEvent e) {
    }
    
    // 从score.json文件读取分数
    public static void loadScores() {
        File file = new File(SCORE_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder jsonContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
                
                // 解析JSON字符串
                String json = jsonContent.toString();
                playerScore = extractScore(json, "player");
                computer1Score = extractScore(json, "computer1");
                computer2Score = extractScore(json, "computer2");
            } catch (Exception e) {
                System.err.println("读取分数文件失败: " + e.getMessage());
                // 读取失败时初始化为0分
                initializeScores();
            }
        } else {
            // 文件不存在时初始化为0分
            initializeScores();
        }
        
        // 更新分数显示
        updateScoreLabels();
    }
    
    // 从JSON字符串中提取分数
    private static int extractScore(String json, String key) {
        try {
            int startIndex = json.indexOf("\"" + key + "\":") + key.length() + 3;
            int endIndex = json.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = json.indexOf("}", startIndex);
            }
            String scoreStr = json.substring(startIndex, endIndex).trim();
            return Integer.parseInt(scoreStr);
        } catch (Exception e) {
            return 0;
        }
    }
    
    // 初始化分数为0
    public static void initializeScores() {
        playerScore = 0;
        computer1Score = 0;
        computer2Score = 0;
    }
    
    // 将分数保存到score.json文件
    public static void saveScores() {
        String json = "{" +
            "\"player\": " + playerScore + ", " +
            "\"computer1\": " + computer1Score + ", " +
            "\"computer2\": " + computer2Score + 
        "}";
        
        try (FileWriter writer = new FileWriter(SCORE_FILE)) {
            writer.write(json);
        } catch (Exception e) {
            System.err.println("保存分数文件失败: " + e.getMessage());
        }
    }
    
    // 更新分数显示标签
    public static void updateScoreLabels() {
        playerScoreLabel.setText("玩家分数: " + playerScore);
        computer1ScoreLabel.setText("电脑1分数: " + computer1Score);
        computer2ScoreLabel.setText("电脑2分数: " + computer2Score);
        multiplierLabel.setText("倍数: " + scoreMultiplier);
    }
    
    // 计算游戏结束时的分数变动
    public static void calculateScores(boolean playerWin) {
        int baseScore = 0;
        
        if(playerWin) {
            if(landlord == 1) {
                // 玩家是地主，胜利
                baseScore = 4 * scoreMultiplier;
                playerScore += baseScore;
                computer1Score -= baseScore / 2;
                computer2Score -= baseScore / 2;
            } else {
                // 玩家是农民，胜利
                baseScore = 2 * scoreMultiplier;
                playerScore += baseScore;
                if(landlord == 2) {
                    computer1Score -= baseScore * 2;
                    computer2Score += baseScore;
                } else if(landlord == 3) {
                    computer1Score += baseScore;
                    computer2Score -= baseScore * 2;
                }
            }
        } else {
            if(landlord == 1) {
                // 玩家是地主，失败
                baseScore = 4 * scoreMultiplier;
                playerScore -= baseScore;
                computer1Score += baseScore / 2;
                computer2Score += baseScore / 2;
            } else {
                // 玩家是农民，失败
                baseScore = 2 * scoreMultiplier;
                playerScore -= baseScore;
                if(landlord == 2) {
                    computer1Score += baseScore * 2;
                    computer2Score -= baseScore;
                } else if(landlord == 3) {
                    computer1Score -= baseScore;
                    computer2Score += baseScore * 2;
                }
            }
        }
        
        // 重置倍数
        scoreMultiplier = 1;
        // 更新分数显示
        updateScoreLabels();
    }
}
