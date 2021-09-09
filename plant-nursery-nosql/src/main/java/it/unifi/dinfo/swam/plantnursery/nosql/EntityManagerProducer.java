package it.unifi.dinfo.swam.plantnursery.nosql;

import org.eclipse.jnosql.communication.cassandra.column.CassandraConfiguration;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.nosql.column.ColumnConfiguration;
import jakarta.nosql.column.ColumnFamilyManager;
import jakarta.nosql.column.ColumnFamilyManagerFactory;

@ApplicationScoped
public class EntityManagerProducer {
	
	private static final String KEY_SPACE = "plant_nursery";

	private ColumnConfiguration cassandraConfiguration;

	private ColumnFamilyManagerFactory managerFactory;

	@PostConstruct
	public void init() {
		cassandraConfiguration = new CassandraConfiguration();
		managerFactory = cassandraConfiguration.get();
	}

	@Produces
	public ColumnFamilyManager getManagerCassandra() {
		return managerFactory.get(KEY_SPACE);
	}
}
