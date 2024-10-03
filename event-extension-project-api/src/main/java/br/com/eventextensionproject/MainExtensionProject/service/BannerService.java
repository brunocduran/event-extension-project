package br.com.eventextensionproject.MainExtensionProject.service;

import br.com.eventextensionproject.MainExtensionProject.dto.BannerDTO;
import br.com.eventextensionproject.MainExtensionProject.entity.Banner;
import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.Situation;
import br.com.eventextensionproject.MainExtensionProject.exception.DataIntegrityViolationException;
import br.com.eventextensionproject.MainExtensionProject.exception.ObjectnotFoundException;
import br.com.eventextensionproject.MainExtensionProject.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class BannerService {

    @Autowired
    private BannerRepository repository;
    @Autowired
    private EventService eventService;

    @Value("${file.upload-dir}")
    private String FILE_DIRECTORY;


    public List<Banner> getAll() {
        return repository.findByOrderByNameBannerAsc();
    }

    public List<Banner> findAllActive() {
        return repository.findBySituationAndInitialDateBannerLessThanEqualAndFinalDateBannerGreaterThanEqualOrderByNameBannerAsc(Situation.ATIVO, LocalDate.now(), LocalDate.now());
    }

    public Banner save(BannerDTO bannerDTO) throws IOException {
        Banner banner = new Banner(bannerDTO);

        if(bannerDTO.getImageBanner().isEmpty()) {
            throw new DataIntegrityViolationException("Campo Imagem não pode ser vazio!");
        }

        banner.setEvent(eventService.findById(bannerDTO.getIdEvent()));

        if(validateBanner(banner)) {
            banner = repository.saveAndFlush(banner);
            if(!bannerDTO.getImageBanner().isEmpty()) {
                saveImage(banner.getIdBanner(), banner.getNameBanner(), bannerDTO.getImageBanner());
            }
            return banner;
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    public HashMap<String, Object> delete(Long idBanner) {
        String status = "";

        Optional<Banner> banner =
                Optional.ofNullable(repository.findById(idBanner).
                        orElseThrow(() -> new ObjectnotFoundException("Banner não encontrado!")));

        if (banner.get().getSituation() == Situation.ATIVO) {
            banner.get().setSituation(Situation.INATIVO);
            status = "inativado";
        } else {
            banner.get().setSituation(Situation.ATIVO);
            status = "ativado";
        }

        repository.saveAndFlush(banner.get());
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("result", "Banner " + banner.get().getNameBanner() + " " + status + " com sucesso!");
        return result;
    }

    public Banner findById(Long idBanner) {
        Optional<Banner> obj = repository.findById(idBanner);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Banner não encontrado!"));
    }

    public Banner update(BannerDTO bannerDTO) throws IOException {
        Banner banner = new Banner(bannerDTO);
        banner.setSituation(Situation.ATIVO);
        banner.setEvent(eventService.findById(bannerDTO.getIdEvent()));

        if(validateBanner(banner)) {
            if(bannerDTO.getImageBanner().isEmpty()) {
                Banner oldBanner = findById(bannerDTO.getIdBanner());
                banner.setNameImage(oldBanner.getNameImage());
                banner.setTypeImage(oldBanner.getTypeImage());
            }

            if (findById(banner.getIdBanner()) != null) {
                banner = repository.saveAndFlush(banner);
                if(!bannerDTO.getImageBanner().isEmpty()) {
                    saveImage(banner.getIdBanner(), banner.getNameBanner(), bannerDTO.getImageBanner());
                }
            }
            return banner;
        } else {
            throw new DataIntegrityViolationException("Nenhum campo pode ser vazio!");
        }
    }

    private boolean validateBanner(Banner banner) {
        if(banner.isValidName()) {
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
