package cesar.cap7.petfy.controller;
import static cesar.cap7.petfy.util.Constants.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cesar.cap7.petfy.model.Papel;

@Controller
public class PetfyController {
	
	@RequestMapping(value = "/")
	public String index(Authentication authentication){
		Collection<Papel> papeis = (Collection<Papel>) authentication.getAuthorities();
		
		List<String> tipos = new ArrayList<String>();
		for(Papel p : papeis){
			tipos.add(p.getNome());
		}
		
		if(tipos.contains(PAPEL_ADMIN)){
			return REDIRECT_PAGE_ADMIN_PROPRIETARIO;
			
		} else if (tipos.contains(PAPEL_PROPRIETARIO)) {
			return REDIRECT_PAGE_PROP_LISTAR_ESTABELECIMENTOS;
			
		}
		
		return PAGE_LOGIN;
	}

}
