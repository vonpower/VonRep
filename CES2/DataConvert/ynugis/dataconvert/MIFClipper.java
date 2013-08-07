/*
 * @author 冯涛，创建日期：2003-9-11
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */
package ynugis.dataconvert;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author VonPower
 *
 */
public class MIFClipper {

	//Enumeration for Process Control 
	final static int PROCESS_HEADER = 0;

	final static int PROCESS_DATA = 1;

	final static int PROCESS_PLINE = 2;

	final static int PROCESS_POST_PLINE = 3;

	final static int PROCESS_MULTIPLE = 4;

	final static int PROCESS_REGION = 5;

	final static int PROCESS_REGION_HEADER = 6;

	final static int PROCESS_POST_REGION = 7;

	final static int PROCESS_POST_LINE = 8;

	//MIF Header Keyword:
	final static String DATA_WORD = "Data";

	final static String VERSION_WORD = "Version";

	final static String DELIMITER_WORD = "Delimiter";

	final static String COORDSYS_WORD = "Coordsys";

	//MIF DATA Section Keyword:
	
	//DATA TYPE: 
	final static String PLINE_WORD = "PLine";

	final static String LINE_WORD = "Line";
	
	final static String ARC_WORD="Arc";
	
	final static String RECT_WORD="Rect";
	
	final static String ROUNDRECT_WORD="Roundrect";
	
	final static String ELLIPSE_WORD="Ellipse";
	
	final static String MUTILPOINT_WORD="Mutilpoint";
	
	final static String TEXT_WORD = "Text";
	
	final static String REGION_WORD = "Region";
	
	final static String POINT_WORD = "Point";

	//DATA Description:
	final static String MULTIPLE_WORD = "Multiple";

	final static String PEN_WORD = "Pen";

	final static String SMOOTH_WORD = "Smooth";

	final static String BRUSH_WORD = "Brush";

	final static String CENTER_WORD = "Center";

	

	
	//Some useful variable:
	private BufferedReader br;

	private String fileName = null;

	private String srcFilePath=null;
	private String destFilePath = null;

	private String currentStr = null;

	private String prefix = null;

	/**
	 * Loads a MIF file from the Reader and split it
	 * 
	 * @param br
	 *            BufferedReader to read the MIF file
	 * 
	 */
	public MIFClipper(BufferedReader br) {

		this.br = br;

	}

	/**
	 * Loads a MIF file from the Reader and split it，output to Src file Path
	 * @param MIFFile filepath of ".mif" file
	 * @throws FileNotFoundException
	 */
	public MIFClipper(String MIFFile) throws FileNotFoundException {

		this.br = new BufferedReader(new FileReader(MIFFile));
		int dotIdx = MIFFile.lastIndexOf(File.separator);
		this.destFilePath = MIFFile.substring(0, dotIdx) + File.separator;
		//输出到源文件所在目录
		this.srcFilePath= this.destFilePath;
		
		this.fileName = MIFFile.substring(dotIdx + 1);
		this.prefix = fileName.substring(0, fileName.lastIndexOf("."));

	}
	/**
	 * Loads a MIF file from the Reader and split it
	 * @param MIFFile filepath of ".mif" file
	 * @throws FileNotFoundException
	 */
	public MIFClipper(String MIFFile,String destPath) throws FileNotFoundException {

		this.br = new BufferedReader(new FileReader(MIFFile));
		int dotIdx = MIFFile.lastIndexOf(File.separator);
		this.srcFilePath= MIFFile.substring(0, dotIdx) + File.separator;
		this.destFilePath = destPath;
		this.fileName = MIFFile.substring(dotIdx + 1);
		this.prefix = fileName.substring(0, fileName.lastIndexOf("."));

	}

