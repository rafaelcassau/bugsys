package br.com.bugsys.descriptionEvent;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.bugsys.event.Event;

@Entity
public class DescriptionEvent {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String description;
	
	private Date lastModifyDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_FK")
	private Event event;

	public Integer getId() {
		return id;
	}
	public DescriptionEvent setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getDescription() {
		return description;
	}
	public DescriptionEvent setDescription(String description) {
		this.description = description;
		return this;
	}

	public Event getEvent() {
		if (event == null) {
			event = new Event();
		}
		return event;
	}
	public DescriptionEvent setEvent(Event event) {
		this.event = event;
		return this;
	}
	
	public Date getLastModifyDate() {
		return lastModifyDate;
	}
	public DescriptionEvent setLastModifyDate(Date lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
		return this;
	}
}
