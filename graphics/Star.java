package graphics;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import titan.Vector3dInterface;

//import jdk.internal.org.xml.sax.InputSource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Star {
	Image image;
	String name;
	double x;
	double y;
	int width;
	int height;

	Vector3dInterface position;
	static InputStream stream;

	public Star() {}

	public Star(String imagePath, double x, double y,String name, Vector3dInterface position)
	{
		this.image = getImage(imagePath);
		this.width=image.getWidth(null);
		this.height=image.getHeight(null);
		this.x = x;
		this.y = y;
		this.name=name;
		this.position = position;
	}
	public void draw(Graphics g) {

		g.drawImage(image, (int) position.mul(0.000000001).getX()+700, (int) position.mul(0.000000001).getY()+400, null);
		g.setColor(Color.white);
		g.drawString(this.name, (int) position.mul(0.000000001).getX()+700, (int) position.mul(0.000000001).getY()+400);
	}
	public static Image getImage(String path) {
		BufferedImage image = null;
		try {
			URL u = Star.class.getClassLoader().getResource(path);
			stream = Star.class.getResourceAsStream(path);
			image = ImageIO.read(stream);
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
	public void setPosition(Vector3dInterface newPos)
	{
		this.position = newPos;
	}
	public Vector3dInterface getPosition()
	{
		return position;
	}
}
