package pl.kurs.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.entity.Client;
import pl.kurs.entity.ClientData;
import pl.kurs.repository.ClientRepository;

@Component
@RequiredArgsConstructor
public class ClientRunner {
    private final ClientRepository clientRepository;

    @Transactional
    public void run() {
        ClientData clientData1 = new ClientData("Mokra 88", "Warszawa", "05-115", "500400300", "95091504789");
        Client client1 = new Client("Adam", "Przekładam", clientData1);
        clientRepository.save(client1);

        ClientData clientData2 = new ClientData("Lipowa 12", "Kraków", "30-001", "600500400", "92010112345");
        Client client2 = new Client("Ewa", "Nowak", clientData2);
        clientRepository.save(client2);

        ClientData clientData3 = new ClientData("Słoneczna 45", "Gdańsk", "80-210", "511222333", "88050567890");
        Client client3 = new Client("Jan", "Kowalski", clientData3);
        clientRepository.save(client3);

        ClientData clientData4 = new ClientData("Polna 7", "Poznań", "60-789", "733444555", "97031245678");
        Client client4 = new Client("Maria", "Wiśniewska", clientData4);
        clientRepository.save(client4);

        ClientData clientData5 = new ClientData("Kwiatowa 99", "Wrocław", "50-123", "799888777", "90082798765");
        Client client5 = new Client("Piotr", "Zieliński", clientData5);
        clientRepository.save(client5);
    }
}
