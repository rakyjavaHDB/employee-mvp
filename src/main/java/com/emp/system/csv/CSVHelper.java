package com.emp.system.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.emp.system.model.Employee;

public class CSVHelper {
	  public static String TYPE = "text/csv";
	  static String[] HEADERs = { "id", "login", "name", "salary","startDate" };

	  public static boolean hasCSVFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getContentType())) {
	      return false;
	    }

	    return true;
	  }

	  public static List<Employee> csvToEmployees(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

	      List<Employee> employees = new ArrayList<Employee>();

	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	      for (CSVRecord csvRecord : csvRecords) {
	    	  Employee employee = new Employee();
	    	  employee.setId(Long.parseLong(csvRecord.get("Id")));
	    	  employee.setLogin(csvRecord.get("login"));
	    	  employee.setName(csvRecord.get("name"));
	    	  employee.setSalary(new BigDecimal(csvRecord.get("salary")));
	    	  employee.setStartDate(Timestamp.valueOf(csvRecord.get("startDate")));

	    	  employees.add(employee);
	      }

	      return employees;
	      
	    } catch (IOException e) {
	      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	    }
	  }

	}
