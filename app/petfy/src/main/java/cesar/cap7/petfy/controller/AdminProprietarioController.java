package cesar.cap7.petfy.controller;

import static cesar.cap7.petfy.util.Constants.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cesar.cap7.petfy.model.Estabelecimento;
import cesar.cap7.petfy.model.Proprietario;
import cesar.cap7.petfy.model.Usuario;
import cesar.cap7.petfy.repository.EstabelecimentoRepository;
import cesar.cap7.petfy.repository.ProprietarioRepository;

@Controller
@RequestMapping("/admin/proprietario")
public class AdminProprietarioController {
	
	@Autowired
	private ProprietarioRepository proprietarioRepository;
	
	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;
	
	@RequestMapping(value = {"", "/", "/listar"})
	public String index(Model model) {
		model.addAttribute("proprietario", new Proprietario());
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("proprietarios", proprietarioRepository.findAll());
		return PAGE_ADMIN_LISTAR_PROPRIETARIO;
	}
	
	@RequestMapping(value = "/cadastrar", method = RequestMethod.POST)
	public String cadastrar(@ModelAttribute("proprietario") Proprietario proprietario) {
		proprietario.getUsuario().setLogin(proprietario.getEmail());
		proprietario.getUsuario().setHabilitado(true);
		proprietarioRepository.save(proprietario);
		return REDIRECT_PAGE_ADMIN_PROPRIETARIO;
	}
	
	@RequestMapping(value = "/detalhe/{id-prop}")
	public String detalhe(@PathVariable("id-prop") Integer idProprietario, Model model) {
		model.addAttribute("proprietario", proprietarioRepository.findOne(idProprietario));
		model.addAttribute("estabelecimentos", estabelecimentoRepository.findByProprietarioId(idProprietario));
		model.addAttribute("estabelecimento", new Estabelecimento());
		return PAGE_ADMIN_DETALHE_PROPRIETARIO;
	}
	
	@RequestMapping(value = "/estabelecimento/cadastrar")
	public String cadastrarEstabelecimento(@ModelAttribute("estabelecimento") Estabelecimento estabelecimento,
			@RequestParam("id-prop") Integer idProprietario, Model model) {
		Proprietario proprietario = proprietarioRepository.findOne(idProprietario);
		estabelecimento.setProprietario(proprietario);
		estabelecimento.setHabilitado(true);
		estabelecimentoRepository.save(estabelecimento);
		return REDIRECT_PAGE_ADMIN_DETALHE_PROPRIETARIO + proprietario.getId();
	}

}
