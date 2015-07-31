package me.shengyi.albumpreview;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;


public class JPEGImageLoader {
	private File jpegImageFile;
	private BufferedImage jpegImage;

	public JPEGImageLoader(String jpegFileName){
		setJpegImageFile(new File(jpegFileName));
	}
	
	public JPEGImageLoader(File jpegFile){
		setJpegImageFile(jpegFile);
	}
	
	public BufferedImage loadImage(){
		BufferedImage img = null;
		try {
			img = ImageIO.read(getJpegImageFile());
			setJpegImage(img);			
			
		} catch (IOException e) {	
			img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g2dText = img.createGraphics();
			g2dText.drawString("ERROR: Cannot open file!", 0, 512/2);
			setJpegImage(img);
			e.printStackTrace();
		}
		
		return getJpegImage();
	}

	public File getJpegImageFile() {
		return jpegImageFile;
	}

	public void setJpegImageFile(File jpegImageFile) {
		this.jpegImageFile = jpegImageFile;
	}

	public BufferedImage getJpegImage() {
		return jpegImage;
	}

	private void setJpegImage(BufferedImage jpegImage) {
		this.jpegImage = jpegImage;
	}
	
	
}
