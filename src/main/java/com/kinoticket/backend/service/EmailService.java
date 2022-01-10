package com.kinoticket.backend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.kinoticket.backend.exceptions.MissingParameterException;
import com.kinoticket.backend.model.Booking;
import com.kinoticket.backend.model.Ticket;
import com.kinoticket.backend.model.User;

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
     */
    public void sendBookingConfirmation(Booking booking) throws MissingParameterException {

        if (booking == null) {
            throw new MissingParameterException("EmailService: booking is null");
        }
        if (booking.getBookingAddress() == null || booking.getBookingAddress().getEmailAddress() == null) {
            throw new MissingParameterException("EmailService: Cannot get Email Address!");
        }

        List<File> ticketPdfs = generateTicketPdfs(booking);

        String messageBody = bookingMessageBody(booking);

        sendEmail(booking.getBookingAddress().getEmailAddress(), messageBody, "Ihre Reservierung bei Theatery", ticketPdfs);
        removeFromDisk(ticketPdfs);
        logger.info("EmailService: Booking Confirmation sent for Booking " + booking.getId());
    }

    /**
     * This methods sends a registration confirmation email,
     * including a link in it's body. This link will activate
     * the user profile.
     * 
     * @param user             The user to activate.
     * @param registrationLink The activation link to be placed
     *                         in the message body.
     */
    public void sendRegistrationEmail(User user, String registrationLink) {

        String messageBody =
              "Hallo "
            + user.getUsername() + "!\n"
            + "Zur Aktivierung deines Accounts auf folgenden Link klicken:\n"
            + registrationLink;

        sendEmail(user.getAddress().getEmailAddress(), messageBody, "Ihre Registrierung bei Theatery", null);
        logger.info("EmailService: Registration Email sent for User " + user.getId());
    }

    /**
     * This methods sends a link for resetting
     * a user's password.
     * 
     * @param user             The user who wants to reset his
     *                         password.
     * @param registrationLink The link under which the password 
     *                         can be reset.
     */
    public void sendPasswordResetEmail(User user, String passwordRestLink) {
        String messageBody =
              "Hallo "
            + user.getUsername() + "!\n"
            + "Zum zurücksetzen Ihres Passwortes hier klicken:\n"
            + passwordRestLink;

        sendEmail(user.getAddress().getEmailAddress(), messageBody, "Passwort zurücksetzten", null);
        logger.info("EmailService: Password reset Email sent for User " + user.getId());
    }

    /**
     * Sends an email to the specified email, containing a subject,
     * a message in it's body and optional attachements.
     * 
     * @param to            The recipient of the email.
     * @param messageBody   The email's content.
     * @param subject       The email's subject.
     * @param attachments   The email's attachments (can be 'null').
     */
    private void sendEmail(String to, String messageBody, String subject, List<File> attachments) {

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);

            helper.setFrom(System.getenv("KINOTICKET_EMAIL"), "Theatery");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(messageBody);

            if (attachments != null) {
                for (File attachment : attachments) {
                    helper.addAttachment(attachment.getName(), attachment);
                }
            }

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        emailSender.send(message);
    }

    /**
     * Removes files from the disk.
     * 
     * @param ticketPdfs The files to be removed.
     */
    private void removeFromDisk(List<File> files) {
        for (File f : files) {
            f.delete();
        }
    }

    private String bookingMessageBody(Booking booking) {
        String message = "Lieber Kunde,\n\n";
        message += "vielen Dank für Ihre Bestellung bei Theatery.\n";
        message += "Im Anhang finden Sie Ihre Tickets. Wir freuen uns auf Sie!\n\n";
        message += "Ihr Team von Theatery";

        return message;
    }

    public List<File> generateTicketPdfs(Booking booking) throws MissingParameterException {
        List<Ticket> tickets = booking.getTickets();

        if (tickets == null) {
            throw new MissingParameterException(
                    "EmailService: PDF Tickets could not be generated. Tickets are null");
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
                    throw new MissingParameterException("EmailService: Invalid Ticket");
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
