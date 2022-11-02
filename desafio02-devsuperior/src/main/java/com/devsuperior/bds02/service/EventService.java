package com.devsuperior.bds02.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.repository.CityRepository;
import com.devsuperior.bds02.repository.EventRepository;
import com.devsuperior.bds02.service.exception.ResourceNotFoundException;

@Service
public class EventService {
	@Autowired
	EventRepository eventRepository;

	@Autowired
	CityRepository cityRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO eventDTO) {
		try {
			Event event = eventRepository.getOne(id);
			eventDtoToEvent(event, eventDTO);
			event = eventRepository.save(event);
			return new EventDTO(event);
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}

	private void eventDtoToEvent(Event event, EventDTO eventDTO) {
		City city = cityRepository.getOne(eventDTO.getCityId());
		event.setName(eventDTO.getName());
		event.setDate(eventDTO.getDate());
		event.setUrl(eventDTO.getUrl());
		event.setCity(city);
	}
}
