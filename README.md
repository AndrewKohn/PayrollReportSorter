<h1 align="center">Payroll Hour Sorter</h1>

<h3 align="center">Andrew Kohn</h1>

## About
A personal program to sort staff's time-clocks in payroll reports between multiple facilities.
* Original report pdfs are created via rTasks charting service.  The files contain very large font and lots of whitespace.  Cross-checking between facilities for staff payroll discrepancies is time-consuming and chaotic with the amount of pages that is printed out in the original pdf files.  Also something to note: All time clock issues are printed at the top of the original file in an unordered list, making it difficult trying to pinpoint who was working what day in order to correct issues.
* Collects report pdf files and joins it into one file.
* Shows each on-staff member during the pay period and displays working hours.
  * If staff worked in multiple facilities, then data is categorized and separated but still under the staff name.
  * If staff has a missing clock out, then show dates/times under their names.

## How to use
<div align="center">This project is currently using ver 2.0.26 of pdfbox.</div>
<div align="center"><a href="https://pdfbox.apache.org/download.html" target="_blank">Link to download</a></div>

1. Download pdfBox
2. Add to Project Structure -> Libraries
3. Ensure payroll report pdfs are in the 'INPUT' folder.
4. Build & Run in IDE.
5. View file in 'OUTPUT' File.  You can also view both original files to check for issues within each facility.

PDF files within the /Input folder will be read, so it is not necessary to rename the report.

IMPORTANT NOTE: Only works with rTasks' generated reports.  This program will not run with any other PDF file.

## To-do
* Move report pdf files to input history folder after files have been transcribed and concatenated.
* Automatically create folders within 'OUTPUT' that stores the output files for the payroll dates.
