package lotto.domain;

public enum Result {
    FIRST(6,2_000_000_000,"6개 일치 (2,000,000,000원) - "),
    SECOND(5,30_000_000,"5개 일치, 보너스 볼 일치 (30,000,000원) - "),
    THIRD(5,1_500_000,"5개 일치 (1,500,000원) - "),
    FOURTH(4,50_000,"4개 일치 (50,000원) - "),
    FIFTH(3,5_000,"3개 일치 (5,000원) - "),
    MISS(0,0,"");


    Result(int countMatch, int lottoMoney, String message) {
        this.countMatch = countMatch;
        this.lottoMoney = lottoMoney;
        this.message = message;
    }

    private final int countMatch;
    private final int lottoMoney;
    private final String message;

    public static Result valueOfMatchCount(int countMatch, int countBonus) {
        if (countMatch == 6) {
            return FIRST;
        }
        if (countMatch == 5 && countBonus == 1) {
            return SECOND;
        }
        if (countMatch == 5) {
            return THIRD;
        }
        if (countMatch == 4) {
            return FOURTH;
        }
        if (countMatch == 3) {
            return FIFTH;
        }
        return MISS;
    }

    public String getMessage() {
        return message;
    }

    public int getLottoMoney() {
        return lottoMoney;
    }
}
