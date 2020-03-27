package clsimulator.view;

import java.awt.Point;

import javax.swing.JFrame;

import clsimulator.main.Wire;
import clsimulator.util.CenterFrame;

public class ResultWindow extends JFrame {
	
	private int eachTimeCutWidth = 10;
	private final int EACH_HEIGHT_STEP = 30;
	private int totalHeight;
	
	public ResultWindow(Wire[] w, int timeCut) {
		super("Waveform");
		int total_width = timeCut * eachTimeCutWidth + 70;
		totalHeight = (w.length +2 )* EACH_HEIGHT_STEP + 50;
		Point point = CenterFrame.getPosition(total_width, totalHeight);
		setBounds(point.x, point.y, total_width, totalHeight);
		add(new WaveForm(w, eachTimeCutWidth, EACH_HEIGHT_STEP));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
