package com.ais_db.service;

import com.ais_db.model.CustomerPolicy;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import java.io.ByteArrayOutputStream;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class PdfService {


    public byte[] generatePolicyPdf(CustomerPolicy policy) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            Document document = new Document();

            PdfWriter.getInstance(document, out);

            document.open();

            document.add(new Paragraph("POLICY DOCUMENT"));
            document.add(new Paragraph("Policy ID: " + policy.getId()));
            document.add(new Paragraph("Policy Details:"+ policy.getPolicy().getDetails()));
            document.add(new Paragraph("Customer: " + policy.getCustomer().getName()));
            document.add(new Paragraph("Start Date: " + policy.getStartDate()));
            document.add(new Paragraph("Expiry Date: " + policy.getExpiryDate()));

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }

        return out.toByteArray();
    }
}
