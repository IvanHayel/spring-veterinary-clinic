package by.teachmeskills.spring.veterinary_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {
    @Column(name = "start_date_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDateTime;
    @Column(name = "duration")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime duration;
    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "pet_id")
    private Pet pet;
    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "veterinarian_id")
    private Veterinarian veterinarian;
    @Column(name = "diagnosis")
    private String diagnosis;
    @Column(name = "treatment")
    private String treatment;
    @Column(name = "status")
    private boolean status;
}