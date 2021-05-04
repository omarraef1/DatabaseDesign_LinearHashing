/**
 * 
 * Omar R.G. Mohamed || CSC460 || Prog1A || Dr. Lester McCann
 * 
 * This Program reads in a csv file of any size with the already given spec format
 * Trims, cleans, and normalizes all fields
 * Then sorts the data record by year field
 * Afterwards, it generalizes and sets a maximum length for string fields
 * and replaces empty fields with either empty strings or -1 or -1.0 if it's a string, int, or double
 * Finally, the program writes the sorted record to a binary file .bin
 * 
 * 
 * 
 * Unfortunately, due to time pressure, I couldn't modulate the program to separate methods in time,
 * Therefore the whole process is nicely commented through sections in the main() method. 
 * Otherwise it should be working okay.
 * 
 * Just make sure to call the program from command-line followed by the input file
 * e.g. java Prog1A MeteoriteLandings.csv
 * 
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 
 * @author omargebril
 *
 */
public class Prog1A {
	
	// Personal Checklist
	// 1. Read file correctly (done)
	// 2. find maximum length in all fields after trimming (done) 
	// 3. Sort file by Year field to an Object array (done)
	// 4. fix max lengths of all fields and replace -1s as necessary (done)
	// 4. write to binary (alphanumeric?)
	
	
	/**
	 * 
	 * @param args aka commandline file
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		int nameFieldMaxLen = 0;
		int idFieldMaxLen = 0;
		int nameTypeFieldMaxLen = 0;
		int recclassFieldMaxLen = 0;
		int massFieldMaxLen = 0;
		int fallFieldMaxLen = 0;
		int yearFieldMaxLen = 0;	// 6th index
		int reclatFieldMaxLen = 0;
		int reclongFieldMaxLen = 0;
		int geolocationFieldMaxLen = 0;
		
		String inFileName = null;
		String outFileName = null;
		
		//handle commandline argument
		String usrInput = args[0];
		inFileName = usrInput.trim();
		String[] firstSplit = inFileName.split("/");
		String[] inFileSplit = firstSplit[firstSplit.length-1].split("\\.");
		outFileName = inFileSplit[0].toString()+".bin";
		
		// READ FILE INTO ARRAY
		
		ArrayList<String[]> inFileContent = new ArrayList<>();
		
		BufferedReader br = new BufferedReader(new FileReader(inFileName));
		String line = "";
		try {
			while ((line = br.readLine()) != null) {
				inFileContent.add(line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)",-1)); //this regex makes sure we only split by csv comma delimiters and not commas included in data
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// exclude first row & normalize asciis & strip year & exclude double quotes and commas included in data
	
		ArrayList<String[]> fixedInFileContent = new ArrayList<>();
		
		for (int i = 1; i<inFileContent.size();i++) {
			if(inFileContent.get(i)[6].length()>0) {
				String[] yearField = inFileContent.get(i)[6].split(" ");
				String[] firstPart = yearField[0].split("/");
				String year = firstPart[2];
				inFileContent.get(i)[6] = year;
			}
			String nameField = inFileContent.get(i)[0];
			String newName = Normalizer.normalize(nameField, Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", "");
			inFileContent.get(i)[0] = newName;
			
			// maximize length of empty fields, FIX: after sorting, replace empty double fields (id, mass, year, reclat, reclong) with -1 or -1.0 
			
			
			if(inFileContent.get(i)[3].length()>0) {
				if(inFileContent.get(i)[3].charAt(0)=='\"') {
					if(inFileContent.get(i)[3].charAt(inFileContent.get(i)[3].length()-1)=='\"') {
						String recNoQuote = inFileContent.get(i)[3].substring(1, inFileContent.get(i)[3].length()-1);
						inFileContent.get(i)[3] =recNoQuote;
					}
					else {
						String recNoQuote = inFileContent.get(i)[3].substring(1);
						inFileContent.get(i)[3] =recNoQuote;
					}
				}
				if(inFileContent.get(i)[3].charAt(inFileContent.get(i)[3].length()-1)=='\"') {
					String recNoQuote = inFileContent.get(i)[3].substring(1, inFileContent.get(i)[3].length()-1);
					inFileContent.get(i)[3] =recNoQuote;
				}
				
			}
			if(inFileContent.get(i)[4].length()>0) {
				if(inFileContent.get(i)[4].charAt(0)=='\"') {
					if(inFileContent.get(i)[4].charAt(inFileContent.get(i)[4].length()-1)=='\"') {
						String massNoQuote = inFileContent.get(i)[4].substring(1, inFileContent.get(i)[4].length()-1);
						String massNoQuoteNoComma = massNoQuote.replaceAll(",", "");
						inFileContent.get(i)[4] = massNoQuoteNoComma;
					}
					else {
						String massNoQuote = inFileContent.get(i)[4].substring(1);
						String massNoQuoteNoComma = massNoQuote.replaceAll(",", "");
						inFileContent.get(i)[4] = massNoQuoteNoComma;
					}
				}
				if(inFileContent.get(i)[4].charAt(inFileContent.get(i)[4].length()-1)=='\"') {
					String massNoQuote = inFileContent.get(i)[4].substring(1, inFileContent.get(i)[4].length()-1);
					String massNoQuoteNoComma = massNoQuote.replaceAll(",", "");
					inFileContent.get(i)[4] = massNoQuoteNoComma;
				}
				
			}
			fixedInFileContent.add(inFileContent.get(i));
			
		}
		
		// find maximum length of each field
		
		for (int i = 0; i<fixedInFileContent.size();i++) {
			if(fixedInFileContent.get(i)[0].length()>nameFieldMaxLen) {
				nameFieldMaxLen = fixedInFileContent.get(i)[0].length();
			}
			if(fixedInFileContent.get(i)[1].length()>idFieldMaxLen) {
				idFieldMaxLen = fixedInFileContent.get(i)[1].length();
			}
			if(fixedInFileContent.get(i)[2].length()>nameTypeFieldMaxLen) {
				nameTypeFieldMaxLen = fixedInFileContent.get(i)[2].length();
			}
			if(fixedInFileContent.get(i)[3].length()>recclassFieldMaxLen) {
				recclassFieldMaxLen = fixedInFileContent.get(i)[3].length();
			}
			if(fixedInFileContent.get(i)[4].length()>massFieldMaxLen) {
				massFieldMaxLen = fixedInFileContent.get(i)[4].length();
			}
			if(fixedInFileContent.get(i)[5].length()>fallFieldMaxLen) {
				fallFieldMaxLen = fixedInFileContent.get(i)[5].length();
			}
			if(fixedInFileContent.get(i)[6].length()>yearFieldMaxLen) {
				yearFieldMaxLen = fixedInFileContent.get(i)[6].length();
			}
			if(fixedInFileContent.get(i)[7].length()>reclatFieldMaxLen) {
				reclatFieldMaxLen = fixedInFileContent.get(i)[7].length();
			}
			if(fixedInFileContent.get(i)[8].length()>reclongFieldMaxLen) {
				reclongFieldMaxLen = fixedInFileContent.get(i)[8].length();
			}
			if(fixedInFileContent.get(i)[9].length()>geolocationFieldMaxLen) {
				geolocationFieldMaxLen = fixedInFileContent.get(i)[9].length();
			}
		}
		
		// fix string lengths and maximize them
		
		for (int i = 0; i<fixedInFileContent.size();i++) {

			if(fixedInFileContent.get(i)[0].length()<1) {
				fixedInFileContent.get(i)[0] = new String(new char[nameFieldMaxLen]).replace("\0", " ");
			}
			else {
				String originStr = fixedInFileContent.get(i)[0];
				int diff = nameFieldMaxLen-originStr.length();
				String strExt = new String(new char[diff]).replace("\0", " ");
				String maxedLengthStr = originStr+strExt;
				fixedInFileContent.get(i)[0] = maxedLengthStr;
			}
			if(fixedInFileContent.get(i)[2].length()<1) {
				fixedInFileContent.get(i)[2] = new String(new char[nameTypeFieldMaxLen]).replace("\0", " ");
			}
			else {
				String originStr = fixedInFileContent.get(i)[2];
				int diff = nameTypeFieldMaxLen-originStr.length();
				String strExt = new String(new char[diff]).replace("\0", " ");
				String maxedLengthStr = originStr+strExt;
				fixedInFileContent.get(i)[2] = maxedLengthStr;
			}
			if(fixedInFileContent.get(i)[3].length()<1) {
				fixedInFileContent.get(i)[3] = new String(new char[recclassFieldMaxLen]).replace("\0", " ");
			}
			else {
				String originStr = fixedInFileContent.get(i)[3];
				int diff = recclassFieldMaxLen-originStr.length();
				String strExt = new String(new char[diff]).replace("\0", " ");
				String maxedLengthStr = originStr+strExt;
				fixedInFileContent.get(i)[3] = maxedLengthStr;
			}
			if(fixedInFileContent.get(i)[5].length()<1) {
				fixedInFileContent.get(i)[5] = new String(new char[fallFieldMaxLen]).replace("\0", " ");
			}
			else {
				String originStr = fixedInFileContent.get(i)[5];
				int diff = fallFieldMaxLen-originStr.length();
				String strExt = new String(new char[diff]).replace("\0", " ");
				String maxedLengthStr = originStr+strExt;
				fixedInFileContent.get(i)[5] = maxedLengthStr;
			}
			if(fixedInFileContent.get(i)[9].length()<1) {
				fixedInFileContent.get(i)[9] = new String(new char[geolocationFieldMaxLen]).replace("\0", " ");
			}
			else {
				String originStr = fixedInFileContent.get(i)[9];
				String noQuoteStr = originStr.substring(1,originStr.length()-1);
				int diff = geolocationFieldMaxLen-noQuoteStr.length();
				String strExt = new String(new char[diff]).replace("\0", " ");
				String maxedLengthStr = noQuoteStr+strExt;
				fixedInFileContent.get(i)[9] = maxedLengthStr;
			}
		}
		
		// fix remaining empty fields to -1/-1.0 to a a new ArrayList<object[]>

		ArrayList<Object[]> finalInFileContent = new ArrayList<>();
		
		for (int i = 0;i<fixedInFileContent.size();i++) {
			int idInt;
			double massInt;
			int yearInt;
			double reclatInt;
			double reclongInt;
			
			if (fixedInFileContent.get(i)[1].length()<1) {
				idInt = -1;
			}
			else {
				idInt = Integer.valueOf(fixedInFileContent.get(i)[1]);
			}
			if (fixedInFileContent.get(i)[4].length()<1) {
				massInt = -1.0;
			}
			else {
				massInt = Double.valueOf(fixedInFileContent.get(i)[4]);
			}
			if (fixedInFileContent.get(i)[6].length()<1) {
				yearInt = -1;
			}
			else {
				yearInt = Integer.valueOf(fixedInFileContent.get(i)[6]);
			}
			if (fixedInFileContent.get(i)[7].length()<1) {
				reclatInt = -1.0;
			}
			else {
				reclatInt = Double.valueOf(fixedInFileContent.get(i)[7]);
			}
			if (fixedInFileContent.get(i)[8].length()<1) {
				reclongInt = -1.0;
			}
			else {
				reclongInt = Double.valueOf(fixedInFileContent.get(i)[8]);
			}
			
			Object[] replacement = {fixedInFileContent.get(i)[0],idInt, fixedInFileContent.get(i)[2], fixedInFileContent.get(i)[3], 
					massInt, fixedInFileContent.get(i)[5], yearInt, reclatInt, reclongInt, fixedInFileContent.get(i)[9]};
			
			finalInFileContent.add(replacement);
		}
		
		// Sort content

		Collections.sort(finalInFileContent, new Comparator<Object[]>() {

			@Override
			public int compare(Object[] o1, Object[] o2) {
				return Integer.compare((int)o1[6], (int)o2[6]);
			}
			
		});
		
		//add max lengths to end of file
		
		//Object[] maxLengths = {nameFieldMaxLen,-1,nameTypeFieldMaxLen, recclassFieldMaxLen,
		//		-1.0, fallFieldMaxLen, -1, -1.0,-1.0,geolocationFieldMaxLen};
		//finalInFileContent.add(maxLengths);
		
		
		// print sorted array
		/*
		for (int i = 45715; i<finalInFileContent.size();i++) {
			System.out.println(finalInFileContent.get(i)[0] + " " + finalInFileContent.get(i)[1] + " " + finalInFileContent.get(i)[2] + 
				 " " + finalInFileContent.get(i)[3] + " " + finalInFileContent.get(i)[4] + " " + finalInFileContent.get(i)[5] + " " + 
				 finalInFileContent.get(i)[6] + " " + finalInFileContent.get(i)[7] + " " + finalInFileContent.get(i)[8] + " " + finalInFileContent.get(i)[9]);
	
		}
		System.out.println(finalInFileContent.size());
		
		*/
		
