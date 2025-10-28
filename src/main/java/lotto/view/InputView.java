package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import lotto.validator.Validator;

public class InputView {
    public static String inputMoney(){
        System.out.println("구입금액을 입력해 주세요.");
        String input = Console.readLine();
        Validator.validateInputMoney(input);
        System.out.println();
        return input;
    }
    public static String[] inputWinLottoNumber(){
        System.out.println("당첨 번호를 입력해 주세요.");
        String[] splitInput = Console.readLine().split(",");
        Validator.validateWinningNumber(splitInput);
        System.out.println();
        return splitInput;
    }
    public static int inputBonusNumber(){
        System.out.println("보너스 번호를 입력해 주세요.");
        String input = Console.readLine();
        Validator.validateBonusNumber(input);
        System.out.println();
        return Integer.parseInt(input);
    }
}
