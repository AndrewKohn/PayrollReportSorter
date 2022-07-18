import java.io.File;
import java.io.IOException;
import java.util.*;

public class PayrollSorter
{
	private final File file;
	private final String facilityName;
	private final String payrollDate;
	private final Set<Integer> employeeIDs = new HashSet<>();
	private final Map<Integer, ArrayList<String>> employeeMap = new HashMap<>();
	private final Set<String> missedClockOuts = new HashSet<>();

	public PayrollSorter(File newFile, String facilityName, String payrollDate){
		this.file = newFile;
		this.facilityName = facilityName;
		this.payrollDate = payrollDate;

		collectEmployeeIDs();
		setEmployeeMap();
		setMissedClockOuts();
	}

	public void displayInfo(){
		System.out.println("*****************************************************************************************");
		System.out.println(getFacilityName() + " : number of clocked staff: " + getEmployeeIDs().size() + " ||  Payroll date: " + getPayrollDate());
		System.out.println("*****************************************************************************************");

		for(Map.Entry<Integer, ArrayList<String>> employee : employeeMap.entrySet()){
			System.out.println(employee.getKey());
			for(String employeeData : employee.getValue()){
				System.out.println("    " + employeeData);
			}
			System.out.println("===============");
		}
	}

	private void collectEmployeeIDs(){
		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String str = scanner.nextLine();

				if(str.contains("#")){
					String[] temp = str.split(" ");
					setEmployeeIDs(Integer.parseInt(temp[3].replace("#", "")));
				}
			}
			scanner.close();
		}catch (IOException e){
			System.out.println("ERROR: Unable to get employee ID's...");
			e.printStackTrace();
		}
	}

	// Collects data in file at the start of the passed entry and stops collecting at the exit
	private ArrayList<String> collectFileLineData(int entryPoint, int exitPoint){
		ArrayList<String> fileLineData = new ArrayList<>();
		int fileLineCount = 0;
		int weekCount = 1;

		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String str = scanner.nextLine();
				fileLineCount++;

				if(fileLineCount >= entryPoint && fileLineCount <= exitPoint){
					if(str.contains("Missing") || str.contains("Page") || str.contains("Time") || str.contains("From")){
						continue;

					}else if(str.contains("In Out Hours Note")){
						fileLineData.add(str + "    ----|--  WEEK " + weekCount);
						weekCount++;
					}else{
						fileLineData.add(str);
					}
				}
			}
			scanner.close();
		}catch (IOException e){
			System.out.println("ERROR: Unable to collect file line data...");
			e.printStackTrace();
		}

		return fileLineData;
	}

	// Finds entry/exit points depending on current line number.
	private int[][] collectFileDataPoints(Set<Integer> employees){
		int[][] fileDataPoints = new int[employees.size()][2];
		int employeeCount = 0;
		int fileLineCount = 0;

		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String str = scanner.nextLine();
				fileLineCount++;

				if(str.contains("#")){
					fileDataPoints[employeeCount][0] = fileLineCount;
				}

				if(str.contains("Totals:")){
					fileDataPoints[employeeCount][1] = fileLineCount;
					employeeCount++;
				}
			}
			scanner.close();
		}catch (IOException e){
			System.out.println("ERROR: Unable to collect file data...");
			e.printStackTrace();
		}

		return fileDataPoints;
	}

	private void setEmployeeIDs(int id){
		employeeIDs.add(id);
	}

	public Set<Integer> getEmployeeIDs(){
		return employeeIDs;
	}

	public String getFacilityName(){
		return	facilityName;
	}

	public String getPayrollDate(){
		return payrollDate;
	}

	public void setMissedClockOuts(){
		try{
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				String str = scanner.nextLine();

				if(str.contains("Missing")){
					missedClockOuts.add(str);
				}
			}
		}catch (IOException e){
			System.out.println("ERROR: Something happened setting Missing Clock-outs...");
			e.printStackTrace();
		}
	}

	public Set<String> getMissedClockOuts(){
		return missedClockOuts;
	}

	// Sets map with 'key'=ID & 'value'= An arrayList of staff's hours.
	private void setEmployeeMap(){
		int[][] dataPoints = collectFileDataPoints(employeeIDs);

		for(int[] dataPoint : dataPoints){
			String[] temp = collectFileLineData(dataPoint[0], dataPoint[1]).get(0).split(" ");
			int id = Integer.parseInt(temp[3].replace("#", ""));
			for(int empID : employeeIDs){
				if(id == empID){
					employeeMap.put(empID, collectFileLineData(dataPoint[0], dataPoint[1]));
				}
			}
		}
	}

	public Map<Integer, ArrayList<String>> getEmployeeMap(){
		return employeeMap;
	}

	public File getFile(){
		return file;
	}
}
