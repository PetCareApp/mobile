package cesar.cap7.petfy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cesar.cap7.petfy.model.TipoServico;
import cesar.cap7.petfy.repository.TipoServicoRepository;

import static cesar.cap7.petfy.util.Constants.*;

@Controller
@RequestMapping("/admin/tipo-servico")
public class AdminTipoServicoController {
	
	@Autowired
	private TipoServicoRepository tipoServicoRepository;
	
	@RequestMapping("/")
	private String index(Model model) {
		model.addAttribute("tipos", tipoServicoRepository.findAll());
		model.addAttribute("tipo", new TipoServico());
		return PAGE_ADMIN_LISTAR_TIPO_SERVICO;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	private String cadastrar(@ModelAttribute("tipo") TipoServico tipo) {
		tipoServicoRepository.save(tipo);
		return REDIRECT_PAGE_ADMIN_TIPO_SERVICO;
	}

}
