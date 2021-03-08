package br.com.cervejaria.comanda.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.cervejaria.comanda.api.model.Comanda;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Long>{

}
