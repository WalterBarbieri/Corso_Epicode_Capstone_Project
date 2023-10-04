package CaptsoneProject.EcommerceGioielleria.ordine;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, UUID> {
	public Page<Ordine> findByUtente_Id(UUID id, Pageable page);
}
