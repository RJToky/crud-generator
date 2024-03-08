package rj.toky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rj.toky.model.EffetsMedicament;

@Repository
public interface EffetsMedicamentRepository extends JpaRepository<EffetsMedicament, Long> {
}