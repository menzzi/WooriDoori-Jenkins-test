package lotto.domain;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        validateDuplicate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException();
        }
    }

    private void validateDuplicate(List<Integer> numbers){
        HashSet<Integer> lottoNumbers = new HashSet<>(numbers);
        if(lottoNumbers.size() != numbers.size()){
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복되면 안됩니다.");
        }
    }

    public static List<Integer> generateLotto(){
        List<Integer> lotto = Randoms.pickUniqueNumbersInRange(1, 45, 6);
        Collections.sort(lotto);
        return lotto;
    }

    public int countMatchNumber(WinningLotto winningLotto){
        int matchCount = 0;
        for(Integer number:numbers){
            if (winningLotto.getWinningLotto().contains(number)) {
                matchCount++;
            }
        }
        return matchCount;
    }

    public int checkMatchBonusNumber(WinningLotto winningLotto){
        int countMatch = 0;
        if(numbers.contains(winningLotto.getBonusNumber())){
            countMatch = 1;
        }
        return countMatch;
    }

    @Override
    public String toString() {
        return numbers.toString();
    }
}
