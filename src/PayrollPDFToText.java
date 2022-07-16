import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PayrollPDFToText
{
	public PayrollPDFToText(File file){
		pdfToText(file);
	}

	private static void pdfToText(File file){
		try{
			PDDocument pdfFile = PDDocument.load(file);
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			String fileTextString = pdfTextStripper.getText(pdfFile);
			File newFile = new File(createNewFileName(fileTextString));
			FileWriter fw = new FileWriter(newFile);

			fw.write(fileTextString);
			fw.close();
			pdfFile.close();

		}catch (IOException e){
			System.out.println("ERROR: Unable to translate a PDF to a text file...");
			e.printStackTrace();
		}

	}

	private static String createNewFileName(String pdfStrippedText){
		String facilityName = null;
		String payrollDate = null;
		Scanner scanner = new Scanner(pdfStrippedText);

		while(scanner.hasNext()){
			String str = scanner.nextLine();

			if(str.contains("Clearview")) facilityName = "Clearview";
			if(str.contains("Williston")) facilityName = "Williston";
			if(str.contains("From")) payrollDate = getFileDate(str)[1] + "--" + getFileDate(str)[3];
		}

		return "src/Payroll Folder/Output/" +  payrollDate + "__" + facilityName + ".txt";
	}

	public static String[] getFileDate(String dateString){
		String temp = dateString.replace("/", "-");
		String[] splitStr = temp.split(" ");

		return splitStr;
	}
}