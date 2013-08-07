package ynugis.utility;

/*
 * @author 冯涛，创建日期：2003-9-7
 * blog--http://apower.blogone.net
 * QQ:17463776
 * MSN:apower511@msn.com
 * E-Mail:VonPower@Gmail.com
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * This Class Support Global Tools for our System
 * 
 * @author VonPower
 * 
 */
public class GT {
	private static HashMap timeMap = new HashMap();

	// private static long endTime=0;
	public static void timeRecordStart(String key) {
		if (timeMap.keySet().contains(key)) {
			System.out.println("The Key:\'" + key + "\' has been Used!!!");
			return;
		}
		timeMap.put(key, new Long(System.currentTimeMillis()));

	}

	public static void timeRecordend(String key, String str) {

		if (timeMap.containsKey(key)) {
			Long startTime = (Long) timeMap.get(key);
			System.out.println(str + " consume time:"
					+ (System.currentTimeMillis() - startTime.longValue()));
			timeMap.remove(key);
		} else {
			System.out.println("call timeRecordStart(key) First!!!");
		}
	}

	/**
	 * 输出文本文件
	 * 
	 * @param filePath
	 * @param content
	 * @throws IOException
	 */
	public static void write2Txt(String filePath, String content)
			throws IOException {
		if (content == null)
			return;
		BufferedReader br = new BufferedReader(new StringReader(content));

		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(
				filePath, true)));
		String s = "";
		while ((s = br.readLine()) != null) {
			pw.println(s);
		}
		pw.close();

	}

	/**
	 * Write some infomation to .Log file
	 * 
	 * @param inLog
	 *            Content of .log file
	 * @throws IOException
	 */
	static public void writeLog(String inLog) throws IOException {
		if (inLog == null)
			return;
		BufferedReader br = new BufferedReader(new StringReader(inLog));
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(GV
				.getLog_FilePath(), true)));

		int lineCount = 1;

		String s;

		// SimpleDateFormat bartDateFormat =new
		// SimpleDateFormat("EEEE-MMMM-dd-yyyy");
		Date date = new Date();
		DateFormat fullDateFormat = DateFormat.getDateTimeInstance(
				DateFormat.FULL, DateFormat.FULL);

		pw.println();
		pw.println("Now Time:" + fullDateFormat.format(date));

		while ((s = br.readLine()) != null)
			pw.println("Line " + lineCount++ + ":" + s);

		pw.close();

	}

	/**
	 * Write some infomation to .Log file
	 * 
	 * @param inLog
	 *            Content of .log file
	 * @throws IOException
	 */
	static public void writeLog(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String outStr = null;
		String temp = br.readLine();
		while (temp != null) {
			// System.out.println(outStr);
			outStr += temp + "\n";
			temp = br.readLine();
		}
		GT.writeLog(outStr);

	}

	/**
	 * Parse input string(a full file path),get file path e.g: input
	 * "c:\haha.txt" ,the function will return "c:\"
	 * 
	 * @param fullFilePath
	 * @return file path
	 */
	static public String getFilePath(String fullFilePath) {
		String path = "";
		int index = fullFilePath.lastIndexOf(File.separator);
		// 为了包含 File.separator ，所以index + 1
		path = fullFilePath.substring(0, index);
		return path;
	}

	/**
	 * Parse input string(a full file path),get file name e.g: input
	 * "c:\haha.txt" ,the function will return "haha.txt"
	 * 
	 * @param fullFilePath
	 * @return file name (include extension)
	 */
	static public String getFileName(String fullFilePath) {
		String name = "";
		int index = fullFilePath.lastIndexOf(File.separator);
		name = fullFilePath.substring(index + 1, fullFilePath.length());
		return name;
	}

	/**
	 * Parse input string(a full file path),get file name without extension e.g:
	 * input "c:\haha.txt" ,the function will return "haha"
	 * 
	 * @param fullFilePath
	 * @return file name without file extension(suffix).
	 */
	static public String getFileNameWithoutExtension(String fullFilePath) {
		String nameWithoutExtension = "";
		String name = getFileName(fullFilePath);
		int dotIdx = name.lastIndexOf(".");
		nameWithoutExtension = name.substring(0, dotIdx);
		return nameWithoutExtension;
	}

	// 删除文件夹下所有内容，包括此文件夹
	public static void delAll(File f) throws IOException {
		if (!f.exists())// 文件夹不存在不存在
			throw new IOException("指定目录不存在:" + f.getName());

		boolean rslt = true;// 保存中间结果
		if (!(rslt = f.delete())) {// 先尝试直接删除
			// 若文件夹非空。枚举、递归删除里面内容
			File subs[] = f.listFiles();
			for (int i = 0; i <= subs.length - 1; i++) {
				if (subs[i].isDirectory())
					delAll(subs[i]);// 递归删除子文件夹内容
				rslt = subs[i].delete();// 删除子文件夹本身
			}
			rslt = f.delete();// 删除此文件夹本身
		}

		if (!rslt)
			throw new IOException("无法删除:" + f.getName());
		return;
	}

}
