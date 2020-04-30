package ym.nemo233.bookstore.sqlite;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class ParseRuleStep {
    @Id(autoincrement = true)
    private Long id;
    private Integer type;//层次
    @NotNull
    private Long bsId;
    private Integer step;//步数
    private Integer action;//类型
    private String key;

    @Generated(hash = 855332715)
    @Keep
    public ParseRuleStep(Long id, Integer type, @NotNull Long bsId, Integer step,
                         Integer action, String key) {
        this.id = id;
        this.type = type;
        this.bsId = bsId;
        this.step = step;
        this.action = action;
        this.key = key;
    }

    @Generated(hash = 1235574175)
    @Keep
    public ParseRuleStep() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getBsId() {
        return this.bsId;
    }

    public void setBsId(Long bsId) {
        this.bsId = bsId;
    }

    public Integer getStep() {
        return this.step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getAction() {
        return this.action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
