package com.jzh.test;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author akyna
 * @date 04/04 004 10:45 PM
 */
public class DynamicMovingLineChart extends JPanel {

    private ArrayList<Integer> data;
    private int dataMaxSize;
    private int numToDelete;

    public DynamicMovingLineChart(int dataMaxSize) {
        this.dataMaxSize = dataMaxSize;
        data = new ArrayList<Integer>();
        numToDelete = 0;
    }

    public void addDataPoint(int newData) {
        data.add(newData);
        if (data.size() > dataMaxSize) { // 从左侧删除的线条数量
            numToDelete = data.size() - dataMaxSize;
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 绘制坐标轴
        g.drawLine(50, 250, 350, 250); // x轴
        g.drawLine(50, 250, 50, 50); // y轴

        // 绘制折线
        int x = 100; // x轴起始点
        for (int i = numToDelete; i < data.size() - 1; i++) {
            int y1 = 250 - data.get(i);
            int y2 = 250 - data.get(i + 1);
            g.drawLine(x, y1, x + 50, y2);
            x += 50; // 每个数据点之间的x轴距离为50
        }

        // 添加标签、注释等
        g.drawString("x轴", 340, 265);
        g.drawString("y轴", 60, 30);
        g.drawString("折线图", 175, 30);
    }

    public static void main(String[] args) {
        DynamicMovingLineChart lineChart = new DynamicMovingLineChart(5);
        JFrame f = new JFrame();
        f.setContentPane(lineChart);
        f.setSize(400, 300);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 模拟动态添加数据点
        int newData = 100;
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
            lineChart.addDataPoint(newData);
            newData += (int) (Math.random() * 30) - 15;
            if (lineChart.numToDelete > 0) {
                for (int i = 0; i < lineChart.numToDelete; i++) {
                    lineChart.data.remove(0);
                }
                lineChart.numToDelete = 0;
            }
        }
    }
}