	/**
	 * separate the standard MIF to single Shape type Mif file  
	 * @return A String  Contain  the filepath of  ".mif"  that have been clipped (Delimiter is ",")     
	 * @throws IOException
	 */
	public String clipMIFFile() throws IOException {

		String currentShape = null;
		String retFiles = "";
		int startIndex = 0;
		int endIndex = 0;
		StringBuffer mifHeader = new StringBuffer();
		StringBuffer mifSpatialInfo = new StringBuffer();
		// Specifies the expected next action in the loop
		int action = PROCESS_HEADER;

		// setting to true means we don't read the same line again
		boolean pushback;

		StringTokenizer st = null;
		String tok = null;
		pushback = false;

		MAIN_LOOP: while (true) {

			if (!pushback) {
				// if it's null then there's no more
				if ((st = getTokens(br)) == null)
					break MAIN_LOOP;

				tok = st.nextToken();
			} else {
				pushback = false; // pushback was true so make it
				// false so it doesn't happen twice
			}

			SWITCH: switch (action) {

			case PROCESS_HEADER: {

				if (isSame(tok, DATA_WORD)) {
					action = PROCESS_DATA;

				} else if (isSame(tok, VERSION_WORD)) {
				} else if (isSame(tok, DELIMITER_WORD)) {
				} else if (isSame(tok, COORDSYS_WORD)) {
				}
				mifHeader.append(currentStr);

			}
				break SWITCH;

			case PROCESS_DATA: {

				if (isSame(tok, PLINE_WORD)) {

					//if have read some Shape & the shape is not PLINE_WORD
					//then output that shape with alone .mif file 
					if (currentShape != null
							&& !isSame(currentShape, PLINE_WORD)) {
						boolean isExist=outputMIF(destFilePath + prefix + "_" + currentShape
								+ ".mif", mifHeader, mifSpatialInfo);
						outpufMID(destFilePath + prefix + "_" + currentShape
								+ ".mid", startIndex, endIndex);
						
						//if the .mif exist ,we needn't add it to files path String
						if(!isExist)retFiles += destFilePath + prefix + "_" + currentShape
								+ ".mif" + ",";
						//set current Shape 
						currentShape = PLINE_WORD;
						//set "start Index" equal "end Index",so wo can read ".MID" file correctly
						startIndex = endIndex;
						//clear Spatial Info
						mifSpatialInfo.delete(0, mifSpatialInfo.length());

					}

					endIndex++;
					currentShape = PLINE_WORD;

				} else if (isSame(tok, REGION_WORD)) {

					if (currentShape != null
							&& !isSame(currentShape, REGION_WORD)) {
						boolean isExist=outputMIF(destFilePath + prefix + "_" + currentShape
								+ ".mif", mifHeader, mifSpatialInfo);
						outpufMID(destFilePath + prefix + "_" + currentShape
								+ ".mid", startIndex, endIndex);
						if(!isExist)
							retFiles += destFilePath + prefix + "_" + currentShape
								+ ".mif" + ",";
						currentShape = REGION_WORD;
						startIndex = endIndex;
						mifSpatialInfo.delete(0, mifSpatialInfo.length());

					}

					endIndex++;
					currentShape = REGION_WORD;
				} else if (isSame(tok, LINE_WORD)) {

					if (currentShape != null
							&& !isSame(currentShape, LINE_WORD)) {
						boolean isExist=outputMIF(destFilePath + prefix + "_" + currentShape
								+ ".mif", mifHeader, mifSpatialInfo);
						outpufMID(destFilePath + prefix + "_" + currentShape
								+ ".mid", startIndex, endIndex);
						if(!isExist)
						retFiles += destFilePath + prefix + "_" + currentShape
								+ ".mif" + ",";
						currentShape = LINE_WORD;
						startIndex = endIndex;
						mifSpatialInfo.delete(0, mifSpatialInfo.length());

					}

					endIndex++;
					currentShape = LINE_WORD;

				} else if (isSame(tok, POINT_WORD)) // handle a MIF
				// POINT primitive
				{
					if (currentShape != null
							&& !isSame(currentShape, POINT_WORD)) {
						boolean isExist=outputMIF(destFilePath + prefix + "_" + currentShape
								+ ".mif", mifHeader, mifSpatialInfo);
						outpufMID(destFilePath + prefix + "_" + currentShape
								+ ".mid", startIndex, endIndex);
						if(!isExist)
						retFiles += destFilePath + prefix + "_" + currentShape
								+ ".mif" + ",";
						currentShape = POINT_WORD;
						startIndex = endIndex;
						mifSpatialInfo.delete(0, mifSpatialInfo.length());

					}

					endIndex++;
					currentShape = POINT_WORD;
				} else if (isSame(tok, TEXT_WORD)) 
				{

					if (currentShape != null
							&& !isSame(currentShape, TEXT_WORD)) {
						boolean isExist=outputMIF(destFilePath + prefix + "_" + currentShape
								+ ".mif", mifHeader, mifSpatialInfo);
						outpufMID(destFilePath + prefix + "_" + currentShape
								+ ".mid", startIndex, endIndex);
						if(!isExist)
						retFiles += destFilePath + prefix + "_" + currentShape
								+ ".mif" + ",";
						currentShape = TEXT_WORD;
						startIndex = endIndex;
						mifSpatialInfo.delete(0, mifSpatialInfo.length());

					}

					endIndex++;
					currentShape = TEXT_WORD;
				}else if (isSame(tok,RECT_WORD))
				{
					if (currentShape != null
							&& !isSame(currentShape, RECT_WORD)) {
						boolean isExist=outputMIF(destFilePath + prefix + "_" + currentShape
								+ ".mif", mifHeader, mifSpatialInfo);
						outpufMID(destFilePath + prefix + "_" + currentShape
								+ ".mid", startIndex, endIndex);
						if(!isExist)
						retFiles += destFilePath + prefix + "_" + currentShape
								+ ".mif" + ",";
						currentShape = RECT_WORD;
						startIndex = endIndex;
						mifSpatialInfo.delete(0, mifSpatialInfo.length());
					}

					endIndex++;
					currentShape = RECT_WORD;
					
				}else if(isSame(tok,ARC_WORD))
				{
					if (currentShape != null
							&& !isSame(currentShape, ARC_WORD)) {
						boolean isExist=outputMIF(destFilePath + prefix + "_" + currentShape
								+ ".mif", mifHeader, mifSpatialInfo);
						outpufMID(destFilePath + prefix + "_" + currentShape
								+ ".mid", startIndex, endIndex);
						if(!isExist)
						retFiles += destFilePath + prefix + "_" + currentShape
								+ ".mif" + ",";
						currentShape =ARC_WORD;
						startIndex = endIndex;
						mifSpatialInfo.delete(0, mifSpatialInfo.length());
					}

					endIndex++;
					currentShape = ARC_WORD;
				}
				else if(isSame(tok,ELLIPSE_WORD))
				{
					if (currentShape != null
							&& !isSame(currentShape, ELLIPSE_WORD)) {
						boolean isExist=outputMIF(destFilePath + prefix + "_" + currentShape
								+ ".mif", mifHeader, mifSpatialInfo);
						outpufMID(destFilePath + prefix + "_" + currentShape
								+ ".mid", startIndex, endIndex);
						if(!isExist)
						retFiles += destFilePath + prefix + "_" + currentShape
								+ ".mif" + ",";
						currentShape = ELLIPSE_WORD;
						startIndex = endIndex;
						mifSpatialInfo.delete(0, mifSpatialInfo.length());
					}

					endIndex++;
					currentShape = ELLIPSE_WORD;
				
				}else if(isSame(tok,ROUNDRECT_WORD))
				{
					if (currentShape != null
							&& !isSame(currentShape,ROUNDRECT_WORD)) {
						boolean isExist=outputMIF(destFilePath + prefix + "_" + currentShape
								+ ".mif", mifHeader, mifSpatialInfo);
						outpufMID(destFilePath + prefix + "_" + currentShape
								+ ".mid", startIndex, endIndex);
						if(!isExist)
						retFiles += destFilePath + prefix + "_" + currentShape
								+ ".mif" + ",";
						currentShape = ROUNDRECT_WORD;
						startIndex = endIndex;
						mifSpatialInfo.delete(0, mifSpatialInfo.length());
					}

					endIndex++;
					currentShape = ROUNDRECT_WORD;
					
				}
					
				mifSpatialInfo.append(currentStr);
			}
				break SWITCH;

			} // end of switch
		} // end of while loop

		
		LAST_THINGS: if (startIndex == 0) {//startIndex==0 means the input mif file was a single shape type file 
			
			
			if(isSame(mifSpatialInfo.toString(),""))
				break LAST_THINGS;
			//直接返回输入的文件路径
			retFiles = this.srcFilePath + this.fileName + ",";

		} else {//write remanent infomation to .mif file 
			
			// hard code
			boolean isExist=outputMIF(destFilePath + prefix + "_" + currentShape + ".mif",
					mifHeader, mifSpatialInfo);
			outpufMID(destFilePath + prefix + "_" + currentShape + ".mid",
					startIndex, endIndex);
			
			if(!isExist)
				retFiles += destFilePath + prefix + "_" + currentShape + ".mif" + ",";
			
			//startIndex = endIndex + 1;

		}

		//close BufferedStream 
		br.close();
		
		return retFiles;
	}

