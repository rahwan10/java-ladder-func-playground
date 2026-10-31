package ladder.controller;

import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import ladder.domain.Ladder;
import ladder.domain.LadderGame;
import ladder.domain.LadderSize;
import ladder.domain.Outcome;
import ladder.domain.PlayerName;
import ladder.view.InputView;
import ladder.view.OutputView;

/** 입력, 사다리 실행, 결과 조회의 순서를 조정한다. */
public final class LadderController {

    private final InputView inputView;
    private final OutputView outputView;

    public LadderController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    /** 참가자와 결과를 입력받아 게임을 만들고 결과 조회를 진행한다. */
    public void run(BooleanSupplier shouldConnect) {
        List<PlayerName> names = inputView.readNames();
        List<Outcome> outcomes = inputView.readOutcomes();
        LadderSize size = new LadderSize(names.size(), inputView.readHeight());
        Ladder ladder = Ladder.generate(size, shouldConnect);
        LadderGame game = LadderGame.play(ladder, names, outcomes);
        outputView.printBoard(ladder, names, outcomes);
        readResults(game);
    }

    private void readResults(LadderGame game) {
        boolean shouldContinue = true;
        while (shouldContinue) {
            shouldContinue = handleNextQuery(game);
        }
    }

    private boolean handleNextQuery(LadderGame game) {
        Optional<String> query = inputView.readQuery();
        if (query.isEmpty()) {
            return false;
        }
        if ("all".equalsIgnoreCase(query.get())) {
            outputView.printAll(game.results());
            return false;
        }
        printIndividual(game, query.get());
        return true;
    }

    private void printIndividual(LadderGame game, String query) {
        try {
            outputView.printOne(game.resultFor(new PlayerName(query)));
        } catch (IllegalArgumentException exception) {
            outputView.printError(exception.getMessage());
        }
    }
}
