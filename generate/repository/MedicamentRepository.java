package rj.toky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rj.toky.model.Medicament;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
}