package accelerate.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import accelerate.utils.CommonUtils;
import accelerate.utils.JSONUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Social user
 * 
 * @version 1.0 Initial Version
 * @author Rohit Narayanan
 * @since January 03, 2018
 */
@Entity
@Table(name = "jhi_social_user_connection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SocialUserConnection implements Serializable {
	/**
	 * 
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
	@Column(name = "user_id", length = 255, nullable = false)
	private String userId;

	/**
	 * 
	 */
	@NotNull
	@Column(name = "provider_id", length = 255, nullable = false)
	private String providerId;

	/**
	 * 
	 */
	@NotNull
	@Column(name = "provider_user_id", length = 255, nullable = false)
	private String providerUserId;

	/**
	 * 
	 */
	@NotNull
	@Column(nullable = false)
	private Long rank;

	/**
	 * 
	 */
	@Column(name = "display_name", length = 255)
	private String displayName;

	/**
	 * 
	 */
	@Column(name = "profile_url", length = 255)
	private String profileURL;

	/**
	 * 
	 */
	@Column(name = "image_url", length = 255)
	private String imageURL;

	/**
	 * 
	 */
	@NotNull
	@Column(name = "access_token", length = 255, nullable = false)
	private String accessToken;

	/**
	 * 
	 */
	@Column(length = 255)
	private String secret;

	/**
	 * 
	 */
	@Column(name = "refresh_token", length = 255)
	private String refreshToken;

	/**
	 * 
	 */
	@Column(name = "expire_time")
	private Long expireTime;

	/**
	 * default constructor
	 */
	public SocialUserConnection() {
		// empty constructor
	}

	/**
	 * @param aUserId
	 * @param aProviderId
	 * @param aProviderUserId
	 * @param aRank
	 * @param aDisplayName
	 * @param aProfileURL
	 * @param aImageURL
	 * @param aAccessToken
	 * @param aSecret
	 * @param aRefreshToken
	 * @param aExpireTime
	 */
	public SocialUserConnection(String aUserId, String aProviderId, String aProviderUserId, Long aRank,
			String aDisplayName, String aProfileURL, String aImageURL, String aAccessToken, String aSecret,
			String aRefreshToken, Long aExpireTime) {
		this.userId = aUserId;
		this.providerId = aProviderId;
		this.providerUserId = aProviderUserId;
		this.rank = aRank;
		this.displayName = aDisplayName;
		this.profileURL = aProfileURL;
		this.imageURL = aImageURL;
		this.accessToken = aAccessToken;
		this.secret = aSecret;
		this.refreshToken = aRefreshToken;
		this.expireTime = aExpireTime;
	}

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

		return CommonUtils.compare(((SocialUserConnection) aObject).id, this.id);
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
		return "SocialUserConnection" + JSONUtil.serialize(this);
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
	 * Getter method for "userId" property
	 * 
	 * @return userId
	 */
	public String getUserId() {
		return this.userId;
	}

	/**
	 * Setter method for "userId" property
	 * 
	 * @param aUserId
	 */
	public void setUserId(String aUserId) {
		this.userId = aUserId;
	}

	/**
	 * Getter method for "providerId" property
	 * 
	 * @return providerId
	 */
	public String getProviderId() {
		return this.providerId;
	}

	/**
	 * Setter method for "providerId" property
	 * 
	 * @param aProviderId
	 */
	public void setProviderId(String aProviderId) {
		this.providerId = aProviderId;
	}

	/**
	 * Getter method for "providerUserId" property
	 * 
	 * @return providerUserId
	 */
	public String getProviderUserId() {
		return this.providerUserId;
	}

	/**
	 * Setter method for "providerUserId" property
	 * 
	 * @param aProviderUserId
	 */
	public void setProviderUserId(String aProviderUserId) {
		this.providerUserId = aProviderUserId;
	}

	/**
	 * Getter method for "rank" property
	 * 
	 * @return rank
	 */
	public Long getRank() {
		return this.rank;
	}

	/**
	 * Setter method for "rank" property
	 * 
	 * @param aRank
	 */
	public void setRank(Long aRank) {
		this.rank = aRank;
	}

	/**
	 * Getter method for "displayName" property
	 * 
	 * @return displayName
	 */
	public String getDisplayName() {
		return this.displayName;
	}

	/**
	 * Setter method for "displayName" property
	 * 
	 * @param aDisplayName
	 */
	public void setDisplayName(String aDisplayName) {
		this.displayName = aDisplayName;
	}

	/**
	 * Getter method for "profileURL" property
	 * 
	 * @return profileURL
	 */
	public String getProfileURL() {
		return this.profileURL;
	}

	/**
	 * Setter method for "profileURL" property
	 * 
	 * @param aProfileURL
	 */
	public void setProfileURL(String aProfileURL) {
		this.profileURL = aProfileURL;
	}

	/**
	 * Getter method for "imageURL" property
	 * 
	 * @return imageURL
	 */
	public String getImageURL() {
		return this.imageURL;
	}

	/**
	 * Setter method for "imageURL" property
	 * 
	 * @param aImageURL
	 */
	public void setImageURL(String aImageURL) {
		this.imageURL = aImageURL;
	}

	/**
	 * Getter method for "accessToken" property
	 * 
	 * @return accessToken
	 */
	public String getAccessToken() {
		return this.accessToken;
	}

	/**
	 * Setter method for "accessToken" property
	 * 
	 * @param aAccessToken
	 */
	public void setAccessToken(String aAccessToken) {
		this.accessToken = aAccessToken;
	}

	/**
	 * Getter method for "secret" property
	 * 
	 * @return secret
	 */
	public String getSecret() {
		return this.secret;
	}

	/**
	 * Setter method for "secret" property
	 * 
	 * @param aSecret
	 */
	public void setSecret(String aSecret) {
		this.secret = aSecret;
	}

	/**
	 * Getter method for "refreshToken" property
	 * 
	 * @return refreshToken
	 */
	public String getRefreshToken() {
		return this.refreshToken;
	}

	/**
	 * Setter method for "refreshToken" property
	 * 
	 * @param aRefreshToken
	 */
	public void setRefreshToken(String aRefreshToken) {
		this.refreshToken = aRefreshToken;
	}

	/**
	 * Getter method for "expireTime" property
	 * 
	 * @return expireTime
	 */
	public Long getExpireTime() {
		return this.expireTime;
	}

	/**
	 * Setter method for "expireTime" property
	 * 
	 * @param aExpireTime
	 */
	public void setExpireTime(Long aExpireTime) {
		this.expireTime = aExpireTime;
	}
}
