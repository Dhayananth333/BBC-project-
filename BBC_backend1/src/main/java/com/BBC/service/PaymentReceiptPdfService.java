package com.BBC.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.BBC.model.Bill;
import com.BBC.model.PaymentMethod;
import com.BBC.model.Customer;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.mail.util.ByteArrayDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class PaymentReceiptPdfService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private CustomerService customerService;

    public void sendPaymentReceiptAsPdf(Long customerID, Bill bill, PaymentMethod payment,double discount, double finalAmount) throws MessagingException, DocumentException, IOException {
        Customer customer = bill.getCustomer();

        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        generatePdf(pdfOutputStream, customer, bill, payment, discount, finalAmount);

        sendEmailWithPdf(customerID, pdfOutputStream);
    }

    private void generatePdf(ByteArrayOutputStream outputStream, Customer customer, Bill bill, PaymentMethod payment, double discount, double finalAmount) throws DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);

        Paragraph title = new Paragraph("BBC Payment Receipt", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("\n"));

        PdfPTable customerTable = new PdfPTable(2);
        addTableCell(customerTable, "Customer Name:", boldFont);
        addTableCell(customerTable, customer.getName(), normalFont);
        addTableCell(customerTable, "Customer ID:", boldFont);
        addTableCell(customerTable, customer.getCustomerId().toString(), normalFont);
        addTableCell(customerTable, "Email:", boldFont);
        addTableCell(customerTable, customer.getEmail(), normalFont);
        document.add(customerTable);

        document.add(new Paragraph("\n"));

        PdfPTable billTable = new PdfPTable(2);
        addTableCell(billTable, "Bill ID:", boldFont);
        addTableCell(billTable, bill.getBillId().toString(), normalFont);
        addTableCell(billTable, "Unit Consumption:", boldFont);
        addTableCell(billTable, String.valueOf(bill.getUnitConsumption()) + " units", normalFont);
        addTableCell(billTable, "Billing Period:", boldFont);
        addTableCell(billTable, bill.getBillingStartDate().toString(), normalFont);
        addTableCell(billTable, "Total Amount:", boldFont);
        addTableCell(billTable, "₹" + String.valueOf(bill.getAmountDue()), normalFont);
        addTableCell(billTable, "Due Date:", boldFont);
        addTableCell(billTable, bill.getBillDueDate().toString(), normalFont);
        document.add(billTable);

        document.add(new Paragraph("\n"));

        
        PdfPTable paymentTable = new PdfPTable(2);
        addTableCell(paymentTable, "Amount Paid:", boldFont);
        addTableCell(paymentTable, "₹" + String.valueOf(finalAmount), normalFont);
        addTableCell(paymentTable, "Discount Applied:", boldFont);
        addTableCell(paymentTable, "₹" + String.valueOf(discount), normalFont);
        addTableCell(paymentTable, "Payment Date:", boldFont);
        addTableCell(paymentTable, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), normalFont);

        
        document.add(paymentTable);

        document.close();
    }

    private void addTableCell(PdfPTable table, String content, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));
        cell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell);
    }
 
    private void sendEmailWithPdf(Long customerID, ByteArrayOutputStream pdfOutputStream) throws MessagingException {
 
        String customerEmail = customerService.getCustomerEmail(customerID);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("dhayawork333@gmail.com");
        helper.setTo(customerEmail);
        helper.setSubject("BBC Payment Receipt");

        helper.setText("Dear Customer,\n\nPlease find the below attached for payment receipt.\n\nThank you for your payment.\n\nBest regards,\nBBC Team");

        helper.addAttachment("PaymentReceipt.pdf", new ByteArrayDataSource(pdfOutputStream.toByteArray(), "application/pdf"));

        mailSender.send(message);
    }
}
