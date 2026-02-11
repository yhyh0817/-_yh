# 斗地主游戏项目

## 项目简介
这是一个基于Java Swing开发的人机斗地主游戏，支持玩家与电脑AI进行斗地主对战，本来还有很多功能想要完善的，无奈屎山代码太多，程序已经无法再扩展了[悲]。
我一直希望我的游戏在玩完第一局之后可以继续开第二局的，可惜屎山太多，写了半天bug越写越多，最终放弃了......

**图片素材参考哔哩哔哩黑马程序员公开课程**

**此README由豆包AI辅助完成**
## 目录结构

```
Landlords/
├── src/
│   └── main/
│       ├── java/
│       │   └── landlords/
│       │       ├── Game.java        # 游戏主类，包含界面和核心逻辑
│       │       ├── poke.java        # 扑克牌类，定义牌的属性和行为
│       │       ├── tool.java        # 工具类，包含牌型判断等功能
│       │       └── test.java        # 游戏入口类
│       └── resources/
│           └── poker/               # 扑克牌图片资源
├── README.md                        # 项目说明文档
└── score.json                       # 分数保存文件
```

## 系统架构

### 核心类说明

| 类名 | 主要职责 | 文件路径 |
|------|---------|----------|
| Game | 游戏主逻辑、界面管理、玩家交互 | src/main/java/landlords/Game.java |
| poke | 扑克牌实体类，定义牌的属性和方法 | src/main/java/landlords/poke.java |
| tool | 工具类，提供牌型判断、比较等功能 | src/main/java/landlords/tool.java |
| test | 游戏入口，启动游戏 | src/main/java/landlords/test.java |

### 游戏流程

1. **初始化游戏**：创建牌组、洗牌、发牌
2. **抢地主阶段**：玩家选择是否抢地主，若不抢则随机选择电脑为地主
3. **出牌阶段**：按顺时针顺序出牌，玩家可选择出牌或不出
4. **游戏结束**：某一方出完所有牌后游戏结束，计算分数并保存

## 核心功能详解

### 1. 扑克牌类

#### poke.java

**属性说明**：
- `sx`：牌面数字数组，按大小顺序排列
- `pa`：牌面对应的权值数组
- `index`：牌在数组中的索引
- `img`：牌的图片
- `v`：牌的 JLabel 组件
- `front`：是否显示牌的正面
- `CanChoose`：是否可以选择
- `IsChoose`：是否被选中
- `x, y`：牌的坐标
- `color`：牌的花色
- `num`：牌的数字
- `name`：牌的名称
- `state`：牌的状态（正面/反面）
- `big`：牌的大小权值

**方法说明**：
- `poke()`：无参构造函数
- `poke(String name)`：创建大小王的构造函数
- `poke(String color, String num)`：创建普通牌的构造函数
- `refresh()`：刷新牌的显示状态
- `contactImage(String name)`：根据牌名生成图片路径
- `FindIndex()`：查找牌在数组中的索引
- `toString()`：返回牌的字符串表示

### 2. 游戏核心逻辑

#### Game.java

**属性说明**：
- `container`：游戏容器
- `all`：所有牌的集合
- `color`：花色数组
- `num`：牌面数字数组
- `player1`：玩家1的牌
- `player2`：电脑2的牌
- `player3`：电脑3的牌
- `put1`：玩家1打出的牌
- `put2`：电脑2打出的牌
- `put3`：电脑3打出的牌
- `hole`：底牌
- `last`：上一次打出的牌
- `lastMan`：上一次出牌的玩家
- `jb1, jb2, jb3`：出牌、不要、提示按钮
- `qiangButton, buqiangButton`：抢地主、不抢按钮
- `landlord`：地主标识
- `p1, p2, p3`：玩家pass标签
- `win, lose`：胜负标签
- `playerScore, computer1Score, computer2Score`：分数
- `scoreMultiplier`：分数倍数
- `SCORE_FILE`：分数文件路径
- `playerScoreLabel, computer1ScoreLabel, computer2ScoreLabel, multiplierLabel`：分数显示标签

**方法说明**：
- `Game()`：构造函数，初始化游戏
- `InitGame()`：初始化游戏设置
- `pokerSort(ArrayList<poke> player)`：对牌进行排序
- `pokerAddHole(ArrayList<poke> player)`：给地主添加底牌
- `InitButton()`：初始化按钮
- `InitJFrame()`：初始化窗口
- `InitScoreLabels()`：初始化分数标签
- `play()`：玩家出牌逻辑
- `play2()`：电脑2出牌逻辑
- `play3()`：电脑3出牌逻辑
- `InitHead()`：初始化头像
- `InitPoker()`：初始化扑克牌显示
- `mouseReleased(MouseEvent e)`：鼠标释放事件处理
- `loadScores()`：加载分数
- `extractScore(String json, String key)`：从JSON中提取分数
- `initializeScores()`：初始化分数
- `saveScores()`：保存分数
- `updateScoreLabels()`：更新分数标签
- `calculateScores(boolean playerWin)`：计算分数

