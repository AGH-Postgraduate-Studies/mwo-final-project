package org.example.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

public class PdfGenerator {

    public static void generatePdf(String description, List<?> results) {
        String fileSuffix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String filename = "report-" + fileSuffix + ".pdf";


        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDFont font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                contents.beginText();
                contents.setFont(font, 12);
                contents.newLineAtOffset(100, 750);
                contents.showText(description);
                contents.endText();

                float yPosition = 730; // Initial Y position for results

                for (Object o : results) {
                    contents.beginText();
                    contents.setFont(font, 12);
                    contents.newLineAtOffset(100, yPosition);
                    contents.showText(o.toString());
                    contents.endText();
                    yPosition -= 15; // Move to the next line
                }
            }

            doc.save(filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
