package et.com.gebeya.safaricom.coreservice.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "proposals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @ManyToOne
    @JoinColumn(name = "gig_worker_id")
    private GigWorker gigWorker;

    @Column(name = "rate_per_form")
    private Double ratePerForm;

    @Column(name = "proposal_text", columnDefinition = "TEXT")
    private String proposalText;
}