		// write to binary file
		
		File outputFileRef = new File(outFileName);
		RandomAccessFile dataStream = new RandomAccessFile(outputFileRef,"rw");
		
		for (int i = 0; i<finalInFileContent.size();i++) {
			StringBuffer name = new StringBuffer(finalInFileContent.get(i)[0].toString());
			name.setLength(nameFieldMaxLen);
			int id = (int) finalInFileContent.get(i)[1];
			StringBuffer nameType = new StringBuffer(finalInFileContent.get(i)[2].toString());
			nameType.setLength(nameTypeFieldMaxLen);
			StringBuffer recclass = new StringBuffer(finalInFileContent.get(i)[3].toString());
			recclass.setLength(recclassFieldMaxLen);
			double mass = (double) finalInFileContent.get(i)[4];
			StringBuffer fall = new StringBuffer(finalInFileContent.get(i)[5].toString());
			fall.setLength(fallFieldMaxLen);
			int year = (int) finalInFileContent.get(i)[6];
			double reclat = (double) finalInFileContent.get(i)[7];
			double reclong = (double) finalInFileContent.get(i)[8];
			StringBuffer geolocation = new StringBuffer(finalInFileContent.get(i)[9].toString());
			geolocation.setLength(geolocationFieldMaxLen);
			
			try {
				dataStream.writeBytes(name.toString());
				dataStream.writeInt(id);
				dataStream.writeBytes(nameType.toString());
				dataStream.writeBytes(recclass.toString());
				dataStream.writeDouble(mass);
				dataStream.writeBytes(fall.toString());
				dataStream.writeInt(year);
				dataStream.writeDouble(reclat);
				dataStream.writeDouble(reclong);
				dataStream.writeBytes(geolocation.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		
		
		}
		try {
			dataStream.writeInt(nameFieldMaxLen);
			dataStream.writeInt(idFieldMaxLen);
			dataStream.writeInt(nameTypeFieldMaxLen);
			dataStream.writeInt(recclassFieldMaxLen);
			dataStream.writeInt(-1);
			dataStream.writeInt(fallFieldMaxLen);
			dataStream.writeInt(-1);
			dataStream.writeInt(-1);
			dataStream.writeInt(-1);
			dataStream.writeInt(geolocationFieldMaxLen);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
