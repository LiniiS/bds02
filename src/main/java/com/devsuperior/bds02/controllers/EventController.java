package com.devsuperior.bds02.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.services.EventService;

@RestController
@RequestMapping(value="/events")
public class EventController {

	@Autowired
	private EventService eventService;

	
	@GetMapping
	public ResponseEntity<List<EventDTO>> findAllEvents(){
		List<EventDTO> listEvents = eventService.findAllEvents();
		return ResponseEntity.ok().body(listEvents);
	}

	
	@PutMapping(value="/{id}")
	public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDto){
		eventDto = eventService.update(id, eventDto);
		
		return ResponseEntity.ok().body(eventDto);
	}


}
