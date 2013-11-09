package br.com.bugsys.descriptionEvent;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import br.com.bugsys.event.Event;
import br.com.bugsys.user.User;

@Entity
public class DescriptionEvent {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String description;
	
	private String creationDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User userDescription;
	
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

	public String getCreationDate() {
		return creationDate;
	}
	public DescriptionEvent setCreationDate(String creationDate) {
		this.creationDate = creationDate;
		return this;
	}

	public User getUserDescription() {
		if (userDescription == null) {
			userDescription = new User();
		}
		return userDescription;
	}
	public DescriptionEvent setUserDescription(User userDescription) {
		this.userDescription = userDescription;
		return this;
	}
}
