package downloader.n6xs.com;

public class ImageChar {
	public int imageOffset;
	public String replaceString;
	public int length;
	
	public ImageChar(int iOffset, int iLength, String replaceStr){
		this.imageOffset = iOffset;
		this.length = iLength;
		this.replaceString = replaceStr;
		
	}

}
