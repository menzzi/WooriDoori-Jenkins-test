package lotto.validator;

import java.util.HashSet;
import java.util.List;

public class Validator {
    public static void validateInputMoney(String input){
        validateNumberFormat(input);
        validateMoneyUnit(input);
    }
    public static void validateBonusNumber(String input){
        validateNumberFormat(input);
        validateLottoNumberRange(input);
    }
    public static void validateNumberFormat(String input){
        try{
            Integer.parseInt(input);
        }catch(NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 숫자 단위가 아닙니다.");
        }
    }
    public static void validateMoneyUnit(String input){
        int unit = Integer.parseInt(input) % 1000;
        if(unit!=0){
            throw new IllegalArgumentException("[ERROR] 1,000 단위로 입력해주세요.");
        }
    }
    public static void validateLottoNumberRange(String input){
        int number = Integer.parseInt(input);
        if(number < 1 || number > 45){
            throw new IllegalArgumentException("[ERROR] 로또 번호 범위는 1~45 입니다.");
        }
    }
    public static void validateDuplicate(String[] numbers){
        HashSet<Integer> lottoNumbers = new HashSet<>();
        for(String value:numbers){
            lottoNumbers.add(Integer.parseInt(value));
        }
        if(lottoNumbers.size() != numbers.length){
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복되면 안됩니다.");
        }
    }
    public static void validateLength(String[] input) {
        if (input.length != 6) {
            throw new IllegalArgumentException("[ERROR] 6자리를 입력하세요.");
        }
    }
    public static void validateWinningNumber(String[] input){
        validateLength(input);
        for(String value:input){
            validateLottoNumberRange(value);
            validateNumberFormat(value);
        }
        validateDuplicate(input);
    }
}
