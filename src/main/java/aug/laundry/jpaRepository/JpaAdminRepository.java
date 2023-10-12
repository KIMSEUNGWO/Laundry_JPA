package aug.laundry.jpaRepository;

import aug.laundry.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAdminRepository extends JpaRepository<Admin, Long> {
}
