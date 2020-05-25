package cz.vse.selenium.churchcrm.testframework;


import java.time.LocalDateTime;

public class Deposit {

    private Integer id;
    private String depositComment;
    private DepositType depositType;
    private LocalDateTime depositDate;

    public Deposit(Integer id, String depositComment, DepositType depositType, LocalDateTime depositDate) {
        this.id = id;
        this.depositComment = depositComment;
        this.depositType = depositType;
        this.depositDate = depositDate;
    }

    public Integer getId() {
        return id;
    }

    public String getDepositComment() {
        return depositComment;
    }

    public DepositType getDepositType() {
        return depositType;
    }

    public LocalDateTime getDepositDate() {
        return depositDate;
    }

}
