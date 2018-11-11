package com.msekta.timesheet.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.msekta.timesheet.enums.UserType;
import com.msekta.timesheet.models.User;
import com.msekta.timesheet.models.Worklog;
import com.msekta.timesheet.repo.UserDao;
import com.msekta.timesheet.repo.WorklogDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.NoSuchElementException;

@Service
public class PdfService {


    private UserDao userDao;
    private WorklogDao worklogDao;

    @Autowired
    public PdfService(UserDao userDao, WorklogDao worklogDao) {
        this.userDao = userDao;
        this.worklogDao = worklogDao;
    }

    public void createPdf(String filename, Integer month)
            throws IOException, DocumentException {
        User user = userDao.findById(1L).orElseThrow(() -> new NoSuchElementException());
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("elo.pdf"));
        document.open();
        this.createHeader(document, user);
        document.add( Chunk.NEWLINE );
        document.add( Chunk.NEWLINE );
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 2, 2, 2, 2, 2, 1});
        table.addCell("Składnik płacowy");
        table.addCell("Przychody");
        table.addCell("Rozchody");
        table.addCell("Data od");
        table.addCell("Data do");
        table.addCell("Godziny");
        table.addCell("Dni");
        if(user.getUserType().equals(UserType.INTERNAL)){
            table.addCell("Umowa o prace");
        }else{
            table.addCell("Umowa b2b");
        }
        LocalDate min = LocalDate.parse("2018-" + month + "-01");
        LocalDate max = min.with(TemporalAdjusters.lastDayOfMonth());
        java.util.List<Worklog> worklogs = worklogDao.findAllByUser_idAndDateBetween(user.getId(), min, max);
        Integer sum = 0;
        for(Worklog w: worklogs){
            sum+= w.getDuration();
        }
        Integer earnings = sum*user.getRatePerHour();
        table.addCell(Integer.toString(earnings)+ " zl");
        if(user.getUserType().equals(UserType.INTERNAL)){
            table.addCell(Double.toString(earnings*0.3)+ " zl");
        }else{
            table.addCell("0 zl");
        }
        table.addCell(min.toString());
        table.addCell(max.toString());
        table.addCell(Integer.toString(sum));
        table.addCell(Double.toString(sum/(user.getWorkDayHours())));
        document.add(table);
        document.close();
    }

    private void createHeader(Document document, User user) throws DocumentException {

        PdfPTable table = new PdfPTable(3);
        table.setWidths(new int[]{2,1,2});
        table.getDefaultCell().setBorder(0);
        table.setWidthPercentage(100);
        table.addCell("Dane firmy:");
        table.addCell("");
        table.addCell("Dane pracownika:");
        table.addCell("Timesheet Company");
        table.addCell("");
        if(user.getUserType().equals(UserType.INTERNAL)) {
            table.addCell(user.getName() + " " + user.getSurname());
        }else{
            table.addCell(user.getPaymentInfo().getName());
        }
        table.addCell("ul.Wiejska 122");
        table.addCell("");
        table.addCell("ul." + user.getPaymentInfo().getStreet() + " " + user.getPaymentInfo().getHouseNo());
        table.addCell("Warszawa 43-200");
        table.addCell("");
        table.addCell(user.getPaymentInfo().getCity() + " " + user.getPaymentInfo().getPostalCode());
        table.addCell("NIP: 123456789");
        table.addCell("");
        if(user.getUserType().equals(UserType.INTERNAL)) {
            table.addCell("Nr konta:" + user.getPaymentInfo().getBankAccountNo());
        }else{
            table.addCell("NIP: " + user.getPaymentInfo().getNip());
            table.addCell("");
            table.addCell("");
            table.addCell("Nr konta:" + user.getPaymentInfo().getBankAccountNo());
        }
        table.setSpacingAfter(50f);
        document.add(table);
    }

}
