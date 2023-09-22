package CaptsoneProject.EcommerceGioielleria.comune;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComuneRepository extends JpaRepository<Comune, Integer> {
	Optional<Comune> findByDenominazione(String denominazione);

	List<Comune> findByDenominazioneIgnoreCaseContaining(String denominazione);

	List<Comune> findByDenominazioneIgnoreCaseContainingAndNomeProvincia(String denominazione, String provincia);

	List<Comune> findByDenominazioneIgnoreCaseContainingAndNomeRegione(String denominazione, String regione);

}
