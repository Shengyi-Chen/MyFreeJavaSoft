package me.shengyi.albumpreview;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.SanselanException;
import org.apache.sanselan.common.ImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TagInfo;



public class JPEGImageLoader {
	private File jpegImageFile;
	private BufferedImage jpegImage;
	private String takenDate = "";
	private String cameraInfo = "";

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
			
			//Get Metadata EXIF information
			JpegImageMetadata metadata = (JpegImageMetadata) Sanselan.getMetadata(getJpegImageFile());
			SimpleDateFormat takenDateFormatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			this.takenDate = metadata.findEXIFValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL).getValueDescription().replace('\'', '-');
			Date date = takenDateFormatter.parse(this.takenDate);
			DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());			
			this.takenDate = df.format(date);
			
			this.cameraInfo = metadata.findEXIFValue(ExifTagConstants.EXIF_TAG_MAKE).getValueDescription() 
					+ metadata.findEXIFValue(ExifTagConstants.EXIF_TAG_MODEL).getValueDescription();			
			
		} catch (IOException | SanselanException | ParseException | NullPointerException e) {	
			img = new BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g2dText = img.createGraphics();
			g2dText.drawString("ERROR: Cannot open file!", 0, 512/2);
			setJpegImage(img);
			this.takenDate = "";
			this.cameraInfo = "";
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

	public String getTakenDate() {
		return takenDate;
	}

	public String getCameraInfo() {
		return cameraInfo;
	}
	
	
	
}