### 3. 工具类

#### tool.java

**枚举说明**：
- `may`：牌型枚举，包括单牌、对子、三张、三带一、三带二、炸弹、顺子、三对、飞机、飞机带单、飞机带对、王炸、无效牌型

**方法说明**：
- `findMay(ArrayList<poke> put)`：判断牌型
- `compare(ArrayList<poke> put, ArrayList<poke> com)`：比较牌的大小
- `findPoke(ArrayList<poke> put, ArrayList<poke> com)`：查找可出的牌

## 游戏规则

### 基本规则
1. **牌型**：单牌、对子、三张、三带一、三带二、炸弹、顺子、三对、飞机、飞机带单、飞机带对、王炸
2. **出牌顺序**：地主先出牌，然后按顺时针顺序出牌
3. **胜负判定**：先出完所有牌的一方获胜

### 分数规则
1. **基础分**：地主获胜得4分，农民获胜得2分
2. **倍数**：每出一个炸弹，分数倍数翻倍
3. **分数计算**：
   - 地主获胜：地主得4×倍数分，两个农民各扣2×倍数分
   - 农民获胜：两个农民各得2×倍数分，地主扣4×倍数分

## 代码详解

### 1. 扑克牌类 (poke.java)

**核心属性**：
- `big`：牌的大小权值，大王(15) > 小王(14) > 2(13) > A(12) > K(11) > Q(10) > J(9) > 10(8) > 9(7) > 8(6) > 7(5) > 6(4) > 5(3) > 4(2) > 3(1)
- `IsChoose`：标记牌是否被选中
- `front`：标记牌是否显示正面

**关键方法**：
- `refresh()`：根据牌的状态刷新显示
- `contactImage()`：根据牌的花色和数字生成对应的图片路径

### 2. 游戏主类 (Game.java)

**核心逻辑**：

#### 初始化游戏
```java
public void InitGame() {
    this.setTitle("人机斗地主");
    setSize(1000, 750);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    // 初始化容器和背景
    container = this.getContentPane();
    container.setLayout(null);
    container.setBackground(Color.LIGHT_GRAY);
    // 洗牌
    Collections.shuffle(all);
    // 发牌
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
    // 底牌
    hole.add(all.get(all.size() - 3));
    hole.add(all.get(all.size() - 2));
    hole.add(all.getLast());
    // 排序
    pokerSort(player1);
    pokerSort(player2);
    pokerSort(player3);
    // 显示抢地主按钮
    qiangButton.setVisible(true);
    buqiangButton.setVisible(true);
    jb1.setVisible(false);
    jb2.setVisible(false);
    jb3.setVisible(false);
}
```

#### 玩家出牌
```java
public void play() {
    p1.setVisible(false);
    if(lastMan == 1) {
        last.clear();
    }
    if(last.isEmpty()) {
        // 清空之前的出牌
        for (int i = 0; i < put1.size(); i++) {
            put1.get(i).v.setVisible(false);
        }
        put1.clear();
        // 收集选中的牌
        for (int i = 0; i < player1.size(); ) {
            if(player1.get(i).IsChoose) {
                put1.add(player1.get(i));
                player1.remove(i);
            } else {
                i++;
            }
        }
        // 检查牌型是否合法
        if(tool.findMay(put1) == tool.may.bug) {
            // 牌型不合法，将牌放回
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
            // 检查是否获胜
            if(player1.isEmpty()) {
                calculateScores(true); // 玩家胜利
                saveScores(); // 保存分数
                win.setVisible(true);
                return;
            }
            play2(); // 电脑2出牌
        }
    } else {
        // 有上家出牌，需要比较大小
        // 逻辑类似，省略...
    }
}
```

#### 电脑AI出牌
```java
public void play2() {
    p2.setVisible(false);
    if(lastMan == 2) {
        last.clear();
    }
    if(last.isEmpty()) {
        // 电脑自主出牌，优先出三张
        int ppp = 1;
        for (int i = player2.size() - 1; i >= 0; i--) {
            player2.get(i).IsChoose = true;
            if(ppp == 3) {
                i--;
            }
            player2.get(i).IsChoose = true;
            if(i <= 0 || player2.get(i - 1).big != player2.get(i).big) {
                break;
            }
            ppp++;
        }
        // 收集选中的牌
        // 逻辑类似，省略...
    } else {
        // 有上家出牌，尝试找到能压过的牌
        if(!tool.findPoke(last, player2)) {
            p2.setVisible(true);
            // 无法压过，选择pass
            // 逻辑类似，省略...
        }
        // 收集选中的牌
        // 逻辑类似，省略...
    }
}
```

