package school.sptech.ensine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import school.sptech.ensine.domain.Materia;
import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.domain.Usuario;

import java.util.List;
import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByNomeIgnoreCase(String nome);
    Optional<Usuario> findByEmailIgnoreCase(String email);
    Optional<Professor> findProfessorByIdUsuario(Integer id);
    Optional<Usuario> findByNomeIgnoreCase(String nome);

//    @Query("SELECT p FROM Usuario p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
//    List<Professor> findByNomeContainingIgnoreCase(@Param("nome") String nome);
//
//    @Query("SELECT p FROM Professor p WHERE LOWER(p.descricao) LIKE LOWER(CONCAT('%', :descricao, '%'))")
//    List<Professor> findByDescricaoContainingIgnoreCase(@Param("descricao") String descricao);
//
//    @Query("SELECT p FROM Professor p JOIN p.materias m WHERE LOWER(m.nome) LIKE LOWER(CONCAT('%', :materiaNome, '%'))")
//    List<Professor> findByMateriasContainingIgnoreCase(@Param("materiaNome") String materiaNome);

    @Query("SELECT p FROM Usuario p WHERE LOWER(TRANSLATE(p.nome, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC')) LIKE LOWER(CONCAT('%', TRANSLATE(:nome, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC'), '%'))")
    List<Professor> findByNomeContainingIgnoreCaseAndNormalize(@Param("nome") String nome);
    @Query("SELECT p FROM Professor p WHERE LOWER(TRANSLATE(p.descricao, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC')) LIKE LOWER(CONCAT('%', TRANSLATE(:descricao, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC'), '%'))")
    List<Professor> findByDescricaoContainingIgnoreCaseAndNormalize(@Param("descricao") String descricao);
    @Query("SELECT p FROM Professor p JOIN p.materias m WHERE LOWER(TRANSLATE(m.nome, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC')) LIKE LOWER(CONCAT('%', TRANSLATE(:materiaNome, 'áàãâäéèẽêëíìĩîïóòõôöúùũûüçÁÀÃÂÄÉÈẼÊËÍÌĨÎÏÓÒÕÔÖÚÙŨÛÜÇ', 'aaaaaeeeeiiiiiooooouuuuucAAAAAEEEEIIIIIOOOOOUUUUUC'), '%'))")
    List<Professor> findByMateriasContainingIgnoreCaseAndNormalize(@Param("materiaNome") String materiaNome);
    @Query("SELECT DISTINCT p FROM Professor p JOIN p.materias m WHERE (SELECT AVG(a.nota) FROM Avaliacao a WHERE a.professor = p) >= 4.0 AND m IN :usuarioMaterias")
    List<Professor> findAProfessoresRecomendados(@Param("usuarioMaterias") List<Materia> usuarioMaterias);

    List<Professor> findByIsProfessorAndNomeStartsWithIgnoreCase(boolean isProfessor, String nome);

    @Query("SELECT COUNT(p) FROM Professor p")
    Long countProfessores();

}
