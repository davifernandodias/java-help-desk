    package com.systemupdate.beta.repository;

    import com.systemupdate.beta.models.TipoChamado;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    @Repository
    public interface TipoChamadoRepository extends JpaRepository<TipoChamado, Long> {
    }
