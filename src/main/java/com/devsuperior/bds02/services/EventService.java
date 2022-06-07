package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	public List<EventDTO> findAllEvents() {
		// dessa forma o retorno não é uma lista ordenada, utilizar o Sort pra ordenar
		// pelo "nome"
		List<Event> listEvents = eventRepository.findAll(Sort.by("name"));
		// converter p/ DTO-> retorna uma lista acumulada de entradas já convertidas
		return listEvents.stream().map(event -> new EventDTO(event)).collect(Collectors.toList());
	}

	@Transactional
	public EventDTO update(Long id, EventDTO eventDto) {
		try {
			Event eventUpdated = eventRepository.getOne(id);
			getEntityFromDto(eventDto, eventUpdated);
			// eventUpdated.setName(eventDto.getName());
			// eventUpdated.setCity(new City (eventDto.getCityId(), null));
			// eventUpdated.setDate(eventDto.getDate());
			// eventUpdated.setUrl(eventDto.getUrl());

			eventUpdated = eventRepository.save(eventUpdated);

			return new EventDTO(eventUpdated);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Event with id: " + id + " not found!");
		}

	}

	// método auxiliar
	private void getEntityFromDto(EventDTO eventDto, Event eventUpdated) {
		eventUpdated.setName(eventDto.getName());
		eventUpdated.setCity(new City(eventDto.getCityId(), null));
		eventUpdated.setDate(eventDto.getDate());
		eventUpdated.setUrl(eventDto.getUrl());
	}
}
