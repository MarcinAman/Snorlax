package com.mycompany.myapp.repository.specification;

import com.mycompany.myapp.domain.Pool;
import com.mycompany.myapp.repository.PoolRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PoolSpecificationTest {

    @Autowired
    private PoolRepository repository;

    private Pool pool1;
    private Pool pool2;
    private Pool pool3;

    @Before
    public void setUp() throws Exception {
        pool1 = new Pool();
        pool1.setPoolId("1");
        pool1.setDisplayName("Distributed systems");
        pool1.setEnabled(true);
        pool1.setMaximumCount(20);
        repository.save(pool1);

        pool2 = new Pool();
        pool2.setPoolId("2");
        pool2.setDisplayName("Programming languages");
        pool2.setEnabled(false);
        pool2.setMaximumCount(10);
        repository.save(pool2);

        pool3 = new Pool();
        pool3.setPoolId("3");
        pool3.setDisplayName("Programming");
        pool3.setEnabled(true);
        pool3.setMaximumCount(16);
        repository.save(pool3);
    }

    @Test
    public void filterByNameTest() {
        PoolSpecification spec = new PoolSpecification(new SearchCriteria("displayName", ":", "Distributed"));

        List<Pool> found = repository.findAll(spec);
        assertThat(found, hasSize(1));
        assertEquals(found.get(0).getPoolId(), pool1.getPoolId());
    }

    @Test
    public void filterBySizeTest() {
        PoolSpecification spec = new PoolSpecification(new SearchCriteria("maximumCount", ">", 15));

        List<Pool> found = repository.findAll(spec);
        assertThat(found, hasSize(2));
        assertEquals(found.get(0).getMaximumCount(), pool1.getMaximumCount());
    }

    @Test
    public void filterByEnabledTest() {
        PoolSpecification spec = new PoolSpecification(new SearchCriteria("enabled", ":", "true"));

        List<Pool> found = repository.findAll(spec);
        assertThat(found, hasSize(2));
        assertEquals(found.get(0).getMaximumCount(), pool1.getMaximumCount());
    }

    @Test
    public void multiplePredicatesTest() {
        PoolSpecificationBuilder builder = new PoolSpecificationBuilder();

        builder.with("maximumCount", ">", 15).with("displayName", ":", "programmin");
        Specification<Pool> spec = builder.build();

        List<Pool> found = repository.findAll(spec);
        assertThat(found, hasSize(1));
        assertEquals(found.get(0).getMaximumCount(), pool3.getMaximumCount());
    }


}
