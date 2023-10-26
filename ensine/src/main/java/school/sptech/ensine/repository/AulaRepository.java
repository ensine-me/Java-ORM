package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.ensine.domain.Aula;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.enumeration.Privacidade;
import school.sptech.ensine.enumeration.Status;
import school.sptech.ensine.service.usuario.dto.ContagemAula;

import java.util.List;

public interface AulaRepository extends JpaRepository<Aula, Integer> {
    List<Aula> findByProfessorNomeEqualsIgnoreCase(String nome);
    List<Aula> findByStatus(String status);
    List<Aula> findByPrivacidade(Privacidade privacidade);
    List<Aula> findByPrivacidadeAndStatus(Privacidade privacidade, Status status);
    Long countByStatus(String status);
    Long countByProfessorNomeEqualsIgnoreCase(String nome);
    List<Aula> findByAlunosIdUsuario(int id);
    @Query("SELECT new school.sptech.ensine.service.usuario.dto.ContagemAula(a.materia.nome, COUNT(a), MONTH(a.dataHora)) " +
            "FROM Aula a " +
            "WHERE a.professor = :professor " +
            "GROUP BY a.materia.nome")
    List<ContagemAula> contagemAulas(@Param("professor") Professor professor);

//    @Query("SELECT a FROM Aula a WHERE LOWER(a.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))")
//    List<Aula> findByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);
//
//    @Query("SELECT a FROM Aula a JOIN a.materia m WHERE LOWER(m.nome) LIKE LOWER(CONCAT('%', :materiaNome, '%'))")
//    List<Aula> findByMateriaNomeContainingIgnoreCase(@Param("materiaNome") String materiaNome);
//
//    @Query("SELECT a FROM Aula a WHERE LOWER(a.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))")
//    List<Aula> findByTituloContainingIgnoreCase(@Param("titulo") String titulo);

    @Query("SELECT a FROM Aula a WHERE LOWER(TRANSLATE(a.descricao, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC')) LIKE LOWER(CONCAT('%', TRANSLATE(:descricao, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC'), '%'))")
    List<Aula> findByDescricaoContainingIgnoreCaseAndNormalize(@Param("descricao") String descricao);
    @Query("SELECT a FROM Aula a WHERE LOWER(TRANSLATE(a.materia.nome, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC')) LIKE LOWER(CONCAT('%', TRANSLATE(:materia, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC'), '%'))")
    List<Aula> findByMateriaContainingIgnoreCaseAndNormalize(@Param("materia") String materia);
    @Query("SELECT a FROM Aula a WHERE LOWER(TRANSLATE(a.titulo, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC')) LIKE LOWER(CONCAT('%', TRANSLATE(:titulo, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC'), '%'))")
    List<Aula> findByTituloContainingIgnoreCaseAndNormalize(@Param("titulo") String titulo);
    @Query("SELECT a FROM Aula a WHERE :usuario MEMBER OF a.alunos " +
            "AND a.professor = :professor AND a.status = school.sptech.ensine.enumeration.Status.CONCLUIDA")

    List<Aula> findByUsuarioAndProfessorAndStatusConcluida(@Param("usuario") Usuario usuario,
                                                  @Param("professor") Professor professor);

    List<Aula> findByProfessor_Id(int id);

    List<Aula> findByAlunos_Id(int id);



}
