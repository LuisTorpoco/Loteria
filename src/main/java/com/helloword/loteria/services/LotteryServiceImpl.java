package com.helloword.loteria.services;

import com.helloword.loteria.entity.Bet;
import com.helloword.loteria.entity.User;
import com.helloword.loteria.exceptions.DuplicateBetException;
import com.helloword.loteria.exceptions.InvalidBetException;
import com.helloword.loteria.exceptions.UserAlreadyExistsException;
import com.helloword.loteria.exceptions.UserNotFoundException;
import com.helloword.loteria.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class LotteryServiceImpl implements LotteryService {

    private final UserRepository userRepository;

    public LotteryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void registerUsers(User u) {
        if(userRepository.existsById(u.getId())){
            throw new UserAlreadyExistsException("Usuario con id: " + u.getId() + " ya existe");
        }
        userRepository.save(u);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("Usuario con id: " + id + " no encontrado"));
    }

    @Override
    public void addApuestaToUser(String id, List<Integer> apuestaNumeros) {
        User user = findUserById(id);

        if (apuestaNumeros.size() != 6) {
            throw new InvalidBetException("La apuesta debe contener exactamente 6 números");
        }

        if (apuestaNumeros.stream().anyMatch(num -> num < 1 || num > 49)) {
            throw new InvalidBetException("Los números de la apuesta deben estar entre 1 y 49");
        }

        if (new HashSet<>(apuestaNumeros).size() != apuestaNumeros.size()) {
            throw new InvalidBetException("Los números de la apuesta deben ser únicos");
        }

        // 2. Creamos el objeto Bet a partir de la lista de números
        Bet nuevaApuesta = new Bet(apuestaNumeros);

        // 3. Verificación de duplicados (Requisito 3.e)
        if (user.getApuestas().contains(nuevaApuesta)) {
            throw new DuplicateBetException("La apuesta ya existe para el usuario con id: " + id);
        }

        // 4. Añadimos la apuesta al usuario y guardamos
        user.getApuestas().add(nuevaApuesta);
        userRepository.save(user);
    }
}