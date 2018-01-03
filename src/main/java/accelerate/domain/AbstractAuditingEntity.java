package accelerate.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * Base abstract class for entities which will hold definitions for created,
 * last modified by and created, last modified by date.
 * 
 * @version 1.0 Initial Version
 * @author JHipster
 * @since January 03, 2018
 */
@MappedSuperclass
@Audited
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@CreatedBy
	@Column(name = "created_by", nullable = false, length = 50, updatable = false)
	@JsonIgnore
	private String createdBy;

	/**
	 * 
	 */
	@CreatedDate
	@Column(name = "created_date", nullable = false)
	@JsonIgnore
	private Instant createdDate = Instant.now();

	/**
	 * 
	 */
	@LastModifiedBy
	@Column(name = "last_modified_by", length = 50)
	@JsonIgnore
	private String lastModifiedBy;

	/**
	 * 
	 */
	@LastModifiedDate
	@Column(name = "last_modified_date")
	@JsonIgnore
	private Instant lastModifiedDate = Instant.now();

	/**
	 * Getter method for "createdBy" property
	 * 
	 * @return createdBy
	 */
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 * Setter method for "createdBy" property
	 * 
	 * @param aCreatedBy
	 */
	public void setCreatedBy(String aCreatedBy) {
		this.createdBy = aCreatedBy;
	}

	/**
	 * Getter method for "createdDate" property
	 * 
	 * @return createdDate
	 */
	public Instant getCreatedDate() {
		return this.createdDate;
	}

	/**
	 * Setter method for "createdDate" property
	 * 
	 * @param aCreatedDate
	 */
	public void setCreatedDate(Instant aCreatedDate) {
		this.createdDate = aCreatedDate;
	}

	/**
	 * Getter method for "lastModifiedBy" property
	 * 
	 * @return lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return this.lastModifiedBy;
	}

	/**
	 * Setter method for "lastModifiedBy" property
	 * 
	 * @param aLastModifiedBy
	 */
	public void setLastModifiedBy(String aLastModifiedBy) {
		this.lastModifiedBy = aLastModifiedBy;
	}

	/**
	 * Getter method for "lastModifiedDate" property
	 * 
	 * @return lastModifiedDate
	 */
	public Instant getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	/**
	 * Setter method for "lastModifiedDate" property
	 * 
	 * @param aLastModifiedDate
	 */
	public void setLastModifiedDate(Instant aLastModifiedDate) {
		this.lastModifiedDate = aLastModifiedDate;
	}
}
