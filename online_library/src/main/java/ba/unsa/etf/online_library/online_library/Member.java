package ba.unsa.etf.online_library.online_library;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="members")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member{

    @Id
    @SequenceGenerator(
            name = "member_sequence",
            sequenceName = "member_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "child_sequence_new"
    )
    private Long id;
    private String Name;
    private String Surname;
    private Boolean kidMale;   
    private String qualities;
    private String preferences;
    private String special;

}