package landlords;

import javax.swing.*;

public class poke {
    public static final String[] sx = {"2", "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3"};
    public static final int[] pa = {13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    public poke() {}
    public poke(String name) {
        this.num = name;
        this.name = contactImage(name);
        index = FindIndex();
        img = new ImageIcon(getClass().getResource("/poker/" + state));
        System.out.println(img);
        v = new JLabel(img);
        v.setLayout(null);
        v.setBounds(x, y, 71, 96);
        if(num.equals("大王")) {
            big = 15;
        } else if(num.equals("小王")) {
            big = 14;
        }
    }
    public poke(String color, String num) {
        this.color = color;
        this.num = num;
        name = contactImage(color + num);
        index = FindIndex();
        img = new ImageIcon(getClass().getResource("/poker/" + state));
        System.out.println(img);
        v = new JLabel(img);
//        Game.container.add(v);
        v.setLayout(null);
        v.setBounds(x, y, 71, 96);
        if(num.equals("大王")) {
            big = 15;
        } else if(num.equals("小王")) {
            big = 14;
        } else {
            for (int i = 0; i < 13; i++) {
                if(sx[i].equals(num)) {
                    big = pa[i];
                    break;
                }
            }
        }

    }
    public int index;
    ImageIcon img;
    JLabel v;
    public boolean front = false;
    public boolean CanChoose = false;
    public boolean IsChoose = false;
    public int x = 200;
    public int y = 500;
    public String color = "";
    public String num;
    public String name;
    public String state = "rear.png";
    public int big;
    public void refresh() {
        if(front) {
            state = name;
        } else {
            state = "rear.png";
        }
        if(y == 500 && IsChoose) {
            y = 470;
        } else if(y == 470 && !IsChoose) {
            y = 500;
        }
        img = new ImageIcon(getClass().getResource("/poker/" + state));
        v.setIcon(img);
        v.setBounds(x, y, 71, 96);
        Game.container.repaint();
    }
    public String contactImage(String name) {
        if(name.equals("大王")) {
            return "5-2.png";
        } else if (name.equals("小王")) {
            return "5-1.png";
        }
        String answer1 = "";
        String answer2 = "";
        String str2 = name.substring(1);
        answer1 = switch (str2) {
            case "J" -> "11";
            case "Q" -> "12";
            case "K" -> "13";
            case "A" -> "1";
            default -> str2;
        };
        if(name.charAt(0) == '♠') {
            answer2 = "1";
        } else if (name.charAt(0) == '♥') {
            answer2 = "2";
        } else if (name.charAt(0) == '♦') {
            answer2 = "3";
        } else {
            answer2 = "4";
        }
        return answer2 + "-" + answer1 + ".png";
    }
    public int FindIndex() {
        String str2 = this.num;
        for (int i = 0; i < sx.length; i++) {
            if(str2.equals(sx[i])) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public String toString() {
        if(this.color.isEmpty()) {
            return this.num;
        } else {
            return this.color + this.num;
        }
    }
}
