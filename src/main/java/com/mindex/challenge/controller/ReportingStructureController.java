package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportingStructureController {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    ReportingStructureService reportingStructureService;

    @GetMapping("/reportingStructure/{employeeId}")
    public ReportingStructure readByEmployeeId(@PathVariable String employeeId) {
        LOG.debug("Reading ReportingStructure by the given employee id [{}]", employeeId);

        ReportingStructure reportingStructure = reportingStructureService.readByEmployeeId(employeeId);
        return reportingStructure;
    }
}