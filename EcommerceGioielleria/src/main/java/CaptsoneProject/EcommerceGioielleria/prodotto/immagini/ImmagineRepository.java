package CaptsoneProject.EcommerceGioielleria.prodotto.immagini;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImmagineRepository extends JpaRepository<Immagine, UUID> {

}
