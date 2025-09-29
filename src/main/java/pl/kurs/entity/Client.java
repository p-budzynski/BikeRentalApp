package pl.kurs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

@Entity
@SQLDelete(sql = "UPDATE clients SET deleted = true WHERE id = ?")
@Table(name = "clients")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Boolean deleted = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_data_id")
    private ClientData clientData;

    public Client(String firstName, String lastName, ClientData clientData) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientData = clientData;
    }
}
