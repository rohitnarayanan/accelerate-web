package accelerate.config.audit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import accelerate.domain.PersistentAuditEvent;

/**
 * Helper {@link Component} for converting {@link PersistentAuditEvent} to
 * {@link AuditEvent}
 * 
 * @version 1.0 Initial Version
 * @author JHipster
 * @since January 03, 2018
 */
@Component
@SuppressWarnings("static-method")
public class AuditEventConverter {
	/**
	 * Convert a list of PersistentAuditEvent to a list of AuditEvent
	 *
	 * @param aPersistentAuditEvents
	 *            the list to convert
	 * @return the converted list.
	 */
	public List<AuditEvent> convertToAuditEvent(Iterable<PersistentAuditEvent> aPersistentAuditEvents) {
		if (aPersistentAuditEvents == null) {
			return Collections.emptyList();
		}
		List<AuditEvent> auditEvents = new ArrayList<>();
		for (PersistentAuditEvent persistentAuditEvent : aPersistentAuditEvents) {
			auditEvents.add(convertToAuditEvent(persistentAuditEvent));
		}
		return auditEvents;
	}

	/**
	 * Convert a PersistentAuditEvent to an AuditEvent
	 *
	 * @param aPersistentAuditEvent
	 *            the event to convert
	 * @return the converted list.
	 */
	public AuditEvent convertToAuditEvent(PersistentAuditEvent aPersistentAuditEvent) {
		if (aPersistentAuditEvent == null) {
			return null;
		}
		return new AuditEvent(Date.from(aPersistentAuditEvent.getAuditEventDate()),
				aPersistentAuditEvent.getPrincipal(), aPersistentAuditEvent.getAuditEventType(),
				convertDataToObjects(aPersistentAuditEvent.getData()));
	}

	/**
	 * Internal conversion. This is needed to support the current SpringBoot
	 * actuator AuditEventRepository interface
	 *
	 * @param aData
	 *            the data to convert
	 * @return a map of String, Object
	 */
	public Map<String, Object> convertDataToObjects(Map<String, String> aData) {
		Map<String, Object> results = new HashMap<>();

		if (aData != null) {
			for (Map.Entry<String, String> entry : aData.entrySet()) {
				results.put(entry.getKey(), entry.getValue());
			}
		}
		return results;
	}

	/**
	 * Internal conversion. This method will allow to save additional data. By
	 * default, it will save the object as string
	 *
	 * @param aData
	 *            the data to convert
	 * @return a map of String, String
	 */
	public Map<String, String> convertDataToStrings(Map<String, Object> aData) {
		Map<String, String> results = new HashMap<>();

		if (aData != null) {
			for (Map.Entry<String, Object> entry : aData.entrySet()) {
				Object object = entry.getValue();

				// Extract the data that will be saved.
				if (object instanceof WebAuthenticationDetails) {
					WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails) object;
					results.put("remoteAddress", authenticationDetails.getRemoteAddress());
					results.put("sessionId", authenticationDetails.getSessionId());
				} else if (object != null) {
					results.put(entry.getKey(), object.toString());
				} else {
					results.put(entry.getKey(), "null");
				}
			}
		}

		return results;
	}
}
