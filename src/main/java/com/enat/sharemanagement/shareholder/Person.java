package com.enat.sharemanagement.shareholder;

import com.enat.sharemanagement.utils.Auditable;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class Person extends Auditable {
    @Convert(converter = SexConverter.class)
    @NotNull(message = "Sex cannot be null")
    private Sex sex;
    @Column(nullable = false)
    @NotNull(message = "First Name cannot be null")
    private String firstName;
    @Column(nullable = false)
    @NotNull(message = "Middle Name cannot be null")
    private String middleName;
    @Column(nullable = false)
    @NotNull(message = "Last Name cannot be null")
    private String lastName;
    private String nationality;
    private String firstNameAmharic;
    private String middleNameAmharic;
    private String lastNameAmharic;
    private String nationalityAmharic;

    @Embedded
    private Contact contact;
    private LocalDate dob;
    @Embedded
    private Address address;


}
