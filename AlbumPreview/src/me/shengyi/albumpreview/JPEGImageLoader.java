package me.shengyi.albumpreview;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.SanselanException;
import org.apache.sanselan.common.ImageMetadata;
import org.apache.sanselan.formats.jpeg.JpegImageMetadata;
import org.apache.sanselan.formats.jpeg.exifRewrite.ExifRewriter;
import org.apache.sanselan.formats.tiff.TiffField;
import org.apache.sanselan.formats.tiff.TiffImageMetadata;
import org.apache.sanselan.formats.tiff.constants.ExifTagConstants;
import org.apache.sanselan.formats.tiff.constants.TagInfo;
import org.apache.sanselan.formats.tiff.write.TiffOutputDirectory;
import org.apache.sanselan.formats.tiff.write.TiffOutputField;
import org.apache.sanselan.formats.tiff.write.TiffOutputSet;



public class JPEGImageLoader {
	private File jpegImageFile;
	private BufferedImage jpegImage;
	private String takenDate = "";
	private String cameraInfo = "";
	private String imageTitle = "";

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
			g2dText.drawString("ERROR: Cannot open file!", 3, 512/2);
			setJpegImage(img);
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
		//Get Metadata EXIF information
		JpegImageMetadata metadata;
		try {
			metadata = (JpegImageMetadata) Sanselan.getMetadata(getJpegImageFile());
			SimpleDateFormat takenDateFormatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			this.takenDate = metadata.findEXIFValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL).getValueDescription().replace('\'', '-');
			Date date = takenDateFormatter.parse(this.takenDate);
			DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());			
			this.takenDate = df.format(date);
			
		} catch (ImageReadException | IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			this.takenDate = "";
		}
		
		return takenDate;
	}

	public String getCameraInfo() {
		JpegImageMetadata metadata;
		try {
			metadata = (JpegImageMetadata) Sanselan.getMetadata(getJpegImageFile());
			this.cameraInfo = metadata.findEXIFValue(ExifTagConstants.EXIF_TAG_MAKE).getValueDescription().replaceAll("\'", "")
					+ " , "
					+ metadata.findEXIFValue(ExifTagConstants.EXIF_TAG_MODEL).getValueDescription().replaceAll("\'", "");
		} catch (ImageReadException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			this.cameraInfo = "";
		}
		return cameraInfo;
	}
	
	public String getImageTitle() {
		JpegImageMetadata metadata;
		try {
			metadata = (JpegImageMetadata) Sanselan.getMetadata(getJpegImageFile());
			this.imageTitle = metadata.findEXIFValue(ExifTagConstants.EXIF_TAG_IMAGE_DESCRIPTION).getValueDescription().replaceAll("\'","");
		} catch (ImageReadException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			this.imageTitle = "";
		}
		return imageTitle;
	}

	public void setImageTitle(String imageTitle) {
		FileOutputStream imageOS = null;
		BufferedOutputStream bufferedOS = null;
		File outputFile = new File(getJpegImageFile().getAbsolutePath() + ".tmp");
		try {            
			TiffOutputSet outputSet = null;
			JpegImageMetadata metadata = (JpegImageMetadata) Sanselan.getMetadata(getJpegImageFile());
			if (null != metadata){
				TiffImageMetadata exif = metadata.getExif();
				if (null != exif){
					outputSet = exif.getOutputSet();
				}
			}
			if (null == outputSet) {
                outputSet = new TiffOutputSet();
            }
            TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
            exifDirectory.removeField(ExifTagConstants.EXIF_TAG_IMAGE_DESCRIPTION);            
            exifDirectory.add(new TiffOutputField(ExifTagConstants.EXIF_TAG_IMAGE_DESCRIPTION, 
            		org.apache.sanselan.formats.tiff.fieldtypes.FieldType.FIELD_TYPE_ASCII, 
            		imageTitle.length(), imageTitle.getBytes()));
			
			
            imageOS = new FileOutputStream(outputFile);
            bufferedOS = new BufferedOutputStream(imageOS);
            
            new ExifRewriter().updateExifMetadataLossless(getJpegImageFile(), bufferedOS,
                    outputSet);
            
            bufferedOS.close();
            imageOS.close();
            getJpegImageFile().delete();
            outputFile.renameTo(getJpegImageFile());
            
		} catch (ImageReadException | ImageWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			try {
				bufferedOS.close();
				imageOS.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
           
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.imageTitle = imageTitle;
		
	}
	
}
