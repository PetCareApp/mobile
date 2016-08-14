package cesar.cap7.petfy.controller;

import static cesar.cap7.petfy.util.Constants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cesar.cap7.petfy.model.Servico;
import cesar.cap7.petfy.repository.EstabelecimentoRepository;
import cesar.cap7.petfy.repository.ServicoRepository;
import cesar.cap7.petfy.repository.TipoServicoRepository;

@Controller
@RequestMapping("/proprietario")
public class ProprietarioController {
	
	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	
	@Autowired
	private TipoServicoRepository tipoServicoRepository;
	
	@RequestMapping(value = {"", "/", "/listar"})
	public String index(Model model, Authentication auth) {
		model.addAttribute("estabelecimentos", estabelecimentoRepository.findByProprietarioEmail(auth.getName()));
		return PAGE_PROP_LISTAR_SERVICO;
	}
	
	@RequestMapping(value = "/estabelecimento/{id-est}")
	public String detalheEstabelecimento(@PathVariable("id-est") Integer idEstabelecimento, Model model, Authentication auth) {
		model.addAttribute("tipos", tipoServicoRepository.findAll());
		model.addAttribute("estabelecimentos", estabelecimentoRepository.findByProprietarioEmail(auth.getName()));
		model.addAttribute("servico", new Servico());
		model.addAttribute("estabelecimento", estabelecimentoRepository.findOne(idEstabelecimento));
		return PAGE_PROP_LISTAR_SERVICO;
	}
	
	@RequestMapping(value = "/servico/cadastrar", method = RequestMethod.POST)
	public String cadastrarEstabelecimento(@RequestParam("id-est") Integer idEstabelecimento, @ModelAttribute("servico") Servico servico) {
		servico.setEstabelecimento(estabelecimentoRepository.findOne(idEstabelecimento));
		servicoRepository.save(servico);
		return REDIRECT_PAGE_PROP_DETALHE_ESTABELECIMENTO + idEstabelecimento;
	}
}
