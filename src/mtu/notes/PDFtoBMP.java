package mtu.notes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.jpedal.*;
import org.jpedal.exception.PdfException;
import org.jpedal.fonts.FontMappings;


public class PDFtoBMP {

	public PDFtoBMP(String fileName, String filePath){
		//Instance of PdfDecoder to convert PDF into image
		PdfDecoder decodePdf = new PdfDecoder(true);
		//set mappings for non-embedded fonts to use
		FontMappings.setFontReplacements();
		//Open the PDF file - can also be a URL or a byte array
		try {
			decodePdf.openPdfFile(filePath);
			decodePdf.setExtractionMode(0, 1f);
			//page range if you want to extract all pages with a loop
			BufferedImage [] images = new BufferedImage [decodePdf.getPageCount() + 1];
			for(int i = 1; i <= decodePdf.getPageCount(); i++){
				images [i - 1] = decodePdf.getPageAsImage(i);
			}
			//Saves the images to a folder
			File dir = new File(fileName);
			dir.mkdir();
			for (int i = 0; i < decodePdf.getPageCount() ; i++) {
				try {
					File temp = new File(dir, fileName + "_page_" + (i + 1) + ".bmp");
					ImageIO.write(images[i], "bmp", temp);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//Close the file
			decodePdf.closePdfFile();
		} 
		catch (PdfException e) {
			e.printStackTrace();
		} 
	}
	
	
}