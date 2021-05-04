/**
 * 
 * Omar R.G. Mohamed || CSC460 || Prog1B || Dr. Lester McCann
 * 
 * This Program reads in the user input binary file,
 * checks the last 10 integer bytes in the file and
 * uses them to calculate the size of all the records,
 * count and field size.
 * Afterwards, it prints First, Mid, Last Five (Mid Four if even record count),
 * Records in the binary file.
 * 
 * The program prompts the user for years to show records for,
 * Then it does exponential binary search and finds the records of those years. (-1 years omitted since you cannot search for empty years)
 * 
 * There is not ArrayList use in this program,
 * everything is fetched through the seek() method,
 * which makes the program really fast.
 * 
 * This program should be able to handle all even or odd record counts.
 * 
 * 
 * 
 * Unfortunately, due to time pressure, I couldn't modulate the program to separate methods in time,
 * Therefore the whole process is nicely commented through sections in the main() method. 
 * Otherwise it should be working okay.
 * 
 * Just make sure to call the program from command-line followed by the input file
 * e.g. java Prog1B MeteoriteLandings.bin
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 * @author omargebril
 *
 */
public class Prog1B {

	
	/**
	 * 
	 * @param args aka commandline file
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		
		
		//handle commandline argument
		String useerInput = args[0];
		String inFileName = useerInput.trim();
		File fileRef = new File(inFileName);
		RandomAccessFile dataStream = new RandomAccessFile(fileRef,"rw");
		long fileLen = 0;
		
		try {
			fileLen = dataStream.length(); //find length of whole file
			
			int recordCount = 0;
			
			
			// This part fetches lengths for each fields from the end of the file.
			dataStream.seek(fileLen-4);
			int lenGeo = dataStream.readInt();
			//System.out.println(lenGeo);
			dataStream.seek(fileLen-4-4);
			int lenReclong = dataStream.readInt();
			//System.out.println(lenReclong);
			dataStream.seek(fileLen-4-4-4);
			int lenReclat = dataStream.readInt();
			//System.out.println(lenReclat);
			dataStream.seek(fileLen-4-4-4-4);
			int lenYear = dataStream.readInt();
			//System.out.println(lenYear);
			dataStream.seek(fileLen-4-4-4-4-4);
			int lenFall = dataStream.readInt();
			//System.out.println(lenFall);
			dataStream.seek(fileLen-4-4-4-4-4-4);
			int lenMass = dataStream.readInt();
			//System.out.println(lenMass);
			dataStream.seek(fileLen-4-4-4-4-4-4-4);
			int lenRecclass = dataStream.readInt();
			//System.out.println(lenRecclass);
			dataStream.seek(fileLen-4-4-4-4-4-4-4-4);
			int lenNameType = dataStream.readInt();
			//System.out.println(lenNameType);
			dataStream.seek(fileLen-4-4-4-4-4-4-4-4-4);
			int lenId = dataStream.readInt();
			//System.out.println(lenId);
			dataStream.seek(fileLen-4-4-4-4-4-4-4-4-4-4);
			int lenName = dataStream.readInt();
			//System.out.println(lenName);
			
			
			// Equation to find number of records in file
			int newLen = (int) (fileLen/(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
			
			
			recordCount = newLen;
			
			
			// if there exists less than 5 records then
			// we print those available records for each category (3 times)
			if(recordCount<5) {
				System.out.println("First Five: ");
				for (int j=0; j<recordCount;j++) {
					int firstStart = 0+j;
					byte[] nameByNu = new byte[lenName];
					dataStream.seek((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
					dataStream.readFully(nameByNu);
					String nameStrNu = new String(nameByNu);
					System.out.print("["+nameStrNu.trim()+"]");
					// add year and geolocation
					dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					int year = dataStream.readInt();
					if(year==-1) {
						System.out.print("["+"]");
					}
					else {
						System.out.print("["+year+"]");
					}
					byte[] geolocationBy = new byte[26];
					dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					dataStream.readFully(geolocationBy);
					String geolocationStr = new String(geolocationBy);
					System.out.print("["+geolocationStr.trim()+"]");
				}
				System.out.println();
				System.out.println("Middle Five: ");
				for (int j=0; j<recordCount;j++) {
					int firstStart = 0+j;
					byte[] nameByNu = new byte[lenName];
					dataStream.seek((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
					dataStream.readFully(nameByNu);
					String nameStrNu = new String(nameByNu);
					System.out.print("["+nameStrNu.trim()+"]");
					// add year and geolocation
					dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					int year = dataStream.readInt();
					if(year==-1) {
						System.out.print("["+"]");
					}
					else {
						System.out.print("["+year+"]");
					}
					byte[] geolocationBy = new byte[26];
					dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					dataStream.readFully(geolocationBy);
					String geolocationStr = new String(geolocationBy);
					System.out.print("["+geolocationStr.trim()+"]");
				}
				System.out.println();
				System.out.println("Last Five: ");
				for (int j=0; j<recordCount;j++) {
					int firstStart = 0+j;
					byte[] nameByNu = new byte[lenName];
					dataStream.seek((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
					dataStream.readFully(nameByNu);
					String nameStrNu = new String(nameByNu);
					System.out.print("["+nameStrNu.trim()+"]");
					// add year and geolocation
					dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					int year = dataStream.readInt();
					if(year==-1) {
						System.out.print("["+"]");
					}
					else {
						System.out.print("["+year+"]");
					}
					byte[] geolocationBy = new byte[26];
					dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					dataStream.readFully(geolocationBy);
					String geolocationStr = new String(geolocationBy);
					System.out.print("["+geolocationStr.trim()+"]");
				}
				System.out.println();
				
				
			}
			
			
			// Otherwise, we divide file length by 2 for the Middle five/four if even record coune
			// and fetch those records specifically
			
			
			else {
				

				if(recordCount%2==0) {
					// even record count
					//first 5
					System.out.println("First Five: ");
					
					for (int j=0; j<5;j++) {
						int firstStart = 0+j;
						byte[] nameByNu = new byte[lenName];
						dataStream.seek((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
						dataStream.readFully(nameByNu);
						String nameStrNu = new String(nameByNu);
						System.out.print("["+nameStrNu.trim()+"]");
						// add year and geolocation
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						int year = dataStream.readInt();
						if(year==-1) {
							System.out.print("["+"]");
						}
						else {
							System.out.print("["+year+"]");
						}
						byte[] geolocationBy = new byte[26];
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						dataStream.readFully(geolocationBy);
						String geolocationStr = new String(geolocationBy);
						System.out.print("["+geolocationStr.trim()+"]");
						System.out.println();
					}
					System.out.println();
					//mid 4
					System.out.println("Middle Four: ");
					for (int j=0;j<4;j++) {
						int midStart = ((recordCount/2)-2)+j;
						byte[] nameByNu = new byte[lenName];
						dataStream.seek((midStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
						dataStream.readFully(nameByNu);
						String nameStrNu = new String(nameByNu);
						System.out.print("["+nameStrNu.trim()+"]");
						// add year and geolocation
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((midStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						int year = dataStream.readInt();
						if(year==-1) {
							System.out.print("["+"]");
						}
						else {
							System.out.print("["+year+"]");
						}
						byte[] geolocationBy = new byte[26];
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((midStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						dataStream.readFully(geolocationBy);
						String geolocationStr = new String(geolocationBy);
						System.out.print("["+geolocationStr.trim()+"]");
						System.out.println();
						
					}
					System.out.println();
					//last 5
					System.out.println("Last 5: ");
					for (int j=0; j<5;j++) {
						int lastStart = recordCount-5+j;
						byte[] nameByNu = new byte[lenName];
						dataStream.seek((lastStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
						dataStream.readFully(nameByNu);
						String nameStrNu = new String(nameByNu);
						System.out.print("["+nameStrNu.trim()+"]");
						// add year and geolocation
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((lastStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						int year = dataStream.readInt();
						if(year==-1) {
							System.out.print("["+"]");
						}
						else {
							System.out.print("["+year+"]");
						}
						byte[] geolocationBy = new byte[26];
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((lastStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						dataStream.readFully(geolocationBy);
						String geolocationStr = new String(geolocationBy);
						System.out.print("["+geolocationStr.trim()+"]");
						System.out.println();
					}
					System.out.println();
				
				}
				else { // if record count is odd
					
					System.out.println("First Five: ");
					
					for (int j=0; j<5;j++) {
						int firstStart = 0+j;
						byte[] nameByNu = new byte[lenName];
						dataStream.seek((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
						dataStream.readFully(nameByNu);
						String nameStrNu = new String(nameByNu);
						System.out.print("["+nameStrNu.trim()+"]");
						// add year and geolocation
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						int year = dataStream.readInt();
						if(year==-1) {
							System.out.print("["+"]");
						}
						else {
							System.out.print("["+year+"]");
						}
						byte[] geolocationBy = new byte[26];
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((firstStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						dataStream.readFully(geolocationBy);
						String geolocationStr = new String(geolocationBy);
						System.out.print("["+geolocationStr.trim()+"]");
						System.out.println();
					}
					System.out.println();
					//mid 4
					System.out.println("Middle Five: ");
					for (int j=0;j<5;j++) {
						int midStart = ((recordCount/2)-2)+j;
						byte[] nameByNu = new byte[lenName];
						dataStream.seek((midStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
						dataStream.readFully(nameByNu);
						String nameStrNu = new String(nameByNu);
						System.out.print("["+nameStrNu.trim()+"]");
						// add year and geolocation
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((midStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						int year = dataStream.readInt();
						if(year==-1) {
							System.out.print("["+"]");
						}
						else {
							System.out.print("["+year+"]");
						}
						byte[] geolocationBy = new byte[26];
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((midStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						dataStream.readFully(geolocationBy);
						String geolocationStr = new String(geolocationBy);
						System.out.print("["+geolocationStr.trim()+"]");
						System.out.println();
						
					}
					System.out.println();
					//last 5
					System.out.println("Last 5: ");
					for (int j=0; j<5;j++) {
						int lastStart = recordCount-5+j;
						byte[] nameByNu = new byte[lenName];
						dataStream.seek((lastStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
						dataStream.readFully(nameByNu);
						String nameStrNu = new String(nameByNu);
						System.out.print("["+nameStrNu.trim()+"]");
						// add year and geolocation
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((lastStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						int year = dataStream.readInt();
						if(year==-1) {
							System.out.print("["+"]");
						}
						else {
							System.out.print("["+year+"]");
						}
						byte[] geolocationBy = new byte[26];
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((lastStart)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						dataStream.readFully(geolocationBy);
						String geolocationStr = new String(geolocationBy);
						System.out.print("["+geolocationStr.trim()+"]");
						System.out.println();
					}
					System.out.println();
				}

				
				
				
		//prompt user for year and search for records with that year
				
				while(true) {
					System.out.println();
					System.out.print("Enter a year: ");
					
					Scanner usrInput = new Scanner(System.in);
					int usrYr = usrInput.nextInt();
					if(usrYr==0) {
						break;
					}
					
					
			//start exponential binary search
					
					// exponential search 
					
					int k = 1;
					Double dummyK = 0.0;
					int currYear = -1;
					int currYear2 = -1;
					
					while(k<recordCount && currYear2<usrYr) {
						Double dummyK2 = -1.0;
						if (dummyK.intValue()>recordCount) {
							break;
						}
						
						Double newKDummy = k+0.0;
						dummyK2 = 2*(Math.pow(2, newKDummy)-1);
						
						if(dummyK2.intValue()>recordCount) {
							break;
						}
						Double newK = k+0.0;
						dummyK = 2*(Math.pow(2, newK)-1);
						
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((dummyK.intValue()-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						//System.out.println(dummyK);
						currYear2 = dataStream.readInt();
						if(currYear2>=usrYr) {
							break;
						}
						
						k++;
					}
					
					
					//start binary search
					
					Double low1 = 2*(Math.pow(2, k-1)-1)+1;
					int low = low1.intValue();
					Double high1 = Math.min(2*(Math.pow(2, k)-1)-1, recordCount);
					int high = high1.intValue()+1;
					for (int p = low; p<=high-1;p++) {
						int mid = (low+high)/2;

						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((p-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						int currYear3 = dataStream.readInt();
						//System.out.println(currYear3);
						if(currYear3<usrYr) {
							low = mid+1;
						}
						else if(currYear3>usrYr) {
							high = mid-1;
						}
						else if(currYear3==usrYr) {

							//System.out.println("we here");
							byte[] nameBy = new byte[lenName];
							dataStream.seek((p-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
							dataStream.readFully(nameBy);
							String nameStr = new String(nameBy);
							System.out.print("["+nameStr.trim()+"]");

							byte[] geolocationBy = new byte[26];
							dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((p-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
							dataStream.readFully(geolocationBy);
							String geolocationStr = new String(geolocationBy);
							if(currYear2==-1) {
								System.out.print("["+"]");
							}
							else {
								System.out.print("["+currYear3+"]");
							}
							System.out.print("["+geolocationStr.trim()+"]");
							System.out.println();
						}
						//System.out.println("we searched");
					}
					
				}
				System.out.println("0 Entered. Program Terninated");
				
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

}
