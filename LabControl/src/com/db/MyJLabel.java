package com.db;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

public class MyJLabel extends JLabel {
    public static final int MARQUEE_SPEED_DIV = 10;
    public static final int REPAINT_WITHIN_MS = 5;

    private static final long serialVersionUID = -7737312573505856484L;

    public MyJLabel() {
        super();
    }

    public MyJLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    public MyJLabel(Icon image) {
        super(image);
    }

    public MyJLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
    }

    public MyJLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        setForeground(Color.white);
        setFont(new Font("Times New Roman", Font.BOLD+Font.ITALIC, 25));
    }

    public MyJLabel(String text) {
        super(text);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.translate((int)((System.currentTimeMillis() / MARQUEE_SPEED_DIV) % (getWidth() * 2)) - getWidth(), 0);
        super.paintComponent(g);
        repaint(REPAINT_WITHIN_MS);
    }
}