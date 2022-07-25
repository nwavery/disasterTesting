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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.training.recovery.labor.model.Job;
import com.training.recovery.labor.model.Machine;
import com.training.recovery.labor.service.JobService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/job")
public class JobController {
	
	@Autowired
	private JobService jobService;
	
	@GetMapping("/job/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Job> get(@PathVariable int id) {
	    try {
	    	Job job = jobService.getJob(id);
	        return new ResponseEntity<Job>(job, HttpStatus.OK);
	    } catch (NoSuchElementException e) {
	        return new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	@PutMapping("/job/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> update(@RequestBody Job job, @PathVariable Integer id) {
	    try {
	      job.setId(id);
	      jobService.saveJob(job);
	      return new ResponseEntity<>(HttpStatus.OK);	
	    }catch (NoSuchElementException e) {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }      
	}
	
	@RequestMapping(value="/job", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN')")
    public List<Job> listJob(){
        return jobService.getJobList();
    }
	
	@RequestMapping(value = "/job", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN')")
    public Job create(@RequestBody Job job){
        return jobService.saveJob(job);
    }
	
	@RequestMapping(value = "/job/{id}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable(value = "id") Integer id){
        jobService.deleteJob(id);
        return "success";
    }
}
