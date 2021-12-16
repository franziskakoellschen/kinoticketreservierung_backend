package com.kinoticket.backend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.Ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public boolean sendBookingConfirmation(Booking booking) {
        List<Ticket> tickets = booking.getTickets();

        List<File> ticketPdfs = generateTicketPdfs(tickets);
        if (ticketPdfs == null) {
            return false;
        }

        String messageBody = createMessageBody(booking);

        MimeMessage message = createBookingConfirmationMessage(booking.getEmail(), messageBody, ticketPdfs);

        emailSender.send(message);
        removeFromDisk(ticketPdfs);
        return true;
    }

    private void removeFromDisk(List<File> ticketPdfs) {
        for (File f : ticketPdfs) {
            f.delete();
        }
    }

    private String createMessageBody(Booking booking) {
        return "TODO: Message Body";
    }

    private MimeMessage createBookingConfirmationMessage(String to, String messageBody, List<File> ticketPdfs) {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setFrom(System.getenv("KINOTICKET_EMAIL"));
            helper.setTo(to);
            helper.setSubject("Ihre Reservierung bei Theatery");
            helper.setText(messageBody);

            for (int i = 0; i < ticketPdfs.size(); i++) {
                helper.addAttachment(ticketPdfs.get(i).getName(), ticketPdfs.get(i));
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public List<File> generateTicketPdfs(List<Ticket> tickets) {

        if (tickets == null) {
            return null;
        }

        List<File> ticketPdfs = new ArrayList<>();

        for (int i = 0; i < tickets.size(); i++) {
            File f = new File("Ticket-" + (i+1) + ".pdf");
            Document document = new Document();

            try (FileOutputStream fileOutputStream = new FileOutputStream(f)) {
                PdfWriter.getInstance(document, fileOutputStream);

                document.open();
                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                Chunk chunk1 = new Chunk("TODO: Message", font);
                Chunk chunk2 = new Chunk("Ticket-" + (i+1), font);

                document.add(chunk1);
                document.add(chunk2);
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e1) {
                e1.printStackTrace();
            }
            ticketPdfs.add(f);
        }
        return ticketPdfs;
    }

}
