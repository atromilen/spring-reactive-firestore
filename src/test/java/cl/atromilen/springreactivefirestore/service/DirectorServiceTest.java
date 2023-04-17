package cl.atromilen.springreactivefirestore.service;

import cl.atromilen.springreactivefirestore.repository.DirectorRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class DirectorServiceTest {

    @Mock
    private DirectorRepository repository;

    @InjectMocks
    private DirectorService directorService;

    @Test
    void whenDirectorIsFound_onFindAll_returnFluxDirector() {

    }
}