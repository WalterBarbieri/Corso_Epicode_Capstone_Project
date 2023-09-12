package CaptsoneProject.EcommerceGioielleria.prodotto.gioiello;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GioielloRepository extends JpaRepository<Gioiello, UUID> {
	Page<Gioiello> findByCategoria(Categoria categoria, Pageable page);

	@Query("SELECT g FROM Gioiello g WHERE g.nomeProdotto ILIKE %:nomeProdotto%")
	Page<Gioiello> cercaGioielli(@Param("nomeProdotto") String nomeProdotto, Pageable page);

	@Query("SELECT g FROM Gioiello g JOIN FETCH g.immagini")
	Page<Gioiello> findGioielliAndPage(Pageable page);
}
