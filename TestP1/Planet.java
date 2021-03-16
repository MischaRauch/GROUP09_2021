package TestP1;

import java.awt.*;

public class Planet extends Star {
	double longAxis;
	double shortAxis;
	double speed;
	double degree;
	Star center;

	public Planet(Star center, String imagePath, double longAxis, double shortAxis, double speed, String name) {
		this.image = super.getImage(imagePath);
		this.center = center;
		this.longAxis = longAxis;
		this.shortAxis = shortAxis;
		this.x = center.x + longAxis;
		this.y = center.y;
		this.speed = speed;
		this.name = name;
	}

	public void draw(Graphics g) {
		super.draw(g);
		x = (center.x + center.width / 2) + longAxis * Math.cos(degree);
		y = (center.y + center.height / 2) + shortAxis * Math.sin(degree);
		degree += speed;
	}


}
