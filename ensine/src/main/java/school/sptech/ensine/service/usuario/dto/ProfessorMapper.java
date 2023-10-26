package school.sptech.ensine.service.usuario.dto;

import school.sptech.ensine.domain.Professor;
import school.sptech.ensine.domain.Usuario;
import school.sptech.ensine.service.usuario.autenticacao.dto.UsuarioTokenDto;

public class ProfessorMapper{
    public static Professor of(ProfessorCriacaoDto professorCriacaoDto) {
        Professor professor = new Professor();

        professor.setEmail(professorCriacaoDto.getEmail());
        professor.setNome(professorCriacaoDto.getNome());
        professor.setSenha(professorCriacaoDto.getSenha());
        professor.setDataNasc(professorCriacaoDto.getDataNasc());
        professor.setProfessor(professorCriacaoDto.isProfessor());
        professor.setDescricao(professorCriacaoDto.getDescricao());

        return professor;
    }

    public static UsuarioTokenDto of(Usuario usuario, String token) {
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId((long) usuario.getIdUsuario());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }
}
