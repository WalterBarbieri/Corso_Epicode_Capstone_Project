package CaptsoneProject.EcommerceGioielleria.utente;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
	Optional<Utente> findByEmail(String email);

	Page<Utente> findByNome(String nome, Pageable page);

	Page<Utente> findByCognome(String cognome, Pageable page);
}
