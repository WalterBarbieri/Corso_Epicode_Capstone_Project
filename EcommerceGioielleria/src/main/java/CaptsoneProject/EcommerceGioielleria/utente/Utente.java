package CaptsoneProject.EcommerceGioielleria.utente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import CaptsoneProject.EcommerceGioielleria.indirizzo.Indirizzo;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "utenti")
@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({ "password", "accountNonExpired", "authorities", "credentialsNonExpired", "accountNonLocked" })
public class Utente implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String nome;
	private String cognome;
	private LocalDate dataNascita;
	private String email;
	private String password;
	private String ragioneSociale;
	private String pIva;
	@Enumerated(EnumType.STRING)
	private Ruolo ruolo;
	@OneToOne
	@Cascade(CascadeType.ALL)
	private Indirizzo residenza;
	@OneToOne
	@Cascade(CascadeType.ALL)
	private Indirizzo domicilio;
	@OneToMany(mappedBy = "utente")
	@Cascade(CascadeType.ALL)
	private List<Indirizzo> indirizzi;

	public Utente(String nome, String cognome, LocalDate dataNascita, String email, String password, Ruolo ruolo) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.dataNascita = dataNascita;
		this.email = email;
		this.password = password;
		this.ruolo = ruolo;
		this.indirizzi = new ArrayList<>();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(ruolo.name()));
	}

	@Override
	public String getUsername() {
		return this.nome;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
