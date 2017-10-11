package db.entities;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class JobContract {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@OneToMany
	@JoinTable(name="JOB_PARTICIPANT",joinColumns={@JoinColumn(name="jobId",referencedColumnName="id")})
	private List<RegisteredUser> participants = new LinkedList<RegisteredUser>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<RegisteredUser> getParticipants() {
		return participants;
	}
	public void setParticipants(List<RegisteredUser> participants) {
		this.participants = participants;
	}
}
