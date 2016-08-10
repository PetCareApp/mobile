package cesar.cap7.petfy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cesar.cap7.petfy.model.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Integer> {

}
