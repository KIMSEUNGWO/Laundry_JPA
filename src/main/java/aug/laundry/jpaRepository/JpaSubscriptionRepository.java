package aug.laundry.jpaRepository;

import aug.laundry.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSubscriptionRepository extends JpaRepository<Subscription, Long> {
}
