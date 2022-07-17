import java.io.File;
import java.util.ArrayList;

public class PayrollReportSorter
{
	public static void main(String[] args)
	{
		if(getInputFiles().toArray().length > 0){
			runApp(getInputFiles());
		}else{
			System.out.println("ArrayList length: " + getInputFiles().toArray().length);
			System.out.println("!!***********  No valid files in 'src/Payroll Folder/Input/' folder  ***********!!");
		}
	}

	private static void runApp(ArrayList<String> reportFiles){
		for(int i = 0; i < reportFiles.toArray().length; i++){
			String filePath = "src/Payroll Folder/Input/" + reportFiles.toArray()[i];
			PayrollPDFToText file = new PayrollPDFToText(new File(filePath));
			PayrollSorter sorter = new PayrollSorter(file.getNewFile(), file.getFacilityName(), file.getPayrollDate());

			System.out.println(sorter.displayInfo());
		}
	}

	// Returns an ArrayList of valid input files
	private static ArrayList<String> getInputFiles(){
		File directory = new File("src/Payroll Folder/Input/");
		String[] files = directory.list();
		ArrayList<String> validFiles = new ArrayList<>();

		if(files != null){
			for(String file : files){
				String fileExtension = file.substring(file.length() - 4);

				if(fileExtension.contains(".")){
					String validFile = fileExtension.contains(".pdf") ?  file : null;
					if(validFile != null){
						validFiles.add(validFile);
					}
				}
			}
		}

		return validFiles;
	}
}
