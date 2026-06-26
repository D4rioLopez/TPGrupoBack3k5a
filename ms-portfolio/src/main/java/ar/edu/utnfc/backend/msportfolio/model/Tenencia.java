package ar.edu.utnfc.backend.msportfolio.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "TENENCIA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String simboloAccion;
    private Double cantidad;

    @ManyToOne
    @JsonBackReference
    private Portfolio portfolio;


}
