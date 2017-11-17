package com.rf.core.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

import com.rf.core.driver.website.RFWebsiteDriver;


/**
 * Utility class for common functions.
 * 
 * @author GSPANN
 * 
 */
public class CommonUtils {

	public static FileOutputStream getFileOutputStream(final String fileName) throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		return fos;
	}

	public static InputStream loadInputStream(final String classpathLocation, final String fileSystemLocation)
			throws IOException {
		InputStream in = null;

		in = CommonUtils.class.getResourceAsStream(classpathLocation);
		if (in == null) {
			in = new FileInputStream(fileSystemLocation);
		}
		return in;
	}

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns test dat file path attached at test class
	 * 
	 * @return the path string
	 */
	public static String getTestDataFilePath(final String className) {
		String fileSep = File.separator;
		return System.getProperty("user.dir") + fileSep + "test"+fileSep+"resources"+fileSep+"testdata"+fileSep + className + ".xlsx";
	}


	/**
	 * Returns Integer value of Amount
	 * 
	 * @param value
	 * @return - Integer Value
	 */
	public static int getIntVal(String value) {
		String stringVal = value;
		if (value.contains(".")) {
			String[] valArray = stringVal.split("[.]");
			stringVal = valArray[0];
		}
		if (value.contains(",")) {
			stringVal = stringVal.replace(",", "");
		}
		return Integer.parseInt(stringVal);
	}

	/**
	 * Generates random integer from a range
	 * 
	 * @param min
	 * @param max
	 * @return - random integer
	 */
	public static int getRandomNum(int min, int max) {
		return new Random().nextInt((max - min) + 1) + min;
	} 

	public static String getRandomWord(int length) {
		String r = "";
		for(int i = 0; i < length; i++) {
			r += (char)(Math.random() * 26 + 97);
		}
		return r;
	}
	
	public static String getCurrentDate(String dayFormat, TimeZone timeZone) {
		Date date = new Date();
		/* Specifying the format */
		DateFormat requiredFormat = new SimpleDateFormat(dayFormat);
		/* Setting the Timezone */
		requiredFormat.setTimeZone(timeZone);
		/* Picking the day value in the required Format */
		String strCurrentDay = requiredFormat.format(date).toUpperCase();
		return strCurrentDay;
	}

	/***
	 * This method take the current window handle
	 * 
	 * @param 
	 * @return current window handle
	 * 
	 */
	public static String getCurrentWindowHandle(){
		String currentWindowHandle = RFWebsiteDriver.driver.getWindowHandle();
		return currentWindowHandle;
	}

	public static String getContentsOfPDF(String testUrl) throws InvalidPasswordException, IOException{
		URL url = new URL(testUrl);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		PDDocument doc = PDDocument.load(bis);
		String text = new PDFTextStripper().getText(doc);
		doc.close();
		bis.close();
		return text;
	}
	
	public static String getCurrentTimeStamp(){
		return new SimpleDateFormat("MM.dd.HH.mm.ss").format(new Timestamp(System.currentTimeMillis())).replace(".", "");
	}
	
	public static String tossTheCoin(){
		if(getRandomNum(2, 20)%2==0)
			return "heads";
		else
			return "tails";
	}
	
	public static void createFolder(String folderNameWithLocation){	
		try {
			File fDir = new File(System.getProperty("user.dir")+"\\"+folderNameWithLocation);
			if (!fDir.exists()) {
				fDir.mkdirs();
			}
			if(folderNameWithLocation.contains("logs")==false)
			FileUtils.cleanDirectory(fDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
