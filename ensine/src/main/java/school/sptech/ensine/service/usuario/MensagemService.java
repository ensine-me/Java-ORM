package school.sptech.ensine.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.ensine.api.security.jwt.GerenciadorTokenJwt;
import school.sptech.ensine.domain.Mensagem;
import school.sptech.ensine.repository.AulaRepository;
import school.sptech.ensine.repository.MateriaRepository;
import school.sptech.ensine.repository.MensagemRepository;
import school.sptech.ensine.service.usuario.dto.MensagemPresentationDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class MensagemService {
    @Autowired
    private MensagemRepository mensagemRepository;


    public Mensagem enviarMensagem(Mensagem mensagem){
        mensagem = mensagemRepository.save(mensagem);
        return mensagem;
    }

    public List<MensagemPresentationDto> listarMensagens(Integer aulaId){
        List<Mensagem> mensagens = mensagemRepository.findByAula_Id(aulaId);
        List<MensagemPresentationDto> mensagensDto = new ArrayList<>();
        for (Mensagem m:
             mensagens) {
            mensagensDto.add(new MensagemPresentationDto(m));
        }
        return mensagensDto;
    }
}
