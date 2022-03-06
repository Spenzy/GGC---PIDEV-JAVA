/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import com.itextpdf.text.Rectangle;


/**
 *
 * @author Azer Lahmer
 */
public class pdf {
    private Connection con;
        private Statement ste;
        public pdf(){
            
        }
         public void add(String file) throws FileNotFoundException, SQLException, DocumentException{
          try {
            con = MyConnection.getInstance().getCnx();
                ste=con.createStatement();
                Document my_pdf_report = new Document();
                PdfWriter.getInstance(my_pdf_report, new FileOutputStream(file));
                  ResultSet rs=ste.executeQuery("select * from Evenement");
                my_pdf_report.open();  
                PdfPTable my_report_table = new PdfPTable(7);
                  PdfPCell table_cell;
                                
                              
                                table_cell=new PdfPCell(new Phrase("reference"));
                                my_report_table.addCell(table_cell);
                               
                                table_cell=new PdfPCell(new Phrase("Titre"));
                                my_report_table.addCell(table_cell);
                                
                                table_cell=new PdfPCell(new Phrase("dateDebut"));
                                my_report_table.addCell(table_cell);
                                
                                table_cell=new PdfPCell(new Phrase("dateFin"));
                                my_report_table.addCell(table_cell);
                                
                                table_cell=new PdfPCell(new Phrase("localisation"));
                                my_report_table.addCell(table_cell);
                                
                                 table_cell=new PdfPCell(new Phrase("description"));
                                my_report_table.addCell(table_cell);
                                
                                 table_cell=new PdfPCell(new Phrase("nbrParticipant"));
                                my_report_table.addCell(table_cell);
                                
                                
                                while (rs.next()) {  
                                            
                                String reference=rs.getString("reference");
                                table_cell=new PdfPCell(new Phrase(reference));
                                my_report_table.addCell(table_cell);
                                
                                String Titre=rs.getString("Titre");
                                table_cell=new PdfPCell(new Phrase(Titre));
                                my_report_table.addCell(table_cell);
                                
                                
                                Date dateDebut= rs.getDate("dateDebut");
                                String dd= String.valueOf(dateDebut);
                                table_cell=new PdfPCell(new Phrase(dd));
                                my_report_table.addCell(table_cell);
                                
                                Date dateFin=rs.getDate("dateFin");
                                String dd2= String.valueOf(dateFin);
                                table_cell=new PdfPCell(new Phrase(dd2));
                                my_report_table.addCell(table_cell);
                                
                                
                                String localisation=rs.getString("localisation");
                                table_cell=new PdfPCell(new Phrase(localisation));
                                my_report_table.addCell(table_cell);
                                
                                
                                String description=rs.getString("description");
                                table_cell=new PdfPCell(new Phrase(description));
                                my_report_table.addCell(table_cell);
                                
                                
                               int nbrParticipant=rs.getInt("nbrParticipant");
                                String pp = String.valueOf(nbrParticipant);
                                table_cell=new PdfPCell(new Phrase(pp));
                                my_report_table.addCell(table_cell);
                               

                }
                my_pdf_report.add(my_report_table);                       
                my_pdf_report.close();
                
               /* Close all DB related objects */
                 rs.close();
                ste.close();
                con.close();

          } catch(Exception ex){
              System.out.println("ex");
          }
         }
         
         
         
         
         
         

    
}
