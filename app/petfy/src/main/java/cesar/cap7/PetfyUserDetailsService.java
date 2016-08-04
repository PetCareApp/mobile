package cesar.cap7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cesar.cap7.petfy.model.Usuario;
import cesar.cap7.petfy.repository.UsuarioRepository;
import cesar.cap7.petfy.util.Constants;

@Service
public class PetfyUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(username);

		if (usuario == null) {
			throw new UsernameNotFoundException(Constants.LOGIN_INVALIDO);
		} else {
			return usuario;
		}
	}

}
