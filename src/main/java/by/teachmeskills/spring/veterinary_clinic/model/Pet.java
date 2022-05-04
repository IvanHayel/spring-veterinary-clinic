package by.teachmeskills.spring.veterinary_clinic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {
    @Column(name = "name")
    private String name;
    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "type_id")
    private PetType type;
    @ManyToOne(
            cascade = CascadeType.MERGE
    )
    @JoinColumn(name = "owner_id")
    private Owner owner;
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "pet"
    )
    private Set<Visit> visits = new HashSet<>();
}