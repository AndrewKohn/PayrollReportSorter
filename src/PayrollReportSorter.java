import java.io.File;

public class PayrollReportSorter
{
	public static void main(String[] args)
	{
		new PayrollPDFToText(new File("src/Payroll Folder/Input/report - cv.pdf"));
		new PayrollPDFToText(new File("src/Payroll Folder/Input/report - wl.pdf"));
	}
}
