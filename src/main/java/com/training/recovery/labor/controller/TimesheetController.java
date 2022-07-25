package com.training.recovery.labor.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.recovery.labor.model.Timesheet;
import com.training.recovery.labor.service.TimesheetService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tim")
public class TimesheetController {
	
	@Autowired
	private TimesheetService timesheetService;
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@GetMapping("/timesheet/{id}")
	public ResponseEntity<Timesheet> get(@PathVariable int id) {
	    try {
	    	Timesheet timesheet = timesheetService.getTimesheet(id);
	        return new ResponseEntity<Timesheet>(timesheet, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<Timesheet>(HttpStatus.NOT_FOUND);
	    }
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/timesheet/{id}")
	public ResponseEntity<?> update(@RequestBody Timesheet timesheet, @PathVariable Integer id) {
	    try {
	      timesheet.setId(id);
	      timesheetService.saveTimesheet(timesheet);
	      return new ResponseEntity<>(HttpStatus.OK);	
	    }catch (NoSuchElementException e) {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@RequestMapping(value="/timesheet", method = RequestMethod.GET)
    public List<Timesheet> listTimesheet(){
        return timesheetService.getTimesheetList();
    }
	
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	@RequestMapping(value = "/timesheet", method = RequestMethod.POST)
    public Timesheet create(@RequestBody Timesheet timesheet){
        return timesheetService.saveTimesheet(timesheet);
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/timesheet/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable(value = "id") Integer id){
		timesheetService.deleteTimesheet(id);
        return "success";
    }
}
