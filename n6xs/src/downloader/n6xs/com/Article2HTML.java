package downloader.n6xs.com;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;


public class Article2HTML {

	private URL urlin = null;
	private String outputFile = "output.html";
	
	public ArrayList<Integer> getBookLinkFromArch(URL linkURL, String searchStr){
		ArrayList<Integer> numberSet = new ArrayList<Integer>();
		//Read all hot books page
		try {
			HTMLEditorKit hotKit = new HTMLEditorKit(); 
			HTMLDocument hotDoc = (HTMLDocument) hotKit.createDefaultDocument(); 
			hotDoc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
	    
	    	Reader HTMLReader;		
			HTMLReader = new InputStreamReader(linkURL.openConnection().getInputStream(), Charset.forName("gbk"));
			hotKit.read(HTMLReader, hotDoc, 0);
			ElementIterator hotIt = new ElementIterator(hotDoc); 
		    Element hotElem = null;
		    while((hotElem = hotIt.next()) != null)
		    {
		    	//for books
		    	System.out.println(" Text: " + 
				    		hotDoc.getText(hotElem.getStartOffset(), hotElem.getEndOffset() - hotElem.getStartOffset()));
		    	
		    	//for article
		    	
		    }
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numberSet;
	}
	public boolean doDownload(){
		try {
			if (urlin == null){
				return false;
			}
			ArrayList<ImageChar> replaceMark = new ArrayList<ImageChar>();
			HTMLEditorKit kit = new HTMLEditorKit(); 
		    HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument(); 
		    doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
		    //, Charset.forName("GBK")
		    Reader HTMLReader = new InputStreamReader(urlin.openConnection().getInputStream(), Charset.forName("gbk"));		    
		    kit.read(HTMLReader, doc, 0);
		    BufferedWriter HTMLWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), Charset.forName("gbk")));

