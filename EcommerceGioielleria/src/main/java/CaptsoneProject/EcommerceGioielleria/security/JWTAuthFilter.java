package CaptsoneProject.EcommerceGioielleria.security;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import CaptsoneProject.EcommerceGioielleria.exceptions.UnauthorizedException;
import CaptsoneProject.EcommerceGioielleria.utente.Utente;
import CaptsoneProject.EcommerceGioielleria.utente.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	@Autowired
	private JWTTools jt;

	@Autowired
	private UtenteService us;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		System.out.println("***FILTRO***");
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			throw new UnauthorizedException("Manca il token nell'authorization header");
		}

		String token = authHeader.substring(7);
		System.out.println("Token: " + token);

		jt.verifyToken(token);

		String id = jt.extractSubject(token);
		Utente currentUtente = us.findById(UUID.fromString(id));

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(currentUtente, null,
				currentUtente.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authToken);
		filterChain.doFilter(request, response);

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		System.out.println(request.getServletPath());
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}

}
