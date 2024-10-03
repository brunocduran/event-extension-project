package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.dto.InstitutionDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Institution;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.InstitutionRepository;
import br.com.eventextensionproject.MainExtensionProject.utils.ValidationCpfCnpj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class InstitutionService {

    @Autowired
    private InstitutionRepository repository;

    @Value("${file.upload-dir}")
    private String FILE_DIRECTORY;

    public List<Institution> getAll() {
        return repository.findByOrderByNameInstitutionAsc();
    }

    public Institution save(InstitutionDTO institutionDTO) throws IOException {
        Institution institution = new Institution(institutionDTO);

        if(institutionDTO.getImageInstitution().isEmpty()) {
            throw new DataIntegrityViolationException("Campo Imagem não pode ser vazio!");
        }

        institution.setCnpjInstitution(ValidationCpfCnpj.limpar(institution.getCnpjInstitution()));
        if(validateInstitution(institution)) {
            institution = repository.saveAndFlush(institution);
            if(!institutionDTO.getImageInstitution().isEmpty()) {
                saveImage(institution.getIdIntitution(), institution.getNameInstitution(), institutionDTO.getImageInstitution());
            }
            return institution;
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public HashMap<String, Object> delete(Long idInstitution) {
        String status = "";

        Optional<Institution> institution =
                Optional.ofNullable(repository.findById(idInstitution).
                        orElseThrow(() -> new ObjectnotFoundException("Instituição não encontrada!")));

        //repository.delete(institution.get());
        if (institution.get().getSituation() == Situation.ATIVO) {
            institution.get().setSituation(Situation.INATIVO);
            status = "inativada";
        } else {
            institution.get().setSituation(Situation.ATIVO);
            status = "ativada";
        }

        repository.saveAndFlush(institution.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Instituição " + institution.get().getNameInstitution() + " " + status + " com sucesso!");
        return result;
    }

    public Institution findById(Long idInstitution) {
        Optional<Institution> obj = repository.findById(idInstitution);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Instituição não encontrada!"));
    }

    public Institution update(InstitutionDTO institutionDTO) throws IOException {
        Institution institution = new Institution(institutionDTO);
        institution.setSituation(Situation.ATIVO);

        institution.setCnpjInstitution(ValidationCpfCnpj.limpar(institution.getCnpjInstitution()));
        if (validateInstitution(institution)) {
            if(institutionDTO.getImageInstitution().isEmpty()) {
                Institution oldInstitution = findById(institutionDTO.getIdIntitution());
                institution.setNameImage(oldInstitution.getNameImage());
                institution.setTypeImage(oldInstitution.getTypeImage());
            }
            if (findById(institution.getIdIntitution()) != null) {
                institution = repository.saveAndFlush(institution);
                if(!institutionDTO.getImageInstitution().isEmpty()) {
                    saveImage(institution.getIdIntitution(), institution.getNameInstitution(), institutionDTO.getImageInstitution());
                }
                return institution;
            } else {
                return null;
            }
        } else {
            throw new ObjectnotFoundException("Nenhum campo pode ser vazio!");
        }
    }

    private boolean validateInstitution(Institution institution) {
        if (!ValidationCpfCnpj.isValidCpfCnpj(institution.getCnpjInstitution())) {
            throw new DataIntegrityViolationException("CNPJ inválido");
        }
        if (repository.existsByCnpjInstitution(institution.getCnpjInstitution())) {
            if(repository.findByCnpjInstitution(institution.getCnpjInstitution()).getIdIntitution() != institution.getIdIntitution()){
                throw new DataIntegrityViolationException("CNPJ já cadastrado");
            }
        }
        if(institution.isValidNameInstitution() && ValidationCpfCnpj.isCNPJ(institution.getCnpjInstitution())) {
            return true;
        }
        return false;
    }

    public void saveImage(Long id, String name, MultipartFile image) throws IOException {
        String type = image.getContentType();
        String[] parts = type.split("/");
        String extension = "";
        if (parts.length > 1) {
            extension = parts[1];
        }

        File convertFile = new File(FILE_DIRECTORY + id + '-' + name + '.' + extension);
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(image.getBytes());
        fout.close();
    }
}
