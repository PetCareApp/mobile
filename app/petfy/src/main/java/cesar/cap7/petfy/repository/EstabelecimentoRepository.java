package cesar.cap7.petfy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cesar.cap7.petfy.model.Estabelecimento;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Integer> {
	
	List<Estabelecimento> findByProprietarioId(Integer idProprietario);
	
	List<Estabelecimento> findByProprietarioEmail(String email);

}
