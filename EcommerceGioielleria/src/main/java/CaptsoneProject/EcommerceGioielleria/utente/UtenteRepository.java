package CaptsoneProject.EcommerceGioielleria.utente;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, UUID> {
	Optional<Utente> findByEmail(String email);

	Page<Utente> findByNome(String nome, Pageable page);

	Page<Utente> findByCognome(String cognome, Pageable page);

	@Query("SELECT u FROM Utente u WHERE (u.nome ILIKE %:nome% AND u.cognome ILIKE %:cognome%) OR (u.cognome ILIKE %:nome% AND u.nome ILIKE %:cognome%)")
	Page<Utente> cercaUtenti(@Param("nome") String nome, @Param("cognome") String cognome, Pageable page);
}
