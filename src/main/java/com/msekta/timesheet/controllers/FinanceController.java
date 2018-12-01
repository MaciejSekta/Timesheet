package com.msekta.timesheet.controllers;

import com.msekta.timesheet.services.PdfService;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/finance")
@CrossOrigin
@Slf4j
public class FinanceController {

    @Autowired
    private PdfService pdfService;

    @GetMapping(path = "/{id}/{month}/{year}", produces = "application/zip")
    public ResponseEntity<?> getAllShortProjects(HttpServletResponse response, @PathVariable("id") Long id, @PathVariable("month") String month, @PathVariable("year") String year){
        try{
            response.setStatus(HttpServletResponse.SC_OK);
            response.addHeader("Content-Disposition", "attachment; filename=\"month + year +.zip\"");
            ByteArrayOutputStream byteArrayOutputStream = pdfService.createPdf(id, month, year);
            IOUtils.closeQuietly(byteArrayOutputStream);
            return ResponseEntity.ok(byteArrayOutputStream.toByteArray());
        }catch(Exception e){
        	log.info(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }

    }
}
