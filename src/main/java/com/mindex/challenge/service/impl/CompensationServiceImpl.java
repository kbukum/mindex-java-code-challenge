package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);
        compensation.setCompensationId(UUID.randomUUID().toString());
        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Reading Compensation with id [{}]", id);
        Compensation compensation = compensationRepository.findByCompensationId(id);

        if (compensation == null) {
            throw new RuntimeException("Invalid compensationId: " + id);
        }
        return compensation;
    }

    @Override
    public Compensation update(Compensation compensation) {
        if (compensation.getEmployee() == null || StringUtils.isEmpty(compensation.getEmployee().getEmployeeId())) {
            throw new RuntimeException("Invalid Compensation");
        }
        LOG.debug("Creating compensation [{}]", compensation);
        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public List<Compensation> readByEmployeeId(String employeeId) {
        LOG.debug("Reading Compensation by employeeId [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        if (employee == null) {
            throw new RuntimeException("Invalid Employee by the given employeeId: " + employeeId);
        }

        return compensationRepository.findByEmployee(employee);
    }
}
