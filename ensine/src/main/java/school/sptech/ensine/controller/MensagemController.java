package school.sptech.ensine.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ensine.domain.Mensagem;
import school.sptech.ensine.service.usuario.MensagemService;
import school.sptech.ensine.service.usuario.dto.MensagemPresentationDto;

import java.util.List;

@Tag(name = "Aula", description = "Requisições relacionada às aulas")
//@SecurityRequirement(name = "Bearer")
@RestController
@RequestMapping("/chat")
public class MensagemController {
    @Autowired
    MensagemService mensagemService;

    @GetMapping("/{id}")
    ResponseEntity<List<MensagemPresentationDto>> exibirChat(@PathVariable int id){
        List<MensagemPresentationDto> mensagens = mensagemService.listarMensagens(id);
        return ResponseEntity.status(200).body(mensagens);
    }
    @PostMapping
    ResponseEntity<Mensagem> enviarMensagem(@RequestBody Mensagem mensagem){
        mensagem = mensagemService.enviarMensagem(mensagem);
        return ResponseEntity.status(201).body(mensagem);
    }


}
