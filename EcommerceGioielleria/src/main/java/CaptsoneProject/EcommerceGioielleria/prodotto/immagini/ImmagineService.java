package CaptsoneProject.EcommerceGioielleria.prodotto.immagini;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import CaptsoneProject.EcommerceGioielleria.exceptions.IllegalArgumentException;
import CaptsoneProject.EcommerceGioielleria.exceptions.NotFoundException;
import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import CaptsoneProject.EcommerceGioielleria.prodotto.gioiello.GioielloRepository;

@Service
public class ImmagineService {
	@Value("${image.upload.directory}")
	private String immagineDirectory;

	private final ImmagineRepository ir;

	private final GioielloRepository gr;

	@Autowired
	public ImmagineService(ImmagineRepository ir, GioielloRepository gr) {
		super();
		this.ir = ir;
		this.gr = gr;
	}

	public Immagine saveImmagine(UUID prodottoId, MultipartFile immagine) throws IOException {
		if (!immagine.isEmpty()) {
			String nomeImmagine = prodottoId.toString() + "_" + immagine.getName();

			Path immaginePath = Paths.get(immagineDirectory + nomeImmagine);

			Files.write(immaginePath, immagine.getBytes());

			Prodotto prodotto = gr.findById(prodottoId).orElseThrow(() -> new NotFoundException(prodottoId));

			Immagine nuovaImmagine = new Immagine(immagine.getBytes(), nomeImmagine, prodotto);

			return ir.save(nuovaImmagine);
		} else {
			throw new IllegalArgumentException("Il file " + immagine.toString() + "è vuoto");
		}
	}

	public List<byte[]> getImmagini(UUID prodottoId) throws IOException {
		List<byte[]> immagini = new ArrayList<>();
		String prodottoDirectory = prodottoId.toString() + "_";

		try {
			Files.walk(Paths.get(immagineDirectory)).filter(Files::isRegularFile)
					.filter(path -> path.getFileName().toString().startsWith(prodottoDirectory)).forEach(path -> {
						try {
							byte[] immagine = Files.readAllBytes(path);
							immagini.add(immagine);
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
		} catch (IOException e) {
			e.printStackTrace();
		}

		return immagini;

	}

	public Immagine getImmagine(UUID id) {
		return ir.findById(id).orElseThrow(() -> new NotFoundException(id));
	}

	public void deleteImmagine(UUID id) {
		Immagine immagine = this.getImmagine(id);

		Prodotto prodotto = immagine.getProdotto();

		if (prodotto != null) {
			prodotto.getImmagini().remove(immagine);
		}

		String nomeImmagine = immagine.getNomeImmagine();

		Path immaginePath = Paths.get(immagineDirectory + nomeImmagine);

		try {
			Files.deleteIfExists(immaginePath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore durante l'eliminazione del file");
		}

		ir.delete(immagine);
	}
}
