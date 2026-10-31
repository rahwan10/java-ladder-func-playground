package ladder;

import java.util.Random;
import ladder.domain.Ladder;
import ladder.domain.LadderSize;
import ladder.view.InputView;
import ladder.view.OutputView;

public final class Application {

    public static void main(String[] args) {
        LadderSize size = new InputView().readSize();
        Random random = new Random();
        Ladder ladder = Ladder.generate(size, random::nextBoolean);
        OutputView outputView = new OutputView();
        outputView.print(ladder);
        outputView.printResults(ladder);
    }
}
