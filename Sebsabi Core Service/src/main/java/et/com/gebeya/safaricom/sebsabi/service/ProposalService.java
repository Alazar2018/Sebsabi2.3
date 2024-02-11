package et.com.gebeya.safaricom.sebsabi.service;

import et.com.gebeya.safaricom.sebsabi.dto.ProposalDto;
import et.com.gebeya.safaricom.sebsabi.model.Proposal;
import et.com.gebeya.safaricom.sebsabi.repository.ProposalRepository;
import org.springframework.stereotype.Service;

@Service
public class ProposalService {
    private final ProposalRepository proposalRepository;

    public ProposalService(ProposalRepository proposalRepository) {
        this.proposalRepository = proposalRepository;
    }
    public void submitProposal(ProposalDto proposalDto) {
        Proposal proposal = new Proposal();
        //proposal.setJobId(proposalDto.getJobId());
        proposal.setRatePerForm(proposalDto.getRatePerForm());
        proposal.setProposalText(proposalDto.getProposalText());
        proposalRepository.save(proposal);
    }
}
