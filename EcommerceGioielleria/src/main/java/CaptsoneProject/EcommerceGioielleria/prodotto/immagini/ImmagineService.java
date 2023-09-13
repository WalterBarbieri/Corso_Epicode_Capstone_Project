package CaptsoneProject.EcommerceGioielleria.prodotto.immagini;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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

			String timestamp = String.valueOf(System.currentTimeMillis());
			String originalFileName = immagine.getOriginalFilename();
			String estensione = "";
			if (originalFileName != null) {
				int lastDotIndex = originalFileName.lastIndexOf(".");
				if (lastDotIndex != -1) {
					estensione = originalFileName.substring(lastDotIndex);
				}
			}

			String nomeImmagine = prodottoId.toString() + "_" + timestamp + estensione;

			Path immaginePath = Paths.get(immagineDirectory + nomeImmagine);

			byte[] compressImage = compressor(immagine.getBytes());

			Files.write(immaginePath, compressImage);

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
							byte[] decompressImage = decompressor(immagine);
							immagini.add(decompressImage);
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

	public byte[] compressor(byte[] dati) {
		Deflater deflater = new Deflater();
		deflater.setLevel(Deflater.BEST_SPEED);
		deflater.setInput(dati);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(dati.length);
		byte[] tmp = new byte[4 * 1024];
		while (!deflater.finished()) {
			int size = deflater.deflate(tmp);
			outputStream.write(tmp, 0, size);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore durante la compressione dei dati");
		}

		return outputStream.toByteArray();
	}

	public byte[] decompressor(byte[] dati) {
		Inflater inflater = new Inflater();
		inflater.setInput(dati);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(dati.length);
		byte[] tmp = new byte[4 * 1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(tmp);
				outputStream.write(tmp, 0, count);
			}
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Errore durante la decompressione dei dati");
		}

		return outputStream.toByteArray();
	}

}
