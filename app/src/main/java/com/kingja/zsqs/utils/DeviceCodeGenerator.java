package com.kingja.zsqs.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Description:TODO
 * Create Time:2020/5/11 0011 上午 9:37
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DeviceCodeGenerator {
    public static void main(String[] args) {

        for (String s : getUniqueRandom(10, 50)) {
            System.out.println(s);
        }
    }

    public static String getRandom(int len) {
        String source = "0123456789";
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int j = 0; j < len; j++) {
            rs.append(source.charAt(r.nextInt(source.length())));
    }
        return rs.toString();
    }

    public static List<String> getUniqueRandom(int len, int num) {
        Set<String> s = new HashSet<>();
        while (s.size() < num) {
            s.add(getRandom(len));
        }
        return new ArrayList<>(s);
    }
}
