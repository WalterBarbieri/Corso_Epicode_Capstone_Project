package CaptsoneProject.EcommerceGioielleria.comune;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, Integer> {
	Optional<Comune> findByDenominazione(String denominazione);

	Page<Comune> findByDenominazioneIgnoreCaseContaining(String denominazione, Pageable page);

}
