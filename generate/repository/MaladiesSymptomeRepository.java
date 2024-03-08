package rj.toky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rj.toky.model.MaladiesSymptome;

@Repository
public interface MaladiesSymptomeRepository extends JpaRepository<MaladiesSymptome, Long> {
}