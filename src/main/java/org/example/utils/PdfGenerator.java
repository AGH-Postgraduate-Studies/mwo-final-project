package org.example.utils;


import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class PdfGenerator {

public static void generatePdf(String report) {
    System.out.println(report);
    String filename = "Report1";
    String message = report;

    try (PDDocument doc = new PDDocument())
    {
        PDPage page = new PDPage();
        doc.addPage(page);

        PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

        try (PDPageContentStream contents = new PDPageContentStream(doc, page))
        {
            contents.beginText();
            contents.setFont(font, 12);
            contents.newLineAtOffset(100, 700);
            contents.showText(message);
            contents.endText();
        }

        doc.save(filename);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}

}