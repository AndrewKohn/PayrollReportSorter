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

	public PayrollFileConcat(Map<Integer, ArrayList<String>> map1, File file1, Map<Integer, ArrayList<String>> map2, File file2, Set<String> missedClockOuts){
		String filePath = "src/Payroll Folder/Output/";
		String[] file1Str = file1.toString().split("\\\\");
		String[] file2Str = file2.toString().split("\\\\");
		String file1Date = file1Str[3].split("__")[0];
		String file2Date = file2Str[3].split("__")[0];
		String facilityName1 = file1Str[3].split("__")[1].replace(".txt", "");
		String facilityName2 = file2Str[3].split("__")[1].replace(".txt", "");

		try{
			if(file1Date.equals(file2Date)){
				File concatFile = new File(filePath + file1Date + "__FINAL.txt");
				FileWriter fw = new FileWriter(concatFile);
				BufferedWriter bw = new BufferedWriter(fw);

				for(Map.Entry<Integer, ArrayList<String>> firstMap: map1.entrySet()){
					String name = firstMap.getValue().toArray()[0].toString().split(" #")[0];
					String currentFacility = facilityName1;

					createHeader(bw, firstMap.getKey().toString(), name, file1Date);
					bw.newLine();
					bw.newLine();

					for(Map.Entry<Integer, ArrayList<String>> secondMap : map2.entrySet()){
						if(firstMap.getKey().equals(secondMap.getKey())){
							bw.write("			" + facilityName1);
							bw.newLine();
							for(String firstStr : firstMap.getValue()){
								bw.write(firstStr);
								bw.newLine();
							}

							bw.write("			" + facilityName2);
							bw.newLine();
							for(String secondStr : secondMap.getValue()){
								bw.write(secondStr);
								bw.newLine();
							}
							bw.write("=======================================");
							bw.newLine();
							bw.newLine();
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
		}catch (IOException e){
			System.out.println("ERROR: unable to create employee headers");
			e.printStackTrace();
		}
	}


	private void setEmployeeMap(Map<Integer, ArrayList<String>> map1, Map<Integer, ArrayList<String>> map2){
		for(Map.Entry<Integer, ArrayList<String>> map : map1.entrySet()){
			employeeMap.put(map.getKey(), map.getValue());
		}

		for(Map.Entry<Integer, ArrayList<String>> map : map2.entrySet()){
			employeeMap.put(map.getKey(), map.getValue());
		}
	}

	public Map<Integer, ArrayList<String>> getEmployeeMap(){
		return employeeMap;
	}

	private void printValues(){
		for(Map.Entry<Integer, ArrayList<String>> map : employeeMap.entrySet()){
			System.out.println(map.getKey());
		}
	}
}
