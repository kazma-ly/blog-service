package com.kazma233.common;

import com.google.common.collect.Lists;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * 随机数图片生成器
 * Created by mac_zly on 2016/12/6.
 */

public class PhotoTokenUtils {

    // 剔除了i和l
    private static final List<String> KEYS = Lists.newArrayList(
            "1", "2", "3", "4", "4", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    );

    private static final int WIDTH = 120;
    private static final int HEIGHT = 30;
    private static final int RANDOM_NUM = 4;

    private BufferedImage bufferedImage = null;

    private StringBuffer stringBuffer = new StringBuffer();


    public PhotoTokenUtils() {

        bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();

        //1.设置背景色   
        setBackground(graphics);
        //2. 设置边框   
        setBorder(graphics);
        //3.画干扰线   
        drawRandomLine(graphics);
        //4.写随机数    
        drawRandom((Graphics2D) graphics);
    }

    // 输出
    public void out(HttpServletResponse response) throws IOException {
        //5.写给浏览器
        response.setContentType("image/jpeg");
        // 高速浏览器不要缓存该图片
        response.setDateHeader("expries", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        ImageIO.write(bufferedImage, "jpg", response.getOutputStream());
    }

    private void setBackground(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
    }

    private void setBorder(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.drawRect(1, 1, WIDTH - 2, HEIGHT - 2);
    }

    private void drawRandomLine(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        // 生成随机数 设置范围
        for (int i = 0; i < 5; i++) {
            int x1 = new Random().nextInt(WIDTH - 2);
            int y1 = new Random().nextInt(HEIGHT - 2);
            int x2 = new Random().nextInt(WIDTH - 2);
            int y2 = new Random().nextInt(HEIGHT - 2);
            graphics.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawRandom(Graphics2D graphics) {
        graphics.setColor(Color.WHITE);
        Font font = new Font("微软雅黑", Font.BOLD, 20);
        graphics.setFont(font);
        for (int i = 0; i < RANDOM_NUM; i++) {
            int x = WIDTH / RANDOM_NUM * i + 5;
            // 设置旋转角度
            int degree = new Random().nextInt(30) - 15;
            graphics.rotate(degree * Math.PI / 180, x, 20);

            String key = KEYS.get((int) (Math.random() * KEYS.size()));

            graphics.drawString(key, x, 20);
            stringBuffer.append(key);
            // 转回去
            graphics.rotate(-degree * Math.PI / 180, x, 20);
        }
    }

    public String token() {
        return stringBuffer.toString();
    }
}
