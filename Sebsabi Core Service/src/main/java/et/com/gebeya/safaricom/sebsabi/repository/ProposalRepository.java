package et.com.gebeya.safaricom.sebsabi.repository;

import et.com.gebeya.safaricom.sebsabi.model.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposalRepository extends JpaRepository <Proposal, Long>{
}
