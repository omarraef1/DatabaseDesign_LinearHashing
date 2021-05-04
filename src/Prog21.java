/**
 * 
 * Omar R.G. Mohamed || CSC460 || Prog21 || Dr. Lester McCann
 * 
 * This Program reads in the user input binary file,
 * checks the last 10 integer bytes in the file and
 * uses them to calculate the size of all the records,
 * count and field size.
 * 
 * The program then creates an index file called 'lhl.idx' based on records' ID numbers
 * 
 * After the index has been created, 
 * The program outputs to console:
 * 1. The total number of buckets present in the index file
 * 2. The number of records present in the lowest-occupancy bucket
 * 3. The number of records present in the highest-occupancy bucket
 * 4. the mean of all occupancies across all buckets
 * 
 * There is no ArrayList use in this program,
 * everything is fetched through the seek() method.
 * 
 * This program should be able to handle all even or odd record counts.
 * 
 * The main hashing logic happens in the main-method, however there are two more functions
 * responsible for computing the statistics and rehashing our lhl.idx file
 * 
 * P.S. The program takes some time to run on big data records.
 * 
 * Just make sure to call the program from command-line followed by the input filepath
 * e.g. java Prog21 MeteoriteLandings.bin
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Prog21 {

	
	public static void main(String[] args) throws FileNotFoundException{
		
		// personal steps (new):
		// 1. create two buckets in idx file
		// 2. loop sequentially, and insert where applicable
		// 3. if insertion bucket is full, extend file by 2 buckets and rehash file
		// 4. decrement i and insert record we left off at
		
		
		//handle commandline argument
		String useerInput = args[0];

		//String useerInput = "src/MeteoriteLandings.bin";
		String inFileName = useerInput.trim();
		File fileRef = new File(inFileName);
		RandomAccessFile inDataStream = new RandomAccessFile(fileRef,"rw");
		long fileLen = 0;
		
		
		// create idx file
		String outFileName = "lhl.idx";
		File outputFileRef = new File(outFileName);
		RandomAccessFile outDataStream = new RandomAccessFile(outputFileRef,"rw");
		
		
		
		try {
			fileLen = inDataStream.length();
			//System.out.println(fileLen);
			
			int recordCount = 0;
			
			
			// This part fetches lengths for each field from the end of the file.
			inDataStream.seek(fileLen-4);
			int lenGeo = inDataStream.readInt();
			//System.out.println(lenGeo);
			inDataStream.seek(fileLen-4-4);
			int lenReclong = inDataStream.readInt();
			//System.out.println(lenReclong);
			inDataStream.seek(fileLen-4-4-4);
			int lenReclat = inDataStream.readInt();
			//System.out.println(lenReclat);
			inDataStream.seek(fileLen-4-4-4-4);
			int lenYear = inDataStream.readInt();
			//System.out.println(lenYear);
			inDataStream.seek(fileLen-4-4-4-4-4);
			int lenFall = inDataStream.readInt();
			//System.out.println(lenFall);
			inDataStream.seek(fileLen-4-4-4-4-4-4);
			int lenMass = inDataStream.readInt();
			//System.out.println(lenMass);
			inDataStream.seek(fileLen-4-4-4-4-4-4-4);
			int lenRecclass = inDataStream.readInt();
			//System.out.println(lenRecclass);
			inDataStream.seek(fileLen-4-4-4-4-4-4-4-4);
			int lenNameType = inDataStream.readInt();
			//System.out.println(lenNameType);
			inDataStream.seek(fileLen-4-4-4-4-4-4-4-4-4);
			int lenId = inDataStream.readInt();
			//System.out.println(lenId);
			inDataStream.seek(fileLen-4-4-4-4-4-4-4-4-4-4);
			int lenName = inDataStream.readInt();
			//System.out.println(lenName);
			
			
			// Equation to find number of records in file
			int newLen = (int) (fileLen/(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
			
			
			recordCount = newLen;
			
			//start reading n hashing
			
			// 1 bucket is 100 integers 50*(idNum,idx)
			// 1 bucket is 100*4= 400 bytes

			
			int tmpBuck1[][] = new int[50][2];
			
			int bucketCount = 2;
			
			// write new buckets
			outDataStream.seek(outDataStream.length());
			for (int i=0; i<tmpBuck1.length;i++) {
				outDataStream.writeInt(tmpBuck1[i][0]);
				outDataStream.writeInt(tmpBuck1[i][1]);
			}
			for (int i=0; i<tmpBuck1.length;i++) {
				outDataStream.writeInt(tmpBuck1[i][0]);
				outDataStream.writeInt(tmpBuck1[i][1]);
			}
			
			
			// read from bin file
			int H = 0;
			
			for (int i=0;i<recordCount;i++) {
				int start = 0+i;
				inDataStream.seek(lenName+((start)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
				int id = inDataStream.readInt();
				int[] record = {id, start};
				
				int k = id;
				int bucketOffset = (int) (k%(Math.pow(2,  H+1)));
				
				//read bucket into memory
				for(int j = 0; j<50;j++) {
					int starty = 0+j*2;
					outDataStream.seek((bucketOffset*400)+(starty*4));
					int readId = outDataStream.readInt();
					outDataStream.seek((bucketOffset*400)+(starty*4)+4);
					int readIdx = outDataStream.readInt();
					int[] readRecord = {readId, readIdx};
					tmpBuck1[j] = readRecord;
				}
				
				
				int targetIdx = 0;
				while((tmpBuck1[targetIdx][0]!=0)) {
					targetIdx++;
					if(targetIdx>49) {
						break;
					}
				}
				if(targetIdx>tmpBuck1.length-1) {
					//increment H, call rehashing function, increment bucketCount by 2, decrement i to (re)hash current record
					H++;
					reHashFile(outDataStream,H);
					bucketCount++;
					bucketCount++;
					i--; // decrement i to (re)hash current record
					tmpBuck1 = new int[50][2];
				}
				else {
					tmpBuck1[targetIdx] = record; //insert record
					// write bucket back to file
					outDataStream.seek(bucketOffset*400);
					for (int g=0;g<tmpBuck1.length;g++) {
						outDataStream.writeInt(tmpBuck1[g][0]);
						outDataStream.writeInt(tmpBuck1[g][1]);
					}
					
				}
				
				
			}
			
			
			// print idx file content (uncomment this section if you'd like to take a look
			//long finalFileLen = outDataStream.length();
			//long boundary = ((finalFileLen/4)/100);
			/*
			for (int i = 0; i<50*boundary;i++) {
				int start = 0+i*2;
				outDataStream.seek(start*4);
				int readId = outDataStream.readInt();
				outDataStream.seek((start*4)+4);
				int readIdx = outDataStream.readInt();
				System.out.println(i+": "+readId+","+readIdx);
			}
			*/
			//System.out.println(outDataStream.length());
			
			
			//display number of buckets
			long newerLen = (outDataStream.length()/4)/100;
			System.out.println("Total Number of Buckets: "+newerLen);
			
			//display number of records in lowest-occupancy bucket & highest-occupancy bucket
			int[] minOccAndMeanOcc = leastOccupancy(outDataStream);	//call leastOccupancy function
			System.out.println("Number of Records in Lowest-Occupancy Bucket: "+minOccAndMeanOcc[0]);
			System.out.println("Number of Records in Highest-Occupancy Bucket: "+minOccAndMeanOcc[3]);
			
			//display mean of occupancies across all buckets
			System.out.println("Mean Occupancies Across All Buckets: "+minOccAndMeanOcc[1]);
			
			//System.out.println("Last H: "+H);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static int[] leastOccupancy(RandomAccessFile ds) throws IOException {
		int totalRecordCount = 0;
		int minRecord = 50;
		int maxRecord = 0;
		long finalFileLen = ds.length();
		long boundary = ((finalFileLen/4)/100);
		for(int i = 0; i<boundary;i++) {
			int tmpBuck1[][] = new int[50][2];

			int buckOffset = 400*i;
			for(int j = 0; j<50;j++) {
				int start = 0+j*2;
				ds.seek(buckOffset+(start*4));
				int readId = ds.readInt();
				ds.seek(buckOffset+(start*4)+4);
				int readIdx = ds.readInt();
				int[] readRecord = {readId, readIdx};
				tmpBuck1[j] = readRecord;
			}
			
			int tmpMin = 0;
			for(int g = 0; g<50;g++) {
				if((tmpBuck1[g][0]!=0)) {
					tmpMin++;
					totalRecordCount++;
				}
			}
			if(tmpMin<minRecord) {
				minRecord = tmpMin;
			}
			
			if(tmpMin>maxRecord) {
				maxRecord = tmpMin;
			}
			
		}
		
		int meanOcc = (int) (totalRecordCount/boundary);
		
		int[] minAndMean = {minRecord, meanOcc, totalRecordCount, maxRecord};
		
		return minAndMean;
	}
	
	public static void reHashFile(RandomAccessFile ds, int hRef) throws IOException {
		
		//rehash file

		long finalFileLen = ds.length();
		long boundary = ((finalFileLen/4)/100);
		for(int i = 0; i<boundary;i++) {

			int tmpBuck1[][] = new int[50][2];
			int tmpBuck2[][] = new int[50][2];
			
			int buckOffset = 400*i;
			for(int j = 0; j<50;j++) {
				int start = 0+j*2;
				ds.seek(buckOffset+(start*4));
				int readId = ds.readInt();
				ds.seek(buckOffset+(start*4)+4);
				int readIdx = ds.readInt();
				int[] readRecord = {readId, readIdx};
				tmpBuck1[j] = readRecord;
			}
			
			for(int tI = 0; tI<50;tI++) {
				if((tmpBuck1[tI][0]!=0)) {
					int[] readRecord2 = {tmpBuck1[tI][0],tmpBuck1[tI][1]};
					int k = tmpBuck1[tI][0];
					int bucketOffset = (int) (k%(Math.pow(2, hRef+1)));
					
					if(bucketOffset==i) {
					}
					else {
						int targetIdx2 = 0;
						while((tmpBuck2[targetIdx2][0]!=0)) {
							targetIdx2++;
							if(targetIdx2>49) {
								break;
							}
						}
						tmpBuck2[targetIdx2] = readRecord2;
						int[] emptyRecord = new int[2];
						tmpBuck1[tI] = emptyRecord;
					}
				}
			}
			// write new buckets
			ds.seek(buckOffset);
			for(int j = 0; j<tmpBuck1.length;j++) {
				ds.writeInt(tmpBuck1[j][0]);
				ds.writeInt(tmpBuck1[j][1]);
			}
				//2nd bucket (end of file)
			ds.seek(ds.length());
			for(int j = 0; j<tmpBuck2.length;j++) {
				ds.writeInt(tmpBuck2[j][0]);
				ds.writeInt(tmpBuck2[j][1]);
			}
		}
	}
	
	
	
	
}
