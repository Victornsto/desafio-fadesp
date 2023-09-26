package com.victornsto.desafiofadesp.repository;

import com.victornsto.desafiofadesp.model.Pagamento;
import com.victornsto.desafiofadesp.model.enums.StatusPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    @Query("SELECT p FROM Pagamento p " +
            "WHERE (:codDebito IS NULL OR p.codDebito = :codDebito) " +
            "   AND (:cpfCnpj IS NULL OR p.cpfCnpj LIKE %:cpfCnpj%) " +
            "   AND (:status IS NULL OR p.status = :status)")
    List<Pagamento> findPagamentoByCodDebitoOrCpfCnpjOrStatus(@Param("codDebito") Integer codDebito, @Param("cpfCnpj") String cpfCnpj, @Param("status") StatusPagamento status);
}
