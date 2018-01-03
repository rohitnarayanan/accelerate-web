package accelerate.domain;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import accelerate.utils.CommonUtils;
import accelerate.utils.JSONUtil;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * An authority (a security role) used by Spring Security.
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 03, 2018
 */
@Entity
@Table(name = "jhi_authority")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Authority implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@NotNull
	@Size(max = 50)
	@Id
	@Column(length = 50)
	private String name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * @param aObject
	 * @return
	 */
	@Override
	public boolean equals(Object aObject) {
		if (this == aObject) {
			return true;
		}

		if ((aObject == null) || !(aObject instanceof Authority)) {
			return false;
		}

		return CommonUtils.compare(((Authority) aObject).name, this.name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * @return
	 */
	@Override
	public int hashCode() {
		return StringUtils.isEmpty(this.name) ? 0 : this.name.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/**
	 * @return
	 */
	@Override
	public String toString() {
		return "Authority" + JSONUtil.serialize(this);
	}

	/**
	 * Getter method for "name" property
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter method for "name" property
	 * 
	 * @param aName
	 */
	public void setName(String aName) {
		this.name = aName;
	}
}