### 3. 工具类 (tool.java)

#### 牌型判断
```java
public static may findMay(ArrayList<poke> put) {
    int len = put.size();
    if(len == 0) {
        return may.bug;
    } else if (len == 1) {
        return may.a;
    } else if (len == 2) {
        if(put.get(0).big == 15 && put.get(1).big == 14) {
            return may.ww; // 王炸
        }
        if(put.get(0).big != put.get(1).big) {
            return may.bug;
        }
        return may.aa; // 对子
    } else if (len == 3) {
        if(put.get(0).big != put.get(1).big || put.get(1).big != put.get(2).big) {
            return may.bug;
        }
        return may.aaa; // 三张
    }
    // 其他牌型判断，省略...
}
```

#### 牌型比较
```java
public static boolean compare(ArrayList<poke> put, ArrayList<poke> com) {
    may t = findMay(put);
    may m = findMay(com);
    if(m == may.ww) {
        return false; // 王炸最大
    }
    if(t != may.aaaa && t != may.ww) {
        if(t != m) {
            return false; // 牌型不同不能比较
        } else {
            if(t == may.a || t == may.aa || t == may.aaa) {
                return put.getFirst().big > com.getFirst().big;
            } else if (t == may.aaab || t == may.aaabb) {
                return put.get(2).big > com.get(2).big;
            }
            // 其他牌型比较，省略...
        }
    } else {
        if(t == may.ww) {
            return true; // 王炸最大
        } else if(m == may.aaaa) {
            return put.getFirst().big > com.getFirst().big;
        } else {
            return true; // 炸弹大于其他牌型
        }
    }
    return false;
}
```

## 游戏界面

### 界面布局
- **顶部**：分数显示区域，显示玩家和电脑的分数以及当前倍数
- **左侧**：电脑3的牌和状态
- **右侧**：电脑2的牌和状态
- **底部**：玩家的牌和操作按钮
- **中间**：出牌区域

### 操作说明
1. **抢地主阶段**：点击「抢地主」或「不抢」按钮
2. **出牌阶段**：
   - 点击牌面选择要出的牌
   - 点击「出牌」按钮打出选中的牌
   - 点击「不要」按钮跳过本轮
   - 点击「提示」按钮获取出牌建议

## 分数系统

### 分数计算规则
1. **基础分**：
   - 地主胜利：地主得4分，农民各扣2分
   - 农民胜利：农民各得2分，地主扣4分
2. **倍数**：
   - 每出一个炸弹，倍数翻倍
3. **分数保存**：
   - 游戏结束后，分数会自动保存到 `score.json` 文件
   - 下次启动游戏时会加载保存的分数

### 分数文件格式
```json
{
  "player": 10,
  "computer1": -5,
  "computer2": -5
}
```

## 如何运行

### 环境要求
- Java JDK 8或更高版本
- 支持Java Swing的IDE（如IntelliJ IDEA、Eclipse等）

### 运行步骤
1. **克隆或下载项目**到本地
2. **导入项目**到IDE中
3. **运行 test.java** 文件的 `main` 方法
4. **开始游戏**：
   - 抢地主阶段选择是否抢地主
   - 出牌阶段点击牌面选择牌，然后点击「出牌」按钮
   - 游戏结束后会显示胜负结果，并更新分数

## 游戏特色

1. **完整的游戏逻辑**：支持所有斗地主牌型和规则
2. **智能AI**：电脑玩家会根据牌型和局势做出合理的出牌决策
3. **美观的界面**：使用真实的扑克牌图片，界面布局合理
4. **分数系统**：记录玩家和电脑的分数，支持分数保存和加载
5. **炸弹翻倍**：出炸弹时分数倍数会翻倍，增加游戏刺激性

## 注意事项

1. **图片资源**：确保 `poker` 目录下有完整的扑克牌图片资源
2. **分数文件**：游戏会自动创建和更新 `score.json` 文件，请勿手动修改
3. **游戏规则**：遵循标准斗地主规则，炸弹可以压其他牌型，王炸最大
4. **界面大小**：游戏窗口大小为1000x750，建议在分辨率较高的显示器上运行


## 技术栈

- **开发语言**：Java
- **GUI框架**：Swing
- **数据存储**：JSON文件
- **图片处理**：ImageIcon

## 作者
**HUTB_YJJ**

**特别感谢：黑马程序员阿玮老师**
