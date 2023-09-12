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
import CaptsoneProject.EcommerceGioielleria.prodotto.Prodotto;
import CaptsoneProject.EcommerceGioielleria.prodotto.gioiello.GioielloService;

@Service
public class ImmagineService {
	@Value("${image.upload.directory}")
	private String immagineDirectory;

	private final ImmagineRepository ir;

	private final GioielloService gs;

	@Autowired
	public ImmagineService(ImmagineRepository ir, GioielloService gs) {
		super();
		this.ir = ir;
		this.gs = gs;
	}

	public Immagine saveImmagine(UUID id, MultipartFile immagine) throws IOException {
		if (!immagine.isEmpty()) {
			String immagineNome = id.toString() + "_" + immagine.getOriginalFilename();

			Path immaginePath = Paths.get(immagineDirectory + immagineNome);

			Files.write(immaginePath, immagine.getBytes());

			Prodotto prodotto = gs.findById(id);

			Immagine nuovaImmagine = new Immagine(immagine.getBytes(), immagineNome, prodotto);

			return ir.save(nuovaImmagine);
		} else {
			throw new IllegalArgumentException("Il file " + immagine.toString() + "è vuoto");
		}
	}

	public List<byte[]> getImmagini(UUID id) throws IOException {
		List<byte[]> immagini = new ArrayList<>();
		String prodottoDirectory = id.toString() + "_";

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
}
