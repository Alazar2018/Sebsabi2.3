package et.com.gebeya.safaricom.sebsabi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import et.com.gebeya.safaricom.sebsabi.dto.FormDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private int usageLimit;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "form_id")
    @JsonIgnore
    private List<FormQuestion> questions;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<UserResponse> userResponses;
//    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
//    private List<Proposal> proposals;
@ManyToOne
@JoinColumn(name = "gig_worker_id")
private GigWorker assignedGigWorker;
    public Form(FormDto formDto) {

        this.setDescription(formDto.getDescription());
        this.setTitle(formDto.getTitle());
        this.setUsageLimit(formDto.getUsageLimit());

    }
}
