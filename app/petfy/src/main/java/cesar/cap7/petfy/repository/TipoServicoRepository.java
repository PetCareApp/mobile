package cesar.cap7.petfy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cesar.cap7.petfy.model.TipoServico;

@Repository
public interface TipoServicoRepository extends JpaRepository<TipoServico, Integer> {

}