		    //  Get an iterator for all HTML tags.
		    ElementIterator it = new ElementIterator(doc); 
		    Element elem = null; 
		    //Element articleName = null;
		    while((elem = it.next()) != null)
		    { 
		      if( elem.getName().equals("img") )
		      {		    	
		        String s = (String) elem.getAttributes().getAttribute(HTML.Attribute.SRC);
		        
		        if( s != null ) {
		          String picName = s.substring(s.lastIndexOf("/")+1 ,s.lastIndexOf("."));
		          //System.out.println(picName + "lastindexofslash: " + s.lastIndexOf("/") + "lastindexofdot: " + s.lastIndexOf("."));
		          int len = s.length();
		          
		          switch(picName.toLowerCase()){
		          case "ai":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"爱"));
		        	  break;
		          case "ba":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"巴"));
		        	  break;
		          case "bang":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"棒"));
		        	  break;
		          case "bao":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"饱"));
		        	  break;
		          case "bi":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"逼"));
		        	  break;
		          case "bi2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"屄"));
		        	  break;
		          case "bo":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"勃"));
		        	  break;
		          case "cao":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"操"));
		        	  break;
		          case "cha":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"插"));
		        	  break;
		          case "chan":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"缠"));
		        	  break;
		          case "chao":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"潮"));
		        	  break;
		          case "chi":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"耻"));
		        	  break;
		          case "chou":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"抽"));
		        	  break;
		          case "chuan":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"喘"));
		        	  break;
		          case "chuang":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"床"));
		        	  break;
		          case "chun":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"春"));
		        	  break;
		          case "chun2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"唇"));
		        	  break;
		          case "cu":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"粗"));
		        	  break;
		          case "cuo":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"搓"));
		        	  break;
		          case "dong":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"洞"));
		        	  break;
		          case "dong2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"胴"));
		        	  break;
		          case "fei":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"肥"));
		        	  break;
		          case "feng":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"缝"));
		        	  break;
		          case "gan":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"感"));
		        	  break;
		          case "gang":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"肛"));
		        	  break;
		          case "gao":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"高"));
		        	  break;
		          case "gen":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"根"));
		        	  break;
		          case "gong":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"宫"));
		        	  break;
		          case "gu":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"股"));
		        	  break;
		          case "gui":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"龟"));
		        	  break;
		          case "gun":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"棍"));
		        	  break;
		          case "huan":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"欢"));
		        	  break;
		          case "ji":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"激"));
		        	  break;
		          case "ji2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"鸡"));
		        	  break;
		          case "jian":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"贱"));
		        	  break;
		          case "jian2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"奸"));
		        	  break;
		          case "jiao":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"交"));
		        	  break;
		          case "jing":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"精"));
		        	  break;
		          case "ku":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"裤"));
		        	  break;
		          case "kua":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"胯"));
		        	  break;
		          case "lang":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"浪"));
		        	  break;
		          case "liao":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"撩"));
		        	  break;
		          case "liu":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"流"));
		        	  break;
		          case "lou":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"露"));
		        	  break;
		          case "lu":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"撸"));
		        	  break;
		          case "luan":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"乱"));
		        	  break;
		          case "luo":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"裸"));
		        	  break;
		          case "man":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"满"));
		        	  break;
		          case "mao":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"毛"));
		        	  break;
		          case "mi":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"蜜"));
		        	  break;
		          case "mi2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"迷"));
		        	  break;
		          case "min":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"敏"));
		        	  break;
		          case "nai":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"奶"));
		        	  break;
		          case "nen":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"嫩"));
		        	  break;
		          case "niang":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"娘"));
		        	  break;
		          case "niao":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"尿"));
		        	  break;
		          case "nong":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"弄"));
		        	  break;
		          case "nue":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"虐"));
		        	  break;
		          case "nv":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"女"));
		        	  break;
		          case "pen":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"喷"));
		        	  break;
		          case "pi":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"屁"));
		        	  break;
		          case "qi":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"骑"));
		        	  break;
		          case "ri":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"日"));
		        	  break;
		          case "rou":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"肉"));
		        	  break;
		          case "rou2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"揉"));
		        	  break;
		          case "ru":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"乳"));
		        	  break;
		          case "ru2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"蠕"));
		        	  break;
		          case "sa2i":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"塞"));
		        	  break;
		          case "sao":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"骚"));
		        	  break;
		          case "se":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"色"));
		        	  break;
		          case "she":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"射"));
		        	  break;
		          case "shen":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"身"));
		        	  break;
		          case "shi":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"湿"));
		        	  break;
		          case "shu":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"熟"));
		        	  break;
		          case "shuang":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"爽"));
		        	  break;
		          case "shun":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"吮"));
		        	  break;
		          case "tian":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"舔"));
		        	  break;
		          case "ting":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"挺"));
		        	  break;
		          case "tun":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"吞"));
		        	  break;
		          case "tun2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"臀"));
		        	  break;
		          case "tuo":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"脱"));
		        	  break;
		          case "xi":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"吸"));
		        	  break;
		          case "xie":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"泄"));
		        	  break;
		          case "xing":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"性"));
		        	  break;
		          case "xiong":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"胸"));
		        	  break;
		          case "xue":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"穴"));
		        	  break;
		          case "ya":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"压"));
		        	  break;
		          case "yang":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"阳"));
		        	  break;
		          case "yang2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"痒"));
		        	  break;
		          case "yao":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"腰"));
		        	  break;
		          case "ye":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"液"));
		        	  break;
		          case "yi2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"衣"));
		        	  break;
		          case "yin":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"阴"));
		        	  break;
		          case "yin2":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"淫"));
		        	  break;
		          case "ying":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"迎"));
		        	  break;
		          case "you":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"诱"));
		        	  break;
		          case "yu":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"欲"));
		        	  break;
		          case "zhang":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"胀"));
		        	  break;
		          case "zuo":
		        	  replaceMark.add(new ImageChar(elem.getStartOffset(),len,"坐"));
		        	  break;
		        	  default:
		        		  break;
		         }
		      } 
		    }
		   }
		    
		    for (int i=replaceMark.size() - 1; i>=0; i--){		    	
		    	doc.remove(replaceMark.get(i).imageOffset, 1);
		    	doc.insertString(replaceMark.get(i).imageOffset, replaceMark.get(i).replaceString, null);
		    }
		    ElementIterator eIt = new ElementIterator(doc); 
		    Element tableELE = null; 
		    //Element articleName = null;
		    String document = "";
		    while((tableELE = eIt.next()) != null)
		    {		    	
		    	if(tableELE.getName().equals("table")){
		    		AttributeSet aSet = tableELE.getAttributes();
		    		if (aSet.containsAttribute(HTML.Attribute.CLASS, "mview")){
		    			document = doc.getText(tableELE.getStartOffset(), tableELE.getEndOffset() - tableELE.getStartOffset());
		    			}		    		
		    	}	    	
		    }   
		    if (document != ""){		    	
		    	HTMLWriter.write(document.replaceAll("\\s+", "\n"));
		    	}
		    HTMLWriter.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public void setDownloadNumber(String articleNumber){
		try {
			this.urlin = new URL("http://www.n6xs.com/view.asp?id=" + articleNumber);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.outputFile = articleNumber + ".txt";
	}
	
	public Article2HTML() {
		
	}
	
	public Article2HTML(String args, String outputDirectory) throws MalformedURLException {
		// TODO Auto-generated method stub
		urlin = new URL("http://www.n6xs.com/view.asp?id=" + args);
		
		outputFile = outputDirectory + args + ".txt";
	}

}
