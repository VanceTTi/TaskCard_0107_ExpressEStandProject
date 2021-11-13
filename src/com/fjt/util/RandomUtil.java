package com.fjt.util;

import java.util.Random;

/**
 * 这个工具类，只用来生成6位随机数
 */
public class RandomUtil {
    private static Random r = new Random();
    public static int getCode(){
        return r.nextInt(900000)+100000;
    }
}
