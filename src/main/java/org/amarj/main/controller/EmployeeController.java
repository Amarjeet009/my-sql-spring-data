package org.amarj.main.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.validation.Valid;

import org.amarj.main.entity.EmployeeEntity;
import org.amarj.main.exception.ResourceNotFoundException;
import org.amarj.main.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/employee")
public class EmployeeController {
    
	@Autowired
	private EmployeeRepository employeeRepository;
	

	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String getTestMethod() {
		return "Love Coding! Go Ahead To Create Something New..ATB";
	}

	@RequestMapping(value = "/listOfEmp", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public List<EmployeeEntity> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@RequestMapping(value = "/createEmpDetails", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
	@ResponseBody
	public EmployeeEntity createEmployee(@Valid @RequestBody EmployeeEntity employee) {
		employee.setJoinAt(new Date());
		return employeeRepository.save(employee);
	}

	@RequestMapping(value = "/empData/{id}", method = RequestMethod.GET, produces = {"application/json"})
	@ResponseBody
	public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable(value = "id") Integer id)
			throws ResourceNotFoundException {
		EmployeeEntity employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));
		return ResponseEntity.ok().body(employee);
	}

	@RequestMapping(value = "/empUpdateData/{id}", method = RequestMethod.POST, produces = {"application/json"})
	@ResponseBody
	public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable(value = "id") Integer id,
			@Valid @RequestBody EmployeeEntity employeeDetails) throws ResourceNotFoundException {
		EmployeeEntity employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

		
		employee.setLastName(employeeDetails.getLastName());
		employee.setFirstName(employeeDetails.getFirstName());
		employee.setEmailId(employeeDetails.getEmailId());
		employee.setContactNumber(employeeDetails.getContactNumber());
		employee.setAddress(employeeDetails.getAddress());
		final EmployeeEntity updatedEmployee = employeeRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@RequestMapping(value = "/empDelete/{id}", method = RequestMethod.POST, produces = {"application/json"})
	@ResponseBody
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Integer id)
			throws ResourceNotFoundException {
		EmployeeEntity employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + id));

		employeeRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	

}
