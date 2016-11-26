/*
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/27905740/remove-text-occurrences-contained-in-a-specified-area-with-itext
 */
 
package com.lapigo;
 
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.pdfcleanup.PdfCleanUpLocation;
import com.itextpdf.text.pdf.pdfcleanup.PdfCleanUpProcessor;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HidePDFContent {
 

    public static void main(String[] args) throws IOException, DocumentException {
    	

    	if (args.length==2){
    		new HidePDFContent().manipulatePdf(args[0], args[1]);
    	} else {

	        final File folder = new File("resources/pdfs/");
	        final File result=new File(folder.getAbsolutePath()+"/tmp/");
	        result.mkdir();
	        
	        for (final File fileEntry : folder.listFiles()) {
	            if (fileEntry.isFile()) {
	            	new HidePDFContent().manipulatePdf(fileEntry.getAbsolutePath(), result.getAbsolutePath()+"/"+fileEntry.getName());
	            } 
	        }
    	}
    }
 
    public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        
        List<PdfCleanUpLocation> cleanUpLocations = new ArrayList<PdfCleanUpLocation>();
        for (int page=1;page<=reader.getNumberOfPages();page++){
            cleanUpLocations.add(new PdfCleanUpLocation(page, new Rectangle(0f, 15f, 490f, 0f), BaseColor.WHITE));
            
        //CCL3 
        if (reader.getNumberOfPages()==6){
        	cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(0f, 125f, 590f, 95f), BaseColor.WHITE));
        }
        
        //EGS
        if (reader.getNumberOfPages()==7){
        	cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(0f, 155f, 300f, 49f), BaseColor.WHITE));
        }       
        //PLJ
        if (reader.getNumberOfPages()==9){
        	cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(0f, 155f, 300f, 52f), BaseColor.WHITE));
        }
        //PCC
        if (reader.getNumberOfPages()==13){
        	cleanUpLocations.add(new PdfCleanUpLocation(1, new Rectangle(0f, 125f, 590f, 95f), BaseColor.WHITE));
        }
        	
        }
        
        PdfCleanUpProcessor cleaner = new PdfCleanUpProcessor(cleanUpLocations, stamper);
        cleaner.cleanUp();
        stamper.close();
        reader.close();
    }
}