	/**
	 * Output Mid file.
	 * (read from a source mid file,output it data from "startIndex" line to  "endIndex" line.
	 * The output file be specify by "path" )
	 * @param path 
	 * @param startIndex 
	 * @param endIndex
	 * @return the mid file is Exist or not 
	 * @throws IOException
	 */
	private boolean outpufMID(String path, int startIndex, int endIndex)
			throws IOException {
		
		
		
		File f=new File(path);
		boolean ret=f.exists();
		String midFileName = this.srcFilePath
				+ this.fileName.substring(0, this.fileName.lastIndexOf("."))
				+ ".mid";
		int lineCount = endIndex - startIndex;
		PrintWriter pw = new PrintWriter(new BufferedWriter(
				new FileWriter(f,true)));
		
		
		BufferedReader br = new BufferedReader(new FileReader(midFileName));

		while (startIndex != 0) {
			br.readLine();
			startIndex--;
		}
		while (lineCount > 0) {
			pw.println(br.readLine());
			lineCount--;
		}
		pw.flush();
		pw.close();
		return ret;
	}

	/**
	 *	Output Mif file.
	 * 
	 *  
	 * @param path specify the mif file's path
	 * @param mifHeader Mif file's Header
	 * @param mifSpatialInfo Mif file's Data section
	 * @return the mif file is Exist or not
	 * @throws IOException
	 */
	private boolean outputMIF(String path, StringBuffer mifHeader,
			StringBuffer mifSpatialInfo) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter pw;

		File f = new File(path);
		boolean ret = f.exists();

		pw = new PrintWriter(new BufferedWriter(new FileWriter(f,true)));
		
		/*if(mifHeader.toString()==null||mifSpatialInfo.toString()==null)
			return false;*/
		
		if(!ret)
			pw.println(mifHeader.toString());
		
		pw.println(mifSpatialInfo.toString());
		
		pw.flush();
		pw.close();

		return ret;

	}

	
	/**
	 * Utility for doing case independant string comparisons.
	 * @param str1
	 * @param str2
	 * @return
	 */
	private boolean isSame(String str1, String str2) {
		if (str1.equalsIgnoreCase(str2)) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * Creates a tokenizer for each line of input
	 * 
	 * @param br 
	 * @return
	 * @throws IOException
	 */
	private StringTokenizer getTokens(BufferedReader br)  {
		String line;
		try {
			WHILE: while ((line = br.readLine()) != null) {

				if (line.equals(""))
					continue WHILE; // skip blank lines

				// should return the tokenizer as soon as we have a line
				this.currentStr = line + "\n";
				return new StringTokenizer(line, " \t\n\r\f,()");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return null;
		
	}

	
}
