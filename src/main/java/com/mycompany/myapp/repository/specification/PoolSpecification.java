package com.mycompany.myapp.repository.specification;

import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.domain.Tool;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.List;

public class PoolSpecification implements Specification<Pool>{

    private SearchCriteria criteria;

    public PoolSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Pool> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        switch (criteria.getOperation()) {
            case ">":
                return criteriaBuilder.greaterThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
            case "<":
                return criteriaBuilder.lessThanOrEqualTo(
                    root.get(criteria.getKey()), criteria.getValue().toString());
            case ":":
                if (root.get(criteria.getKey()).getJavaType() == boolean.class) {
                    return criteriaBuilder.equal(root.get(criteria.getKey()), Boolean.parseBoolean((String) criteria.getValue()));
                } else if (root.get(criteria.getKey()).getJavaType() == List.class) {
                    Join<Pool, Tool> join = root.join("tools");
                    return criteriaBuilder.equal(join.get("id"), criteria.getValue());
                } else if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase() + "%");
                } else {
                    return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
                }
        }

        return null;
    }
}
