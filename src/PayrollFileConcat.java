import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PayrollFileConcat
{
	private Map<Integer, ArrayList<String>> employeeMap = new HashMap<>();
	private ArrayList<Integer> duplicateKeys = new ArrayList<>();

	public PayrollFileConcat(Map<Integer, ArrayList<String>> map1, File file1, Map<Integer, ArrayList<String>> map2, File file2, Set<String> missedClockOuts){
		String filePath = "src/Payroll Folder/Output/";
		String[] file1Str = file1.toString().split("\\\\");
		String[] file2Str = file2.toString().split("\\\\");
		String file1Date = file1Str[3].split("__")[0];
		String file2Date = file2Str[3].split("__")[0];
		String facilityName1 = file1Str[3].split("__")[1].replace(".txt", "");
		String facilityName2 = file2Str[3].split("__")[1].replace(".txt", "");

		try{
			File concatFile = new File(filePath + file1Date + "__FINAL.txt");
			FileWriter fw = new FileWriter(concatFile);
			BufferedWriter bw = new BufferedWriter(fw);
			if(file1Date.equals(file2Date)){
				// writes duplicate keys for facilities first
				for(Map.Entry<Integer, ArrayList<String>> firstMap: map1.entrySet()){
					String name = firstMap.getValue().toArray()[0].toString().split(" #")[0];

					for(Map.Entry<Integer, ArrayList<String>> secondMap : map2.entrySet())
					{
						writeDuplicateKey(bw, firstMap, secondMap, name, file2Date, facilityName1, facilityName2, missedClockOuts);
					}
				}

				// Writes all other keys
				for(Map.Entry<Integer, ArrayList<String>> map: employeeMap.entrySet()){
					for(int duplicateKey : duplicateKeys){

						// Compares against first map
						for(Map.Entry<Integer, ArrayList<String>> firstMap : map1.entrySet()){
							if(map.getKey().equals(firstMap.getKey()) && !map.getKey().equals(duplicateKey)){
								writeKeyValues(bw, map, facilityName1, file1Date, missedClockOuts);
							}
						}

						// Compares against second map
						for(Map.Entry<Integer, ArrayList<String>> secondMap : map2.entrySet()){
							if(map.getKey().equals(secondMap.getKey()) && !map.getKey().equals(duplicateKey)){
								writeKeyValues(bw, map, facilityName2, file2Date, missedClockOuts);
							}
						}
					}

				}
				bw.close();
			}else{
				System.out.println("File dates do not match");
				System.out.println("    " + file1Date);
				System.out.println("    " + file2Date);
			}
		}catch (IOException e){
			System.out.println("ERROR: Unable to concatenate text file...");
			e.printStackTrace();
		}
	}

	private void createHeader(BufferedWriter bw, String id, String name, String date){
		try{
			bw.write(id + ": " + name);
			bw.newLine();

			bw.write((date));
			bw.newLine();
			bw.newLine();
		}catch (IOException e){
			System.out.println("ERROR: unable to create employee headers");
			e.printStackTrace();
		}
	}

	// if map.key is present in multiple facilities, then data is written to file first.
	private void writeDuplicateKey(BufferedWriter bw, Map.Entry<Integer, ArrayList<String>> map1, Map.Entry<Integer, ArrayList<String>> map2, String name, String fileDate, String facilityName1, String facilityName2, Set<String> missedClockOuts){
		try{

			if(map2.getKey().equals(map1.getKey())){
				createHeader(bw, map2.getKey().toString(), name, fileDate);

				bw.write("			" + facilityName1);
				bw.newLine();
				for(String firstStr : map1.getValue()){
					bw.write("    " + firstStr);
					bw.newLine();
				}
				bw.write("			" + facilityName2);
				bw.newLine();
				for(String secondStr : map2.getValue()){
					bw.write("    " + secondStr);
					bw.newLine();
				}

				bw.newLine();
				bw.write("      ********Missing Clock Out********");
				bw.newLine();
				for(String missedClockOut : missedClockOuts){
					if(missedClockOut.contains(name)){
						bw.write("      " + missedClockOut);
						bw.newLine();
					}
				}
				bw.newLine();
				bw.write("=======================================");
				bw.newLine();
				bw.newLine();

				duplicateKeys.add(map2.getKey());
			}else{
				employeeMap.put(map1.getKey(), map1.getValue());
				employeeMap.put(map2.getKey(), map2.getValue());
			}
		}catch (IOException e){
			System.out.println("ERROR: problem with attempting to write a duplicate key...");
			e.printStackTrace();
		}
	}

	// Writes the rest of the unique map.keys
	private void writeKeyValues(BufferedWriter bw, Map.Entry<Integer, ArrayList<String>> map, String facilityName, String fileDate, Set<String> missedClockOuts){
		try{
			String name = map.getValue().toArray()[0].toString().split(" #")[0];

			createHeader(bw, map.getKey().toString(), name, fileDate);

			bw.write("			" + facilityName);
			bw.newLine();
			for(String firstStr : map.getValue()){
				bw.write("    " + firstStr);
				bw.newLine();
			}
			bw.newLine();
			bw.write("      ********Missing Clock Out********");
			bw.newLine();
			for(String missedClockOut : missedClockOuts){
				if(missedClockOut.contains(name)){
					bw.write("      " + missedClockOut);
					bw.newLine();
				}
			}
			bw.newLine();
			bw.write("=======================================");
			bw.newLine();
			bw.newLine();
		}catch (IOException e){
			System.out.println("ERROR: Issues writing map values onto file...");
			e.printStackTrace();
		}
	}
}
