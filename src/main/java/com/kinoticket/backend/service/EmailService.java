package com.kinoticket.backend.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.qrcode.ByteArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void generatePdf(String documentName, FileOutputStream fos) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, fos);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        try {
            document.add(chunk);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    public void sendEmail(String to, String subject, String text) {


        MimeMessage message = emailSender.createMimeMessage();
     
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
        
            helper.setFrom(System.getenv("KINOTICKET_EMAIL"));
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            String docName = "Ticket.pdf";
            File f = new File("Ticket.pdf");
            try (FileOutputStream fos = new FileOutputStream(f)) {
                generatePdf(docName, fos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            helper.addAttachment(docName, f);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        emailSender.send(message);
    }

}
