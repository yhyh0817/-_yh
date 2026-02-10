package landlords;

import java.util.ArrayList;

public final class tool {
    private tool() {}
    public enum may {a, aa, aaa, aaab, aaabb, aaaa, abcde, aabbcc, feiji, feiji_dan, feiji_shuang, ww, bug}
    public static may findMay(ArrayList<poke> put) {
        int len = put.size();
        if(len == 0) {
            return may.bug;
        } else if (len == 1) {
            return may.a;
        } else if (len == 2) {
            if(put.get(0).big == 15 && put.get(1).big == 14) {  // num→big
                return may.ww;
            }
            if(put.get(0).big != put.get(1).big) {  // num→big
                return may.bug;
            }
            return may.aa;
        } else if (len == 3) {
            if(put.get(0).big != put.get(1).big || put.get(1).big != put.get(2).big) {  // num→big
                return may.bug;
            }
            return may.aaa;
        } else if (len == 4) {
            if((put.get(0).big == (put.get(1).big) && put.get(1).big == put.get(2).big)  // num→big
                    || (put.get(1).big == (put.get(2).big) && put.get(2).big == (put.get(3).big))) {  // num→big
                if(put.get(0).big != (put.get(3).big)) {  // num→big
                    return may.aaab;
                } else {
                    return may.aaaa;
                }
            } else {
                return may.bug;
            }
        } else if(len == 5) {
            if((put.get(0).big == (put.get(1).big) && put.get(1).big == put.get(2).big // num→big
                    && put.get(3).big == (put.get(4).big)) || (put.get(0).big == put.get(1).big
                    && put.get(2).big == put.get(3).big && put.get(3).big == (put.get(4).big))) {  // num→big
                return may.aaabb;
            }
            for (int i = 1; i < put.size(); i++) {
                if(put.get(i - 1).big != put.get(i).big + 1) {
                    return may.bug;
                }
            }
            return may.abcde;
        } else if (len == 6) {
            boolean ok = true;
            if(put.get(0).big == (put.get(1).big) && put.get(2).big == (put.get(3).big)  // num→big
                    && put.get(4).big == (put.get(5).big)) {  // num→big
                for (int i = 1; i < put.size(); i += 2) {
                    if(put.get(i - 1).big != (put.get(i).big)) {
                        ok = false;
                        break;
                    }
                }
                if(ok) {
                    return may.aabbcc;
                }
            }
            for (int i = 1; i < put.size(); i++){
                if(put.get(i - 1).big != (put.get(i).big + 1)) {
                    return may.bug;
                }
            }
            return may.abcde;
        } else {
            // 检查是否为飞机带翅膀
            if (len >= 6) {
                // 检查是否为单飞机（两个连续的三张牌）：333444
                if (len == 6) {
                    boolean isPlane = true;
                    for (int i = 0; i < 3; i++) {
                        if (put.get(i).big != put.get(0).big) {
                            isPlane = false;
                            break;
                        }
                    }
                    for (int i = 3; i < 6; i++) {
                        if (put.get(i).big != put.get(3).big) {
                            isPlane = false;
                            break;
                        }
                    }
                    if (isPlane && put.get(0).big == put.get(3).big + 1) {
                        return may.feiji;
                    }
                }
                
                // 检查是否为飞机带单牌翅膀：33344456
                if (len == 8) {
                    boolean isPlane = true;
                    for (int i = 0; i < 3; i++) {
                        if (put.get(i).big != put.get(0).big) {
                            isPlane = false;
                            break;
                        }
                    }
                    for (int i = 3; i < 6; i++) {
                        if (put.get(i).big != put.get(3).big) {
                            isPlane = false;
                            break;
                        }
                    }
                    if (isPlane && put.get(0).big == put.get(3).big + 1) {
                        return may.feiji_dan;
                    }
                }
                
                // 检查是否为飞机带对子翅膀：3334445566
                if (len == 10) {
                    boolean isPlane = true;
                    for (int i = 0; i < 3; i++) {
                        if (put.get(i).big != put.get(0).big) {
                            isPlane = false;
                            break;
                        }
                    }
                    for (int i = 3; i < 6; i++) {
                        if (put.get(i).big != put.get(3).big) {
                            isPlane = false;
                            break;
                        }
                    }
                    if (isPlane && put.get(0).big == put.get(3).big + 1) {
                        for (int i = 6; i < 10; i += 2) {
                            if (put.get(i).big != put.get(i + 1).big) {
                                isPlane = false;
                                break;
                            }
                        }
                        if (isPlane) {
                            return may.feiji_shuang;
                        }
                    }
                }
            }
            
            if(len % 2 != 0) {
                for (int i = 1; i < put.size(); i++) {
                    if(put.get(i - 1).big != (put.get(i).big) + 1) {
                        return may.bug;
                    }
                }
                return may.abcde;
            } else {
                boolean ok = true;
                for (int i = 0; i < put.size(); i+=2) {
                    if(put.get(i).big != (put.get(i + 1).big)) {
                        ok = false;
                        break;
                    }
                }
                if(ok) {
                    for (int i = 1; i < put.size() - 2; i += 2) {
                        if(put.get(i + 1).big != (put.get(i).big) - 1) {
                            ok = false;
                            break;
                        }
                    }
                    if(ok) {
                        return may.aabbcc;
                    }
                }
                for (int i = 1; i < put.size(); i++) {
                    if(put.get(i - 1).big != (put.get(i).big) + 1) {
                        return may.bug;
                    }
                }
                return may.abcde;
            }
        }
    }
    public static boolean compare(ArrayList<poke> put, ArrayList<poke> com) {
        may t = findMay(put);
        may m = findMay(com);
        if(m == may.ww) {
            return false;
        }
        if(t != may.aaaa && t != may.ww) {
            if(t != m) {
                return false;
            } else {
                if(t == may.a || t == may.aa || t == may.aaa) {
                    return put.getFirst().big > com.getFirst().big;
                } else if (t == may.aaab || t == may.aaabb) {
                    return put.get(2).big > com.get(2).big;
                } else if (t == may.feiji || t == may.feiji_dan || t == may.feiji_shuang) {
                    if(put.size() != com.size()) {
                        return false;
                    } else {
                        return put.get(2).big > com.get(2).big;
                    }
                } else if (t == may.abcde || t == may.aabbcc) {
                    if(put.size() != com.size()) {
                        return false;
                    } else {
                        return put.getFirst().big > com.getFirst().big;
                    }
                }
            }
        } else {
            if(t == may.ww) {
                return true;
            } else if(m == may.aaaa) {
                return put.getFirst().big > com.getFirst().big;
            } else {
                return true;
            }
        }
        return false;
    }
    public static boolean findPoke(ArrayList<poke> put, ArrayList<poke> com) {
        may t = findMay(put);
        if(t == may.a) {
            for (int i = com.size() - 2; i > 0; i--) {
                if(com.get(i).big > put.getFirst().big && com.get(i - 1).big != com.get(i).big
                        && com.get(i + 1).big != com.get(i).big) {
                    com.get(i).IsChoose = true;
                    com.get(i).front = true;
                    return true;
                }
            }
            for (int i = com.size() - 1; i >= 0; i--) {
                if(com.get(i).big > put.getFirst().big) {
                    com.get(i).IsChoose = true;
                    com.get(i).front = true;
                    return true;
                }
            }
            return false;
        } else if (t == may.aa) {
            if(com.size() < 2) {
                return false;
            }
            for (int i = com.size() - 1; i > 0; i--) {
                if(com.get(i).big > put.getFirst().big && com.get(i - 1).big == com.get(i).big) {
                    com.get(i).IsChoose = true;
                    com.get(i).front = true;
                    com.get(i - 1).IsChoose = true;
                    com.get(i - 1).front = true;
                    return true;
                }
            }
            return false;
        } else if (t == may.aaa) {
            if(com.size() < 3) {
                return false;
            }
            for (int i = com.size() - 1; i > 1; i--) {
                if(com.get(i).big > put.getFirst().big && com.get(i - 1).big == com.get(i).big
                        && com.get(i - 1).big == com.get(i - 2).big) {
                    com.get(i).IsChoose = true;
                    com.get(i - 1).IsChoose = true;
                    com.get(i - 2).IsChoose = true;
                    com.get(i).front = true;
                    com.get(i - 1).front = true;
                    com.get(i - 2).front = true;
                    return true;
                }
            }
            return false;
        } else if (t == may.aaab) {
            if(com.size() < 4) {
                return false;
            }
            for (int i = com.size() - 1; i > 1; i--) {
                if ((com.get(i).big > put.get(2).big) && (com.get(i - 1).big == com.get(i).big)
                        && (com.get(i - 1).big == com.get(i - 2).big)) {
                    com.get(i).IsChoose = true;
                    com.get(i - 1).IsChoose = true;
                    com.get(i - 2).IsChoose = true;
                    com.get(i).front = true;
                    com.get(i - 1).front = true;
                    com.get(i - 2).front = true;
                    for (int j = com.size() - 1; j >= 0; j--) {
                        if(j != i && j != i - 1 && j != i - 2) {
                            com.get(j).IsChoose = true;
                            com.get(j).front = true;
                            return true;
                        }
                    }
                }
            }
            return false;
        } else if (t == may.aaabb) {
            if(com.size() < 5) {
                return false;
            }
            boolean ok = true;
            for (int i = com.size() - 1; i > 1; i--) {
                if (com.get(i).big > put.get(3).big && com.get(i - 1).big == com.get(i).big
                        && com.get(i - 1).big == com.get(i - 2).big) {
                    com.get(i).IsChoose = true;
                    com.get(i - 1).IsChoose = true;
                    com.get(i - 2).IsChoose = true;
                    com.get(i).front = true;
                    com.get(i - 1).front = true;
                    com.get(i - 2).front = true;
                    for (int j = com.size() - 2; j > 0; j--) {
                        if(j != i && j != i - 1 && j != i - 2 && j != i - 3 && com.get(j).big == com.get(j + 1).big) {
                            com.get(j).IsChoose = true;
                            com.get(j + 1).IsChoose = true;
                            com.get(j).front = true;
                            com.get(j + 1).front = true;
                            return true;
                        }
                    }
                }
            }
            return false;
        } else if (t == may.feiji) {
            if(com.size() < 6) {
                return false;
            }
            for (int i = com.size() - 1; i > 4; i--) {
                if(com.get(i).big > put.get(2).big && com.get(i - 1).big == com.get(i).big && com.get(i - 2).big == com.get(i).big) {
                    for (int j = i - 3; j > 1; j--) {
                        if(com.get(j).big == com.get(i).big - 1 && com.get(j - 1).big == com.get(j).big && com.get(j - 2).big == com.get(j).big) {
                            com.get(i).IsChoose = true;
                            com.get(i - 1).IsChoose = true;
                            com.get(i - 2).IsChoose = true;
                            com.get(j).IsChoose = true;
                            com.get(j - 1).IsChoose = true;
                            com.get(j - 2).IsChoose = true;
                            com.get(i).front = true;
                            com.get(i - 1).front = true;
                            com.get(i - 2).front = true;
                            com.get(j).front = true;
                            com.get(j - 1).front = true;
                            com.get(j - 2).front = true;
                            return true;
                        }
                    }
                }
            }
            return false;
        } else if (t == may.feiji_dan) {
            if(com.size() < 8) {
                return false;
            }
            for (int i = com.size() - 1; i > 4; i--) {
                if(com.get(i).big > put.get(2).big && com.get(i - 1).big == com.get(i).big && com.get(i - 2).big == com.get(i).big) {
                    for (int j = i - 3; j > 1; j--) {
                        if(com.get(j).big == com.get(i).big - 1 && com.get(j - 1).big == com.get(j).big && com.get(j - 2).big == com.get(j).big) {
                            com.get(i).IsChoose = true;
                            com.get(i - 1).IsChoose = true;
                            com.get(i - 2).IsChoose = true;
                            com.get(j).IsChoose = true;
                            com.get(j - 1).IsChoose = true;
                            com.get(j - 2).IsChoose = true;
                            com.get(i).front = true;
                            com.get(i - 1).front = true;
                            com.get(i - 2).front = true;
                            com.get(j).front = true;
                            com.get(j - 1).front = true;
                            com.get(j - 2).front = true;
                            int count = 0;
                            for (int k = com.size() - 1; k >= 0; k--) {
                                if(k != i && k != i - 1 && k != i - 2 && k != j && k != j - 1 && k != j - 2) {
                                    com.get(k).IsChoose = true;
                                    com.get(k).front = true;
                                    count++;
                                    if(count == 2) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return false;
        } else if (t == may.feiji_shuang) {
            if(com.size() < 10) {
                return false;
            }
            for (int i = com.size() - 1; i > 4; i--) {
                if(com.get(i).big > put.get(2).big && com.get(i - 1).big == com.get(i).big && com.get(i - 2).big == com.get(i).big) {
                    for (int j = i - 3; j > 1; j--) {
                        if(com.get(j).big == com.get(i).big - 1 && com.get(j - 1).big == com.get(j).big && com.get(j - 2).big == com.get(j).big) {
                            int pairCount = 0;
                            for (int k = com.size() - 1; k > 0; k--) {
                                if(k != i && k != i - 1 && k != i - 2 && k != j && k != j - 1 && k != j - 2 && com.get(k).big == com.get(k - 1).big) {
                                    com.get(k).IsChoose = true;
                                    com.get(k - 1).IsChoose = true;
                                    com.get(k).front = true;
                                    com.get(k - 1).front = true;
                                    pairCount++;
                                    if(pairCount == 2) {
                                        com.get(i).IsChoose = true;
                                        com.get(i - 1).IsChoose = true;
                                        com.get(i - 2).IsChoose = true;
                                        com.get(j).IsChoose = true;
                                        com.get(j - 1).IsChoose = true;
                                        com.get(j - 2).IsChoose = true;
                                        com.get(i).front = true;
                                        com.get(i - 1).front = true;
                                        com.get(i - 2).front = true;
                                        com.get(j).front = true;
                                        com.get(j - 1).front = true;
                                        com.get(j - 2).front = true;
                                        return true;
                                    }
                                    k--;
                                }
                            }
                        }
                    }
                }
            }
            return false;
        }
        return false;
    }
}