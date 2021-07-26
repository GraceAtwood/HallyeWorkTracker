package com.hallye.config;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.hallye.application.contribution.ContributionResource;
import com.hallye.application.contribution.ContributionService;
import com.hallye.application.person.PersonResource;
import com.hallye.application.person.PersonService;
import com.hallye.application.workflow.WorkflowRepository;
import com.hallye.application.workflow.WorkflowResource;
import com.hallye.application.workflow.WorkflowService;
import org.hibernate.dialect.Oracle12cDialect;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.*;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;
import static org.hibernate.jpa.AvailableSettings.JDBC_DRIVER;
import static org.hibernate.jpa.AvailableSettings.JDBC_URL;

public class ResourceConfigModule extends AbstractModule {

    private static PersistenceUnitInfo archiverPersistenceUnitInfo() {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return "ApplicationPersistenceUnit";
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "org.hibernate.jpa.HibernatePersistenceProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<java.net.URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass()
                            .getClassLoader()
                            .getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return Collections.emptyList();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return new Properties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer transformer) {

            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }

    @Provides
    @Inject
    public EntityManager provideEntityManager() {

        var entityManagerFactory = new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                archiverPersistenceUnitInfo(),
                ImmutableMap.<String, Object>builder()
                        .put(JPA_JDBC_DRIVER, "org.h2.Driver")
                        .put(JPA_JDBC_URL, "jdbc:h2:mem:bookstore")
                        .put(JPA_JDBC_USER, "sa")
                        .put(JPA_JDBC_PASSWORD, "")
                        .put(DIALECT, "org.hibernate.dialect.H2Dialect")
                        .put(HBM2DDL_AUTO, "update")
                        .put(SHOW_SQL, "true")
                        .put(FORMAT_SQL, "true")
                        .put(USE_SQL_COMMENTS, "true")
                        .build());

        var entityManager = entityManagerFactory.createEntityManager();

        return entityManager;
    }

    @Override
    protected void configure() {

        bind(PersonResource.class);
        bind(WorkflowResource.class);
        bind(ContributionResource.class);

        bind(PersonService.class);
        bind(WorkflowService.class);
        bind(ContributionService.class);

        bind(WorkflowRepository.class);
    }
}