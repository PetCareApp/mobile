package cesar.cap7.petfy.controller;

import static cesar.cap7.petfy.util.Constants.PAGE_ADMIN_LISTAR_ESTABELECIMENTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cesar.cap7.petfy.repository.EstabelecimentoRepository;

@Controller
@RequestMapping("/admin/estabelecimento")
public class AdminEstabelecimentoController {
	
	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;
	
	@RequestMapping(value = {"", "/", "/listar"})
	public String index(Model model) {
		model.addAttribute("estabelecimentos", estabelecimentoRepository.findAll());
		return PAGE_ADMIN_LISTAR_ESTABELECIMENTO;
	}
	
	@RequestMapping(value = "/{id-est}")
	public String detalhe(@PathVariable(value = "id-est") Integer idEstabelecimento, Model model) {
		model.addAttribute("estabelecimento", estabelecimentoRepository.findOne(idEstabelecimento));
		model.addAttribute("estabelecimentos", estabelecimentoRepository.findAll());
		return PAGE_ADMIN_LISTAR_ESTABELECIMENTO;
	}

}
