package org.oopscraft.arch4j.core.alarm.dao;

import org.oopscraft.arch4j.core.alarm.AlarmSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity,String>, JpaSpecificationExecutor<AlarmEntity> {

    default Page<AlarmEntity> findAll(AlarmSearch alarmSearch, Pageable pageable) {
        Specification<AlarmEntity> specification = Specification.where(null);

        if(alarmSearch.getId() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(AlarmEntity_.ID), '%'+ alarmSearch.getId() + '%'));
        }
        if(alarmSearch.getName() != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(AlarmEntity_.NAME), '%' + alarmSearch.getName() + '%'));
        }

        return findAll(specification, pageable);
    }

}
