package com.helloword.loteria.services;

import com.helloword.loteria.entity.Bet;
import com.helloword.loteria.entity.User;
import com.helloword.loteria.exceptions.DuplicateBetException;
import com.helloword.loteria.exceptions.InvalidBetException;
import com.helloword.loteria.exceptions.UserAlreadyExistsException;
import com.helloword.loteria.exceptions.UserNotFoundException;
import com.helloword.loteria.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class LotteryServiceImpl implements LotteryService {

    private final UserRepository userRepository;

    public LotteryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUsers(User u) {
        log.debug("Intentando registrar usuario con ID: {} y con Nombre: {}", u.getId(), u.getName());
        if(userRepository.existsById(u.getId())){
            log.warn("Operacion fallida: Usuario con id: {} ya existe", u.getId());
            throw new UserAlreadyExistsException("Usuario con id: " + u.getId() + " ya existe");
        }
        userRepository.save(u);
        log.info("Operacion exitosa: Usuario registrado: {}", u);
    }

    @Override
    public List<User> findAllUsers() {
        log.debug("Obteniendo todos los usuarios registrados");

        List<User> users = userRepository.findAll();
        log.info("Operacion exitosa: {} usuarios encontrados", users.size());
        return users;
    }

    @Override
    public User findUserById(String id) {
        log.debug("Buscando usuario con ID: {}", id);
        return userRepository.findById(id).orElseThrow(() -> {
            log.warn("Operacion fallida: Usuario con id: {} no encontrado", id);
            return new UserNotFoundException("Usuario con id: " + id + " no encontrado");
        });
    }

    @Override
    public void addApuestaToUser(String id, List<Integer> apuestaNumeros) {

        log.debug("Procesando nueva apuesta para usuario con ID: {}. Apuesta: {}", id, apuestaNumeros);
        User user = findUserById(id);

        if (apuestaNumeros.size() != 6) {
            log.warn("Operacion fallida: Apuesta inválida para usuario con id: {}. Detalles: La apuesta debe contener exactamente 6 números", id);
            throw new InvalidBetException("La apuesta debe contener exactamente 6 números");
        }

        if (apuestaNumeros.stream().anyMatch(num -> num < 1 || num > 49)) {
            log.warn("Operacion fallida: Apuesta inválida para usuario con id: {}. Detalles: La apuesta contiene números fuera del rango permitido (1-49)", id);
            throw new InvalidBetException("Los números de la apuesta deben estar entre 1 y 49");
        }

        if (new HashSet<>(apuestaNumeros).size() != apuestaNumeros.size()) {
            log.warn("Operacion fallida: Apuesta inválida para usuario con id: {}. Detalles: La apuesta contiene números duplicados", id);
            throw new InvalidBetException("Los números de la apuesta deben ser únicos");
        }


        // Verificación de duplicados
        boolean ya_existe=user.getApuestas().stream()
                .anyMatch(b->b.getNumbers().equals(apuestaNumeros));
        if (ya_existe) {
            log.warn("Operacion fallida: Apuesta duplicada para usuario con id: {}. Detalles: El usuario ya tiene una apuesta con la misma combinación de números", id);
            throw new DuplicateBetException("El usuario ya tiene una apuesta con la misma combinación de números");
        }

        // Añadimos la apuesta al usuario y guardamos
        Bet nuevaApuesta = new Bet(apuestaNumeros);
        user.getApuestas().add(nuevaApuesta);
        userRepository.save(user);

        log.info("Operacion exitosa: Apuesta agregada para usuario con ID: {}. Apuesta: {}", id, nuevaApuesta);
    }
}