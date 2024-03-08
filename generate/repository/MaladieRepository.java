package rj.toky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rj.toky.model.Maladie;

@Repository
public interface MaladieRepository extends JpaRepository<Maladie, Long> {
}