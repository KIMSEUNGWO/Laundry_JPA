package aug.laundry.jpaRepository;

import aug.laundry.domain.Paymentinfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPaymentInfoRepository extends JpaRepository<Paymentinfo, Long> {
}
