package accelerate.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import accelerate.config.Constants;
import accelerate.utils.CommonUtils;
import accelerate.utils.JSONUtil;

/**
 * A user.
 */
/**
 * PUT DESCRIPTION HERE
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 03, 2018
 */
@Entity
@Table(name = "jhi_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "user")
public class User extends AbstractAuditingEntity {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 
	 */
	@NotNull
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 100)
	@Column(length = 100, unique = true, nullable = false)
	private String login;

	/**
	 * 
	 */
	@JsonIgnore
	@NotNull
	@Size(min = 60, max = 60)
	@Column(name = "password_hash", length = 60)
	private String password;

	/**
	 * 
	 */
	@Size(max = 50)
	@Column(name = "first_name", length = 50)
	private String firstName;

	/**
	 * 
	 */
	@Size(max = 50)
	@Column(name = "last_name", length = 50)
	private String lastName;

	/**
	 * 
	 */
	@Email
	@Size(min = 5, max = 100)
	@Column(length = 100, unique = true)
	private String email;

	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private boolean activated = false;

	/**
	 * 
	 */
	@Size(min = 2, max = 6)
	@Column(name = "lang_key", length = 6)
	private String langKey;

	/**
	 * 
	 */
	@Size(max = 256)
	@Column(name = "image_url", length = 256)
	private String imageUrl;

	/**
	 * 
	 */
	@Size(max = 20)
	@Column(name = "activation_key", length = 20)
	@JsonIgnore
	private String activationKey;

	/**
	 * 
	 */
	@Size(max = 20)
	@Column(name = "reset_key", length = 20)
	@JsonIgnore
	private String resetKey;

	/**
	 * 
	 */
	@Column(name = "reset_date")
	private Instant resetDate = null;

	/**
	 * 
	 */
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "jhi_user_authority", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_name", referencedColumnName = "name") })
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@BatchSize(size = 20)
	private Set<Authority> authorities = new HashSet<>();

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

		return CommonUtils.compare(((User) aObject).id, this.id);
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
		return Objects.hashCode(this.id);
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
		return "User" + JSONUtil.serialize(this);
	}

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
	 * Getter method for "login" property
	 * 
	 * @return login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Setter method for "login" property
	 * 
	 * @param aLogin
	 */
	public void setLogin(String aLogin) {
		this.login = aLogin;
	}

	/**
	 * Getter method for "password" property
	 * 
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Setter method for "password" property
	 * 
	 * @param aPassword
	 */
	public void setPassword(String aPassword) {
		this.password = aPassword;
	}

	/**
	 * Getter method for "firstName" property
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Setter method for "firstName" property
	 * 
	 * @param aFirstName
	 */
	public void setFirstName(String aFirstName) {
		this.firstName = aFirstName;
	}

	/**
	 * Getter method for "lastName" property
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Setter method for "lastName" property
	 * 
	 * @param aLastName
	 */
	public void setLastName(String aLastName) {
		this.lastName = aLastName;
	}

	/**
	 * Getter method for "email" property
	 * 
	 * @return email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Setter method for "email" property
	 * 
	 * @param aEmail
	 */
	public void setEmail(String aEmail) {
		this.email = aEmail;
	}

	/**
	 * Getter method for "activated" property
	 * 
	 * @return activated
	 */
	public boolean isActivated() {
		return this.activated;
	}

	/**
	 * Setter method for "activated" property
	 * 
	 * @param aActivated
	 */
	public void setActivated(boolean aActivated) {
		this.activated = aActivated;
	}

	/**
	 * Getter method for "langKey" property
	 * 
	 * @return langKey
	 */
	public String getLangKey() {
		return this.langKey;
	}

	/**
	 * Setter method for "langKey" property
	 * 
	 * @param aLangKey
	 */
	public void setLangKey(String aLangKey) {
		this.langKey = aLangKey;
	}

	/**
	 * Getter method for "imageUrl" property
	 * 
	 * @return imageUrl
	 */
	public String getImageUrl() {
		return this.imageUrl;
	}

	/**
	 * Setter method for "imageUrl" property
	 * 
	 * @param aImageUrl
	 */
	public void setImageUrl(String aImageUrl) {
		this.imageUrl = aImageUrl;
	}

	/**
	 * Getter method for "activationKey" property
	 * 
	 * @return activationKey
	 */
	public String getActivationKey() {
		return this.activationKey;
	}

	/**
	 * Setter method for "activationKey" property
	 * 
	 * @param aActivationKey
	 */
	public void setActivationKey(String aActivationKey) {
		this.activationKey = aActivationKey;
	}

	/**
	 * Getter method for "resetKey" property
	 * 
	 * @return resetKey
	 */
	public String getResetKey() {
		return this.resetKey;
	}

	/**
	 * Setter method for "resetKey" property
	 * 
	 * @param aResetKey
	 */
	public void setResetKey(String aResetKey) {
		this.resetKey = aResetKey;
	}

	/**
	 * Getter method for "resetDate" property
	 * 
	 * @return resetDate
	 */
	public Instant getResetDate() {
		return this.resetDate;
	}

	/**
	 * Setter method for "resetDate" property
	 * 
	 * @param aResetDate
	 */
	public void setResetDate(Instant aResetDate) {
		this.resetDate = aResetDate;
	}

	/**
	 * Getter method for "authorities" property
	 * 
	 * @return authorities
	 */
	public Set<Authority> getAuthorities() {
		return this.authorities;
	}

	/**
	 * Setter method for "authorities" property
	 * 
	 * @param aAuthorities
	 */
	public void setAuthorities(Set<Authority> aAuthorities) {
		this.authorities = aAuthorities;
	}
}
