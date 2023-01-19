package gov.va.vba.rbps.claimprocessor.pension;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Date;


public class IncomeDecisionReturn {
    private int return_code;
    private long vet_id;
    private String message;
    private boolean net_worth_over_limit;
    private Date efctv_date;
    private Date net_worth_limit_date;
    private BigDecimal net_worth_amt;
    private BigDecimal net_worth_limit;
    private boolean net_worth_is_a_bar;
    private  Long[] deps_with_income;

    public IncomeDecisionReturn(
            @JsonProperty("return_code") int return_code,
            @JsonProperty("vet_id") long vet_id,
            @JsonProperty("message") String message,
            @JsonProperty("efctv_date") Date efctv_date,
            @JsonProperty("net_worth_limit_date") Date net_worth_limit_date,
            @JsonProperty("net_worth_over_limit") boolean net_worth_over_limit,
            @JsonProperty("net_worth_amt") BigDecimal net_worth_amt,
            @JsonProperty("net_worth_limit") BigDecimal net_worth_limit,
            @JsonProperty("net_worth_is_a_bar") boolean net_worth_is_a_bar,
            @JsonProperty("deps_with_income") Long[] deps_with_income
    ) {
        this.return_code = return_code;
        this.vet_id = vet_id;
        this.message = message;
        this.efctv_date = efctv_date;
        this.net_worth_limit_date = net_worth_limit_date;
        this.net_worth_over_limit = net_worth_over_limit;
        this.net_worth_amt = net_worth_amt;
        this.net_worth_limit = net_worth_limit;
        this.net_worth_is_a_bar = net_worth_is_a_bar;
        this.deps_with_income = deps_with_income;
    }

    public int getReturnCode()           { return return_code; }
    public long getVetId()               { return vet_id; }
    public String getMessage()           { return message; }
    public Date getEfctvDate()           { return efctv_date; }
    public boolean isNetWorthOverLimit() { return net_worth_over_limit; }
    public BigDecimal getNetWorthAmt()   { return net_worth_amt; }
    public BigDecimal getNetWorthLimit() { return net_worth_limit; }
    public Date getNetWorthLimitDate()   { return net_worth_limit_date; }
    public boolean isNetWorthABar()      { return net_worth_is_a_bar; }
    public Long[] getDepsWithIncome()    { return deps_with_income; }

    public String toString() {
        return
                "================ Income Decision Results ======================= \n" +
                "vet_id:               " + vet_id  + "\n" +
                "return code:          " + return_code + "\n" +
                "vet_id:               " + vet_id  + "\n" +
                "message:              " + message  + "\n" +
                "efctv_date:           " + efctv_date  + "\n" +
                "net_worth_amt:        " + net_worth_amt + "\n" +
                "net_worth_limit_date: " + net_worth_limit_date  + "\n" +
                "net_worth_over_limit: " + net_worth_over_limit + "\n" +
                "net_worth_limit:      " + net_worth_limit + "\n" +
                "net_worth_is_a_bar:   " + net_worth_is_a_bar + "\n" ;
    }
}
