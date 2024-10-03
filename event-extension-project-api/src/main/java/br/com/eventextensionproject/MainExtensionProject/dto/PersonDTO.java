package br.com.eventextensionproject.MainExtensionProject.dto;

import br.com.eventextensionproject.MainExtensionProject.entity.enumerator.TypePerson;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long idPerson;
    private String namePerson;
    private String rgIePerson;
    private String cpfCnpjPerson;
    private TypePerson type;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="GMT-3")
    private LocalDate dateBornFoundation;
    private String phonePerson;
    private String addressPerson;
    private String numberAddressPerson;
    private String neighborhoodPerson;
    private String zipCodePerson;
    private String emailPerson;
    private Boolean acceptTerms;
    private Long idCity;
    private String passwordPerson;
    protected Set<Integer> rolePermission = new HashSet<>();

}
