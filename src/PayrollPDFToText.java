import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PayrollPDFToText
{
	private File newFile = new File("");
	private String facilityName = null;
	private String payrollDate = null;

	public PayrollPDFToText(File file){
		pdfToText(file);
	}

	private void pdfToText(File file){
		try{
			PDDocument pdfFile = PDDocument.load(file);
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			String fileTextString = pdfTextStripper.getText(pdfFile);
			this.newFile = new File(createNewFileName(fileTextString));
			FileWriter fw = new FileWriter(newFile);

			fw.write(fileTextString);
			fw.close();
			pdfFile.close();
		}catch (IOException e){
			System.out.println("ERROR: Unable to translate a PDF to a text file...");
			e.printStackTrace();
		}
	}

	private String createNewFileName(String pdfStrippedText){
		Scanner scanner = new Scanner(pdfStrippedText);

		while(scanner.hasNext()){
			String str = scanner.nextLine();

			if(str.contains("Clearview")) facilityName = "Clearview";
			if(str.contains("Williston")) facilityName = "Williston";
			if(str.contains("From")) payrollDate = getFileDate(str)[1] + "--" + getFileDate(str)[3];
		}

		return "src/Payroll Folder/Output/" +  payrollDate + "__" + facilityName + ".txt";
	}

	public String[] getFileDate(String dateString){
		String temp = dateString.replace("/", "-");

		return temp.split(" ");
	}

	public File getNewFile(){
		return newFile;
	}

	public String getFacilityName(){
		return facilityName;
	}

	public String getPayrollDate(){
		return payrollDate;
	}
}