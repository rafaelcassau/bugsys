package br.com.bugsys.eventType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EventType {

	@Id
	@GeneratedValue
	private Integer id;
	private String eventType;

	public Integer getId() {
		return id;
	}
	public EventType setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public String getEventType() {
		return eventType;
	}
	public EventType setEventType(String eventType) {
		this.eventType = eventType;
		return this;
	}
}
