package com.kinoticket.backend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.kinoticket.backend.Exceptions.MissingParameterException;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.Ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    Logger logger = LoggerFactory.getLogger(EmailService.class);

    /**
     * This method sends an email containing ticket pdf files to the email address
     * contained in the Booking object.
     * 
     * @param booking This is the booking object, containing a list of all the
     *                tickets for which a pdf file is generated and sent.
     * @return An indication wether the email was sent successful.
     */

    public void sendBookingConfirmation(Booking booking) throws MissingParameterException {

        if (booking == null) {
            throw new MissingParameterException("EmailService: booking is null");
        }

        List<File> ticketPdfs = generateTicketPdfs(booking);

        String messageBody = createMessageBody(booking);

        MimeMessage message = createBookingConfirmationMessage(
                booking.getBookingAddress().getEmailAddress(),
                messageBody, ticketPdfs);

        emailSender.send(message);
        removeFromDisk(ticketPdfs);
    }

    private void removeFromDisk(List<File> ticketPdfs) {
        for (File f : ticketPdfs) {
            f.delete();
        }
    }

    private String createMessageBody(Booking booking) {
        String message = "Lieber Kunde,\n\n";
        message += "vielen Dank für Ihre Bestellung bei Theatery.\n";
        message += "Im Anhang finden Sie Ihre Tickets. Wir freuen uns auf Sie!\n\n";
        message += "Ihr Team von Theatery";

        return message;
    }

    private MimeMessage createBookingConfirmationMessage(String to, String messageBody, List<File> ticketPdfs) {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setFrom(System.getenv("KINOTICKET_EMAIL"), "Theatery");
            helper.setTo(to);
            helper.setSubject("Ihre Reservierung bei Theatery");
            helper.setText(messageBody);

            for (int i = 0; i < ticketPdfs.size(); i++) {
                helper.addAttachment(ticketPdfs.get(i).getName(), ticketPdfs.get(i));
            }

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return message;
    }

    private List<File> generateTicketPdfs(Booking booking) throws MissingParameterException {
        List<Ticket> tickets = booking.getTickets();

        if (tickets == null) {
            throw new MissingParameterException(
                "EmailService: PDF Tickets could not be generated. Tickets are null"
            );
        }

        List<File> ticketPdfs = new ArrayList<>();

        for (int i = 0; i < tickets.size(); i++) {
            File f = new File("Ticket-" + (i + 1) + ".pdf");
            Document document = new Document();

            try (FileOutputStream fileOutputStream = new FileOutputStream(f)) {
                PdfWriter.getInstance(document, fileOutputStream);

                document.open();
                document.addTitle("Kinoticket");

                Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

                try {
                    String paragraph1 = "Ticketnummer: " + tickets.get(i).getId() + "\n"
                            + "Bestellnummer: " + booking.getId() + "\n";
                    String paragraph2 = "Vorstellung: " + tickets.get(i).getFilmShow().getMovie().getTitle() + "\n"
                            + "Datum: " + tickets.get(i).getFilmShow().getDate() + "\n"
                            + "Uhrzeit: " + tickets.get(i).getFilmShow().getTime() + "\n";
                    String paragraph3 = "Kinosaal: " + tickets.get(i).getFilmShow().getCinemaHall().getId() + "\n"
                            + "Reihe: " + tickets.get(i).getFilmShowSeat().getSeat().getRow() + "\n"
                            + "Sitznummer: " + tickets.get(i).getFilmShowSeat().getSeat().getSeatNumber() + "\n";
                    String paragraph4 = "Preis: " + tickets.get(i).getPrice() + "€\n";

                    document.add(new Paragraph(paragraph1, font));
                    document.add(new Paragraph(paragraph2, font));
                    document.add(new Paragraph(paragraph3, font));
                    document.add(new Paragraph(paragraph4, font));
                } catch (NullPointerException e) {
                    logger.error("Invalid Ticket!", e);
                    fileOutputStream.close();
                    f.delete();
                    for (File alreadyCreatedTicket : ticketPdfs)
                        alreadyCreatedTicket.delete();
                    return null;
                }
                document.close();
                fileOutputStream.close();
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
