package clsimulator.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import clsimulator.main.Wire;

public class WaveForm extends JPanel {
	
	private Wire[] wires;
	private int eachTimeCut;
	private int eachHeightStep;
	private final Color ZERO = Color.ORANGE;
	private final Color UNKNOWN = Color.RED;
	private final Color ONE = Color.GREEN;
	
	public WaveForm(Wire[] wires, int eachTimeCut, int h_step) {
		setLayout(new FlowLayout());
		setBackground(Color.BLACK);
		this.wires = wires;
		this.eachTimeCut = eachTimeCut;
		this.eachHeightStep = h_step;
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int initial_h = -20;
		int initial_x = 50;
		
		for(int i=0; i < wires.length; i++) {
			Wire wire = wires[i];
			int dynamic_x = initial_x;
			int prev_offset=5;
			initial_h += eachHeightStep;
			g.setColor(Color.WHITE);
			Font font = new Font("Courier", 0, 12);
			g.setFont(font);
			g.drawString("w" + wire.getIdx(), 20, initial_h + 10);
			//System.out.println();
			for(int j=0; j < wire.getHistoryValueCount(); j++) {
				int value = wire.getHistoryValue(j);
				int offset;
				//System.out.print(value + " - ");
				if(value == -1) {
					offset = 5;
					g.setColor(UNKNOWN);
				}
				else if (value == 1) {
					offset = 0;
					g.setColor(ONE);
				}
				else {
					offset = 10;
					g.setColor(ZERO);
				}
				
				
				if(j > 0){
					int diff = prev_offset - offset;
					if(diff < 0) {
						//rise
						g.drawLine(dynamic_x, initial_h + prev_offset,  dynamic_x, initial_h + offset);
					}
					else if (diff > 0) {
						//fall
						g.drawLine(dynamic_x, initial_h + prev_offset,  dynamic_x, initial_h + offset);
					}
				}
				g.drawLine(dynamic_x, initial_h + offset,  dynamic_x + eachTimeCut, initial_h + offset);
				dynamic_x += eachTimeCut;
				prev_offset = offset;
			}
			
		}
	}
}
