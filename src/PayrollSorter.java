import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PayrollSorter
{
	private final File file;
	private final String facilityName;
	private final String payrollDate;

	public PayrollSorter(File newFile, String facilityName, String payrollDate){
		this.file = newFile;
		this.facilityName = facilityName;
		this.payrollDate = payrollDate;


		System.out.println("***********************************************************************" + facilityName);

		for(Map.Entry<Integer, String> employee : createEmployeesMap().entrySet()){
			createHeader(employee.getKey(), employee.getValue());
			System.out.println();
			for(int i = 0; i < getFileLinePoints(createEmployeesMap()).length; i++){
				ArrayList<String> scannedLines = scanLines(getFileLinePoints(createEmployeesMap())[i][0], getFileLinePoints(createEmployeesMap())[i][1]);

				String[] temp = scannedLines.get(0).split(" ");
//				System.out.println(Integer.parseInt(temp[3].replace("#", "")));
//				System.out.println(scannedLines.get(0));

				if(Integer.parseInt(temp[3].replace("#", "")) == employee.getKey()){
//					System.out.println(Integer.parseInt(temp[3].replace("#", "")));
					for(String line : scannedLines){
						System.out.println(line);
					}
				}
			}

			System.out.println("===========================================");
		}

		System.out.println("Missed Clock Outs:");
		for(String missedClockOutLine : scanMissingClockOut()){
			System.out.println(missedClockOutLine);
		}
		System.out.println("***********************************************************************");
	}

	public String displayInfo(){
		return facilityName + " : " + payrollDate + "  |  " + file;
	}

	private ArrayList<String> scanLines(int entryPoint, int exitPoint){
		int lineCount = 0;
		int weekCount = 1;

		ArrayList<String> scannedLines = new ArrayList<>();

		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String str = scanner.nextLine();
				lineCount++;
				if(lineCount >= entryPoint && lineCount <= exitPoint){

					if(str.contains("Missing") || str.contains("Page") || str.contains("Time") || str.contains("From")){
						continue;

					}else if(str.contains("In Out Hours Note")){
						scannedLines.add(str + "    ----|--  WEEK " + weekCount);
						weekCount++;
					}else{
						scannedLines.add(str);
					}
				}


			}
		}catch (IOException e){
			e.printStackTrace();
		}

		return scannedLines;
	}

	private ArrayList<String> scanMissingClockOut(){
		ArrayList<String> missedClockOuts = new ArrayList<>();

		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String str = scanner.nextLine();
				if(str.contains("Missing")){
						missedClockOuts.add(str);
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}

		return missedClockOuts;
	}

//	Retrieves line positions of each employee's information within the file
	private int[][] getFileLinePoints(Map<Integer, String> employees){
		int fileLineCount = 0;
		int amountOfEmployees = 0;
		int[][] recordFileLinePoints = new int[employees.size()][2];

		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String str = scanner.nextLine();
				fileLineCount++;
				if(str.contains("#")){
					recordFileLinePoints[amountOfEmployees][0] = fileLineCount;		// line count of entry point
				}

				if(str.contains("Totals:")){
					recordFileLinePoints[amountOfEmployees][1] = fileLineCount;		// line count of exit point
					amountOfEmployees++;
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}

		return recordFileLinePoints;
	}

	private Map<Integer, String> createEmployeesMap(){
		Map<Integer, String> employees = new HashMap<>();

		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String str = scanner.nextLine();
				if(str.contains("#")){
					String[] temp = str.split(" ");

					String name = temp[0].concat(" " + temp[1]);
					int id =  Integer.parseInt(temp[3].replace("#", ""));

					employees.put(id, name);
				}
			}
		}catch (IOException e){
			System.out.println("ERROR:  Error scanning for employees");
			e.printStackTrace();
		}

		return employees;
	}

	private String getFacilityName(){
		return	facilityName;
	}

	private String getPayrollDate(){
		return payrollDate;
	}

	private void createHeader(Integer id, String name){
		String employee = id.toString() + " : " + name;

		System.out.println(employee);
		System.out.println(getFacilityName());
		System.out.println(getPayrollDate());
	}
}
