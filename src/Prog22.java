/**
 * 
 * Omar R.G. Mohamed || CSC460 || Prog22 || Dr. Lester McCann
 * 
 * This Program reads in the user input index file and binary file in that order,
 * checks the last 10 integer bytes in the binary file and
 * uses them to calculate the size of all the records,
 * count and field size.
 * 
 * The program prompts the user for record ID's to show records for,
 * 
 * There is no ArrayList use in this program,
 * everything is fetched through the seek() method,
 * which makes the program really fast.
 * 
 * This program should be able to handle all even or odd record counts.
 * 
 * Just make sure to call the program from command-line followed by the input filepath
 * e.g. java Prog22 lhl.idx MeteoriteLandings.bin
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Prog22 {
	
	
	

	public static void main(String[] args) throws IOException{
		
		//handle commandline argument
		String idxFile = args[0];
		String binFile = args[1];
		
		//String idxFile = "lhl.idx";
		//String binFile = "src/MeteoriteLandings.bin";
		File idxFileRef = new File(idxFile);
		File binFileRef = new File(binFile);
		RandomAccessFile idxDataStream = new RandomAccessFile(idxFileRef, "rw");
		RandomAccessFile binDataStream = new RandomAccessFile(binFileRef, "rw");
		
		// This part fetches lengths for each fields from the end of the bin file.
		long binFileLen = binDataStream.length();
		
		binDataStream.seek(binFileLen-4);
		int lenGeo = binDataStream.readInt();
		//System.out.println(lenGeo);
		binDataStream.seek(binFileLen-4-4);
		int lenReclong = binDataStream.readInt();
		//System.out.println(lenReclong);
		binDataStream.seek(binFileLen-4-4-4);
		int lenReclat = binDataStream.readInt();
		//System.out.println(lenReclat);
		binDataStream.seek(binFileLen-4-4-4-4);
		int lenYear = binDataStream.readInt();
		//System.out.println(lenYear);
		binDataStream.seek(binFileLen-4-4-4-4-4);
		int lenFall = binDataStream.readInt();
		//System.out.println(lenFall);
		binDataStream.seek(binFileLen-4-4-4-4-4-4);
		int lenMass = binDataStream.readInt();
		//System.out.println(lenMass);
		binDataStream.seek(binFileLen-4-4-4-4-4-4-4);
		int lenRecclass = binDataStream.readInt();
		//System.out.println(lenRecclass);
		binDataStream.seek(binFileLen-4-4-4-4-4-4-4-4);
		int lenNameType = binDataStream.readInt();
		//System.out.println(lenNameType);
		binDataStream.seek(binFileLen-4-4-4-4-4-4-4-4-4);
		int lenId = binDataStream.readInt();
		//System.out.println(lenId);
		binDataStream.seek(binFileLen-4-4-4-4-4-4-4-4-4-4);
		int lenName = binDataStream.readInt();
		//System.out.println(lenName);
		
		
		// Equation to find number of records in bin file
		int newBinLen = (int) (binFileLen/(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
		
		// find last H
		int idxLen = (int) idxDataStream.length();
		int bucketCount = (idxLen/4)/100;
		int lastH = (int) (Math.log(bucketCount) / Math.log(2))-1;
		//System.out.println("Last H: "+lastH);
		
		// loop-prompt user for id
		while(true) {
			System.out.print("Enter ID Query: ");
			Scanner usrInput = new Scanner(System.in);
			int usrId = usrInput.nextInt();
			if(usrId==0) {
				break;
			}
			
			// carry out search
			int k = usrId;
			int bucketOffset = (int) (k%(Math.pow(2,  lastH+1)));
			
			int tmpBuck1[][] = new int[50][2];
			//read bucket into memory
			for(int j = 0; j<50;j++) {
				int start = 0+j*2;
				idxDataStream.seek((bucketOffset*400)+(start*4));
				int readId = idxDataStream.readInt();
				idxDataStream.seek((bucketOffset*400)+(start*4)+4);
				int readIdx = idxDataStream.readInt();
				int[] readRecord = {readId, readIdx};
				tmpBuck1[j] = readRecord;
			}
			boolean recordFound = false;
			for (int i = 0; i<tmpBuck1.length;i++) {
				if(tmpBuck1[i][0]==k) {
					//System.out.println("Found: "+k);
					byte[] nameByNu = new byte[lenName];
					binDataStream.seek((tmpBuck1[i][1])*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
					binDataStream.readFully(nameByNu);
					String nameStrNu = new String(nameByNu);
					System.out.print("["+nameStrNu.trim()+"]");
					// add year and geolocation
					binDataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((tmpBuck1[i][1])*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					int year = binDataStream.readInt();
					if(year==-1) {
						System.out.print("["+"]");
					}
					else {
						System.out.print("["+year+"]");
					}
					byte[] geolocationBy = new byte[26];
					binDataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((tmpBuck1[i][1])*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					binDataStream.readFully(geolocationBy);
					String geolocationStr = new String(geolocationBy);
					System.out.print("["+geolocationStr.trim()+"]");
					System.out.println();
					
					recordFound = true;
					break;
				}
			}
			if(recordFound==false) {
				System.out.println("The key value \'"+k+"\' was not found.");
			}
			
			
			
			
		}
		System.out.println("0 Entered. Program Terminated");
		
		
	}
	
	
	
	
}
