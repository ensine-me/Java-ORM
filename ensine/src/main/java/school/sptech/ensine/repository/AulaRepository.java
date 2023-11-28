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
import school.sptech.ensine.service.usuario.dto.ContagemAulaStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AulaRepository extends JpaRepository<Aula, Integer> {
    List<Aula> findByProfessorNomeEqualsIgnoreCase(String nome);
    List<Aula> findByStatus(Status status);
    List<Aula> findByPrivacidade(Privacidade privacidade);
    List<Aula> findByPrivacidadeAndStatus(Privacidade privacidade, Status status);
    Long countByStatus(Status status);
    Long countByProfessorNomeEqualsIgnoreCase(String nome);
    Long countByProfessorIdUsuario(int id);
    @Query("SELECT a FROM Aula a WHERE a.professor.idUsuario = :professorIdUsuario AND a.status = 0")
    List<Aula> findByProfessorIdUsuarioSolicitado(int professorIdUsuario);

    @Query("SELECT COUNT(a) FROM Aula a WHERE a.professor.idUsuario = :professorIdUsuario AND a.status = 4")
    Long countConcluidasByProfessorIdUsuario(int professorIdUsuario);
    // de agr3
    @Query("SELECT COUNT(a) FROM Aula a WHERE a.status = 4")
    Long countTotalAulasConcluidas();
    @Query("SELECT COUNT(a) FROM Aula a WHERE a.professor.idUsuario = :professorIdUsuario AND a.status = 2")
    Long countAgendadasByProfessorIdUsuario(int professorIdUsuario);
    List<Aula> findByAlunosIdUsuario(int id);
    @Query("SELECT new school.sptech.ensine.service.usuario.dto.ContagemAula(a.materia.nome, COUNT(a), MONTH(a.dataHora)) " +
            "FROM Aula a " +
            "WHERE a.professor = :professor " +
            "GROUP BY a.materia.nome")
    List<ContagemAula> contagemAulas(@Param("professor") Professor professor);

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

    List<Aula> findByProfessor_IdUsuario(int id);

    List<Aula> findByAlunos_IdUsuario(int id);

    @Query("SELECT new school.sptech.ensine.service.usuario.dto.ContagemAula(a.materia.nome, COUNT(a), FUNCTION('MONTH', a.dataHora)) " +
            "FROM Aula a " +
            "WHERE a.materia.nome IN ('Matematica', 'Lingua Portuguesa', 'Geografia', 'Historia', 'Biologia') " +
            "AND a.dataHora BETWEEN :start AND :end " +
            "GROUP BY a.materia.nome, EXTRACT(YEAR FROM a.dataHora), EXTRACT(MONTH FROM a.dataHora)")
    List<ContagemAula> countAulasByMateriaAndMonth(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("SELECT MONTH(a.dataHora), SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.dataHora >= :start AND a.dataHora <= :end " +
            "GROUP BY MONTH(a.dataHora)")
    List<Object[]> totalValorArrecadadoUltimosDoisMeses(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

//    @Query("SELECT AVG(FUNCTION('TIMESTAMPDIFF', SECOND, a.dataHora, a.dataHoraFim)) " +
//            "FROM Aula a " +
//            "WHERE a.status = 4")
//    Double calcularTempoMedioAulas();

    @Query("SELECT AVG(FUNCTION('TIMESTAMPDIFF', SECOND, a.dataHora, a.dataHoraFim)) " +
            "FROM Aula a " +
            "WHERE a.status = 4 " +
            "AND EXTRACT(MONTH FROM a.dataHora) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(YEAR FROM a.dataHora) = EXTRACT(YEAR FROM CURRENT_DATE)")
    Double calcularTempoMedioAulas();

    @Query("SELECT COUNT(a) " +
            "FROM Aula a " +
            "WHERE EXTRACT(YEAR FROM a.dataHora) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM a.dataHora) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(DAY FROM a.dataHora) = EXTRACT(DAY FROM CURRENT_DATE)")
    Long countAulasMarcadasParaHoje();

    @Query("SELECT p.idUsuario, p.nome, SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Matematica' " +
            "GROUP BY p.idUsuario, p.nome")
    List<Object[]> totalPrecoPorProfessorDeMatematica();

    @Query("SELECT SUM(p.precoHoraAula), a.materia.nome " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome IN ('Matematica', 'Lingua Portuguesa', 'Geografia', 'Historia', 'Biologia') " +
            "AND EXTRACT(MONTH FROM a.dataHora) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(YEAR FROM a.dataHora) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "GROUP BY a.materia.nome " +
            "ORDER BY SUM(p.precoHoraAula) DESC")
    List<Object[]> totalPrecoParaMatematica();


    @Query("SELECT SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Fisica'")
    Long totalPrecoParaFisica();

    @Query("SELECT SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Artes'")
    Long totalPrecoParaArtes();

    @Query("SELECT SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Filosofia'")
    Long totalPrecoParaFilosofia();

    @Query("SELECT SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Sociologia'")
    Long totalPrecoParaSociologia();

    @Query("SELECT SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Lingua Inglesa'")
    Long totalPrecoParaLinguaInglesa();

    @Query("SELECT SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Quimica'")
    Long totalPrecoParaQuimica();

    @Query("SELECT SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Biologia'")
    Long totalPrecoParaBiologia();

    @Query("SELECT SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Geografia'")
    Long totalPrecoParaGeografia();

    @Query("SELECT SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Historia'")
    Long totalPrecoParaHistoria();

    @Query("SELECT SUM(p.precoHoraAula) " +
            "FROM Aula a " +
            "JOIN Professor p ON a.professor = p " +
            "WHERE a.materia.nome = 'Lingua Portuguesa'")
    Long totalPrecoParaLinguaPortuguesa();

    @Query("SELECT NEW school.sptech.ensine.service.usuario.dto.ContagemAulaStatus(a.status, COUNT(a)) " +
            "FROM Aula a " +
            "WHERE EXTRACT(MONTH FROM a.dataHora) = EXTRACT(MONTH FROM CURRENT_DATE) " +
            "AND EXTRACT(YEAR FROM a.dataHora) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "GROUP BY a.status")
    List<ContagemAulaStatus> countAulasByStatus();

//    @Query ("SELECT new school.sptech.ensine.service.usuario.dto.ContagemAulaStatus(a.status, COUNT(a)) from Aula a Group by a.status")
//    List<ContagemAulaStatus> countAulasByStatus(
//    );


    List<Aula> findByAlunos_IdUsuarioAndStatus(int idUsuario, Status status);


}
