package rj.toky.model;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Data
@Entity
@Table(name = "maladies_symptomes")
public class MaladiesSymptome {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_maladie")
	private Maladie maladie;

	@ManyToOne
	@JoinColumn(name = "id_symptome")
	private Symptome symptome;

	@Column(name = "age_debut")
	private int ageDebut;

	@Column(name = "age_fin")
	private int ageFin;

	@Column(name = "effet_min")
	private int effetMin;

	@Column(name = "effet_max")
	private int effetMax;

}