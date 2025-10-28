package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputViewTest {
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private OutputView outputView;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
        outputView = new OutputView();
    }

    @Test
    void 티켓개수_출력_성공() {
        outputView.printTicketCount(5);
        assertEquals("5개를 구매했습니다.\n", outputStream.toString());
    }

    @Test
    void 로또출력_성공() {
        List<Lotto> lottos = new ArrayList<>();
        lottos.add(new Lotto(List.of(1, 2, 3, 4, 5, 6)));
        lottos.add(new Lotto(List.of(7, 8, 9, 10, 11, 12)));

        outputView.printLottoTicketsResult(lottos);
        assertEquals("[1, 2, 3, 4, 5, 6]\n[7, 8, 9, 10, 11, 12]\n", outputStream.toString());
    }

    @Test
    void 결과출력_성공() {
        Map<Result, Integer> resultMap = new HashMap<>();
        resultMap.put(Result.FIRST, 2);
        resultMap.put(Result.SECOND, 0);
        resultMap.put(Result.THIRD, 0);
        resultMap.put(Result.FOURTH, 0);
        resultMap.put(Result.FIFTH, 1);
        resultMap.put(Result.MISS, 2);
        outputView.printResultList(resultMap);

        String expectedOutput = "6개 일치 (2,000,000,000원) - 2개\n" +
                "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개\n" +
                "5개 일치 (1,500,000원) - 0개\n" +
                "4개 일치 (50,000원) - 0개\n" +
                "3개 일치 (5,000원) - 1개\n";

        assertEquals(expectedOutput, outputStream.toString());
    }
}
