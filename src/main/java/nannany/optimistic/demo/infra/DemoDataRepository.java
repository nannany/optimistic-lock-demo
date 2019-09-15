package nannany.optimistic.demo.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemoDataRepository extends JpaRepository<DemoDataEntity, Integer> {


}
