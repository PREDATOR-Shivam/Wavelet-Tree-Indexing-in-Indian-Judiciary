package com.crawler.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReadFiles {

	public static String readFromURL(String sUrl) {
		String words = "";
		try {
			URL url = new URL(sUrl);
			byte[] ba1 = new byte[1024];
			int baLength;
			File outputFile = new File("donwload.pdf");
			FileOutputStream fos1 = new FileOutputStream(outputFile);
			System.out.println("Connecting to" + sUrl + "...");
			URLConnection urlConn = url.openConnection();
			 if (!urlConn.getContentType().equalsIgnoreCase("application/pdf")) {
		          System.out.println("FAILED.\n[Sorry. This is not a PDF.]");
		      } else {
		    	  try {

		              // Read the PDF from the URL and save to a local file
		              InputStream is1 = url.openStream();
		              while ((baLength = is1.read(ba1)) != -1) {
		                  fos1.write(ba1, 0, baLength);
		              }
		              fos1.flush();
		              fos1.close();
		              is1.close();

		              // Load the PDF document and display its page count
		              System.out.print("DONE.\nProcessing the PDF ... ");
		  			  return ReadFiles.readFile(outputFile);
		            } catch (ConnectException ce) {
		              System.out.println("FAILED.\n[" + ce.getMessage() + "]\n");
		            }
		      }
			 outputFile.deleteOnExit();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return words;
	}
	
	public static String readFile(File file) {
		String allWords = null;
		try{
            PDDocument document = PDDocument.load(file);
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(document);
			text = text.toLowerCase();
			allWords = text;
			document.close();
		}catch(IOException e) {
			System.out.println(e);
		}
		return allWords;
	}
}
