package racingcar.service;

import camp.nextstep.edu.missionutils.Randoms;
import racingcar.domain.RacingCar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GameService {

    private static final int MOVE_NUMBER = 4;

    private List<RacingCar> racingCars;

    public GameService() {
        racingCars = new ArrayList<>();
    }

    public void initializeRacingCars(String carNames) {
        racingCars = Arrays.stream(carNames.split(","))
                .map(name -> new RacingCar(name.trim()))
                .collect(Collectors.toList());
    }

    private boolean canMove() {
        int pickedNumber = Randoms.pickNumberInRange(0, 9);
        return pickedNumber >= MOVE_NUMBER;
    }

    public List<RacingCar> game() {
        racingCars.stream()
                .filter(car -> canMove())
                .forEach(RacingCar::move);

        return new ArrayList<>(racingCars);
    }

    public List<String> findWinner() {
        int maxDistance = racingCars.stream()
                .mapToInt(RacingCar::getDistance)
                .max()
                .orElseThrow();

        return racingCars.stream()
                .filter(car -> car.getDistance() == maxDistance)
                .map(RacingCar::getName)
                .collect(Collectors.toList());
    }
}