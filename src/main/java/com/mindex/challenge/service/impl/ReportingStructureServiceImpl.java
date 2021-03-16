package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure readByEmployeeId(String employeeId) {
        LOG.debug("Reading reporting structure by the given employee id [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);

        // Find number of reports for the given employee
        Integer numberOfReports = findNumberOfReports(employee.getEmployeeId());
        reportingStructure.setNumberOfReports(numberOfReports);

        return reportingStructure;
    }

    /**
     * Help to find numberOfReports for the given employee
     * @param employeeId
     * @return
     */
    private Integer findNumberOfReports(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if(employee == null) {
            return 0;
        }
        List<Employee> directReports = employee.getDirectReports();

        if(directReports == null || directReports.size() == 0) { // There  is no report
            return 0;
        }
        int numberOfReports = 0;
        for(Employee directReportEmployee: directReports) {
            numberOfReports += findNumberOfReports(directReportEmployee.getEmployeeId()) + 1;
        }
        return numberOfReports;
    }
}
