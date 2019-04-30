package br.com.londrisoft.uniqueweb.endpoint;

import br.com.londrisoft.uniqueweb.model.dto.AcessoDTO;
import br.com.londrisoft.uniqueweb.model.dto.response.ApiResponse;
import br.com.londrisoft.uniqueweb.model.entity.common.Arquivo;
import br.com.londrisoft.uniqueweb.model.entity.common.Documento;
import br.com.londrisoft.uniqueweb.repository.ArquivoRepository;
import br.com.londrisoft.uniqueweb.repository.DocumentoRepository;
import br.com.londrisoft.uniqueweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoEndpoint {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private ArquivoRepository arquivoRepository;

    @GetMapping
    public ResponseEntity<?> listar() {
        AcessoDTO acesso = usuarioService.acesso();
        // Alerta que não foi selecionada nenhuma empresa antes de realizar o envio do documento
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false,"Não há empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        List<Documento> documentos = documentoRepository.findByEmpresaId(acesso.getEmpresa().getId());
        return ResponseEntity.ok()
                .body(new ApiResponse(documentos));
    }

    @PostMapping
    public ResponseEntity<?> enviar(@RequestBody Documento request) {
        AcessoDTO acesso = usuarioService.acesso();
        // Alerta que não foi selecionada nenhuma empresa antes de realizar o envio do documento
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false,"Não há empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        // Limita o tipo de arquivo enviado
        if (!request.getArquivoNome().contains(".pdf")) {
            return new ResponseEntity<>(new ApiResponse(false,"Somente arquivos PDF podem ser enviados."), HttpStatus.BAD_REQUEST);
        }

        Arquivo arquivo = new Arquivo();
        arquivo.setEmpresaId(acesso.getEmpresa().getId());
        arquivo.setConteudo(Base64.getDecoder().decode(request.getConteudo()));
        arquivo.setNome(request.getArquivoNome());

        arquivo = arquivoRepository.save(arquivo);
        request.setArquivoId(arquivo.getId());
        request.setEmpresaId(acesso.getEmpresa().getId());

        Documento documento = documentoRepository.save(request);
        documento.setConteudo(null);
        return new ResponseEntity<>(new ApiResponse("Arquivo enviado com sucesso.", documento), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable Long id) {
        Arquivo arquivo = arquivoRepository.findById(id).orElse(null);
        byte[] data = arquivo != null ? arquivo.getConteudo() : null;

        String[] split = arquivo.getNome().split("\\.");
        String extensao = split[split.length -1].toUpperCase()  ;
        String contentType = "";

        switch (extensao) {
            case "JPG":
                contentType = "image/jpeg";
                break;
            case "PDF":
                contentType = "application/pdf";
        }

        return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .body(data);
    }

}
