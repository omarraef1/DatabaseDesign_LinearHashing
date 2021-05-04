import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.Normalizer;
import java.util.Scanner;

public class testingDraft {

	
	public static void main(String[] args) throws FileNotFoundException {
		
		File fileRef = new File("MeteoriteLandings.bin");
		RandomAccessFile dataStream = new RandomAccessFile(fileRef,"rw");
		int fileLen = 0;
		try {
			fileLen = (int) dataStream.length();
			
			int i = 1;
			int currentLen = 0;
			
			int x = 7;
			//System.out.println(x/2);
			
			
			
			
			int uu = 1;
			Double nn = 1+0.0;
			//System.out.println(nn.intValue());
			
			int yyy = 2;
			int rrr = 5+yyy;
			while(rrr<10) {
				yyy++;
				rrr=5+yyy;
				System.out.println(rrr);
			}
			
			
			String g = "a af saf        ";
			System.out.println(g);
			System.out.println(9/2);
			
			/*
			
			
			
			int userYear = 2102;
			System.out.println();
			
			while(true) {
				System.out.print("Enter a year: ");
				
				Scanner usrInput = new Scanner(System.in);
				int usrYr = usrInput.nextInt();
				if(usrYr==0) {
					break;
				}
				System.out.println("User entered: "+usrYr);
				
				
				//start search
				dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((1-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
				int currYear = dataStream.readInt();
				if(currYear==usrYr) {
					byte[] nameBy = new byte[lenName];
					dataStream.seek((1-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
					dataStream.readFully(nameBy);
					String nameStr = new String(nameBy);
					System.out.print(nameStr+" ");

					byte[] geolocationBy = new byte[26];
					dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((1-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					dataStream.readFully(geolocationBy);
					String geolocationStr = new String(geolocationBy);
					System.out.print(currYear+" ");
					System.out.print(geolocationStr+" ");
					
				}
				
				int k = 1;
				int dummyK = 1;
				int currYear2 = -1;
				while(k<recordCount && currYear2<=usrYr) {
					k = k*2;
					if(k>recordCount) {
						break;
					}
					dummyK = k;
					dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((k-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					currYear2 = dataStream.readInt();
					//System.out.println(k);
				}
				System.out.println(currYear2);
				
				int low=dummyK-1;
				System.out.println(low);
				//int high = Math.min(low-1, 45715);
				int high = 45716;
				System.out.println(high);
				while(low<=high) {
					int mid = (low+high)/2;

					dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+((mid-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
					int currYear3 = dataStream.readInt();
					System.out.println(currYear3);
					if(currYear3<usrYr) {
						low = mid+1;
					}
					else if(currYear3>usrYr) {
						high = mid-1;
					}
					else if(currYear3==usrYr) {
						byte[] nameBy = new byte[lenName];
						dataStream.seek((mid-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo));
						dataStream.readFully(nameBy);
						String nameStr = new String(nameBy);
						System.out.print(nameStr+" ");

						byte[] geolocationBy = new byte[26];
						dataStream.seek(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+((mid-1)*(lenName+4+lenNameType+lenRecclass+8+lenFall+4+8+8+lenGeo)));
						dataStream.readFully(geolocationBy);
						String geolocationStr = new String(geolocationBy);
						System.out.print(currYear3+" ");
						System.out.print(geolocationStr+" ");
					}
					System.out.println("we searched");
				}
				
				
			
			}
			System.out.println("0 entered n terninated");
				
			
			
			*/
			
			
			
			
			
			
			
			/*
			while(currentLen<(fileLen-40)) {
				byte[] nameBy = new byte[28];
				dataStream.seek((i-1)*(28+4+6+26+8+5+4+8+8+26));
				dataStream.readFully(nameBy);
				String nameStr = new String(nameBy);
				System.out.print(nameStr+ " ");
				
				currentLen+=28;
				
				dataStream.seek(28+((i-1)*(28+4+6+26+8+5+4+8+8+26)));
				int id = dataStream.readInt();
				System.out.print(id+" ");

				currentLen+=4;
				
				byte[] nameTypeBy = new byte[6];
				dataStream.seek(28+4+((i-1)*(28+4+6+26+8+5+4+8+8+26)));
				dataStream.readFully(nameTypeBy);
				String nameTypeStr = new String(nameTypeBy);
				System.out.print(nameTypeStr+" ");
				
				currentLen+=6;
				
				byte[] recclassBy = new byte[26];
				dataStream.seek(28+4+6+((i-1)*(28+4+6+26+8+5+4+8+8+26)));
				dataStream.readFully(recclassBy);
				String recclassStr = new String(recclassBy);
				System.out.print(recclassStr+" ");
				

				currentLen+=26;
	
				dataStream.seek(28+4+6+26+((i-1)*(28+4+6+26+8+5+4+8+8+26)));
				double mass = dataStream.readDouble();
				System.out.print(mass+" ");

				currentLen+=8;
				
				byte[] fallBy = new byte[5];
				dataStream.seek(28+4+6+26+8+((i-1)*(28+4+6+26+8+5+4+8+8+26)));
				dataStream.readFully(fallBy);
				String fallStr = new String(fallBy);
				System.out.print(fallStr+" ");

				currentLen+=5;
	
				dataStream.seek(28+4+6+26+8+5+((i-1)*(28+4+6+26+8+5+4+8+8+26)));
				int year = dataStream.readInt();
				System.out.print(year+" ");

				currentLen+=4;
	
				dataStream.seek(28+4+6+26+8+5+4+((i-1)*(28+4+6+26+8+5+4+8+8+26)));
				double reclat = dataStream.readDouble();
				System.out.print(reclat+" ");
				
				currentLen+=8;
	
				dataStream.seek(28+4+6+26+8+5+4+8+((i-1)*(28+4+6+26+8+5+4+8+8+26)));
				double reclong = dataStream.readDouble();
				System.out.print(reclong+" ");
				

				currentLen+=8;
	
				byte[] geolocationBy = new byte[26];
				dataStream.seek(28+4+6+26+8+5+4+8+8+((i-1)*(28+4+6+26+8+5+4+8+8+26)));
				dataStream.readFully(geolocationBy);
				String geolocationStr = new String(geolocationBy);
				System.out.print(geolocationStr+" ");
				System.out.println();

				currentLen+=26;
				
				i++;
			}
		*/
			
			//System.out.println(fileLen);
			//System.out.println(currentLen);
			//System.out.println(i-1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		/*
		String x = "1 , 2 , 3 , 4";
		String[] y = x.split(",");
		System.out.println(y.length);
		
		for (int i = 0;i<y.length;i++) {
			System.out.print(y[i]+ ", ");
		}

		System.out.println();
		System.out.println(y.length);
		
		String z = "Nagy-Vázsony";
		
		System.out.println(Normalizer.normalize(z, Normalizer.Form.NFKD).replaceAll("[^\\p{ASCII}]", ""));
		
		
		
		String d = "1";
		String repeated = new String(new char[3]).replace("\0", d);
		System.out.println(repeated);
		
		String s = "a";
		String b = "b";
		String all = s+b;
		System.out.println(all);
		
		String o = "adasfsaffdsa";
		System.out.println(o.substring(1,o.length()-1));
		
		String num = "\"1,000,234\"";
		String num2 = num.replaceAll(",", "");
		System.out.println(num2);
		
		System.out.println();
		System.out.println();
		
		int p = 2;
		if(p==1) {
			System.out.println("lllll");
		}
		else {
			System.out.println("else");
		}
		if(p==2) {
			System.out.println("nextIf");
		}
		
		
		String uu = "asdfert";
		int diff = 7-uu.length();
		String ud = new String(new char[diff]).replace("\0", " ");
		String fu = uu+ud;
		System.out.println(fu);
		System.out.println(fu.length());
		
		
		String usrInput = "MeteoriteLandings.csv";
		String inFileName = usrInput.trim();
		String[] firstSplit = inFileName.split("/");
		String[] inFileSplit = firstSplit[firstSplit.length-1].split("\\.");
		System.out.println(inFileSplit[0]);
		String outFileName = inFileSplit[0].toString()+".bin";
		System.out.println(outFileName);
		*/
		/*
		String[] asd = {"a","b","c"};
		byte[] asdf = new byte[] {'a','b','c',1,2,3};
		
		String countyName = "moTown1234";
		int nameLen = 10;
		int nameLenD = 10;
		StringBuffer name = new StringBuffer(countyName);
		File outputFileRef = new File("testingBinariesDraft.bin");
		RandomAccessFile dataStream = new RandomAccessFile(outputFileRef,"rw");
		name.setLength(nameLen);
		System.out.println(name + " " + nameLen);
		for (int i = 0; i<5;i++) {

			try {
				//dataStream.writeInt(nameLen);
				dataStream.writeDouble(nameLenD);
				dataStream.writeBytes(name.toString());
				//dataStream.writeByte(i);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			String inp = reader.readLine();
			inp = inp.trim();
			String[] inpS = inp.split("\\.");
			System.out.println(inpS[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		/*
		DataOutputStream outputStream = new DataOutputStream(new FileOutputStream("testingBinaryFile.bin"));
		
		try {
			outputStream.write(asdf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
	}
}

//System.out.println(inFileContent.size());
/*
		
		// find maximum length of each field
		
		for (int i = 0; i<inFileContent.size();i++) {
			if(inFileContent.get(i)[0].length()>nameFieldMaxLen) {
				nameFieldMaxLen = inFileContent.get(i)[0].length();
			}
			if(inFileContent.get(i)[1].length()>idFieldMaxLen) {
				idFieldMaxLen = inFileContent.get(i)[1].length();
			}
			if(inFileContent.get(i)[2].length()>nameTypeFieldMaxLen) {
				nameTypeFieldMaxLen = inFileContent.get(i)[2].length();
			}
			if(inFileContent.get(i)[3].length()>recclassFieldMaxLen) {
				recclassFieldMaxLen = inFileContent.get(i)[3].length();
			}
			if(inFileContent.get(i)[4].length()>massFieldMaxLen) {
				massFieldMaxLen = inFileContent.get(i)[4].length();
			}
			if(inFileContent.get(i)[5].length()>fallFieldMaxLen) {
				fallFieldMaxLen = inFileContent.get(i)[5].length();
			}
			if(inFileContent.get(i)[6].length()>yearFieldMaxLen) {
				yearFieldMaxLen = inFileContent.get(i)[6].length();
			}
			if(inFileContent.get(i)[7].length()>reclatFieldMaxLen) {
				reclatFieldMaxLen = inFileContent.get(i)[7].length();
			}
			if(inFileContent.get(i)[8].length()>reclongFieldMaxLen) {
				reclongFieldMaxLen = inFileContent.get(i)[8].length();
			}
			if(inFileContent.get(i)[9].length()>geolocationFieldMaxLen) {
				geolocationFieldMaxLen = inFileContent.get(i)[9].length();
			}
		}
		*/
