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

	public void resize(int factor) {
		ImageIcon icon = new ImageIcon(this.image);

		if(icon.getIconWidth()+10*factor == 0 || icon.getIconHeight()+10*factor == 0) {
			return;
		}else if(icon.getIconWidth()> 60) {
			Image scaleImage = icon.getImage().getScaledInstance(60, 60,Image.SCALE_DEFAULT);
			image= scaleImage;
		}else if(icon.getIconWidth()<10){
			Image scaleImage = icon.getImage().getScaledInstance(10, 10,Image.SCALE_DEFAULT);
			image= scaleImage;
		}else{
			Image scaleImage = icon.getImage().getScaledInstance(icon.getIconWidth()+10*factor, icon.getIconHeight()+10*factor,Image.SCALE_DEFAULT);
			image = scaleImage;
		}
	}
}
