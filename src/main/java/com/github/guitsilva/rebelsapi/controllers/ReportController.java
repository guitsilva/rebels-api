package com.github.guitsilva.rebelsapi.controllers;

import com.github.guitsilva.rebelsapi.domain.dtos.ReportDTO;
import com.github.guitsilva.rebelsapi.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ReportDTO generateReport() {
        return this.reportService.generateReport();
    }
}
