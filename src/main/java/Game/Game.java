package Game;

import java.util.*;

public class Game {
    static ArrayList<String> all = new ArrayList<>();
    static final String[] color = {"♠", "♥", "♣", "♦"};;
    static final String[] num = {"2", "A", "K", "Q", "j", "10", "9", "8", "7", "6", "5", "4", "3"};;
    static ArrayList<String> player1 = new ArrayList<>();
    static ArrayList<String> player2 = new ArrayList<>();
    static ArrayList<String> player3 = new ArrayList<>();
    static ArrayList<String> hole = new ArrayList<>();
    static {
        for (String c : color) {
            for (String n : num) {
                all.add(c + n);
            }
        }
        all.add("大王");
        all.add("小王");
    }
    public Game() {
        InitGame();
    }
    public void InitGame() {
        Collections.shuffle(all);
//        System.out.println(all);
        for(int i = 0; i < all.size() - 3; i++) {
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
        System.out.println("抢地主：1 不抢：0");
        Scanner sc = new Scanner(System.in);
        int choose = sc.nextInt();
        if(choose == 1) {
            pokerAddHole(player1);
        } else if(choose == 0) {
            pokerAddHole(player2);
        }
        System.out.println(player1);
    }
    public int FindIndex(String str) {
        String str2 = str.substring(1);
        for (int i = 0; i < num.length; i++) {
            if(str2.equals(num[i])) {
                return i;
            }
        }
        return -1;
    }
    public void pokerSort(ArrayList<String> player) {
        player.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.equals("大王")) {
                    return -1;
                } else if (o2.equals("大王")) {
                    return 1;
                } else if (o1.equals("小王")) {
                    return -1;
                } else if (o2.equals("小王")) {
                    return 1;
                }
                if(FindIndex(o1) == FindIndex(o2)) {
                    if(o1.charAt(0) > o2.charAt(0)) {
                        return -1;
                    } else {
                        return 1;
                    }
                } else {
                    return FindIndex(o1) - FindIndex(o2);
                }
            }
        });
    }
    public void pokerAddHole(ArrayList<String> player) {
        player.addAll(hole);
        pokerSort(player);
    }

}
