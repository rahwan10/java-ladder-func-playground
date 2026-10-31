package ladder;

import java.util.Random;
import java.util.Scanner;
import ladder.controller.LadderController;
import ladder.view.InputView;
import ladder.view.OutputView;

public final class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView(new Scanner(System.in));
        LadderController controller = new LadderController(inputView, new OutputView());
        controller.run(new Random()::nextBoolean);
    }
}
