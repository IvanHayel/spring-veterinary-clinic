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
@NamedEntityGraph(
        name = "graph.Specialization.specializations",
        attributeNodes = @NamedAttributeNode(value = "specializations", subgraph = "subgraph.PetType.petTypes"),
        subgraphs = @NamedSubgraph(name = "subgraph.PetType.petTypes", attributeNodes = @NamedAttributeNode("petTypes"))
)
@Table(name = "veterinarians")
public class Veterinarian extends Person {
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "veterinarian_specialization",
            joinColumns = @JoinColumn(name = "veterinarian_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<Specialization> specializations = new HashSet<>();
    @Column(name = "experience")
    private Integer experience;
}