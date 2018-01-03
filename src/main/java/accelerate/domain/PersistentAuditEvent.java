package accelerate.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Persist AuditEvent managed by the Spring Boot actuator.
 *
 * @see org.springframework.boot.actuate.audit.AuditEvent
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 03, 2018
 */
@Entity
@Table(name = "jhi_persistent_audit_event")
public class PersistentAuditEvent implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "event_id")
	private Long id;

	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private String principal;

	/**
	 * 
	 */
	@Column(name = "event_date")
	private Instant auditEventDate;

	/**
	 * 
	 */
	@Column(name = "event_type")
	private String auditEventType;

	/**
	 * 
	 */
	@ElementCollection
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	@CollectionTable(name = "jhi_persistent_audit_evt_data", joinColumns = @JoinColumn(name = "event_id"))
	private Map<String, String> data = new HashMap<>();

	/**
	 * Getter method for "id" property
	 * 
	 * @return id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Setter method for "id" property
	 * 
	 * @param aId
	 */
	public void setId(Long aId) {
		this.id = aId;
	}

	/**
	 * Getter method for "principal" property
	 * 
	 * @return principal
	 */
	public String getPrincipal() {
		return this.principal;
	}

	/**
	 * Setter method for "principal" property
	 * 
	 * @param aPrincipal
	 */
	public void setPrincipal(String aPrincipal) {
		this.principal = aPrincipal;
	}

	/**
	 * Getter method for "auditEventDate" property
	 * 
	 * @return auditEventDate
	 */
	public Instant getAuditEventDate() {
		return this.auditEventDate;
	}

	/**
	 * Setter method for "auditEventDate" property
	 * 
	 * @param aAuditEventDate
	 */
	public void setAuditEventDate(Instant aAuditEventDate) {
		this.auditEventDate = aAuditEventDate;
	}

	/**
	 * Getter method for "auditEventType" property
	 * 
	 * @return auditEventType
	 */
	public String getAuditEventType() {
		return this.auditEventType;
	}

	/**
	 * Setter method for "auditEventType" property
	 * 
	 * @param aAuditEventType
	 */
	public void setAuditEventType(String aAuditEventType) {
		this.auditEventType = aAuditEventType;
	}

	/**
	 * Getter method for "data" property
	 * 
	 * @return data
	 */
	public Map<String, String> getData() {
		return this.data;
	}

	/**
	 * Setter method for "data" property
	 * 
	 * @param aData
	 */
	public void setData(Map<String, String> aData) {
		this.data = aData;
	}
}
