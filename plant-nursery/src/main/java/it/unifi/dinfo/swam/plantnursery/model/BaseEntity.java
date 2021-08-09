package it.unifi.dinfo.swam.plantnursery.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String uuid;

	protected BaseEntity() {
	}

	public BaseEntity(String uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("uuid cannot be null");
		}
		this.uuid = uuid;
	}

	public Long getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		
		BaseEntity other = (BaseEntity) obj;
		
		return uuid.equals(other.getUuid());
	}

}
