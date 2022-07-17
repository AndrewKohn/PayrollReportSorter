import java.io.File;

public class PayrollSorter
{
	private final File file;
	private final String facilityName;
	private final String payrollDate;

	public PayrollSorter(File newFile, String facilityName, String payrollDate){
		this.file = newFile;
		this.facilityName = facilityName;
		this.payrollDate = payrollDate;
	}

	public String displayInfo(){
		return facilityName + " : " + payrollDate + "  |  " + file;
	}
}
