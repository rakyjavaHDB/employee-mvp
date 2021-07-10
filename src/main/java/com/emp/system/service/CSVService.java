package com.emp.system.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emp.system.csv.CSVHelper;
import com.emp.system.model.Employee;
import com.emp.system.repository.EmployeeRepository;

@Service
public class CSVService {
	
  @Autowired
  EmployeeRepository employeeRepository;

  public void save(MultipartFile file) {
    try {
      List<Employee> employees = CSVHelper.csvToEmployees(file.getInputStream());
      employeeRepository.saveAll(employees);
      
    } catch (IOException e) {
      throw new RuntimeException("fail to store csv data: " + e.getMessage());
    }
  }

}
