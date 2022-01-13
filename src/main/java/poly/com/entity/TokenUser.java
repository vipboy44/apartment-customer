package poly.com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUser implements Serializable {


	private static final long serialVersionUID = -5564266084709612740L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String token;

    private Date expiryDate;

    @OneToOne(targetEntity = Apartment.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_apartment")
    private Apartment apartment;

    public TokenUser(Apartment apartment) {
        this.apartment = apartment;
        expiryDate = new Date();
        token = UUID.randomUUID().toString();
    }
}
