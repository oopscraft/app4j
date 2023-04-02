package org.oopscraft.arch4j.core.sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends JpaRepository<SampleEntity, String>, JpaSpecificationExecutor<SampleEntity>, SampleRepositorySupport {

}
