package com.mycompany.myapp.repository.specification;

import com.mycompany.myapp.domain.Pool;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PoolSpecificationBuilder {

    private final List<SearchCriteria> params;

    public PoolSpecificationBuilder() {
        params = new ArrayList<>();
    }

    public PoolSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Pool> build() {
        if (params.size() == 0) {
            return null;
        }

        List<Specification> specs = params.stream()
            .map(PoolSpecification::new)
            .collect(Collectors.toList());

        Specification result = specs.get(0);

        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }

        return result;

    }
}
