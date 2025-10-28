package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.Result;
import lotto.domain.WinningLotto;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottoGame {

    private final InputView input;
    private final OutputView output;
    private WinningLotto winningLotto;

    public LottoGame(InputView input, OutputView output) {
        this.input = input;
        this.output = output;
    }

    public void run(){
        try {
            proceedGame();
        } catch (IllegalArgumentException e) {
            System.out.println("[ERROR] 입력 값이 올바르지 않습니다. 다시 시도해 주세요.");
            run();
        }
    }

    public void proceedGame(){
        String userMoney = inputMoney();
        int ticketCount = convertMoneyToTicketCount(userMoney);
        output.printTicketCount(ticketCount);
        List<Lotto> lottos = generateLottoTicket(ticketCount);
        output.printLottoTicketsResult(lottos);
        winningLotto = makeWinningLotto();
        List<List<Integer>> results = compareLottos(lottos,winningLotto);
        List<Result> resultList = returnListToResult(results);
        output.printResultList(resultCount(resultList));
        printEarningRate(resultCount(resultList),ticketCount);
    }

    private int convertMoneyToTicketCount(String userMoney){
        return Integer.parseInt(userMoney)/1000;
    }
    private List<Integer> covertStringToList(String[] numbers){
        List<Integer> WinningLotto = new ArrayList<>();
        for(String value : numbers){
            WinningLotto.add(Integer.parseInt(value));
        }
        return WinningLotto;
    }

    private String inputMoney(){
        String userMoney;
        try{
            userMoney = input.inputMoney();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return inputMoney();
        }
        return userMoney;
    }

    private String[] inputWinningLotto(){
        String[] lotto;
        try{
            lotto = input.inputWinLottoNumber();
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return inputWinningLotto();
        }
        return lotto;
    }

    private int inputBonusNumber(){
        int bonus;
        try{
            bonus = input.inputBonusNumber();
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return inputBonusNumber();
        }
        return bonus;
    }

    private WinningLotto makeWinningLotto(){
        try{
            String[] winningNumbers = inputWinningLotto();
            int bonus = inputBonusNumber();
            winningLotto = new WinningLotto(covertStringToList(winningNumbers),bonus);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return makeWinningLotto();
        }
        return winningLotto;
    }

    private List<Lotto> generateLottoTicket(int ticketCount){
        List<Lotto> lottos = new ArrayList<>();
        for(int i=0;i<ticketCount;i++){
            List<Integer> lotto = Lotto.generateLotto();
            lottos.add(new Lotto(lotto));
        }
        return lottos;
    }

    private List<List<Integer>> compareLottos(List<Lotto> lottos, WinningLotto winningLotto){
        List<List<Integer>> results = new ArrayList<>();
        for(Lotto lotto:lottos){
            List<Integer> result = new ArrayList<>();
            int countMatchNumber = lotto.countMatchNumber(winningLotto);
            result.add(0,countMatchNumber);
            int countMatchBonusNumber = lotto.checkMatchBonusNumber(winningLotto);
            result.add(1,countMatchBonusNumber);
            results.add(result);
        }
        return results;
    }

    private List<Result> returnListToResult(List<List<Integer>> results){
        List<Result> resultList = new ArrayList<>();
        for(List<Integer> result : results){
            int countMatch = result.get(0);
            int countBonus = result.get(1);
            resultList.add(Result.valueOfMatchCount(countMatch,countBonus));
        }
        return resultList;
    }

    private void printEarningRate(Map<Result, Integer> result, int ticketAmount){
        int totalEarnings = 0;
        for (Map.Entry<Result, Integer> entry : result.entrySet()) {
            Result res = entry.getKey();
            int count = entry.getValue();

            totalEarnings += count * res.getLottoMoney();
        }
        double earningRate = (double) totalEarnings / (ticketAmount*10);
        output.printRate(earningRate);
    }

    private Map<Result, Integer> resultCount(List<Result> resultList){
        Map<Result, Integer> resultCount = new HashMap<>();
        for (Result result : Result.values()) {
            resultCount.put(result, 0);
        }
        for (Result result : resultList) {
            resultCount.put(result, resultCount.get(result) + 1);
        }
        return resultCount;
    }
}
