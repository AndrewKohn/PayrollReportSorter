import java.io.File;
import java.util.ArrayList;

public class PayrollReportSorter
{
	public static void main(String[] args)
	{
		for(int i = 0; i < getInputFiles().toArray().length; i++){
			PayrollPDFToText file = new PayrollPDFToText(new File("src/Payroll Folder/Input/" + getInputFiles().toArray()[i]));
			PayrollSorter sorter = new PayrollSorter(file.getNewFile(), file.getFacilityName(),file.getPayrollDate());

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
