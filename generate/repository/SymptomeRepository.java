package rj.toky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rj.toky.model.Symptome;

@Repository
public interface SymptomeRepository extends JpaRepository<Symptome, Long> {
}