package clsimulator.util;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

public class CenterFrame {

	public static Point getPosition(int width, int height) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		int x = (dimension.width - width) /2;
		int y = (dimension.height - height) /2;
		return (new Point(x, y));
	}

}
