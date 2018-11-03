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

    public void createPdf(String filename)
            throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("elo.pdf"));
        document.open();
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 2, 2, 1, 1, 1, 1});
        table.addCell("Składnik płacowy");
        table.addCell("Przychody");
        table.addCell("Rozchody");
        table.addCell("Data od");
        table.addCell("Data do");
        table.addCell("Godziny");
        table.addCell("Dni");
        User user = userDao.findById(3L).orElseThrow(() -> new NoSuchElementException());
        if(user.getUserType().equals(UserType.INTERNAL)){
            table.addCell("Umowa o prace");
        }else{
            table.addCell("Umowa b2b");
        }
        LocalDate min = LocalDate.parse("2018-11-01");
        LocalDate max = min.with(TemporalAdjusters.lastDayOfMonth());
        java.util.List<Worklog> worklogs = worklogDao.findAllByUser_idAndDateBetween(user.getId(), min, max);
        Integer sum = 0;
        for(Worklog w: worklogs){
            sum+= w.getDuration();
        }
        table.addCell(Integer.toString(sum*user.getRatePerHour()));
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
        document.add(table);
        document.close();
    }

}
