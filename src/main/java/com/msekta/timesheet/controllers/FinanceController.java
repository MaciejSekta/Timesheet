package com.msekta.timesheet.controllers;

import com.msekta.timesheet.services.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/finance")
@CrossOrigin
public class FinanceController {

    @Autowired
    private PdfService pdfService;

    @GetMapping
    public ResponseEntity<?> getAllShortProjects(){
        try{
            pdfService.createPdf("test.pdf");
            return ResponseEntity.ok().build();
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }
}
