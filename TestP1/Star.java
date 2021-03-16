package TestP1;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Star {
	Image image;
	String name;
	double x;
	double y;
	int width;
	int height;

	public Star() {}

	public Star(String imagePath, double x, double y,String name){
		this.image = getImage(imagePath);
		this.width=image.getWidth(null);
		this.height=image.getHeight(null);
		this.x = x;
		this.y = y;
		this.name=name;
	}
	public void draw(Graphics g) {
		g.drawImage(image, (int) x, (int) y, null);
		g.setColor(Color.white);
		g.drawString(this.name, (int) x, (int) y);
	}
	public static Image getImage(String path) {
		BufferedImage image = null;
		try {
			URL u = Star.class.getClassLoader().getResource(path);
			image = ImageIO.read(u);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public void resize(double factor) {
		ImageIcon icon = new ImageIcon(this.image);

		if((int)(icon.getIconWidth()*factor) == 0 || (int)(icon.getIconHeight()*factor) == 0) {
			return;
		}else{
			Image scaleImage = icon.getImage().getScaledInstance((int)(icon.getIconWidth()*(factor)), (int)(icon.getIconHeight()*(factor)),Image.SCALE_DEFAULT);
			image = scaleImage;
		}
	}
}
