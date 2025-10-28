package lotto.view;

import camp.nextstep.edu.missionutils.Console;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputViewTest {
    private final InputView inputView = new InputView();

    @AfterEach
    void closeConsole() {
        Console.close();
    }

    @Test
    void 사용자_금액_입력_실패() {
        final String input = "3500";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertThrows(IllegalArgumentException.class, () -> {
            inputView.inputMoney();
        });
    }
    @Test
    void 사용자_금액_입력_실패2() {
        final String input = "hello";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertThrows(IllegalArgumentException.class, () -> {
            inputView.inputMoney();
        });
    }
    @Test
    void 사용자_금액_입력_성공() {
        final String input = "3000";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> {
            inputView.inputMoney();
        });
    }
    @Test
    void 사용자_당첨번호_입력_실패() {
        final String input = "1,2,3,4,5,46";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertThrows(IllegalArgumentException.class, () -> {
            inputView.inputWinLottoNumber();
        });
    }
    @Test
    void 사용자_당첨번호_입력_실패2() {
        final String input = "hello";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertThrows(IllegalArgumentException.class, () -> {
            inputView.inputWinLottoNumber();
        });
    }
    @Test
    void 사용자_당첨번호_입력_성공() {
        final String input = "1,2,3,4,5,6";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> {
            inputView.inputWinLottoNumber();
        });
    }
    @Test
    void 사용자_보너스번호_입력_실패() {
        final String input = "hello";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertThrows(IllegalArgumentException.class, () -> {
            inputView.inputBonusNumber();
        });
    }
    @Test
    void 사용자_보너스번호_입력_실패2() {
        final String input = "48";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertThrows(IllegalArgumentException.class, () -> {
            inputView.inputBonusNumber();
        });
    }
    @Test
    void 사용자_보너스번호_입력_성공() {
        final String input = "15";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> {
            inputView.inputBonusNumber();
        });
    }
}
