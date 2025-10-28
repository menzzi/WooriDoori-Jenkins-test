package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.Result;

import java.util.List;
import java.util.Map;

public class OutputView {
    public void printTicketCount(int ticketCount){
        System.out.println(ticketCount + "개를 구매했습니다.");
    }

    public void printLottoTicketsResult(List<Lotto> lottos){
        for(Lotto lotto : lottos){
            System.out.println(lotto);
        }
        System.out.println();
    }

    public void printResultList(Map<Result, Integer> resultMap){
        System.out.println("당첨 통계");
        System.out.println("---");
        for (Result result : Result.values()) {
            if (result == Result.MISS) continue;
            System.out.println(result.getMessage() + resultMap.get(result) + "개");
        }
    }

    public void printRate(double rate){
        System.out.printf("총 수익률은 %.1f%%입니다.",rate);
    }
}
