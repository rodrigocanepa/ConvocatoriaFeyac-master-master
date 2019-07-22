package com.colabora.soluciones.convocatoriafeyac.Modelos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Environment;
import android.text.Layout;
import android.util.Log;

import com.colabora.soluciones.convocatoriafeyac.VerPDFActivity;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by rodrigocanepacruz on 08/11/18.
 */

public class TemplatePDF {

    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private BaseColor baseColor = new BaseColor(100,100,100,255);
    private Font fTitle = new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD);
    private Font fEspecialidad = new Font(Font.FontFamily.HELVETICA, 16,Font.ITALIC);
    private Font fDatos = new Font(Font.FontFamily.HELVETICA, 11,Font.NORMAL, BaseColor.DARK_GRAY);
    private Font fDatosTabla = new Font(Font.FontFamily.HELVETICA, 11,Font.NORMAL, BaseColor.DARK_GRAY);
    private Font fSubTitle = new Font(Font.FontFamily.HELVETICA, 16,Font.NORMAL);
    private Font fText = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
    private Font fText2 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
    private Font fHigh = new Font(Font.FontFamily.HELVETICA, 20,Font.BOLD, baseColor);
    private Font fHigh2 = new Font(Font.FontFamily.HELVETICA, 14,Font.NORMAL, baseColor);
    private Font fTextoNormal = new Font(Font.FontFamily.HELVETICA, 14, Font.NORMAL, BaseColor.BLACK);


    public TemplatePDF(Context context) {
        this.context = context;
    }

    public void openDocument(String nombre){
        createFile(nombre);
        try{
            document = new Document(PageSize.A4);
            document.setMargins(10,10,20,20);
           // document = new Document(new Rectangle(595 , 842), 0, 0, 0, 0);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();


        } catch (Exception e){
            Log.e("openDocument", e.toString());
        }
    }

    public void createFile(String nombre){
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");

        if(!folder.exists())
            folder.mkdirs();
        pdfFile = new File(folder, nombre + ".pdf");
    }

    public void closeDocument(){
        document.close();
    }

    public void addMetaData(String title, String subject, String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }

    public void addTitles(String title, String subtitle, String date){

        try{
            paragraph = new Paragraph();
            addChild(new Paragraph(title, fTitle));
            addChild(new Paragraph(subtitle, fSubTitle));
            addChild(new Paragraph("Generado: " + date, fHigh2));
            paragraph.setSpacingAfter(10);
            document.add(paragraph);

        } catch (Exception e){
            Log.e("addTitles", e.toString());
        }

    }

    public void addLine(){
        LineSeparator ls = new LineSeparator();
        try {
            document.add(new Chunk(ls));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void addSections(String title){

        try{
            paragraph = new Paragraph();
            addChild(new Paragraph(title, fHigh));
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);

        } catch (Exception e){
            Log.e("addSections", e.toString());
        }

    }

    public void addSectionsCenter(String title){

        try{
            paragraph = new Paragraph();
            addChildCenter(new Paragraph(title, fHigh));
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);

        } catch (Exception e){
            Log.e("addSections", e.toString());
        }

    }

    private void addChildCenter(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }

    public void addParagraphCenter(String text){
        try{
            paragraph = new Paragraph(text, fTextoNormal);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(1);
            paragraph.setSpacingBefore(1);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("addParagraph", e.toString());
        }

    }

    private void addChild(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
    }

    public void addParagraph(String text){
        try{
            paragraph = new Paragraph(text, fTextoNormal);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("addParagraph", e.toString());
        }

    }

    public void addImage(Context context) {

        try {
            // get input stream
            /*File imgFile = new File("sdcard/Pictures/SignaturePad/Signature_.jpg");

            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                image.scaleToFit(250, 150);
                image.setAlignment(Element.ALIGN_CENTER);
                document.add(image);
            }*/
            InputStream ims = context.getAssets().open("header.png");
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scaleToFit(document.getPageSize().getWidth(), 75);
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void createTableReconocimientos(String[]header, ArrayList<String[]> clients){

        try{
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(3);
            pdfPTable.setWidthPercentage(90);
            pdfPTable.setWidths(new int[]{1, 2, 1});
            pdfPTable.setSpacingBefore(10);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC<header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fText));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(baseColor);
                pdfPTable.addCell(pdfPCell);
            }

            for(int indexR=0; indexR<clients.size(); indexR++){
                String[] row = clients.get(indexR);
                for(indexC = 0; indexC < header.length; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(row[indexC], fDatosTabla));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    // pdfPCell.setFixedHeight(30);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("createTable", e.toString());
        }

    }

    public void createTableWith2cell(String[]header, ArrayList<String[]> clients){

        try{
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(2);
            pdfPTable.setWidthPercentage(90);
            pdfPTable.setWidths(new int[]{2, 1});
            pdfPTable.setSpacingBefore(10);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC<header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fText));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(baseColor);
                pdfPTable.addCell(pdfPCell);
            }

            for(int indexR=0; indexR<clients.size(); indexR++){
                String[] row = clients.get(indexR);
                for(indexC = 0; indexC < header.length; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(row[indexC], fDatosTabla));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    // pdfPCell.setFixedHeight(30);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("createTable", e.toString());
        }

    }

    public void createTableWith2SameLengthcell(String[]header, ArrayList<String[]> clients){

        try{
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(2);
            pdfPTable.setWidthPercentage(90);
            pdfPTable.setWidths(new int[]{1, 1});
            pdfPTable.setSpacingBefore(10);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC<header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fText));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(baseColor);
                pdfPTable.addCell(pdfPCell);
            }

            for(int indexR=0; indexR<clients.size(); indexR++){
                String[] row = clients.get(indexR);
                for(indexC = 0; indexC < header.length; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(row[indexC], fDatosTabla));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    // pdfPCell.setFixedHeight(30);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("createTable", e.toString());
        }

    }

    public void createTableTotal(String subtotal, String iva, String envio, String descuento, String total, String tipoDescuento){

        try{
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(1);
            pdfPTable.setWidthPercentage(50);
            pdfPTable.setSpacingAfter(20);
            pdfPTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
            //pdfPTable.setWidths(new int[]{ 3, 2});
            //pdfPTable.setSpacingBefore(10);
            PdfPCell pdfPCell;

            // tabla subtotal
            PdfPTable pdfPTableSubTotal = new PdfPTable(2);
            // tabla iva
            PdfPTable pdfPTableIVA = new PdfPTable(2);
            // tabla envio
            PdfPTable pdfPTableEnvio = new PdfPTable(2);
            // tabla descuento
            PdfPTable pdfPTableDescuento = new PdfPTable(2);
            // tabla tota
            PdfPTable pdfPTableTotal = new PdfPTable(2);

            PdfPCell celdaSubtotal = new PdfPCell();
            pdfPTableSubTotal.addCell(createTextCellLeft("Subtotal"));
            pdfPTableSubTotal.addCell(createTextCellLeft(subtotal));
            celdaSubtotal.addElement(pdfPTableSubTotal);

            PdfPCell celdaIVA = new PdfPCell();
            pdfPTableIVA.addCell(createTextCellLeft("IVA (16%)"));
            pdfPTableIVA.addCell(createTextCellLeft(iva));
            celdaIVA.addElement(pdfPTableIVA);

            PdfPCell celdaEnvio = new PdfPCell();
            pdfPTableEnvio.addCell(createTextCellLeft("Gastos de envío"));
            pdfPTableEnvio.addCell(createTextCellLeft(envio));
            celdaEnvio.addElement(pdfPTableEnvio);

            PdfPCell celdaDescuento = new PdfPCell();
            pdfPTableDescuento.addCell(createTextCellLeft("Descuento " + tipoDescuento));
            pdfPTableDescuento.addCell(createTextCellLeft(descuento));
            celdaDescuento.addElement(pdfPTableDescuento);

            PdfPCell celdaTotal = new PdfPCell();
            pdfPTableTotal.addCell(createTextCellLeftBold("Total"));
            pdfPTableTotal.addCell(createTextCellLeftBold(total));
            celdaTotal.addElement(pdfPTableTotal);

            /*pdfPTableSubTotalPrecios.addCell(createTextCellRight(subtotal));
            pdfPTableSubTotalPrecios.addCell(createTextCellRight(iva));
            pdfPTableSubTotalPrecios.addCell(createTextCellRight(envio));
            pdfPTableSubTotalPrecios.addCell(createTextCellRight(descuento));

            pdfPTableTotal.addCell(createTextCellNombre("Total"));
            pdfPTableTotalPrecio.addCell(createTextCellNombre(total));*/
            pdfPTable.addCell(celdaSubtotal);
            pdfPTable.addCell(celdaIVA);
            if(!(envio.equals("$0.00") || (envio.equals("$0")))){
                pdfPTable.addCell(celdaEnvio);
            }
            if(!(descuento.equals("-$0.00")) || (envio.equals("-$0"))){
                pdfPTable.addCell(celdaDescuento);
            }
            pdfPTable.addCell(celdaTotal);


            /*
            int indexC = 0;
            while (indexC<header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fText));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(baseColor);
                pdfPTable.addCell(pdfPCell);
            }

            for(int indexR=0; indexR<clients.size(); indexR++){
                String[] row = clients.get(indexR);
                for(indexC = 0; indexC < header.length; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(row[indexC], fDatosTabla));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    // pdfPCell.setFixedHeight(30);
                    pdfPTable.addCell(pdfPCell);
                }
            }*/

            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("createTable", e.toString());
        }

    }

    public void creaTableCotizacion(String[]header, ArrayList<String[]> clients){

        try{
            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(4);
            pdfPTable.setWidths(new int[]{3, 1, 1, 1});
            pdfPTable.setSpacingBefore(20);
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC = 0;
            while (indexC<header.length){
                pdfPCell = new PdfPCell(new Phrase(header[indexC++], fText));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(baseColor);
                pdfPTable.addCell(pdfPCell);
            }

            for(int indexR=0; indexR<clients.size(); indexR++){
                String[] row = clients.get(indexR);
                for(indexC = 0; indexC < header.length; indexC++){
                    pdfPCell = new PdfPCell(new Phrase(row[indexC], fDatosTabla));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    // pdfPCell.setFixedHeight(30);
                    pdfPTable.addCell(pdfPCell);
                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        } catch (Exception e){
            Log.e("createTable", e.toString());
        }

    }

    public void createGraficaFoto(Context context, Bitmap bitmap){

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scaleToFit(600, 600);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void createTableWithFoto(Context context, Bitmap bitmap, String formato, String folio, String fecha, String vencimiento, String nombreEmpresa){

        try {
            // get input stream
            /*File imgFile = new File("sdcard/Pictures/SignaturePad/Signature_.jpg");

            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Image image = Image.getInstance(stream.toByteArray());
                image.scaleToFit(250, 150);
                image.setAlignment(Element.ALIGN_CENTER);
                document.add(image);
            }*/
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image image = Image.getInstance(stream.toByteArray());
            image.scaleToFit(125, 115);
            image.setAlignment(Element.ALIGN_CENTER);
            //document.add(image);

            try{
                paragraph = new Paragraph();
                paragraph.setFont(fText);
                PdfPTable pdfPTable = new PdfPTable(3);
                PdfPTable pdfPTableTexto = new PdfPTable(1);

                // tabla folio
                PdfPTable pdfPTable1 = new PdfPTable(2);
                // tabla fecha
                PdfPTable pdfPTable2 = new PdfPTable(2);
                // tabla vencimiento
                PdfPTable pdfPTable5 = new PdfPTable(2);
                // tabla vacia
                PdfPTable pdfPTable3 = new PdfPTable(2);
                //tabla foto + correo + nombre
                PdfPTable pdfPTable4 = new PdfPTable(1);

                pdfPTable.setWidthPercentage(100);

                /*PdfPCell celdaFormato = new PdfPCell();
                celdaFormato.addElement(createTextCellNombre(formato));
                celdaFormato.setBorder(Rectangle.NO_BORDER);
                pdfPTableTexto.addCell(celdaFormato);*/

                /*PdfPCell celdaFolio = new PdfPCell();
                celdaFolio.addElement(createTextCellLeft("Folio: "));
                celdaFolio.setBorder(Rectangle.NO_BORDER);

                PdfPCell celdaFolio2 = new PdfPCell();
                celdaFolio2.addElement(createTextCellRight(folio));
                celdaFolio2.setBorder(Rectangle.NO_BORDER);

                pdfPTable1.addCell(celdaFolio);
                pdfPTable1.addCell(celdaFolio2);
                pdfPTableTexto.addCell(pdfPTable1);*/

                pdfPTableTexto.addCell(createTextCellNombre(formato));

                PdfPCell celdaFolio = new PdfPCell();
                pdfPTable1.addCell(createTextCellLeft("Folio: "));
                pdfPTable1.addCell(createTextCellRight(folio));
                celdaFolio.addElement(pdfPTable1);
                celdaFolio.setBorder(Rectangle.NO_BORDER);

                PdfPCell celdaFecha = new PdfPCell();
                pdfPTable2.addCell(createTextCellLeft("Fecha: "));
                pdfPTable2.addCell(createTextCellRight(fecha));
                celdaFecha.addElement(pdfPTable2);
                celdaFecha.setBorder(Rectangle.NO_BORDER);

                PdfPCell celdaVencimiento = new PdfPCell();
                pdfPTable5.addCell(createTextCellLeft("Vencimiento: "));
                pdfPTable5.addCell(createTextCellRight(vencimiento));
                celdaFecha.addElement(pdfPTable5);
                celdaFecha.setBorder(Rectangle.NO_BORDER);

                pdfPTableTexto.addCell(celdaFolio);
                pdfPTableTexto.addCell(celdaFecha);

               // pdfPTableTexto.addCell(createTextCellEspecialidad(especialidad));
               // pdfPTableTexto.addCell(createTextCellDatosExtra(datos));

                pdfPTable.setWidths(new int[]{6, 1, 6});
                pdfPTable.setSpacingBefore(20);
                pdfPTable.setSpacingAfter(20);

                PdfPCell pdfPCell = new PdfPCell();
                pdfPCell.addElement(image);
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBorder(Rectangle.NO_BORDER);

                pdfPTable4.addCell(pdfPCell);
                pdfPTable4.addCell(createTextCellCenter(nombreEmpresa));
                PdfPCell celdaDatos = new PdfPCell();
                celdaDatos.addElement(pdfPTable4);
                celdaDatos.setBorder(Rectangle.NO_BORDER);

                /*PdfPCell pdfPCellNombre = new PdfPCell();
                pdfPCellNombre.addElement(createTextCellLeft("gaby"));
                pdfPTable4.addCell(pdfPCellNombre);*/

                PdfPCell pdfPCell1 = new PdfPCell();
                pdfPCell1.addElement(pdfPTableTexto);
                pdfPCell1.setBorder(Rectangle.NO_BORDER);

               /* PdfPCell celdaVacia = new PdfPCell();
                celdaVacia.addElement(createTextCellRight(""));
                celdaVacia.setBorder(Rectangle.NO_BORDER);*/

                PdfPCell celdaVacia = new PdfPCell();
                pdfPTable3.addCell(createTextCellLeft("Fecha: "));
                pdfPTable3.addCell(createTextCellRight(fecha));
                celdaFecha.addElement(pdfPTable3);
                celdaVacia.setBorder(Rectangle.NO_BORDER);

                pdfPTable.addCell(celdaDatos);
                pdfPTable.addCell(celdaVacia);
                pdfPTable.addCell(pdfPCell1);


                paragraph.add(pdfPTable);
                document.add(paragraph);
            } catch (Exception e){
                Log.e("createTable", e.toString());
            }


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public PdfPCell createTextCellNombre(String text) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text);
        p.setFont(fTitle);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public PdfPCell createTextCellLeft(String text) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text);
        p.setFont(fDatos);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public PdfPCell createTextCellCenter(String text) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text);
        p.setFont(fDatos);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public PdfPCell createTextCellRight(String text) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text);
        p.setFont(fDatos);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public PdfPCell createTextCellLeftBold(String text) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text);
        p.setFont(fText2);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }

    public void viewPDF(String tipo){
        Intent intent = new Intent(context, VerPDFActivity.class);
        intent.putExtra("path", pdfFile.getAbsolutePath());
        intent.putExtra("tipo", tipo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void viewPDF2(String tipo){
        Intent intent = new Intent(context, VerPDFDiagActivity.class);
        intent.putExtra("path", pdfFile.getAbsolutePath());
        intent.putExtra("tipo", tipo);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
